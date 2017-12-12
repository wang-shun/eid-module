package org.aiav.aptoassdk.service.bizservice.impl;

import net.sf.json.JSONObject;

import org.aiav.aptoassdk.constants.Constant;
import org.aiav.aptoassdk.service.bizservice.IBuildUserIdInfoService;
import org.aiav.aptoassdk.service.bizservice.params.UserIdInfo;
import org.aiav.aptoassdk.util.FuncUtil;

public class BuildUserIdInfoService implements IBuildUserIdInfoService {

	@Override
	public String buildUserIdInfo(UserIdInfo userIdInfo) {
		JSONObject json = new JSONObject();
		if (FuncUtil.isEmpty(userIdInfo.getIdType())
				&& FuncUtil.isEmpty(userIdInfo.getUserName())
				&& FuncUtil.isEmpty(userIdInfo.getUserId())) {
			return null;
		}
		if (!FuncUtil.isEmpty(userIdInfo.getUserName())) {
			json.put(Constant.NAME, userIdInfo.getUserName());
		}
		if (!FuncUtil.isEmpty(userIdInfo.getIdType())) {
			json.put(Constant.IDTYPE, userIdInfo.getIdType().getIndex());
		}
		if (!FuncUtil.isEmpty(userIdInfo.getUserId())) {
			json.put(Constant.IDNUM, userIdInfo.getUserId());
		}
		String s = json.toString();
		return s;

	}
}
