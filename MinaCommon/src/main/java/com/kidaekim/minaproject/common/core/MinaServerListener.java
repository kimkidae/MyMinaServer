package com.kidaekim.minaproject.common.core;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

import com.kidaekim.minaproject.common.enums.HandleEvent;
import com.kidaekim.minaproject.common.enums.MinaServerType;
import com.kidaekim.minaproject.common.net.Protocol;

public interface MinaServerListener {

	public void receiveRequestEvent(Protocol protocol, IoSession session, IoBuffer buffer) throws Exception;

	public void receiveHandleEvent(MinaServerType minaServerType, int serverIndex, HandleEvent handleEvent, IoSession session, IoBuffer buffer) throws Exception;
	
}
