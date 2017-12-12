package org.aiav.astoopsdk.service.eidservice.params.result.biz;

import net.sf.json.JSONObject;

import org.aiav.astoopsdk.constants.Constant;
import org.aiav.astoopsdk.service.eidservice.params.result.BaseResult;
import org.aiav.astoopsdk.util.FuncUtil;

@SuppressWarnings("serial")
public class BizBaseResult extends BaseResult {
	private String userInfo;
	private String encryptType;
	private String encryptFactor;

	public BizBaseResult(JSONObject resultJson) {
		super(resultJson);
		if (!FuncUtil.isEmpty(resultJson.get(Constant.USER_INFO))) {
			this.userInfo = resultJson.getString(Constant.USER_INFO);
			this.encryptType = resultJson.getString(Constant.ENCRYPT_TYPE);
			this.encryptFactor = resultJson.getJSONObject(
					Constant.SECURITY_FACTOR)
					.getString(Constant.ENCRYPT_FACTOR);
		}
	}

	public String getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(String userInfo) {
		this.userInfo = userInfo;
	}

	public String getEncryptType() {
		return encryptType;
	}

	public void setEncryptType(String encryptType) {
		this.encryptType = encryptType;
	}

	public String getEncryptFactor() {
		return encryptFactor;
	}

	public void setEncryptFactor(String encryptFactor) {
		this.encryptFactor = encryptFactor;
	}

}
