package org.aiav.aptoassdk.service.eidservice.biz.hmac;

import net.sf.json.JSONObject;

import org.aiav.aptoassdk.constants.Constant;
import org.aiav.aptoassdk.service.dataprotection.secresy.IEncryptService;
import org.aiav.aptoassdk.service.dataprotection.sign.ISignService;
import org.aiav.aptoassdk.service.eidservice.AbstractService;
import org.aiav.aptoassdk.service.eidservice.params.request.biz.hmac.HmacRNVParams;
import org.aiav.aptoassdk.service.eidservice.params.result.biz.hmac.HmacRNVResult;
import org.aiav.aptoassdk.util.FuncUtil;

public class HmacRNVService extends AbstractService {

	public HmacRNVService(ISignService signService,
			IEncryptService encryptService, String url) {
		super(signService, encryptService);
		this.url = url;
	}

	public String doRequestSyn(HmacRNVParams req) {
		return doRequest(req, true);
	}

	public String doRequestAsyn(HmacRNVParams req) {
		return doRequest(req, false);
	}

	public HmacRNVResult doRequestSynAP(HmacRNVParams req) {
		HmacRNVResult result = (HmacRNVResult) doRequestAP(req, true);
		return result;
	}

	@Override
	protected String buildReqParameters(Object params) {
		HmacRNVParams req = (HmacRNVParams) params;
		if (params != null) {
			JSONObject reqJson = req.buildReq();
			String userIdInfo = FuncUtil.isEmpty(reqJson
					.get(Constant.USER_ID_INFO)) ? null : doEncrypt(
					reqJson.getString(Constant.USER_ID_INFO),
					req.getEncryptFactor());
			String dataToSign = FuncUtil.isEmpty(reqJson
					.get(Constant.DATA_TO_SIGN)) ? null : doEncrypt(
					reqJson.getString(Constant.DATA_TO_SIGN),
					req.getEncryptFactor());
			reqJson.put(Constant.USER_ID_INFO, userIdInfo);
			reqJson.put(Constant.DATA_TO_SIGN, dataToSign);
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

}
