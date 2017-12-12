package org.aiav.astoopsdk.service.eidservice.params.result.manage;

import net.sf.json.JSONObject;

import org.aiav.astoopsdk.constants.Constant;
import org.aiav.astoopsdk.service.eidservice.params.result.BaseResult;
import org.aiav.astoopsdk.util.FuncUtil;

@SuppressWarnings("serial")
public class AppkeyUpdateResult extends BaseResult {
	private String appkeyFactor;

	public AppkeyUpdateResult(JSONObject resultJson) {
		super(resultJson);
		this.appkeyFactor = FuncUtil.isEmpty(resultJson
				.get(Constant.APPKEY_FACTOR)) ? null : resultJson
				.getString(Constant.APPKEY_FACTOR);
	}

	public String getAppkeyFactor() {
		return appkeyFactor;
	}

	public void setAppkeyFactor(String appkeyFactor) {
		this.appkeyFactor = appkeyFactor;
	}

}
