package org.aiav.astoopsdk.service.eidservice.params.result.manage;

import net.sf.json.JSONObject;

import org.aiav.astoopsdk.constants.Constant;
import org.aiav.astoopsdk.service.eidservice.params.result.BaseResult;
import org.aiav.astoopsdk.util.FuncUtil;

@SuppressWarnings("serial")
public class AppRegResult extends BaseResult {
	private String appid;
	private String appkeyFactor;

	public AppRegResult(JSONObject resultJson) {
		super(resultJson);
		if (!FuncUtil.isEmpty(resultJson)) {
			this.appid = FuncUtil.isEmpty(resultJson.get(Constant.APPID)) ? null
					: resultJson.getString(Constant.APPID);
			this.appkeyFactor = FuncUtil.isEmpty(resultJson
					.get(Constant.APPKEY_FACTOR)) ? null : resultJson
					.getString(Constant.APPKEY_FACTOR);
		}
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getAppkeyFactor() {
		return appkeyFactor;
	}

	public void setAppkeyFactor(String appkeyFactor) {
		this.appkeyFactor = appkeyFactor;
	}

}
