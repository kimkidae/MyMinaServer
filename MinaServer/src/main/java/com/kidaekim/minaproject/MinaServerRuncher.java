package com.kidaekim.minaproject;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kidaekim.minaproject.auth.MinaAuthServer;
import com.kidaekim.minaproject.common.core.ServerController;
import com.kidaekim.minproject.MinaGameServer;

public class MinaServerRuncher {
	private static Logger logger = LoggerFactory.getLogger(MinaServerRuncher.class);

	public static void main(String args[]) {
		MinaAuthServer authServer = new MinaAuthServer();
		ServerController.get().addServer(authServer);

		MinaGameServer gameServer1 = new MinaGameServer();
		ServerController.get().addServer(gameServer1);

		MinaGameServer gameServer2 = new MinaGameServer();
		ServerController.get().addServer(gameServer2);

		try {
			ServerController.get().bindServer();
		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		}
	}
}
