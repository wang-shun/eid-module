package org.aiav.aptoassdk.service.eidservice.biz.hmac;

import net.sf.json.JSONObject;

import org.aiav.aptoassdk.constants.Constant;
import org.aiav.aptoassdk.service.dataprotection.secresy.IEncryptService;
import org.aiav.aptoassdk.service.dataprotection.sign.ISignService;
import org.aiav.aptoassdk.service.eidservice.AbstractService;
import org.aiav.aptoassdk.service.eidservice.params.request.biz.hmac.HmacBizQrcodeVerifyParams;
import org.aiav.aptoassdk.service.eidservice.params.result.biz.hmac.HmacBizQrcodeVerifyResult;
import org.aiav.aptoassdk.util.FuncUtil;

public class HmacBizQrcodeVerifyService extends AbstractService {

	public HmacBizQrcodeVerifyService(ISignService signService,
			IEncryptService encryptService, String url) {
		super(signService, encryptService);
		this.url = url;
	}

	public String doRequestSyn(HmacBizQrcodeVerifyParams req) {
		return doRequest(req, true);
	}

	public String doRequestAsyn(HmacBizQrcodeVerifyParams req) {
		return doRequest(req, false);
	}

	@Override
	protected String buildReqParameters(Object params) {
		HmacBizQrcodeVerifyParams req = (HmacBizQrcodeVerifyParams) params;
		if (params != null) {
			JSONObject reqJson = req.buildReq();
			String bizData = FuncUtil.isEmpty(reqJson.get(Constant.BIZ_DATA)) ? null
					: doEncrypt(reqJson.getString(Constant.BIZ_DATA),
							req.getEncryptFactor());
			String userIdInfo = FuncUtil.isEmpty(reqJson
					.get(Constant.USER_ID_INFO)) ? null : doEncrypt(
					reqJson.getString(Constant.USER_ID_INFO),
					req.getEncryptFactor());
			reqJson.put(Constant.USER_ID_INFO, userIdInfo);
			reqJson.put(Constant.BIZ_DATA, bizData);
			reqJson.put(Constant.SIGN, createReqSign(reqJson));
			return reqJson.toString();
		}
		return null;
	}

	@Override
	public HmacBizQrcodeVerifyResult parseResponse(String resStr) {
		JSONObject resJson = parseRes(resStr);
		if (!FuncUtil.isEmpty(resJson)) {
			HmacBizQrcodeVerifyResult result = new HmacBizQrcodeVerifyResult(
					resJson);
			return result;
		} else {
			return null;
		}
	}

}
