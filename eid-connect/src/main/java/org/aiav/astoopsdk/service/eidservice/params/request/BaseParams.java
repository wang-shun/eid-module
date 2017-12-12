package org.aiav.astoopsdk.service.eidservice.params.request;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import lombok.ToString;
import net.sf.json.JSONObject;

import org.aiav.astoopsdk.constants.Constant;
import org.aiav.astoopsdk.constants.EEncryptType;
import org.aiav.astoopsdk.constants.ESecurityType;
import org.aiav.astoopsdk.constants.ESignType;
import org.aiav.astoopsdk.util.DateUtil;
import org.aiav.astoopsdk.util.FuncUtil;
import org.aiav.astoopsdk.util.ServiceUtil;

@SuppressWarnings("serial")
@ToString
public class BaseParams implements Serializable {
	private String version;
	private String asid;
	private String bizTime;
	private String bizSequenceId;
	private String signFactor;
	private ESecurityType securityType;
	private ESignType signType;
	private EEncryptType encryptType;
	private String attach;
	private String returnUrl;

	protected JSONObject buildBaseReq() {

		bizSequenceId = FuncUtil.isEmpty(bizSequenceId) ? ServiceUtil
				.genHexString(16) : bizSequenceId;
		version = FuncUtil.isEmpty(version) ? "1.0.0" : version;
		bizTime = FuncUtil.isEmpty(bizTime) ? DateUtil
				.getNow(new SimpleDateFormat(DateUtil.DATE_TIME_FORMAT_2))
				: bizTime;
		signFactor = FuncUtil.isEmpty(signFactor) ? ServiceUtil.genHexString(8)
				: signFactor;

		JSONObject req = new JSONObject();
		if (!FuncUtil.isEmpty(securityType)) {
			req.put(Constant.SECURITY_TYPE, securityType.getIndex());
		}
		if (!FuncUtil.isEmpty(signType)) {
			req.put(Constant.SIGN_TYPE, signType.getIndex());
		}
		if (!FuncUtil.isEmpty(encryptType)) {
			req.put(Constant.ENCRYPT_TYPE, encryptType.getIndex());
		}
		req.put(Constant.ASID, asid);
		req.put(Constant.BIZ_SEQUENCE_ID, bizSequenceId);
		req.put(Constant.VERSION, version);
		req.put(Constant.RETURN_URL, returnUrl);
		req.put(Constant.BIZ_TIME, bizTime);
		req.put(Constant.ATTACH, attach);

		return req;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = FuncUtil.trimStr(version);
	}

	public String getAsid() {
		return asid;
	}

	public void setAsid(String asid) {
		this.asid = FuncUtil.trimStr(asid);
	}

	public String getBizTime() {
		return bizTime;
	}

	public void setBizTime(String bizTime) {
		this.bizTime = FuncUtil.trimStr(bizTime);
	}

	public String getBizSequenceId() {
		return bizSequenceId;
	}

	public void setBizSequenceId(String bizSequenceId) {
		this.bizSequenceId = FuncUtil.trimStr(bizSequenceId);
	}

	public String getSignFactor() {
		return signFactor;
	}

	public void setSignFactor(String signFactor) {
		this.signFactor = FuncUtil.trimStr(signFactor);
	}

	public ESecurityType getSecurityType() {
		return securityType;
	}

	public void setSecurityType(ESecurityType securityType) {
		this.securityType = securityType;
	}

	public ESignType getSignType() {
		return signType;
	}

	public void setSignType(ESignType signType) {
		this.signType = signType;
	}

	public EEncryptType getEncryptType() {
		return encryptType;
	}

	public void setEncryptType(EEncryptType encryptType) {
		this.encryptType = encryptType;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = FuncUtil.trimStr(attach);
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = FuncUtil.trimStr(returnUrl);
	}
}
