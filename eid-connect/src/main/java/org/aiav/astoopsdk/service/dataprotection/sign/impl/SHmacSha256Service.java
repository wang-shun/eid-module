package org.aiav.astoopsdk.service.dataprotection.sign.impl;

import com.as.dev.DeviceService;
import com.eid.dev.constant.EDeviceSignType;
import com.eid.dev.constant.RC;
import com.eid.dev.pojo.sign.rk2.AskeySignParameters;
import com.eid.dev.pojo.sign.rk2.AskeySignResult;
import org.aiav.astoopsdk.service.dataprotection.SBaseCryptoService;
import org.aiav.astoopsdk.service.dataprotection.sign.ISignService;
import org.aiav.astoopsdk.util.EncryptionMachine;
import org.aiav.astoopsdk.util.FuncUtil;
import org.aiav.crypto.EidCryptoUtils;
import org.aiav.crypto.util.CryptoFuncUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class SHmacSha256Service extends SBaseCryptoService implements
		ISignService {
	private static final Logger log = Logger
			.getLogger(SHmacSha256Service.class);

	public SHmacSha256Service(String key) {
		super(key);
	}

//	@Override
//	public String createSign(String strToSign, String signFactor) {
//
//		log.debug("strToSign:" + strToSign + ";signFactor:" + signFactor
//				+ ";key:" + this.getKey());
//		String sign = null;
//		byte[] result = EidCryptoUtils.createHMACSHA256(
//				CryptoFuncUtil.getStringBytes(strToSign),
//				buildSM4Key(signFactor));
//		if (!FuncUtil.isEmpty(result)) {
//			sign = CryptoFuncUtil.encodeBytesToBase64(result);
//		}
//
//		log.debug("sign:" + sign);
//		return sign;
//	}

	//	/**
//	 * 	自建非加密机模式
//	 *
//	 * @param sign
//	 * @param strToSign
//	 * @param signFactor
//	 * @return
//	 */
//	@Override
//	public boolean verifySign(String sign, String strToSign, String signFactor) {
//		log.debug("sign:" + sign + ";strToSign:" + strToSign + ";signFactor:"
//				+ signFactor + ";key:" + this.getKey());
//
//		return EidCryptoUtils.verifyHMACSHA256(
//				CryptoFuncUtil.getStringBytes(strToSign),
//				CryptoFuncUtil.decodeBase64ToBytes(sign),
//				buildSM4Key(signFactor));
//	}

	@Override
	public String createSign(String strToSign, String signFactor) {

		log.debug("strToSign:" + strToSign + ";signFactor:" + signFactor
				+ ";key:" + this.getKey());

		try
		{
			String sign = new EncryptionMachine().asToOpSign(strToSign,signFactor,this.getKey(),EDeviceSignType.HMAC_SHA256);

			log.debug("加密机MD5签名值sign:" + sign);

			return sign;

		} catch (Exception e)
		{
		    e.printStackTrace();
		}
		return null;
	}

	/**
	 * 	自建加密机模式，验签
	 *
	 * @param sign			签名值
	 * @param strToSign		待签原文
	 * @param signFactor	签名因子
	 * @return
	 */
	@Override
	public boolean verifySign(String sign, String strToSign, String signFactor) {
		log.debug("sign:" + sign + ";strToSign:" + strToSign + ";signFactor:"
				+ signFactor + ";key:" + this.getKey());

		try
		{
			String newSign = new EncryptionMachine().asToOpSign(strToSign,signFactor,this.getKey(),EDeviceSignType.HMAC_SHA256);
			if(!StringUtils.isBlank(newSign) && sign.equals(newSign))
				return true;

		} catch (Exception e)
		{
		    e.printStackTrace();
		}
		return false;
	}

}
