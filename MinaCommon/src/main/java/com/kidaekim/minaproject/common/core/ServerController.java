package com.kidaekim.minaproject.common.core;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
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
public class ServerController {
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

	private static Logger logger = LoggerFactory.getLogger(ServerController.class);

	private IoAcceptor acceptor;
	private ConcurrentHashMap<Integer, MinaServer> servers = new ConcurrentHashMap<Integer, MinaServer>();//serverIndex, MinaServer

	/**
	 * bind min server
	 */
	public void bindAcceptor(){
		try {
			acceptor = new NioSocketAcceptor();
        	//acceptor.getFilterChain().addLast( "logger", new LoggingFilter() );
	        //acceptor.getFilterChain().addLast( "codec", new ProtocolCodecFilter( new TextLineCodecFactory( Charset.forName( "UTF-8" ))));

        	acceptor.setHandler( new MinsServerHandler() );
        	acceptor.getSessionConfig().setReadBufferSize( 2048 );
        	acceptor.getSessionConfig().setIdleTime( IdleStatus.BOTH_IDLE, 1 );

        	acceptor.bind( new InetSocketAddress(9090) );

    		logger.info("acceptor bind success");
		}catch(Exception e) {
			if(acceptor != null) try{acceptor.dispose();}catch (Exception e2) {}
    		logger.error("acceptor bind fail. {}", ExceptionUtils.getStackTrace(e));
		}
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

	/**
	 * receiveEvent 
	 * @param handleEvent
	 * @param session
	 * @param buffer
	 * @throws Exception 
	 */
	public void receiveEvent(HandleEvent handleEvent, IoSession session, IoBuffer buffer) throws Exception {
		switch(handleEvent) {
			case LOGIN :
				MinaServerType minaServerType = MinaServerType.getEnum(buffer.get());
				if(minaServerType == null) {
					throw new Exception("unknown minaServerType");//TODO LoginException
				}
				MinaServer minaServer;
				if(minaServerType == MinaServerType.GAME) {
					int serverIndex = buffer.getInt();
					minaServer = servers.get(serverIndex);
					if(minaServer == null) {
						throw new Exception("unknown serverIndex");//TODO LoginException
					}
				}else {
					//random server
					minaServer = null;
				}
				minaServer.receiveEvent(handleEvent, session, buffer);
				break;
			case LOGOUT :
				
				break;
			case REQUEST :
				//protocol
				short proto = buffer.getShort();
				Protocol protocol = Protocol.getProtocol(proto);
				if(protocol == null) {
					logger.error("unknown protocol code: {}", proto);
					session.closeNow();
					return;
				}
				break;
			default : 
				logger.error("messageReceived fail. bad handleEvent :{}", handleEvent.getValue());
				session.closeNow();
				return;
		}
	}

}
