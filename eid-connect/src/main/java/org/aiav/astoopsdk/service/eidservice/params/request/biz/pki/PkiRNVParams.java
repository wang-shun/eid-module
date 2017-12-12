package org.aiav.astoopsdk.service.eidservice.params.request.biz.pki;

import net.sf.json.JSONObject;

import org.aiav.astoopsdk.constants.Constant;
import org.aiav.astoopsdk.service.eidservice.params.request.biz.pki.base.PkiBaseParameters;
import org.aiav.astoopsdk.util.FuncUtil;

@SuppressWarnings("serial")
public class PkiRNVParams extends PkiBaseParameters {
	private String userIdInfo;

	public JSONObject buildReq() {
		JSONObject req = buildPkiBaseReq();
		req.put(Constant.USER_ID_INFO, userIdInfo);
		return req;
	}

	public String getUserIdInfo() {
		return userIdInfo;
	}

	public void setUserIdInfo(String userIdInfo) {
		this.userIdInfo = FuncUtil.trimStr(userIdInfo);
	}
}
