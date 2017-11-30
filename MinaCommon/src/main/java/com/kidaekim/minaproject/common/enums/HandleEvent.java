package com.kidaekim.minaproject.common.enums;

import java.util.HashMap;
import java.util.Map;

public enum HandleEvent {
	 LOGIN(1)
	,LOGOUT(2)
	,REQUEST(3);

	private static Map<Byte, HandleEvent> types;
	static {
		types = new HashMap<Byte, HandleEvent>();
		for(HandleEvent type : HandleEvent.values()) {
			types.put(type.value, type);
		}
	}

	private byte value;

	private HandleEvent(int value) {
		this.value = (byte)value;
	}
	
	public byte getValue() {
		return value;
	}

	public static HandleEvent getEnum(byte value) {
		return types.get(value);
	}
}
