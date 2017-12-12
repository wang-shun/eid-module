package org.aiav.astoopsdk.constants;

public enum EEncryptType {

	TRIPLE_DES_ECB_PKCS5PADDING("1", "3DES_ECB_PKCS5PADDING"),

	SM4_ECB_PKCS5PADDING("2", "SM4_ECB_PKCS5PADDING");

	private String index;
	private String meaning;

	EEncryptType(String index, String meaning) {
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

	public static EEncryptType getEnum(String index) {
		for (EEncryptType st : EEncryptType.values()) {
			if (st.index.equals(index)) {
				return st;
			}
		}
		return null;
	}

	public static boolean isCorrectEncryptType(String encryptType) {
		for (EEncryptType st : EEncryptType.values()) {
			if (st.index.equals(encryptType)) {
				return true;
			}
		}
		return false;
	}

}
