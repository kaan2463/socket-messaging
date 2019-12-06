package messaging.client.comm.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import messaging.client.comm.constants.ClientConstants;
import messaging.client.comm.listeners.ClientSubject;

public class Client extends Thread implements ClientSubject {
	private Socket socket;
	private InputStream is;
	private OutputStream os;

	public Client() {
		try {
			socket = new Socket(ClientConstants.getHostname(), ClientConstants.getPort());
			this.is = socket.getInputStream();
			this.os = socket.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		start();
	}

	@Override
	public void run() {
		try {
			byte[] bytes = new byte[1024];
			int len;
			while ((len = is.read(bytes)) != -1) {
				String received = new String(bytes, 0, len);
				notifyListeners(received);
				System.out.println(received);
			}
			is.close();
			os.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void sendMessage(String msg) {
		try {
			os.write(msg.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void send(String msg) {
		sendMessage(msg);
	}

}
