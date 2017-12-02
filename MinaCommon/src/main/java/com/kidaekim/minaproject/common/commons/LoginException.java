package com.kidaekim.minaproject.common.commons;

import java.util.HashMap;
import java.util.Map;

public class LoginException extends Exception{
	private static final long serialVersionUID = -2110019320057066977L;

	private static final Map<LoginError, LoginException> map = new HashMap<LoginError, LoginException>();
	static {
		for(LoginError error : LoginError.getErrors()){
			map.put(error, new LoginException(error));
		}
	}

	private LoginException(LoginError loginError) {
		super(loginError.getMessage());
	}

	public static LoginException getLoginException(LoginError error){
		return map.get(error);
	}

	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}
}
