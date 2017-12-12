package org.aiav.astoopsdk.service.eidservice.params.request.manage;

import net.sf.json.JSONObject;

import org.aiav.astoopsdk.constants.Constant;
import org.aiav.astoopsdk.service.bizservice.params.SecurityFactor;
import org.aiav.astoopsdk.service.eidservice.params.request.BaseParams;
import org.aiav.astoopsdk.util.FuncUtil;

@SuppressWarnings("serial")
public class AppkeyUpdateParams extends BaseParams {
	private String appid;

	public JSONObject buildReq() {
		JSONObject req = buildBaseReq();
		req.put(Constant.SECURITY_FACTOR,
				new SecurityFactor(null, this.getSignFactor()).toJson());
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
