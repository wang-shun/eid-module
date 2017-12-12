package org.aiav.astoopsdk.service.eidservice.params.request.manage;

import net.sf.json.JSONObject;

import org.aiav.astoopsdk.constants.Constant;
import org.aiav.astoopsdk.service.eidservice.params.request.manage.base.AppRegBaseParams;
import org.aiav.astoopsdk.util.FuncUtil;

@SuppressWarnings("serial")
public class AppRegUpdateParams extends AppRegBaseParams {
	private String appid;

	public JSONObject buildReq() {
		JSONObject req = buildAppRegInfoBaseReq();
		req.put(Constant.APPID, appid);

		return req;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = FuncUtil.trimStr(appid);
	}
}
