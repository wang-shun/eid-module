package org.aiav.astoopsdk.constants;

public enum EKeyType {

	ALL("0", "更新所有key_factor"),

	SKEY_FACTOR("1", "只更新skey_factor"),

	EKEY_FACTOR("2", "只更新ekey_factor");

	private String index;
	private String value;

	EKeyType(String index, String value) {
		this.index = index;
		this.value = value;
	}

	public String getIndex() {
		return this.index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public static boolean isCorrectKeyType(String keyType) {
		for (EKeyType kt : EKeyType.values()) {
			if (kt.index.equals(keyType)) {
				return true;
			}
		}
		return false;
	}

}
