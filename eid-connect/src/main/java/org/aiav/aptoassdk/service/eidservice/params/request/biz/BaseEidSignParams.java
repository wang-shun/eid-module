package org.aiav.aptoassdk.service.eidservice.params.request.biz;

import net.sf.json.JSONObject;

import org.aiav.aptoassdk.constants.Constant;
import org.aiav.aptoassdk.constants.EEidSignA;
import org.aiav.aptoassdk.service.eidservice.params.request.BaseParams;
import org.aiav.aptoassdk.util.FuncUtil;

@SuppressWarnings("serial")
public class BaseEidSignParams extends BaseParams {
	private String eidSign;
	private EEidSignA eidSignAlgorithm;
	private String dataToSign;

	public JSONObject buildBaseEidSignReq() {
		JSONObject req = buildBaseReq();

		req.put(Constant.EID_SIGN, eidSign);
		if (!FuncUtil.isEmpty(eidSignAlgorithm)) {
			req.put(Constant.EID_SIGN_ALGORITHM, eidSignAlgorithm.getIndex());
		}
		req.put(Constant.DATA_TO_SIGN, dataToSign);
		return req;
	}

	public String getEidSign() {
		return eidSign;
	}

	public void setEidSign(String eidSign) {
		this.eidSign = FuncUtil.trimStr(eidSign);
	}

	public EEidSignA getEidSignAlgorithm() {
		return eidSignAlgorithm;
	}

	public void setEidSignAlgorithm(EEidSignA eidSignAlgorithm) {
		this.eidSignAlgorithm = eidSignAlgorithm;
	}

	public String getDataToSign() {
		return dataToSign;
	}

	public void setDataToSign(String dataToSign) {
		this.dataToSign = FuncUtil.trimStr(dataToSign);
	}
}
