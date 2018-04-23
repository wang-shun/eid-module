package org.aiav.astoopsdk.service.eidservice.params.request.biz.pki;

import com.google.common.base.Strings;
import net.sf.json.JSONObject;
import org.aiav.aptoassdk.service.dataprotection.secresy.impl.SDesedeService;
import org.aiav.astoopsdk.constants.Constant;
import org.aiav.astoopsdk.service.eidservice.params.request.biz.BaseBizParams;
import org.aiav.astoopsdk.util.FuncUtil;

@SuppressWarnings("serial")
// eID SIM 请求IDSO参数封装
public class PkiRNVSIMParams extends BaseBizParams {
	private String userIdInfo;
	private String dataToSign;
	private String msisdn;
	private String dataToBeDisplayed;
	private String extension;

	public JSONObject buildReq() {
		JSONObject req = buildBaseBizReq();

		// 兼容eID SIM 和 eID公用参数key不一样的情况
		if(req.containsKey(Constant.APPID)){
			String appid = req.getString(Constant.APPID);
			req.remove(Constant.APPID);
			req.put(Constant.APP_ID,appid);
		}
		if(req.containsKey(Constant.ASID)){
			String asid = req.getString(Constant.ASID);
			req.remove(Constant.ASID);
			req.put(Constant.IDSP_ID,asid);
		}

		if(!Strings.isNullOrEmpty(userIdInfo))
			req.put(Constant.USER_ID_INFO, userIdInfo);

		req.put(Constant.DATA_TO_SIGN, dataToSign);
		req.put(Constant.MSISDN, msisdn);
		req.put(Constant.DATA_TO_BE_DISPLAYED, dataToBeDisplayed);
		req.put(Constant.EXTENSION, extension);
		return req;
	}

	public String getUserIdInfo() {
		return userIdInfo;
	}

	public void setUserIdInfo(String userIdInfo) {
		this.userIdInfo = FuncUtil.trimStr(userIdInfo);
	}

	public String getDataToSign() {
		return dataToSign;
	}

	public void setDataToSign(String dataToSign) {
		this.dataToSign = FuncUtil.trimStr(dataToSign);
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = FuncUtil.trimStr(msisdn);
	}

	public String getDataToBeDisplayed() {
		return dataToBeDisplayed;
	}

	public void setDataToBeDisplayed(String dataToBeDisplayed) {
		this.dataToBeDisplayed = FuncUtil.trimStr(dataToBeDisplayed);
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

}
