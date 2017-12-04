package com.kidaekim.minaproject.common.net;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

public abstract class BaseRequestEvent {
	public abstract Protocol protocol();
	public abstract void receive(IoSession session, IoBuffer buffer);
}
