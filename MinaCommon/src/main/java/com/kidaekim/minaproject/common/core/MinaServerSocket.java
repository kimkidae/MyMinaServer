package com.kidaekim.minaproject.common.core;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoEventType;
import org.apache.mina.core.session.IoSessionConfig;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.executor.OrderedThreadPoolExecutor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MinaServerSocket {
	private static Logger logger = LoggerFactory.getLogger(MinaServerSocket.class);

	private int processorCount;
	private int corePoolSize;
	private int maximumPoolSize;
	private int readBufferSize;
	private int bothIdleTime;
	private int port;

	private IoAcceptor acceptor;
	private MinaServerListener minaServerListener;
	
	public MinaServerSocket(MinaServerListener minaServerListener) {
		this.minaServerListener = minaServerListener;
	}

    private static IoEventType[] ioEventTypes = null;
    static {
		ioEventTypes = new IoEventType[IoEventType.values().length-1];
        int i=0;
    	for(IoEventType ioEventType : IoEventType.values()) {
            if (ioEventType == IoEventType.SESSION_CREATED) continue;
    		ioEventTypes[i++] = ioEventType;
    	}
    }

	public void bind() throws Exception {
		try {
			acceptor = new NioSocketAcceptor(processorCount);

        	acceptor.setHandler( new MinaServerHandler(minaServerListener) );

        	IoSessionConfig ioSessionConfig = acceptor.getSessionConfig();
        	ioSessionConfig.setReadBufferSize(readBufferSize);
        	ioSessionConfig.setIdleTime(IdleStatus.BOTH_IDLE, bothIdleTime);

			//TODO encrypt
	        //acceptor.getFilterChain().addLast( "codec", new ProtocolCodecFilter( new TextLineCodecFactory( Charset.forName( "UTF-8" ))));

        	//Thread
        	ExecutorFilter filter = new ExecutorFilter(corePoolSize, maximumPoolSize, 30, TimeUnit.SECONDS, ioEventTypes);
			OrderedThreadPoolExecutor otp = (OrderedThreadPoolExecutor)filter.getExecutor();
			otp.prestartAllCoreThreads();

        	acceptor.getFilterChain().addLast("executor", filter);

        	acceptor.bind( new InetSocketAddress(port) );

    		logger.info("acceptor bind success");
		}catch(Exception e) {
			if(acceptor != null) try{acceptor.dispose();}catch (Exception e2) {}
    		logger.error("acceptor bind fail. {}", ExceptionUtils.getStackTrace(e));
    		throw e;
		}
	}

	public int getProcessorCount() {
		return processorCount;
	}

	public void setProcessorCount(int processorCount) {
		this.processorCount = processorCount;
	}

	public int getCorePoolSize() {
		return corePoolSize;
	}

	public void setCorePoolSize(int corePoolSize) {
		this.corePoolSize = corePoolSize;
	}

	public int getMaximumPoolSize() {
		return maximumPoolSize;
	}

	public void setMaximumPoolSize(int maximumPoolSize) {
		this.maximumPoolSize = maximumPoolSize;
	}

	public int getReadBufferSize() {
		return readBufferSize;
	}

	public void setReadBufferSize(int readBufferSize) {
		this.readBufferSize = readBufferSize;
	}

	public int getBothIdleTime() {
		return bothIdleTime;
	}

	public void setBothIdleTime(int bothIdleTime) {
		this.bothIdleTime = bothIdleTime;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public IoAcceptor getAcceptor() {
		return acceptor;
	}

	public void setAcceptor(IoAcceptor acceptor) {
		this.acceptor = acceptor;
	}
}
