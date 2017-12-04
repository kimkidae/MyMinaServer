package com.kidaekim.minaproject.common.core;

import java.util.HashMap;
import java.util.Map;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

import com.kidaekim.minaproject.common.enums.MinaServerType;
import com.kidaekim.minaproject.common.net.BaseRequestEvent;
import com.kidaekim.minaproject.common.net.Protocol;

public abstract class MinaServer {
	private Map<Protocol, BaseRequestEvent> requestEvents = new HashMap<Protocol, BaseRequestEvent>();

	protected MinaServerType minaServerType;

	public MinaServer(MinaServerType minaServerType) {
		this.minaServerType = minaServerType;
	}

	public MinaServerType getMinaServerType() {
		return minaServerType;
	}

	public void setMinaServerType(MinaServerType minaServerType) {
		this.minaServerType = minaServerType;
	}

	public void receiveRequest(Protocol protocol, IoSession session, IoBuffer buffer) {
		if(!requestEvents.containsKey(protocol)) return;
		BaseRequestEvent minaRequestEvent = requestEvents.get(protocol);
		minaRequestEvent.receive(session, buffer);
	}
	
}
