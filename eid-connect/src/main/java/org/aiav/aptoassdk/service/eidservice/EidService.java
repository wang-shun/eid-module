package org.aiav.aptoassdk.service.eidservice;

import net.sf.json.JSONObject;

import org.aiav.aptoassdk.constants.Constant;
import org.aiav.aptoassdk.service.dataprotection.secresy.IEncryptService;
import org.aiav.aptoassdk.service.dataprotection.sign.ISignService;
import org.aiav.aptoassdk.util.FuncUtil;
import org.aiav.aptoassdk.util.ServiceUtil;

public class EidService {
	private ISignService signService;
	private IEncryptService encryptService;

	public EidService(ISignService signService, IEncryptService encryptService) {
		this.signService = signService;
		this.encryptService = encryptService;
	}

	public String createReqSign(JSONObject data) {
		JSONObject securityFactor = data
				.getJSONObject(Constant.SECURITY_FACTOR);

		String sign = signService.createSign(ServiceUtil.createStrToSign(data),
				securityFactor.getString(Constant.SIGN_FACTOR));
		if (FuncUtil.isEmpty(sign)) {
			throw new IllegalArgumentException();
		}
		return sign;
	}

	public boolean verifyResSign(JSONObject resultJson) {
		boolean isSignValid = false;
		if (FuncUtil.isEmpty(resultJson.get(Constant.SIGN))) {
			throw new RuntimeException("sign is null with result ["
					+ resultJson.getString(Constant.RESULT_DETAIL) + "]");
		}

		isSignValid = signService.verifySign(
				resultJson.getString(Constant.SIGN),
				ServiceUtil.createStrToSign(resultJson),
				resultJson.getJSONObject(Constant.SECURITY_FACTOR).getString(
						Constant.SIGN_FACTOR));

		if (!isSignValid) {
			throw new RuntimeException("sign verify failed");
		}
		return isSignValid;
	}

	public JSONObject parseRes(String resultStr) {
		System.out.println("resultStr:" + resultStr);
		if (FuncUtil.isEmpty(resultStr)) {
			throw new RuntimeException("response:is null");
		}

		JSONObject resultJson = JSONObject.fromObject(resultStr);
		verifyResSign(resultJson);
		resultJson.put(Constant.USER_INFO, decryptUserInfoInRes(resultJson));
		return resultJson;

	}

	public String doEncrypt(String encryptStr, String encryptFactor) {
		String result = encryptService.doEncrypt(encryptStr, encryptFactor);
		if (FuncUtil.isEmpty(result)) {
			throw new IllegalArgumentException();
		}
		return result;
	}

	public String doDecrypt(String decryptStr, String encrytpFactor) {

		String result = encryptService.doDecrypt(decryptStr, encrytpFactor);
		if (FuncUtil.isEmpty(result)) {
			throw new IllegalArgumentException();
		}
		return result;
	}

	private JSONObject decryptUserInfoInRes(JSONObject resultJson) {
		if (!FuncUtil.isEmpty(resultJson.get(Constant.USER_INFO))) {
			JSONObject userInfo = JSONObject.fromObject(doDecrypt(resultJson
					.getString(Constant.USER_INFO),
					resultJson.getJSONObject(Constant.SECURITY_FACTOR)
							.getString(Constant.ENCRYPT_FACTOR)));
			if (FuncUtil.isEmpty(userInfo)) {
				throw new RuntimeException("decrypt user_info error");
			}
			return userInfo;
		}
		return null;
	}
}
