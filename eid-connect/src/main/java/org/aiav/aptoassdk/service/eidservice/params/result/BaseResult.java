package org.aiav.aptoassdk.service.eidservice.params.result;

import java.io.Serializable;

import net.sf.json.JSONObject;

import org.aiav.aptoassdk.constants.Constant;
import org.aiav.aptoassdk.util.FuncUtil;

@SuppressWarnings("serial")
public class BaseResult implements Serializable {

	public BaseResult(JSONObject resultJson) {
		this.version = resultJson.getString(Constant.VERSION);
		this.bizSequenceId = resultJson.getString(Constant.BIZ_SEQUENCE_ID);
		this.result = resultJson.getString(Constant.RESULT);
		this.resultDetail = (FuncUtil.isEmpty(resultJson
				.get(Constant.RESULT_DETAIL))) ? null : resultJson
				.getString(Constant.RESULT_DETAIL);
		this.resultTime = resultJson.getString(Constant.RESULT_TIME);
		this.attach = (FuncUtil.isEmpty(resultJson.get(Constant.ATTACH)) ? null
				: resultJson.getString(Constant.ATTACH));

	}

	private String version;
	private String result;
	private String resultDetail;
	private String resultTime;
	private String bizSequenceId;
	private String attach;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getResultTime() {
		return resultTime;
	}

	public void setResultTime(String resultTime) {
		this.resultTime = resultTime;
	}

	public String getBizSequenceId() {
		return bizSequenceId;
	}

	public void setBizSequenceId(String bizSequenceId) {
		this.bizSequenceId = bizSequenceId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getResultDetail() {
		return resultDetail;
	}

	public void setResultDetail(String resultDetail) {
		this.resultDetail = resultDetail;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

}
