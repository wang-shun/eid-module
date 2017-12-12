package org.aiav.astoopsdk.service.eidservice.params.request.biz;

import lombok.ToString;
import net.sf.json.JSONObject;

import org.aiav.aptoassdk.util.ServiceUtil;
import org.aiav.astoopsdk.constants.Constant;
import org.aiav.astoopsdk.constants.EBizType;
import org.aiav.astoopsdk.service.bizservice.params.SecurityFactor;
import org.aiav.astoopsdk.service.eidservice.params.request.BaseParams;
import org.aiav.astoopsdk.util.FuncUtil;

@SuppressWarnings("serial")
@ToString(callSuper = true)
public class BaseBizParams extends BaseParams {
	private String appid;
	private EBizType bizType;
	private String encryptFactor;

	public JSONObject buildBaseBizReq() {
		JSONObject req = buildBaseReq();

		encryptFactor = org.aiav.aptoassdk.util.FuncUtil.isEmpty(encryptFactor) ? ServiceUtil.genHexString(8) : encryptFactor;

		req.put(Constant.APPID, appid);
		if (!FuncUtil.isEmpty(bizType)) {
			req.put(Constant.BIZ_TYPE, bizType.getIndex());
		}

		req.put(Constant.SECURITY_FACTOR, new SecurityFactor(encryptFactor,
				this.getSignFactor()).toJson());
		return req;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = FuncUtil.trimStr(appid);
	}

	public String getEncryptFactor() {
		return encryptFactor;
	}

	public void setEncryptFactor(String encryptFactor) {
		this.encryptFactor = FuncUtil.trimStr(encryptFactor);
	}

	public EBizType getBizType() {
		return bizType;
	}

	public void setBizType(EBizType bizType) {
		this.bizType = bizType;
	}

}
