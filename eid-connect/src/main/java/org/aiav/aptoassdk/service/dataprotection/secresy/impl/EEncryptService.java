package org.aiav.aptoassdk.service.dataprotection.secresy.impl;

import org.aiav.aptoassdk.service.dataprotection.secresy.IEncryptService;

public class EEncryptService implements IEncryptService {
	private String opid;
	private String keyFactor;

	public EEncryptService(String opid, String keyFactor) {
		this.opid = opid;
		this.keyFactor = keyFactor;
	}

	@Override
	public String doEncrypt(String strToEncrypt, String encryptFactor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String doDecrypt(String strToDecrypt, String encryptFactor) {
		// TODO Auto-generated method stub
		return null;
	}

}
