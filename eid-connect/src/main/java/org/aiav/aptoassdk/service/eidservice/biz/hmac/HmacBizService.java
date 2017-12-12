package org.aiav.aptoassdk.service.eidservice.biz.hmac;

import net.sf.json.JSONObject;

import org.aiav.aptoassdk.constants.Constant;
import org.aiav.aptoassdk.service.dataprotection.secresy.IEncryptService;
import org.aiav.aptoassdk.service.dataprotection.sign.ISignService;
import org.aiav.aptoassdk.service.eidservice.AbstractService;
import org.aiav.aptoassdk.service.eidservice.params.request.biz.hmac.HmacBizParams;
import org.aiav.aptoassdk.service.eidservice.params.result.biz.hmac.HmacBizResult;
import org.aiav.aptoassdk.util.FuncUtil;

public class HmacBizService extends AbstractService {

	public HmacBizService(ISignService signService,
			IEncryptService encryptService, String url) {
		super(signService, encryptService);
		this.url = url;
	}

	public String doRequestSyn(HmacBizParams req) {
		return doRequest(req, true);
	}

	public String doRequestAsyn(HmacBizParams req) {
		return doRequest(req, false);
	}

	public HmacBizResult doRequestSynAP(HmacBizParams req) {
		HmacBizResult result = (HmacBizResult) doRequestAP(req, true);
		return result;
	}

	@Override
	protected String buildReqParameters(Object params) {
		HmacBizParams req = (HmacBizParams) params;
		if (params != null) {
			JSONObject reqJson = req.buildReq();
			String dataToSign = FuncUtil.isEmpty(reqJson
					.get(Constant.DATA_TO_SIGN)) ? null : doEncrypt(
					reqJson.getString(Constant.DATA_TO_SIGN),
					req.getEncryptFactor());
			reqJson.put(Constant.DATA_TO_SIGN, dataToSign);
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
