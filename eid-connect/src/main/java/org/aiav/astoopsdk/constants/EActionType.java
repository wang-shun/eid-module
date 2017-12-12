package org.aiav.astoopsdk.constants;

public enum EActionType {

	PAUSE("1", "暂停"),

	RECOVER("2", "恢复/可用"),

	STOP("3", "终止");

	private String index;
	private String meaning;

	EActionType(String index, String meaning) {
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

	public static EActionType getEnum(String index) {
		for (EActionType st : EActionType.values()) {
			if (st.index.equals(index)) {
				return st;
			}
		}
		return null;
	}

}
