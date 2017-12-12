package org.aiav.astoopsdk.service.eidservice.params.request.biz;

import lombok.ToString;
import net.sf.json.JSONObject;

import org.aiav.astoopsdk.constants.Constant;
import org.aiav.astoopsdk.constants.EEidSignA;
import org.aiav.astoopsdk.util.FuncUtil;

@SuppressWarnings("serial")
@ToString(callSuper = true)
public class BaseEidSignParams extends BaseBizParams {
	private String eidSign;
	private EEidSignA eidSignAlgorithm;
	private String dataToSign;

	public JSONObject buildBaseEidSignReq() {
		JSONObject req = buildBaseBizReq();

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
