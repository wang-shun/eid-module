package org.aiav.astoopsdk.constants;

public enum ESignType {

	HMAC_SHA1("1", "HMAC_SHA1"),

	HMAC_SHA256("2", "HMAC_SHA256"),

	HMAC_MD5("3", "HMAC_MD5"),

	HMAC_SM3("4", "HMAC_SM3");

	private String index;
	private String meaning;

	ESignType(String index, String meaning) {
		this.index = index;
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

	public static ESignType getEnum(String index) {
		for (ESignType st : ESignType.values()) {
			if (st.index.equals(index)) {
				return st;
			}
		}
		return null;
	}

	public static boolean isCorrectSignType(String signType) {
		for (ESignType st : ESignType.values()) {
			if (st.index.equals(signType)) {
				return true;
			}
		}
		return false;
	}

}
