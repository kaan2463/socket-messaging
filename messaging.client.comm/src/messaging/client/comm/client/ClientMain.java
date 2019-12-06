package messaging.client.comm.client;

public class ClientMain {
	public static void main(String[] args) {
		Client client = new Client();
		client.start();
		for (int i = 0; i < 5; i++) {
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
