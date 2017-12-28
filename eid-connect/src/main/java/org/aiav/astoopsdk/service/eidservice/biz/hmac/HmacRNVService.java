package org.aiav.astoopsdk.service.eidservice.biz.hmac;

import net.sf.json.JSONObject;

import org.aiav.astoopsdk.constants.Constant;
import org.aiav.astoopsdk.service.dataprotection.sign.ISignService;
import org.aiav.astoopsdk.service.eidservice.AbstractService;
import org.aiav.astoopsdk.service.eidservice.params.request.biz.hmac.HmacRNVParams;
import org.aiav.astoopsdk.service.eidservice.params.result.biz.hmac.HmacRNVResult;
import org.aiav.astoopsdk.util.FuncUtil;

public class HmacRNVService extends AbstractService {

	public HmacRNVService(ISignService signService, String url) {
		super(signService);
		this.url = url;
	}

	public HmacRNVResult doRequestSyn(HmacRNVParams req) {
		HmacRNVResult result = (HmacRNVResult) doRequest(req, true);
		return result;
	}

	public JSONObject doRequestAsyn(HmacRNVParams req) {
		JSONObject result = (JSONObject) doRequest(req, false);
		return result;
	}

	@Override
	protected String buildReqParameters(Object params) {
		HmacRNVParams req = (HmacRNVParams) params;
		if (params != null) {
			JSONObject reqJson = req.buildReq();
			reqJson.put(Constant.SIGN, createReqSign(reqJson));
			return reqJson.toString();
		}
		return null;
	}

	@Override
	public HmacRNVResult parseResponse(String resStr) {
		JSONObject resJson = parseRes(resStr);
		if (!FuncUtil.isEmpty(resJson)) {
			HmacRNVResult result = new HmacRNVResult(resJson);
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
