package org.aiav.aptoassdk.service.eidservice.params.request.biz.hmac;

import net.sf.json.JSONObject;

import org.aiav.aptoassdk.constants.Constant;
import org.aiav.aptoassdk.constants.EBizType;
import org.aiav.aptoassdk.constants.EIdType;
import org.aiav.aptoassdk.service.bizservice.impl.BuildUserIdInfoService;
import org.aiav.aptoassdk.service.bizservice.params.UserIdInfo;
import org.aiav.aptoassdk.service.eidservice.params.request.BaseParams;

@SuppressWarnings("serial")
public class HmacBizQrcodeVerifyParams extends BaseParams {
	private String name;
	private String idnum;
	private EIdType idtype;
	private String bizData;
	private JSONObject extension;

	public JSONObject buildReq() {
		JSONObject req = buildBaseReq();

		req.put(Constant.BIZ_TYPE, EBizType.SIGN_VERIFY_QRCODE_HMAC.getIndex());
		req.put(Constant.USER_ID_INFO, new BuildUserIdInfoService()
				.buildUserIdInfo(new UserIdInfo(name, idnum, idtype)));
		req.put(Constant.BIZ_DATA, bizData);
		req.put(Constant.EXTENSION, extension);
		return req;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdnum() {
		return idnum;
	}

	public void setIdnum(String idnum) {
		this.idnum = idnum;
	}

	public EIdType getIdtype() {
		return idtype;
	}

	public void setIdtype(EIdType idtype) {
		this.idtype = idtype;
	}

	public String getBizData() {
		return bizData;
	}

	public void setBizData(String bizData) {
		this.bizData = bizData;
	}

	public JSONObject getExtension() {
		return extension;
	}

	public void setExtension(JSONObject extension) {
		this.extension = extension;
	}
}
