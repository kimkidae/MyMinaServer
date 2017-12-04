package com.kidaekim.minaproject.common.enums;

public enum MinaSessionType {
	SERVER_INDEX ("serverIndex", int.class)
	;

	private String key;
	private Class<?> clazz;

	private MinaSessionType(String key, Class<?> clazz) {
		this.key = key;
		this.clazz = clazz;
	}

	public String getKey() {
		return key;
	}

	public Class<?> getClazz() {
		return clazz;
	}
}
