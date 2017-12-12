package org.aiav.aptoassdk.service.eidservice.biz.pki;

import net.sf.json.JSONObject;

import org.aiav.aptoassdk.constants.Constant;
import org.aiav.aptoassdk.service.dataprotection.secresy.IEncryptService;
import org.aiav.aptoassdk.service.dataprotection.sign.ISignService;
import org.aiav.aptoassdk.service.eidservice.AbstractService;
import org.aiav.aptoassdk.service.eidservice.params.request.biz.pki.PkiRNVParams;
import org.aiav.aptoassdk.service.eidservice.params.result.biz.pki.PkiRNVResult;
import org.aiav.aptoassdk.util.FuncUtil;

public class PkiRNVService extends AbstractService {

	public PkiRNVService(ISignService signService,
			IEncryptService encryptService, String url) {
		super(signService, encryptService);
		this.url = url;
	}

	public String doRequestSyn(PkiRNVParams req) {
		return doRequest(req, true);
	}

	public PkiRNVResult doRequestSynAP(PkiRNVParams req) {
		PkiRNVResult result = (PkiRNVResult) doRequestAP(req, true);
		return result;
	}

	public String doRequestAsyn(PkiRNVParams req) {
		return doRequest(req, false);
	}

	@Override
	protected String buildReqParameters(Object params) {
		PkiRNVParams req = (PkiRNVParams) params;
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
	public PkiRNVResult parseResponse(String resStr) {
		JSONObject resJson = parseRes(resStr);
		if (!FuncUtil.isEmpty(resJson)) {
			PkiRNVResult result = new PkiRNVResult(resJson);
			return result;
		} else {
			return null;
		}
	}
}
