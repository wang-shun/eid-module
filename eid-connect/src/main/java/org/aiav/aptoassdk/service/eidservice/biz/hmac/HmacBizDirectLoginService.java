package org.aiav.aptoassdk.service.eidservice.biz.hmac;

import net.sf.json.JSONObject;

import org.aiav.aptoassdk.constants.Constant;
import org.aiav.aptoassdk.service.dataprotection.secresy.IEncryptService;
import org.aiav.aptoassdk.service.dataprotection.sign.ISignService;
import org.aiav.aptoassdk.service.eidservice.AbstractService;
import org.aiav.aptoassdk.service.eidservice.params.request.biz.hmac.HmacBizDirectLoginParams;
import org.aiav.aptoassdk.service.eidservice.params.result.biz.hmac.HmacBizDirectLoginResult;
import org.aiav.aptoassdk.util.FuncUtil;

public class HmacBizDirectLoginService extends AbstractService {

	public HmacBizDirectLoginService(ISignService signService,
			IEncryptService encryptService, String url) {
		super(signService, encryptService);
		this.url = url;
	}

	public String doRequestSyn(HmacBizDirectLoginParams req) {
		return doRequest(req, true);
	}

	public HmacBizDirectLoginResult doRequestAP(HmacBizDirectLoginParams req, boolean isSyn) {
		HmacBizDirectLoginResult result = (HmacBizDirectLoginResult) super.doRequestAP(req, isSyn);
		return result;
	}

	public String doRequestAsyn(HmacBizDirectLoginParams req) {
		 return doRequest(req, false);
	}

	@Override
	protected String buildReqParameters(Object params) {
		HmacBizDirectLoginParams req = (HmacBizDirectLoginParams) params;
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
