package com.kidaekim.minaproject.common.net;

import java.util.HashMap;
import java.util.Map;

public class Protocol {
	public static final Protocol LOGIN_REQUEST = new Protocol(1, "로그인 요청");
	
	

	private static Map<Short, Protocol> protocols = new HashMap<Short, Protocol>();

	private short code;

	public Protocol(int code, String desc) {
		this.code = (short)code;
		protocols.put(this.code, this);
	}

	public short getCode() {
		return code;
	}

	public static Protocol getProtocol(short code) {
		return protocols.get(code);
	}
}
