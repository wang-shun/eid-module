package org.aiav.aptoassdk.service.eidservice.params.result.biz.hmac;

import net.sf.json.JSONObject;

import org.aiav.aptoassdk.constants.Constant;
import org.aiav.aptoassdk.service.eidservice.params.result.biz.BizBaseResult;
import org.aiav.aptoassdk.util.FuncUtil;

@SuppressWarnings("serial")
public class HmacBizQrcodeVerifyResult extends BizBaseResult {
	private String idInfo;

	public HmacBizQrcodeVerifyResult(JSONObject resultJson) {
		super(resultJson);
		if (!FuncUtil.isEmpty(resultJson.get(Constant.USER_INFO))
				&& !FuncUtil.isEmpty(resultJson.getJSONObject(
						Constant.USER_INFO).get(Constant.ID_INFO))) {
			this.setIdInfo(resultJson.getJSONObject(Constant.USER_INFO)
					.getString(Constant.ID_INFO));
		}
	}

	public String getIdInfo() {
		return idInfo;
	}

	public void setIdInfo(String idInfo) {
		this.idInfo = idInfo;
	}

}
