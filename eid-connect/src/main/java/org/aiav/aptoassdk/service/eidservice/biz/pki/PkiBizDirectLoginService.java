package org.aiav.aptoassdk.service.eidservice.biz.pki;

import net.sf.json.JSONObject;

import org.aiav.aptoassdk.constants.Constant;
import org.aiav.aptoassdk.service.dataprotection.secresy.IEncryptService;
import org.aiav.aptoassdk.service.dataprotection.sign.ISignService;
import org.aiav.aptoassdk.service.eidservice.AbstractService;
import org.aiav.aptoassdk.service.eidservice.params.request.biz.pki.PkiBizDirectLoginParams;
import org.aiav.aptoassdk.service.eidservice.params.result.biz.pki.PkiBizDirectLoginResult;
import org.aiav.aptoassdk.util.FuncUtil;

public class PkiBizDirectLoginService extends AbstractService {

	public PkiBizDirectLoginService(ISignService signService,
			IEncryptService encryptService, String url) {
		super(signService, encryptService);
		this.url = url;
	}

	public String doRequestSyn(PkiBizDirectLoginParams req) {
		return doRequest(req, true);
	}

	public PkiBizDirectLoginResult doRequestSynAP(PkiBizDirectLoginParams req) {
		PkiBizDirectLoginResult result = (PkiBizDirectLoginResult) doRequestAP(req, true);
		return result;
	}

	public String doRequestAsyn(PkiBizDirectLoginParams req) {
		return doRequest(req, false);
	}

	@Override
	protected String buildReqParameters(Object params) {
		PkiBizDirectLoginParams req = (PkiBizDirectLoginParams) params;
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
