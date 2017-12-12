package org.aiav.astoopsdk.service.dataprotection.sign.impl;

import org.aiav.astoopsdk.service.dataprotection.SBaseCryptoService;
import org.aiav.astoopsdk.service.dataprotection.sign.ISignService;
import org.aiav.astoopsdk.util.FuncUtil;
import org.aiav.crypto.EidCryptoUtils;
import org.aiav.crypto.util.CryptoFuncUtil;
import org.apache.log4j.Logger;

public class SHmacSha256Service extends SBaseCryptoService implements
		ISignService {
	private static final Logger log = Logger
			.getLogger(SHmacSha256Service.class);

	public SHmacSha256Service(String key) {
		super(key);
	}

	@Override
	public String createSign(String strToSign, String signFactor) {
		log.debug("strToSign:" + strToSign + ";signFactor:" + signFactor
				+ ";key:" + this.getKey());
		String sign = null;
		byte[] result = EidCryptoUtils.createHMACSHA256(
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

		return EidCryptoUtils.verifyHMACSHA256(
				CryptoFuncUtil.getStringBytes(strToSign),
				CryptoFuncUtil.decodeBase64ToBytes(sign),
				buildSM4Key(signFactor));
	}

}
