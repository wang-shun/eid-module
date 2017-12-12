package org.aiav.aptoassdk.service.eidservice.params.request.biz.appeidcode;

import net.sf.json.JSONObject;

import org.aiav.aptoassdk.constants.Constant;
import org.aiav.aptoassdk.constants.EBizType;
import org.aiav.aptoassdk.constants.EIdType;
import org.aiav.aptoassdk.service.bizservice.impl.BuildUserIdInfoService;
import org.aiav.aptoassdk.service.bizservice.params.UserIdInfo;
import org.aiav.aptoassdk.service.eidservice.params.request.BaseParams;
import org.aiav.aptoassdk.util.FuncUtil;

@SuppressWarnings("serial")
public class AppeidcodeCreateParams extends BaseParams {

	private String name;
	private String idnum;
	private EIdType idtype;

	public JSONObject buildReq() {
		JSONObject req = buildBaseReq();
		req.put(Constant.BIZ_TYPE, EBizType.APPEIDCODE_CREATE.getIndex());
		req.put(Constant.USER_ID_INFO, new BuildUserIdInfoService()
				.buildUserIdInfo(new UserIdInfo(name, idnum, idtype)));
		return req;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = FuncUtil.trimStr(name);
	}

	public String getIdnum() {
		return idnum;
	}

	public void setIdnum(String idnum) {
		this.idnum = FuncUtil.trimStr(idnum).toUpperCase();
	}

	public EIdType getIdtype() {
		return idtype;
	}

	public void setIdtype(EIdType idtype) {
		this.idtype = idtype;
	}
}
