package com.kidaekim.minaproject.common.commons;

import org.apache.mina.core.session.IoSession;

import com.kidaekim.minaproject.common.enums.MinaSessionType;

public class MinaUtils {

	public static int getSessionData(MinaSessionType serverIndex) {
		
		return 0;
	}

	@SuppressWarnings("unchecked")
	public static <E> E getSessionData(IoSession session, MinaSessionType minaSessionType) {
		return (E)session.getAttribute(minaSessionType.getKey());
	}
	
}
