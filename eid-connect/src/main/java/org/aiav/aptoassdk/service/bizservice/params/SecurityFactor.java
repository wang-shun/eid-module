package org.aiav.aptoassdk.service.bizservice.params;

import net.sf.json.JSONObject;

import org.aiav.aptoassdk.constants.Constant;
import org.aiav.aptoassdk.util.FuncUtil;

public class SecurityFactor {
	private String encryptFactor;
	private String signFactor;

	public SecurityFactor(String encryptFactor, String signFactor) {
		this.signFactor = signFactor;
		this.encryptFactor = encryptFactor;
	}

	public String getEncryptFactor() {
		return encryptFactor;
	}

	public void setEncryptFactor(String encryptFactor) {
		this.encryptFactor = encryptFactor;
	}

	public String getSignFactor() {
		return signFactor;
	}

	public void setSignFactor(String signFactor) {
		this.signFactor = signFactor;
	}

	public JSONObject toJson() {
		JSONObject json = new JSONObject();
		if (!FuncUtil.isEmpty(encryptFactor)) {
			json.put(Constant.ENCRYPT_FACTOR, encryptFactor);
		}
		json.put(Constant.SIGN_FACTOR, signFactor);
		return json;
	}
}
