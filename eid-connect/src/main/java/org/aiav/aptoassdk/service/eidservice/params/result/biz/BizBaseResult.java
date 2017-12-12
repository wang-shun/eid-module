package org.aiav.aptoassdk.service.eidservice.params.result.biz;

import net.sf.json.JSONObject;

import org.aiav.aptoassdk.constants.Constant;
import org.aiav.aptoassdk.service.eidservice.params.result.BaseResult;
import org.aiav.aptoassdk.util.FuncUtil;

@SuppressWarnings("serial")
public class BizBaseResult extends BaseResult {
	private String appeidcode;

	public BizBaseResult(JSONObject resultJson) {
		super(resultJson);
		this.appeidcode = FuncUtil.isEmpty(resultJson.get(Constant.USER_INFO)) ? null
				: resultJson.getJSONObject(Constant.USER_INFO).getString(
						Constant.APPEIDCODE);

	}

	public String getAppeidcode() {
		return appeidcode;
	}

	public void setAppeidcode(String appeidcode) {
		this.appeidcode = appeidcode;
	}

}
