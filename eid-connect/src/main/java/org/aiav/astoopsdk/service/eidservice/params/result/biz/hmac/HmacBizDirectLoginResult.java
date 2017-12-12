package org.aiav.astoopsdk.service.eidservice.params.result.biz.hmac;

import net.sf.json.JSONObject;

import org.aiav.astoopsdk.service.eidservice.params.result.biz.BizBaseResult;

@SuppressWarnings("serial")
public class HmacBizDirectLoginResult extends BizBaseResult {

	public HmacBizDirectLoginResult(JSONObject resultJson) {
		super(resultJson);
	}

}
