package com.kidaekim.minaproject.common.core;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

import com.kidaekim.minaproject.common.enums.HandleEvent;
import com.kidaekim.minaproject.common.enums.MinaServerType;

public abstract class MinaServer {
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

	public void receiveEvent(HandleEvent handleEvent, IoSession session, IoBuffer buffer) {
		// TODO Auto-generated method stub
		
	}
	
}
