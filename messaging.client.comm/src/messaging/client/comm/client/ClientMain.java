package messaging.client.comm.client;

public class ClientMain {
	public static void main(String[] args) {
		MessagingClient client = new MessagingClient();
		for (int i = 0; i < 1000; i++) {
			try {
				Thread.sleep(2000L);
				client.send("hello" + i);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
