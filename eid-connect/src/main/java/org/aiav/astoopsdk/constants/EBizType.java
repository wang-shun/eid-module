package org.aiav.astoopsdk.constants;

public enum EBizType {

	REAL_NAME_SIGN_VERIFY_PKI("0101001", "PKI身份识别"),

	REAL_NAME_SIGN_VERIFY_HMAC("0102001", "HMAC身份识别"),

	BIZ_SIGN_VERIFY_PKI("0201001", "PKI签名验签"),

	BIZ_SIGN_VERIFY_HMAC("0202001", "HMAC签名验签"),

	BIZ_DIRECT_LOGIN_PKI("0201002", "PKI实名登录"),

	BIZ_DIRECT_LOGIN_HMAC("0202002", "HMAC实名登录");

	private String index;
	private String meaning;

	EBizType(String index, String meaning) {
		this.setIndex(index);
		this.setMeaning(meaning);
	}

	public String getIndex() {
		return this.index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getMeaning() {
		return meaning;
	}

	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}

	public static EBizType getEnum(String index) {
		for (EBizType st : EBizType.values()) {
			if (st.index.equals(index)) {
				return st;
			}
		}
		return null;
	}

	public static boolean isCorrectBizType(String bizType) {
		for (EBizType st : EBizType.values()) {
			if (st.index.equals(bizType)) {
				return true;
			}
		}
		return false;
	}

}
