package org.aiav.aptoassdk.service.eidservice.params.result.biz.hmac;

import net.sf.json.JSONObject;

import org.aiav.aptoassdk.constants.Constant;
import org.aiav.aptoassdk.service.eidservice.params.result.biz.BizBaseResult;
import org.aiav.aptoassdk.util.FuncUtil;

@SuppressWarnings("serial")
public class HmacBizDirectLoginResult extends BizBaseResult {
	private String idhash;

	public HmacBizDirectLoginResult(JSONObject resultJson) {
		super(resultJson);
		this.idhash = FuncUtil.isEmpty(resultJson.get(Constant.USER_INFO)) ? null
				: resultJson.getJSONObject(Constant.USER_INFO).getString(
						Constant.IDHASH);
	}

	public String getIdhash() {
		return idhash;
	}

	public void setIdhash(String idhash) {
		this.idhash = idhash;
	}

}
