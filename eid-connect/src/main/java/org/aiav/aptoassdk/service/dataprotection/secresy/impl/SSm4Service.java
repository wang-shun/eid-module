package org.aiav.aptoassdk.service.dataprotection.secresy.impl;

import org.aiav.aptoassdk.service.dataprotection.SBaseCryptoService;
import org.aiav.aptoassdk.service.dataprotection.secresy.IEncryptService;
import org.aiav.aptoassdk.util.FuncUtil;
import org.aiav.crypto.EidCryptoUtils;
import org.aiav.crypto.util.CryptoFuncUtil;
import org.apache.log4j.Logger;

public class SSm4Service extends SBaseCryptoService implements IEncryptService {
	private static final Logger log = Logger.getLogger(SSm4Service.class);

	public SSm4Service(String key) {
		super(key);
	}

	@Override
	public String doEncrypt(String strToEncrypt, String encryptFactor) {
		log.debug("strToEncrypt:" + strToEncrypt + ";encryptFactor:"
				+ encryptFactor + ";key:" + this.getKey());
		String encryptStr = null;
		byte[] result = EidCryptoUtils.encryptSM4ECBPKCS5(
				CryptoFuncUtil.getStringBytes(strToEncrypt),
				buildSM4Key(encryptFactor));
		if (!FuncUtil.isEmpty(result)) {
			encryptStr = CryptoFuncUtil.encodeBytesToBase64(result);
		}

		log.debug("encryptStr:" + encryptStr);
		return encryptStr;
	}

	@Override
	public String doDecrypt(String strToDecrypt, String encryptFactor) {
		log.debug("strToDecrypt:" + strToDecrypt + ";encryptFactor:"
				+ encryptFactor + ";key:" + this.getKey());
		String decryptStr = null;
		byte[] result = EidCryptoUtils.decryptSM4ECBPKCS5(
				CryptoFuncUtil.decodeBase64ToBytes(strToDecrypt),
				buildSM4Key(encryptFactor));
		if (!FuncUtil.isEmpty(result)) {
			decryptStr = CryptoFuncUtil.getBytesToString(result);
		}

		log.debug("decryptStr:" + decryptStr);
		return decryptStr;
	}
}
