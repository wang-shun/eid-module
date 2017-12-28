package org.aiav.astoopsdk.service.eidservice.params.request.manage;

import lombok.ToString;
import net.sf.json.JSONObject;

import org.aiav.astoopsdk.constants.Constant;
import org.aiav.astoopsdk.service.eidservice.params.request.manage.base.AppRegBaseParams;
import org.aiav.astoopsdk.util.FuncUtil;

@ToString(callSuper = true)
@SuppressWarnings("serial")
public class AppRegParams extends AppRegBaseParams {
	public JSONObject buildReq() {
		JSONObject req = buildAppRegInfoBaseReq();
		req.put(Constant.EXTENSION, getExtension());// 异步，添加扩展字段（APP信息表id）

		return req;
	}

	private String getExtension() {
		JSONObject extension = new JSONObject();
		extension.put(Constant.RELATED_APPID, relatedAppid);
		return extension.toString();
	}

	private String relatedAppid;

	public String getRelatedAppid() {
		return relatedAppid;
	}

	public void setRelatedAppid(String relatedAppid) {
		this.relatedAppid = FuncUtil.trimStr(relatedAppid);
	}

}
