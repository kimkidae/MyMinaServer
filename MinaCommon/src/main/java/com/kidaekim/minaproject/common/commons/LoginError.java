package com.kidaekim.minaproject.common.commons;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class LoginError {
	private static final Map<Short, LoginError> errors = new HashMap<Short, LoginError>();

	public static final LoginError AUTH_FAIL = new LoginError(0,"인증실패");

	private short value;
	private String message;

	public LoginError(int value, String message) {
		this(value, message, 0);
	}

	public LoginError(int value, String message, int parameterCount) {
		this.value = (short)value;
		this.message = message;

		errors.put(this.value, this);
	}

	public short getValue() {
		return value;
	}

	public String getMessage() {
		return message;
	}

	public static Collection<LoginError> getErrors() {
		return errors.values();
	}

}