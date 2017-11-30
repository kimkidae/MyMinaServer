package com.kidaekim.minaproject.common.enums;

import java.util.HashMap;
import java.util.Map;

public enum MinaServerType {
	AUTH(1), GAME(2), GM(3);

	private static Map<Byte, MinaServerType> map;
	static {
		map = new HashMap<Byte, MinaServerType>();
		for(MinaServerType type : MinaServerType.values()) {
			map.put(type.value, type);
		}
	}
	
	private byte value;

	private MinaServerType(int value) {
		this.value = (byte)value;
	}
	
	public byte getValue() {
		return value;
	}

	public static MinaServerType getEnum(byte value) {
		return map.get(value);
	}
}
