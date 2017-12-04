package com.kidaekim.minaproject.common.core;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kidaekim.minaproject.common.commons.MinaUtils;
import com.kidaekim.minaproject.common.enums.MinaServerType;
import com.kidaekim.minaproject.common.enums.MinaSessionType;
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

	private ConcurrentHashMap<Integer, MinaServer> servers = new ConcurrentHashMap<Integer, MinaServer>();//serverIndex, MinaServer

	private MinaServerSocket minaServerSocket;

	public void bindServer() throws Exception {
		//TODO 설정 파일로
		minaServerSocket = new MinaServerSocket(this);

		minaServerSocket.setProcessorCount(4);
		minaServerSocket.setCorePoolSize(30);
		minaServerSocket.setMaximumPoolSize(100);

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
	public void receiveRequestEvent(Protocol protocol, IoSession session, IoBuffer buffer) throws Exception {
		MinaServer minaServer;
		Integer serverIndex;
		if(protocol == Protocol.USER_LOGIN) {
			serverIndex = MinaUtils.getSessionData(session, MinaSessionType.SERVER_INDEX);
			if(serverIndex != null) throw new Exception("already login session");

			MinaServerType minaServerType = MinaServerType.getEnum(buffer.get());
			if(minaServerType == MinaServerType.GAME) {
				minaServer = servers.get(buffer.getInt());//serverIndex
			}else {
				//random server
				minaServer = null;
			}
		}else {
			serverIndex = MinaUtils.getSessionData(session, MinaSessionType.SERVER_INDEX);
			if(serverIndex == null) throw new IllegalAccessError();
			minaServer = servers.get(serverIndex);
		}

		minaServer.receiveRequest(protocol, session, buffer);
	}

}
