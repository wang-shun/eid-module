package org.aiav.aptoassdk.service.eidservice.biz.appeidcode;

import net.sf.json.JSONObject;

import org.aiav.aptoassdk.constants.Constant;
import org.aiav.aptoassdk.service.dataprotection.secresy.IEncryptService;
import org.aiav.aptoassdk.service.dataprotection.sign.ISignService;
import org.aiav.aptoassdk.service.eidservice.AbstractService;
import org.aiav.aptoassdk.service.eidservice.params.request.biz.appeidcode.AppeidcodeCreateParams;
import org.aiav.aptoassdk.service.eidservice.params.result.biz.appeidcode.AppeidcodeCreateResult;
import org.aiav.aptoassdk.util.FuncUtil;

public class AppeidcodeCreateService extends AbstractService {

	public AppeidcodeCreateService(ISignService signService,
			IEncryptService encryptService, String url) {
		super(signService, encryptService);
		this.url = url;
	}

	public String doRequestSyn(AppeidcodeCreateParams req) {
		return doRequest(req, true);
	}

	public String doRequestAsyn(AppeidcodeCreateParams req) {
		return doRequest(req, false);
	}

	@Override
	protected String buildReqParameters(Object params) {
		AppeidcodeCreateParams req = (AppeidcodeCreateParams) params;
		if (params != null) {
			JSONObject reqJson = req.buildReq();
			String userIdInfo = FuncUtil.isEmpty(reqJson
					.get(Constant.USER_ID_INFO)) ? null : doEncrypt(
					reqJson.getString(Constant.USER_ID_INFO),
					req.getEncryptFactor());
			reqJson.put(Constant.USER_ID_INFO, userIdInfo);
			reqJson.put(Constant.SIGN, createReqSign(reqJson));
			return reqJson.toString();
		}
		return null;
	}

	@Override
	public AppeidcodeCreateResult parseResponse(String resStr) {
		JSONObject resJson = parseRes(resStr);
		if (!FuncUtil.isEmpty(resJson)) {
			AppeidcodeCreateResult result = new AppeidcodeCreateResult(resJson);
			return result;
		} else {
			return null;
		}
	}

}
