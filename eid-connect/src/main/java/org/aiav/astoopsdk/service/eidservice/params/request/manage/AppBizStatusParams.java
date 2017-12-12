package org.aiav.astoopsdk.service.eidservice.params.request.manage;

import net.sf.json.JSONObject;

import org.aiav.astoopsdk.constants.Constant;
import org.aiav.astoopsdk.constants.EActionType;
import org.aiav.astoopsdk.constants.EBizType;
import org.aiav.astoopsdk.service.bizservice.params.SecurityFactor;
import org.aiav.astoopsdk.service.eidservice.params.request.BaseParams;
import org.aiav.astoopsdk.util.FuncUtil;

@SuppressWarnings("serial")
public class AppBizStatusParams extends BaseParams {
	private String appid;
	private EBizType bizType;
	private EActionType actionType;

	public JSONObject buildReq() {
		JSONObject req = buildBaseReq();
		req.put(Constant.SECURITY_FACTOR,
				new SecurityFactor(null, this.getSignFactor()).toJson());
		req.put(Constant.APPID, appid);
		if (!FuncUtil.isEmpty(bizType)) {
			req.put(Constant.BIZ_TYPE, bizType.getIndex());
		}
		if (!FuncUtil.isEmpty(actionType)) {
			req.put(Constant.ACTION_TYPE, actionType.getIndex());
		}
		return req;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = FuncUtil.trimStr(appid);
	}

	public EBizType getBizType() {
		return bizType;
	}

	public void setBizType(EBizType bizType) {
		this.bizType = bizType;
	}

	public EActionType getActionType() {
		return actionType;
	}

	public void setActionType(EActionType actionType) {
		this.actionType = actionType;
	}

}
