package org.aiav.aptoassdk.constants;

public enum EIdType {

	ID_CARD("01", "身份证"),

	// PASSPORT_CHN("03", "中国护照"),
	//
	// ID_CARD_ARMY("04", "军官证"),
	//
	// ARMED_POLICE_CARD("05", "武警证"),
	//
	// HONGKONG_MACAU_LAISSEZ_PASSER("06", "港澳通行证"),
	//
	// MTP("07", "台胞证"),
	//
	// PASSPORT_FOREIGN("08", "外国护照"),
	//
	// SOLDIER_CARD("09", "士兵证"),
	//
	// ID_CARD_TMP("10", "临时身份证"),
	//
	// HOUSEHOLD_REGISTRATION("11", "户口本"),
	//
	// POLICE_CARD("12", "警官证"),
	//
	// FOREIGN_PRP("13", "外国人永久居住证"),
	//
	// OTHERS("99", "其他证件")

	;

	private String index;
	private String meaning;

	EIdType(String index, String meaning) {
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

	public static EIdType getEnum(String index) {
		for (EIdType st : EIdType.values()) {
			if (st.index.equals(index)) {
				return st;
			}
		}
		return null;
	}

	public static boolean isCorrectIdType(String idType) {
		for (EIdType it : EIdType.values()) {
			if (it.index.equals(idType)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isIdCard(String idType) {
		if (ID_CARD.getIndex().equals(idType))
			return true;
		else
			return false;
	}

}
