package org.aiav.aptoassdk.service.eidservice.params.request.biz.hmac;

import net.sf.json.JSONObject;

import org.aiav.aptoassdk.constants.Constant;
import org.aiav.aptoassdk.constants.EBizType;
import org.aiav.aptoassdk.service.eidservice.params.request.biz.hmac.base.HmacBaseParams;

@SuppressWarnings("serial")
public class HmacBizDirectLoginParams extends HmacBaseParams {
	public JSONObject buildReq() {
		JSONObject req = buildHmacBaseReq();
		req.put(Constant.BIZ_TYPE, EBizType.BIZ_DIRECT_LOGIN_HMAC.getIndex());
		return req;
	}
}
