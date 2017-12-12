package org.aiav.astoopsdk.service.eidservice.biz.hmac;

import net.sf.json.JSONObject;

import org.aiav.astoopsdk.constants.Constant;
import org.aiav.astoopsdk.service.dataprotection.sign.ISignService;
import org.aiav.astoopsdk.service.eidservice.AbstractService;
import org.aiav.astoopsdk.service.eidservice.params.request.biz.hmac.HmacBizDirectLoginParams;
import org.aiav.astoopsdk.service.eidservice.params.result.biz.hmac.HmacBizDirectLoginResult;
import org.aiav.astoopsdk.util.FuncUtil;

public class HmacBizDirectLoginService extends AbstractService {

	public HmacBizDirectLoginService(ISignService signService, String url) {
		super(signService);
		this.url = url;
	}

	public HmacBizDirectLoginResult doRequestSyn(HmacBizDirectLoginParams req) {
		HmacBizDirectLoginResult result = (HmacBizDirectLoginResult) doRequest(
				req, true);
		return result;
	}

	public JSONObject doRequestAsyn(HmacBizDirectLoginParams req) {
		JSONObject result = (JSONObject) doRequest(req, false);
		return result;
	}

	@Override
	protected String buildReqParameters(Object params) {
		HmacBizDirectLoginParams req = (HmacBizDirectLoginParams) params;
		if (params != null) {
			JSONObject reqJson = req.buildReq();
			reqJson.put(Constant.SIGN, createReqSign(reqJson));
			return reqJson.toString();
		}
		return null;
	}

	@Override
	public HmacBizDirectLoginResult parseResponse(String resStr) {
		JSONObject resJson = parseRes(resStr);
		if (!FuncUtil.isEmpty(resJson)) {
			HmacBizDirectLoginResult result = new HmacBizDirectLoginResult(
					resJson);
			return result;
		} else {
			return null;
		}
	}

}
