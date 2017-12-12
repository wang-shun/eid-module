package org.aiav.astoopsdk.constants;

public enum ESecurityType {

	SKEY("10", "软key方式"),

	EKEY("30", "加密机方式");

	private String index;
	private String meaning;

	ESecurityType(String index, String meaning) {
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

	public static ESecurityType getEnum(String index) {
		for (ESecurityType st : ESecurityType.values()) {
			if (st.index.equals(index)) {
				return st;
			}
		}
		return null;
	}

	public static boolean isCorrectSecurityType(String securityType) {
		for (ESecurityType st : ESecurityType.values()) {
			if (st.index.equals(securityType)) {
				return true;
			}
		}
		return false;
	}

}
