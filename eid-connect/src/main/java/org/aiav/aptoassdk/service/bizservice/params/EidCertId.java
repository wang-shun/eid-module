package org.aiav.aptoassdk.service.bizservice.params;

import net.sf.json.JSONObject;

import org.aiav.aptoassdk.constants.Constant;
import org.aiav.aptoassdk.util.FuncUtil;

public class EidCertId {
	private String eidIssuer;
	private String eidIssuerSn;
	private String eidSn;

	public EidCertId(String eidIssuer, String eidIssuerSn, String eidSn) {
		this.eidIssuer = eidIssuer;
		this.eidIssuerSn = eidIssuerSn;
		this.eidSn = eidSn;
	}

	public String getEidIssuer() {
		return eidIssuer;
	}

	public void setEidIssuer(String eidIssuer) {
		this.eidIssuer = eidIssuer;
	}

	public String getEidIssuerSn() {
		return eidIssuerSn;
	}

	public void setEidIssuerSn(String eidIssuerSn) {
		this.eidIssuerSn = eidIssuerSn;
	}

	public String getEidSn() {
		return eidSn;
	}

	public void setEidSn(String eidSn) {
		this.eidSn = eidSn;
	}

	public JSONObject toJson() {
		JSONObject json = new JSONObject();
		if (!FuncUtil.isEmpty(eidIssuer)) {
			json.put(Constant.EID_ISSUER, eidIssuer);
		}
		if (!FuncUtil.isEmpty(eidIssuerSn)) {
			json.put(Constant.EID_ISSUER_SN, eidIssuerSn);
		}
		if (!FuncUtil.isEmpty(eidSn)) {
			json.put(Constant.EID_SN, eidSn);
		}
		return json;
	}
}
