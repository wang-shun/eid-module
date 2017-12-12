package org.aiav.aptoassdk.constants;

public enum EBizType {

	REAL_NAME_SIGN_VERIFY_PKI("0101001", "PKI实名认证", new String[] { "2.0.0" }),

	REAL_NAME_SIGN_VERIFY_HMAC("0102001", "HMAC实名认证", new String[] { "2.0.0" }),

	BIZ_SIGN_VERIFY_PKI("0201001", "PKI签名验签", new String[] { "2.0.0" }),

	BIZ_SIGN_VERIFY_HMAC("0202001", "HMAC签名验签", new String[] { "2.0.0" }),

	BIZ_DIRECT_LOGIN_PKI("0201002", "PKI实名登录", new String[] { "2.0.0" }),

	BIZ_DIRECT_LOGIN_HMAC("0202002", "HMAC实名登录", new String[] { "2.0.0" }),

	SIGN_VERIFY_QRCODE_HMAC("0202003", "eID扫码认证", new String[] { "2.0.0" }),

	APPEIDCODE_CREATE("0301001", "appeidcode生成", new String[] { "2.0.0" });

	private String index;
	private String meaning;
	private String[] versions;

	EBizType(String index, String meaning, String[] versions) {
		this.setIndex(index);
		this.setMeaning(meaning);
		this.setVersions(versions);
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

	public String[] getVersions() {
		return versions;
	}

	public void setVersions(String[] versions) {
		this.versions = versions;
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
