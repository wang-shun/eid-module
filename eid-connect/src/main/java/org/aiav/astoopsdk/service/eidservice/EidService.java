package org.aiav.astoopsdk.service.eidservice;

import net.sf.json.JSONObject;

import org.aiav.astoopsdk.constants.Constant;
import org.aiav.astoopsdk.service.dataprotection.sign.ISignService;
import org.aiav.astoopsdk.util.FuncUtil;
import org.aiav.astoopsdk.util.ServiceUtil;

public class EidService {
	private ISignService signService;

	public EidService(ISignService signService) {
		this.signService = signService;
	}

	/**
	 * as到op请求报文签名
	 * @param data	待签原文
	 * @return sign
	 */
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
		if (FuncUtil.isEmpty(resultStr)) {
			throw new RuntimeException("result:is null");
		}

		JSONObject resultJson = JSONObject.fromObject(resultStr);
		verifyResSign(resultJson);

		return resultJson;

	}
}
