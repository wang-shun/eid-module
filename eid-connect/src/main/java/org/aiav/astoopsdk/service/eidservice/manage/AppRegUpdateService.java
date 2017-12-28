package org.aiav.astoopsdk.service.eidservice.manage;

import net.sf.json.JSONObject;

import org.aiav.astoopsdk.constants.Constant;
import org.aiav.astoopsdk.service.dataprotection.sign.ISignService;
import org.aiav.astoopsdk.service.eidservice.AbstractService;
import org.aiav.astoopsdk.service.eidservice.params.request.manage.AppRegUpdateParams;
import org.aiav.astoopsdk.service.eidservice.params.result.manage.AppRegUpdateResult;
import org.aiav.astoopsdk.util.FuncUtil;

public class AppRegUpdateService extends AbstractService {

	public AppRegUpdateService(ISignService signService, String url) {
		super(signService);
		this.url = url;
	}

	public AppRegUpdateResult doRequestSyn(AppRegUpdateParams req) {
		AppRegUpdateResult result = (AppRegUpdateResult) doRequest(req, true);
		return result;
	}

	public JSONObject doRequestAsyn(AppRegUpdateParams req) {
		JSONObject result = (JSONObject) doRequest(req, false);
		return result;
	}

	@Override
	protected String buildReqParameters(Object params) {
		AppRegUpdateParams req = (AppRegUpdateParams) params;
		if (params != null) {
			JSONObject reqJson = req.buildReq();
			reqJson.put(Constant.SIGN, createReqSign(reqJson));
			return reqJson.toString();
		}
		return null;
	}

	@Override
	public AppRegUpdateResult parseResponse(String resStr) {
		JSONObject resJson = parseRes(resStr);
		if (!FuncUtil.isEmpty(resJson)) {
			AppRegUpdateResult result = new AppRegUpdateResult(resJson);
			return result;
		} else {
			return null;
		}
	}

	@Override
	protected JSONObject parseResponseJson(String resStr) {
		return null;
	}

}
