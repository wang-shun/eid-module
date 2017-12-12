package org.aiav.aptoassdk.service.eidservice.params.request.biz.pki;

import net.sf.json.JSONObject;

import org.aiav.aptoassdk.constants.Constant;
import org.aiav.aptoassdk.constants.EBizType;
import org.aiav.aptoassdk.service.eidservice.params.request.biz.pki.base.PkiBaseParameters;

@SuppressWarnings("serial")
public class PkiBizParams extends PkiBaseParameters {
	public JSONObject buildReq() {
		JSONObject req = buildPkiBaseReq();
		req.put(Constant.BIZ_TYPE, EBizType.BIZ_SIGN_VERIFY_PKI.getIndex());
		return req;
	}
}
