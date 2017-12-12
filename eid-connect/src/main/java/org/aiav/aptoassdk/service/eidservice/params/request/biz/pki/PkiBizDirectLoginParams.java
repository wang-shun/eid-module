package org.aiav.aptoassdk.service.eidservice.params.request.biz.pki;

import net.sf.json.JSONObject;

import org.aiav.aptoassdk.constants.Constant;
import org.aiav.aptoassdk.constants.EBizType;
import org.aiav.aptoassdk.service.eidservice.params.request.biz.pki.base.PkiBaseParameters;

@SuppressWarnings("serial")
public class PkiBizDirectLoginParams extends PkiBaseParameters {
	public JSONObject buildReq() {
		JSONObject req = buildPkiBaseReq();
		req.put(Constant.BIZ_TYPE, EBizType.BIZ_DIRECT_LOGIN_PKI.getIndex());
		return req;
	}
}
