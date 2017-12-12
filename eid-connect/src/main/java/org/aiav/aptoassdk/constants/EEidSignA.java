package org.aiav.aptoassdk.constants;

public enum EEidSignA {

	SHA1("10", "SHA-1"),

	SHA256("11", "SHA-256"),

	SM3("12", "SM3"),

	RSA_WITH_SHA1("20", "1"),

	SM2_WITH_SM3("21", "2"),

	RSA("22", "3"),

	SM2("23", "4");

	private String index;
	private String indexOfEidDb;

	EEidSignA(String index, String indexOfEidDb) {
		this.index = index;
		this.indexOfEidDb = indexOfEidDb;
	}

	public String getIndex() {
		return this.index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getIndexOfEidDb() {
		return indexOfEidDb;
	}

	public void setIndexOfEidDb(String indexOfEidDb) {
		this.indexOfEidDb = indexOfEidDb;
	}

	public static EEidSignA getEnum(String index) {
		for (EEidSignA st : EEidSignA.values()) {
			if (st.index.equals(index)) {
				return st;
			}
		}
		return null;
	}

	public static boolean isCorrectHmacAlgorithm(String eidSignAlgorithm) {
		if (SHA1.getIndex().equals(eidSignAlgorithm)
				|| SHA256.getIndex().equals(eidSignAlgorithm)
				|| SM3.getIndex().equals(eidSignAlgorithm))
			return true;
		return false;
	}

	public static boolean isCorrectPkiAlgorithm(String eidSignAlgorithm) {
		if (RSA_WITH_SHA1.getIndex().equals(eidSignAlgorithm)
				|| SM2_WITH_SM3.getIndex().equals(eidSignAlgorithm)
				|| RSA.getIndex().equals(eidSignAlgorithm)
				|| SM2.getIndex().equals(eidSignAlgorithm))
			return true;
		return false;
	}

}
