package org.aiav.aptoassdk.service.eidservice.params.request.biz.pki.base;

import net.sf.json.JSONObject;

import org.aiav.aptoassdk.constants.Constant;
import org.aiav.aptoassdk.service.bizservice.params.EidCertId;
import org.aiav.aptoassdk.service.eidservice.params.request.biz.BaseEidSignParams;
import org.aiav.aptoassdk.util.FuncUtil;

@SuppressWarnings("serial")
public class PkiBaseParameters extends BaseEidSignParams {

	public JSONObject buildPkiBaseReq() {
		JSONObject req = buildBaseEidSignReq();
		req.put(Constant.EID_CERT_ID, new EidCertId(eidIssuer, eidIssuerSn,
				eidSn).toJson());
		return req;
	}

	private String eidIssuer;
	private String eidIssuerSn;
	private String eidSn;

	public String getEidIssuer() {
		return eidIssuer;
	}

	public void setEidIssuer(String eidIssuer) {
		this.eidIssuer = FuncUtil.trimStr(eidIssuer);
	}

	public String getEidIssuerSn() {
		return eidIssuerSn;
	}

	public void setEidIssuerSn(String eidIssuerSn) {
		this.eidIssuerSn = FuncUtil.trimStr(eidIssuerSn);
	}

	public String getEidSn() {
		return eidSn;
	}

	public void setEidSn(String eidSn) {
		this.eidSn = FuncUtil.trimStr(eidSn);
	}

}
