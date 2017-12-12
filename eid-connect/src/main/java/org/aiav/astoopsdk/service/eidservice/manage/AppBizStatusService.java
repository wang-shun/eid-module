package org.aiav.astoopsdk.service.eidservice.manage;

import net.sf.json.JSONObject;

import org.aiav.astoopsdk.constants.Constant;
import org.aiav.astoopsdk.service.dataprotection.sign.ISignService;
import org.aiav.astoopsdk.service.eidservice.AbstractService;
import org.aiav.astoopsdk.service.eidservice.params.request.manage.AppBizStatusParams;
import org.aiav.astoopsdk.service.eidservice.params.result.manage.AppBizStatusResult;
import org.aiav.astoopsdk.util.FuncUtil;

public class AppBizStatusService extends AbstractService {

	public AppBizStatusService(ISignService signService, String url) {
		super(signService);
		this.url = url;
	}

	public AppBizStatusResult doRequestSyn(AppBizStatusParams req) {
		AppBizStatusResult result = (AppBizStatusResult) doRequest(req, true);
		return result;
	}

	public JSONObject doRequestAsyn(AppBizStatusParams req) {
		JSONObject result = (JSONObject) doRequest(req, false);
		return result;
	}

	@Override
	protected String buildReqParameters(Object params) {
		AppBizStatusParams req = (AppBizStatusParams) params;
		if (params != null) {
			JSONObject reqJson = req.buildReq();
			reqJson.put(Constant.SIGN, createReqSign(reqJson));
			return reqJson.toString();
		}
		return null;
	}

	@Override
	public AppBizStatusResult parseResponse(String resStr) {
		JSONObject resJson = parseRes(resStr);
		if (!FuncUtil.isEmpty(resJson)) {
			AppBizStatusResult result = new AppBizStatusResult(resJson);
			return result;
		} else {
			return null;
		}
	}

}
