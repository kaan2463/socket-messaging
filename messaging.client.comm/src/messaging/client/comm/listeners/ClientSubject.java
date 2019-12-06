package messaging.client.comm.listeners;

import java.util.ArrayList;
import java.util.List;

public interface ClientSubject {
	public List<ClientListener> listeners = new ArrayList<>();

	default void addListener(ClientListener listener) {
		listeners.add(listener);
	}

	default void notifyListeners(String msg) {
		listeners.forEach(item -> item.receiveMessage(msg));
	}

	void send(String msg);
}
