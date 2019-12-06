package messaging.client.comm.listeners;

public abstract class ClientListener {
	protected ClientSubject clientSubject;

	public ClientListener(ClientSubject clientSubject) {
		this.clientSubject = clientSubject;
		clientSubject.addListener(this);
	}

	public abstract void receiveMessage(String msg);

}
