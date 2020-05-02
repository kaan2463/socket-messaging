package messaging.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import messaging.server.constants.ServerConstants;
import messaging.server.handlers.ClientHandler;

public class MessagingServer extends Thread {

	private static Vector<ClientHandler> handlers = new Vector<>();

	@Override
	public void run() {
		startServer();
	}

	public void startServer() {
		try (ServerSocket serverSocket = new ServerSocket(ServerConstants.TCP_SERVER_PORT)) {
			int i = 0;
			while (true) {
				Socket socket = serverSocket.accept();
				InputStream inputStream = socket.getInputStream();
				OutputStream outputStream = socket.getOutputStream();
				addHandler(new ClientHandler("client_" + i, inputStream, outputStream)).start();
				i++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Vector<ClientHandler> getHandlers() {
		return handlers;
	}

	public static ClientHandler addHandler(ClientHandler handler) {
		handlers.add(handler);
		return handler;
	}

}
