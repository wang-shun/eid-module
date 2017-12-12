package org.aiav.astoopsdk.service.eidservice.manage;

import net.sf.json.JSONObject;

import org.aiav.astoopsdk.constants.Constant;
import org.aiav.astoopsdk.service.dataprotection.sign.ISignService;
import org.aiav.astoopsdk.service.eidservice.AbstractService;
import org.aiav.astoopsdk.service.eidservice.params.request.manage.AppkeyUpdateParams;
import org.aiav.astoopsdk.service.eidservice.params.result.manage.AppkeyUpdateResult;
import org.aiav.astoopsdk.util.FuncUtil;

public class AppkeyUpdateService extends AbstractService {

	public AppkeyUpdateService(ISignService signService, String url) {
		super(signService);
		this.url = url;
	}

	public AppkeyUpdateResult doRequestSyn(AppkeyUpdateParams req) {
		AppkeyUpdateResult result = (AppkeyUpdateResult) doRequest(req, true);
		return result;
	}

	public JSONObject doRequestAsyn(AppkeyUpdateParams req) {
		JSONObject result = (JSONObject) doRequest(req, false);
		return result;
	}

	@Override
	protected String buildReqParameters(Object params) {
		AppkeyUpdateParams req = (AppkeyUpdateParams) params;
		if (params != null) {
			JSONObject reqJson = req.buildReq();
			reqJson.put(Constant.SIGN, createReqSign(reqJson));
			return reqJson.toString();
		}
		return null;
	}

	@Override
	public AppkeyUpdateResult parseResponse(String resStr) {
		JSONObject resJson = parseRes(resStr);
		if (!FuncUtil.isEmpty(resJson)) {
			AppkeyUpdateResult result = new AppkeyUpdateResult(resJson);
			return result;
		} else {
			return null;
		}
	}

}
