package messaging.client.comm;

import org.eclipse.e4.core.di.InjectorFactory;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import messaging.client.comm.client.Client;
import messaging.client.comm.listeners.ClientSubject;

public class Activator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		InjectorFactory.getDefault().addBinding(ClientSubject.class).implementedBy(Client.class);
	}

	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

}
