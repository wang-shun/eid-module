package org.aiav.astoopsdk.service.eidservice.biz.hmac;

import net.sf.json.JSONObject;

import org.aiav.astoopsdk.constants.Constant;
import org.aiav.astoopsdk.service.dataprotection.sign.ISignService;
import org.aiav.astoopsdk.service.eidservice.AbstractService;
import org.aiav.astoopsdk.service.eidservice.params.request.biz.hmac.HmacBizParams;
import org.aiav.astoopsdk.service.eidservice.params.result.biz.hmac.HmacBizResult;
import org.aiav.astoopsdk.util.FuncUtil;

public class HmacBizService extends AbstractService {

	public HmacBizService(ISignService signService, String url) {
		super(signService);
		this.url = url;
	}

	public HmacBizResult doRequestSyn(HmacBizParams req) {
		HmacBizResult result = (HmacBizResult) doRequest(req, true);
		return result;
	}

	public JSONObject doRequestAsyn(HmacBizParams req) {
		JSONObject result = (JSONObject) doRequest(req, false);
		return result;
	}

	@Override
	protected String buildReqParameters(Object params) {
		HmacBizParams req = (HmacBizParams) params;
		if (params != null) {
			JSONObject reqJson = req.buildReq();
			reqJson.put(Constant.SIGN, createReqSign(reqJson));
			return reqJson.toString();
		}
		return null;
	}

	@Override
	public HmacBizResult parseResponse(String resStr) {
		JSONObject resJson = parseRes(resStr);
		if (!FuncUtil.isEmpty(resJson)) {
			HmacBizResult result = new HmacBizResult(resJson);
			return result;
		} else {
			return null;
		}
	}

}
