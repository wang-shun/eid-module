package org.aiav.aptoassdk.service.eidservice.biz.pki;

import net.sf.json.JSONObject;

import org.aiav.aptoassdk.constants.Constant;
import org.aiav.aptoassdk.service.dataprotection.secresy.IEncryptService;
import org.aiav.aptoassdk.service.dataprotection.sign.ISignService;
import org.aiav.aptoassdk.service.eidservice.AbstractService;
import org.aiav.aptoassdk.service.eidservice.params.request.biz.pki.PkiBizParams;
import org.aiav.aptoassdk.service.eidservice.params.result.biz.pki.PkiBizResult;
import org.aiav.aptoassdk.util.FuncUtil;

public class PkiBizService extends AbstractService {

	public PkiBizService(ISignService signService,
			IEncryptService encryptService, String url) {
		super(signService, encryptService);
		this.url = url;
	}

	public String doRequestSyn(PkiBizParams req) {
		return doRequest(req, true);
	}

	public PkiBizResult doRequestSynAP(PkiBizParams req) {
		PkiBizResult result = (PkiBizResult) doRequestAP(req, true);
		return result;
	}

	public String doRequestAsyn(PkiBizParams req) {
		return doRequest(req, false);
	}

	@Override
	protected String buildReqParameters(Object params) {
		PkiBizParams req = (PkiBizParams) params;
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
