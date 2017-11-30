package com.kidaekim.minaproject;

import com.kidaekim.minaproject.auth.MinaAuthServer;
import com.kidaekim.minaproject.common.core.ServerController;
import com.kidaekim.minproject.MinaGameServer;

public class MinaServerRuncher {

	public static void main(String args[]) {
		MinaAuthServer authServer = new MinaAuthServer();
		ServerController.get().addServer(authServer);

		MinaGameServer gameServer1 = new MinaGameServer();
		ServerController.get().addServer(gameServer1);

		MinaGameServer gameServer2 = new MinaGameServer();
		ServerController.get().addServer(gameServer2);

		ServerController.get().bindAcceptor();
	}
}
