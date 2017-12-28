package org.aiav.astoopsdk.service.eidservice.biz.pki;

import net.sf.json.JSONObject;

import org.aiav.astoopsdk.constants.Constant;
import org.aiav.astoopsdk.service.dataprotection.sign.ISignService;
import org.aiav.astoopsdk.service.eidservice.AbstractService;
import org.aiav.astoopsdk.service.eidservice.params.request.biz.pki.PkiRNVParams;
import org.aiav.astoopsdk.service.eidservice.params.result.biz.pki.PkiRNVResult;
import org.aiav.astoopsdk.util.FuncUtil;

public class PkiRNVService extends AbstractService {

	public PkiRNVService(ISignService signService, String url) {
		super(signService);
		this.url = url;
	}

	public PkiRNVResult doRequestSyn(PkiRNVParams req) {
		PkiRNVResult result = (PkiRNVResult) doRequest(req, true);
		return result;
	}

	public JSONObject doRequestAsyn(PkiRNVParams req) {
		JSONObject result = (JSONObject) doRequest(req, false);
		return result;
	}

	@Override
	protected String buildReqParameters(Object params) {
		PkiRNVParams req = (PkiRNVParams) params;
		if (params != null) {
			JSONObject reqJson = req.buildReq();
			reqJson.put(Constant.SIGN, createReqSign(reqJson));
			return reqJson.toString();
		}
		return null;
	}

	@Override
	public PkiRNVResult parseResponse(String resStr) {
		JSONObject resJson = parseRes(resStr);
		if (!FuncUtil.isEmpty(resJson)) {
			PkiRNVResult result = new PkiRNVResult(resJson);
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
