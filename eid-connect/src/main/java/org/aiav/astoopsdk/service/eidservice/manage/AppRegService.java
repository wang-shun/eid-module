package org.aiav.astoopsdk.service.eidservice.manage;

import net.sf.json.JSONObject;

import org.aiav.astoopsdk.constants.Constant;
import org.aiav.astoopsdk.service.dataprotection.sign.ISignService;
import org.aiav.astoopsdk.service.eidservice.AbstractService;
import org.aiav.astoopsdk.service.eidservice.params.request.manage.AppRegParams;
import org.aiav.astoopsdk.service.eidservice.params.result.manage.AppRegResult;
import org.aiav.astoopsdk.util.FuncUtil;

public class AppRegService extends AbstractService {

	public AppRegService(ISignService signService, String url) {
		super(signService);
		this.url = url;
	}

	public AppRegResult doRequestSyn(AppRegParams req) {
		AppRegResult result = (AppRegResult) doRequest(req, true);
		return result;
	}

	public JSONObject doRequestAsyn(AppRegParams req) {
		JSONObject result = (JSONObject) doRequest(req, false);
		return result;
	}

	@Override
	protected String buildReqParameters(Object params) {
		AppRegParams req = (AppRegParams) params;
		if (params != null) {
			JSONObject reqJson = req.buildReq();
			reqJson.put(Constant.SIGN, createReqSign(reqJson));
			return reqJson.toString();
		}
		return null;
	}

	@Override
	public AppRegResult parseResponse(String resStr) {
		JSONObject resJson = parseRes(resStr);

		if (!FuncUtil.isEmpty(resJson)) {
			AppRegResult result = new AppRegResult(resJson);
			return result;
		} else {
			return null;
		}
	}

	@Override
	public JSONObject parseResponseJson(String resStr) {

		JSONObject resJson = parseRes(resStr);

		if (!FuncUtil.isEmpty(resJson)) {
			return resJson;
		} else {
			return null;
		}
	}

}
