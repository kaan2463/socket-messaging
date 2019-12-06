package messaging.client.comm.constants;

public final class ClientConstants {
	private final static int PORT = 9000;
	private final static String hostName = "127.0.0.1";

	public static int getPort() {
		return PORT;
	}

	public static String getHostname() {
		return hostName;
	}

}
