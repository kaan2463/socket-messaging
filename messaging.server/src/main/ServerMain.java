package main;

import messaging.server.MessagingServer;

public class ServerMain {

	public static void main(String[] args) {
		MessagingServer server = new MessagingServer();
		server.start();
	}

}