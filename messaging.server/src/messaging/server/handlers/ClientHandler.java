package messaging.server.handlers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import messaging.server.MessagingServer;

public class ClientHandler extends Thread {
	private InputStream is;
	private OutputStream os;

	public ClientHandler(String name, InputStream is, OutputStream os) {
		super(name);
		this.os = os;
		this.is = is;
	}

	@Override
	public void run() {
		byte[] bytes = new byte[1024];
		int len;
		try {
			while ((len = is.read(bytes)) != -1) {
				String received = new String(bytes, 0, len);
				sendMessageToOtherClients(getName(), (getName() + " " + received).getBytes());
			}
			is.close();
			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			removeThisInstanceFromClients();
		}
	}

	private void removeThisInstanceFromClients() {
		MessagingServer.getHandlers().remove(this);
	}

	private static void sendMessageToOtherClients(String thisName, byte[] bytes) {
		MessagingServer.getHandlers().forEach(handler -> {
			if (!handler.getName().equals(thisName)) {
				try {
					handler.getOutputStream().write(bytes);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
	}

	private OutputStream getOutputStream() {
		return this.os;
	}

}