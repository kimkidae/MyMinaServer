package com.kidaekim.minaproject.auth.net.login;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

import com.kidaekim.minaproject.common.net.BaseRequestEvent;
import com.kidaekim.minaproject.common.net.Protocol;

public class RUserLogin extends BaseRequestEvent{
	@Override
	public Protocol protocol() {
		return Protocol.USER_LOGIN;
	}

	@Override
	public void receive(IoSession session, IoBuffer buffer) {
		
	}
}
