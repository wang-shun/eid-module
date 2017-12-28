package org.aiav.astoopsdk.service.eidservice.biz.pki;

import net.sf.json.JSONObject;

import org.aiav.astoopsdk.constants.Constant;
import org.aiav.astoopsdk.service.dataprotection.sign.ISignService;
import org.aiav.astoopsdk.service.eidservice.AbstractService;
import org.aiav.astoopsdk.service.eidservice.params.request.biz.pki.PkiBizDirectLoginParams;
import org.aiav.astoopsdk.service.eidservice.params.result.biz.pki.PkiBizDirectLoginResult;
import org.aiav.astoopsdk.util.FuncUtil;

public class PkiBizDirectLoginService extends AbstractService {
	@Override
	protected JSONObject parseResponseJson(String resStr) {
		return null;
	}

	public PkiBizDirectLoginService(ISignService signService, String url) {
		super(signService);
		this.url = url;
	}

	public PkiBizDirectLoginResult doRequestSyn(PkiBizDirectLoginParams req) {
		PkiBizDirectLoginResult result = (PkiBizDirectLoginResult) doRequest(
				req, true);
		return result;
	}

	public JSONObject doRequestAsyn(PkiBizDirectLoginParams req) {
		JSONObject result = (JSONObject) doRequest(req, false);
		return result;
	}

	@Override
	protected String buildReqParameters(Object params) {
		PkiBizDirectLoginParams req = (PkiBizDirectLoginParams) params;
		if (params != null) {
			JSONObject reqJson = req.buildReq();
			reqJson.put(Constant.SIGN, createReqSign(reqJson));
			return reqJson.toString();
		}
		return null;
	}

	@Override
	public PkiBizDirectLoginResult parseResponse(String resStr) {
		JSONObject resJson = parseRes(resStr);
		if (!FuncUtil.isEmpty(resJson)) {
			PkiBizDirectLoginResult result = new PkiBizDirectLoginResult(
					resJson);
			return result;
		} else {
			return null;
		}
	}

}
