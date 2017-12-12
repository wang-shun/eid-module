package org.aiav.astoopsdk.service.dataprotection.sign.impl;

import org.aiav.astoopsdk.service.dataprotection.sign.ISignService;

public class ESignService implements ISignService {
	private String opid;
	private String keyFactor;

	public ESignService(String opid, String keyFactor) {
		this.opid = opid;
		this.keyFactor = keyFactor;
	}

	@Override
	public String createSign(String strToSign, String encryptFactor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean verifySign(String sign, String strToSign,
			String encryptFactor) {
		// TODO Auto-generated method stub
		return false;
	}

}
