package org.aiav.astoopsdk.service.eidservice.params.request.biz.hmac.base;

import lombok.ToString;
import net.sf.json.JSONObject;

import org.aiav.astoopsdk.constants.Constant;
import org.aiav.astoopsdk.service.eidservice.params.request.biz.BaseEidSignParams;
import org.aiav.astoopsdk.util.FuncUtil;

@SuppressWarnings("serial")
@ToString(callSuper = true)
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
