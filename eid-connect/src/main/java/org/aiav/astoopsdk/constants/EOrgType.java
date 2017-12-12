package org.aiav.astoopsdk.constants;

public enum EOrgType {

	GOV("00", "政府Government"),

	COM("01", "企业Company"),

	PO("02", "公共组织Public Organization"),

	ETC("03", "其他");

	private String index;
	private String meaning;

	EOrgType(String index, String meaning) {
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

	public static EOrgType getEnum(String index) {
		for (EOrgType st : EOrgType.values()) {
			if (st.index.equals(index)) {
				return st;
			}
		}
		return null;
	}

	public static boolean isCorrectOrgType(String orgType) {
		for (EOrgType ot : EOrgType.values()) {
			if (ot.index.equals(orgType)) {
				return true;
			}
		}
		return false;
	}
}
