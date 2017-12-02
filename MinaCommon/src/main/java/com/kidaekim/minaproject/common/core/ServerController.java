package com.kidaekim.minaproject.common.core;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kidaekim.minaproject.common.enums.HandleEvent;
import com.kidaekim.minaproject.common.enums.MinaServerType;
import com.kidaekim.minaproject.common.net.Protocol;

/**
 * mina server controller
 * @author kkd
 *
 */
public class ServerController implements MinaServerListener{
	protected static Logger logger = LoggerFactory.getLogger(ServerController.class);
	private volatile static ServerController controller;
	private ServerController() {}

	public static ServerController get() {
		if(controller == null) {
			synchronized (ServerController.class) {
				if(controller == null) {
					controller = new ServerController();
				}
			}
		}
		return controller;
	}

	protected ConcurrentHashMap<Integer, MinaServer> servers = new ConcurrentHashMap<Integer, MinaServer>();//serverIndex, MinaServer

	private MinaServerSocket minaServerSocket;

	public void bindServer() throws Exception {
		//TODO 설정 파일로
		minaServerSocket = new MinaServerSocket(this);

		minaServerSocket.setProcessorCount(4);
		minaServerSocket.setCorePoolSize(100);
		minaServerSocket.setMaximumPoolSize(300);

		minaServerSocket.setReadBufferSize(2048);
		minaServerSocket.setBothIdleTime(10);

		minaServerSocket.setPort(9090);
		minaServerSocket.bind();
	}

	/**
	 * add server
	 * @param minaServer
	 * @return
	 */
	public void addServer(MinaServer minaServer) {
		servers.put(minaServer.hashCode(), minaServer);

		logger.info("{} server {} start success", minaServer.getMinaServerType(), minaServer.hashCode());
	}

	@Override
	public void receiveHandleEvent(MinaServerType minaServerType, int serverIndex, HandleEvent handleEvent, IoSession session, IoBuffer buffer) throws Exception {
		MinaServer minaServer = null;
		switch(handleEvent) {
			case LOGIN :
				if(minaServerType == MinaServerType.GAME) {
					minaServer = servers.get(serverIndex);
				}else {
					//random server
					minaServer = null;
				}
				break;
			case LOGOUT :
				//session minaServer
				break;
			default : 
				logger.error("messageReceived fail. bad handleEvent :{}", handleEvent.getValue());
				session.closeNow();
				return;
		}
		if(minaServer == null) {
			//TODO message send to session 
			throw new Exception("minaServer is null");
		}
		minaServer.receiveEvent(handleEvent, session, buffer);
	}

	@Override
	public void receiveRequestEvent(Protocol protocol, IoSession session, IoBuffer buffer) {
		
		
		
	}

}
