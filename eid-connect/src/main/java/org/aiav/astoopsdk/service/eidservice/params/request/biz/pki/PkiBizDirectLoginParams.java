package org.aiav.astoopsdk.service.eidservice.params.request.biz.pki;

import net.sf.json.JSONObject;

import org.aiav.astoopsdk.service.eidservice.params.request.biz.pki.base.PkiBaseParameters;

@SuppressWarnings("serial")
public class PkiBizDirectLoginParams extends PkiBaseParameters {
	public JSONObject buildReq() {
		JSONObject req = buildPkiBaseReq();
		return req;
	}
}
