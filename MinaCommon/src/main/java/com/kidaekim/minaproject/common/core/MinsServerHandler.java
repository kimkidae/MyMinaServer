package com.kidaekim.minaproject.common.core;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kidaekim.minaproject.common.enums.HandleEvent;

public class MinsServerHandler implements IoHandler {
	private static Logger logger = LoggerFactory.getLogger(MinsServerHandler.class);

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		logger.error("session created. sessionId:{}, address:{} ", session.getId(), session.getRemoteAddress());
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
//		logger.info("sessionOpened");
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
//		logger.info("sessionClosed");
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		if(session.getIdleCount(status) > 10) {
			session.closeNow();
			return;
		}
		//TODO disconnect check
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		
	}

	/* 
	 * IoBuffer
	 * [handler/request(1) + protocol(2) + params]
	 * 
	 */
	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		try {
			if(!(message instanceof IoBuffer)) {
				logger.error("messageReceived fail. unknown message");
				session.closeNow();
				return;
			}
			IoBuffer buffer = (IoBuffer)message;
			HandleEvent handleEvent = HandleEvent.getEnum(buffer.get());
			if(handleEvent == null) {
				logger.error("messageReceived fail. unknown handleEvent");
				session.closeNow();
				return;
			}
			ServerController.get().receiveEvent(handleEvent, session, buffer);
		}catch(Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
			session.closeNow();
		}
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
//		logger.info("messageSent");
	}

	@Override
	public void inputClosed(IoSession session) throws Exception {
//		logger.info("inputClosed");
	}

}
