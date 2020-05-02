package messaging.client.comm.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import messaging.client.comm.constants.ClientConstants;
import messaging.client.comm.listeners.ClientSubject;
import messaging.common.ui.factory.UiFactory;

public class MessagingClient implements ClientSubject {
	private static final Log LOGGER = LogFactory.getLog(MessagingClient.class);

	private Socket socket;
	private InputStream is;
	private OutputStream os;

	private Thread connectionProviderThread = null;
	/**
	 * @formatter:off
	 * This thread has to live as long as the application, 
	 * if connection is successful.
	 * @formatter:on
	 */
	private Thread receiverThread = null;

	private Runnable receiverRunnable = () -> {
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
			LOGGER.error(e.getLocalizedMessage(), e);
		}
	};

	@SuppressWarnings("deprecation") // use better way to kill thread
	private Runnable connectionProviderRunnable = () -> {
		try {
			socket = new Socket(ClientConstants.HOST_NAME, ClientConstants.PORT);
			this.is = socket.getInputStream();
			this.os = socket.getOutputStream();
		} catch (ConnectException e) {
			LOGGER.error(e.getLocalizedMessage(), e);
		} catch (IOException e) {
			LOGGER.error(e.getLocalizedMessage(), e);
		}

		if (socket != null && socket.isConnected()) {
			LOGGER.info("Successfully connected to Messaging Server!");
			if (receiverThread != null) {
				receiverThread.stop();
			}
			receiverThread = new Thread(receiverRunnable, "ReceiverThread");
		} else {
			final String message = "Application will try to reconnect after "
					+ ClientConstants.RECONNECT_TIME_IN_SECONDS + " seconds!";

			if (!UiFactory.createWarningDialog("WARNING", message))
				// FIXME: return is not a good approach, it can be cause of missing condition!
				return;

			try {
				LOGGER.info(message);
				Thread.sleep(ClientConstants.RECONNECT_TIME_IN_SECONDS);
				connectionProviderThread.run();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

	public MessagingClient() {
		connectionProviderThread = new Thread(connectionProviderRunnable, "ConnectionProviderThread");
		connectionProviderThread.start();
	}

	private void sendMessage(String msg) {
		try {
			os.write(msg.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOGGER.error(e.getLocalizedMessage(), e);
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error(e.getLocalizedMessage(), e);
		}
	}

	@Override
	public void send(String msg) {
		sendMessage(msg);
	}

}
