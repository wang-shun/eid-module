package org.aiav.astoopsdk.service.eidservice.params.request.biz.hmac;

import net.sf.json.JSONObject;

import org.aiav.astoopsdk.service.eidservice.params.request.biz.hmac.base.HmacBaseParams;

@SuppressWarnings("serial")
public class HmacBizParams extends HmacBaseParams {
	public JSONObject buildReq() {
		JSONObject req = buildHmacBaseReq();
		return req;
	}
}
