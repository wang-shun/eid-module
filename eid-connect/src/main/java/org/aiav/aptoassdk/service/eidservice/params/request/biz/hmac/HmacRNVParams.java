package org.aiav.aptoassdk.service.eidservice.params.request.biz.hmac;

import com.google.common.base.Strings;
import net.sf.json.JSONObject;

import org.aiav.aptoassdk.constants.Constant;
import org.aiav.aptoassdk.constants.EBizType;
import org.aiav.aptoassdk.constants.EIdType;
import org.aiav.aptoassdk.service.bizservice.impl.BuildUserIdInfoService;
import org.aiav.aptoassdk.service.bizservice.params.UserIdInfo;
import org.aiav.aptoassdk.service.eidservice.params.request.biz.hmac.base.HmacBaseParams;
import org.aiav.aptoassdk.util.FuncUtil;

@SuppressWarnings("serial")
public class HmacRNVParams extends HmacBaseParams {
	private String name;
	private String idnum;
	private EIdType idtype;
	private String user_id_info;

	public JSONObject buildReq() {
		JSONObject req = buildHmacBaseReq();
		req.put(Constant.BIZ_TYPE,
				EBizType.REAL_NAME_SIGN_VERIFY_HMAC.getIndex());
		req.put(Constant.USER_ID_INFO, new BuildUserIdInfoService()
				.buildUserIdInfo(new UserIdInfo(name, idnum, idtype)));
		if(!Strings.isNullOrEmpty(user_id_info))
			req.put(Constant.USER_ID_INFO, user_id_info);

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

	public String getUser_id_info() {
		return user_id_info;
	}

	public void setUser_id_info(String user_id_info) {
		this.user_id_info = user_id_info;
	}
}
