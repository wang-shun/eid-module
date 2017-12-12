package org.aiav.astoopsdk.service.dataprotection;

import org.aiav.crypto.EidCryptoUtils;
import org.aiav.crypto.util.CryptoFuncUtil;

public class SBaseCryptoService {
	private String key;

	public SBaseCryptoService(String key) {
		this.key = key;
	}

	protected byte[] buildSM4Key(String securityFactor) {
		return EidCryptoUtils.encryptSM4ECB(rebuildFactor(securityFactor),
				CryptoFuncUtil.hexStringToBytes(key));
	}

	protected byte[] rebuildFactor(String securityFactor) {
		byte[] factorHex = CryptoFuncUtil.hexStringToBytes(securityFactor);
		byte[] ret = new byte[16];
		for (int i = 0; i < factorHex.length; i++) {
			ret[i] = factorHex[i];
			ret[i + factorHex.length] = (byte) (factorHex[i] ^ 0xFFFFFFFF);
		}
		return ret;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
