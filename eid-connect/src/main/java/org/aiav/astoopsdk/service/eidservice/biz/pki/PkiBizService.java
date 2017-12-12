package org.aiav.astoopsdk.service.eidservice.biz.pki;

import net.sf.json.JSONObject;

import org.aiav.astoopsdk.constants.Constant;
import org.aiav.astoopsdk.service.dataprotection.sign.ISignService;
import org.aiav.astoopsdk.service.eidservice.AbstractService;
import org.aiav.astoopsdk.service.eidservice.params.request.biz.pki.PkiBizParams;
import org.aiav.astoopsdk.service.eidservice.params.result.biz.pki.PkiBizResult;
import org.aiav.astoopsdk.util.FuncUtil;

public class PkiBizService extends AbstractService {

	public PkiBizService(ISignService signService, String url) {
		super(signService);
		this.url = url;
	}

	public PkiBizResult doRequestSyn(PkiBizParams req) {
		PkiBizResult result = (PkiBizResult) doRequest(req, true);
		return result;
	}

	public JSONObject doRequestAsyn(PkiBizParams req) {
		JSONObject result = (JSONObject) doRequest(req, false);
		return result;
	}

	@Override
	protected String buildReqParameters(Object params) {
		PkiBizParams req = (PkiBizParams) params;
		if (params != null) {
			JSONObject reqJson = req.buildReq();
			reqJson.put(Constant.SIGN, createReqSign(reqJson));
			return reqJson.toString();
		}
		return null;
	}

	@Override
	public PkiBizResult parseResponse(String resStr) {
		JSONObject resJson = parseRes(resStr);
		if (!FuncUtil.isEmpty(resJson)) {
			PkiBizResult result = new PkiBizResult(resJson);
			return result;
		} else {
			return null;
		}
	}

}
