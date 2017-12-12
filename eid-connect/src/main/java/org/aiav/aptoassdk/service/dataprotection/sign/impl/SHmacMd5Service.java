package org.aiav.aptoassdk.service.dataprotection.sign.impl;

import org.aiav.aptoassdk.service.dataprotection.SBaseCryptoService;
import org.aiav.aptoassdk.service.dataprotection.sign.ISignService;
import org.aiav.aptoassdk.util.FuncUtil;
import org.aiav.crypto.EidCryptoUtils;
import org.aiav.crypto.util.CryptoFuncUtil;
import org.apache.log4j.Logger;

public class SHmacMd5Service extends SBaseCryptoService implements ISignService {
	private static final Logger log = Logger.getLogger(SHmacMd5Service.class);

	public SHmacMd5Service(String key) {
		super(key);
	}

	@Override
	public String createSign(String strToSign, String signFactor) {
		log.debug("strToSign:" + strToSign + ";signFactor:" + signFactor
				+ ";key:" + this.getKey());
		String sign = null;
		byte[] result = EidCryptoUtils.createHMACMD5(
				CryptoFuncUtil.getStringBytes(strToSign),
				buildSM4Key(signFactor));
		if (!FuncUtil.isEmpty(result)) {
			sign = CryptoFuncUtil.encodeBytesToBase64(result);
		}

		log.debug("sign:" + sign);
		return sign;
	}

	@Override
	public boolean verifySign(String sign, String strToSign, String signFactor) {
		log.debug("sign:" + sign + ";strToSign:" + strToSign + ";signFactor:"
				+ signFactor + ";key:" + this.getKey());

		return EidCryptoUtils.verifyHMACMD5(
				CryptoFuncUtil.getStringBytes(strToSign),
				CryptoFuncUtil.decodeBase64ToBytes(sign),
				buildSM4Key(signFactor));
	}

}
