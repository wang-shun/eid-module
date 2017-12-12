package org.aiav.aptoassdk.service.eidservice.params.request.biz.hmac.base;

import net.sf.json.JSONObject;

import org.aiav.aptoassdk.constants.Constant;
import org.aiav.aptoassdk.service.eidservice.params.request.biz.BaseEidSignParams;
import org.aiav.aptoassdk.util.FuncUtil;

@SuppressWarnings("serial")
public class HmacBaseParams extends BaseEidSignParams {
	private String idcarrier;

	public JSONObject buildHmacBaseReq() {
		JSONObject req = buildBaseEidSignReq();

		req.put(Constant.IDCARRIER, idcarrier);
		return req;
	}

	public String getIdcarrier() {
		return idcarrier;
	}

	public void setIdcarrier(String idcarrier) {
		this.idcarrier = FuncUtil.trimStr(idcarrier);
	}
}
