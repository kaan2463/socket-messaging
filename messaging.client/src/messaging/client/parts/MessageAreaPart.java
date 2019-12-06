package messaging.client.parts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class MessageAreaPart {
	Text text;

	public MessageAreaPart() {
		System.out.println("construct");
	}

	@Inject
	@Optional
	public void handleSendEvent(@UIEventTopic("SEND_OCCUR") Object obj) {
		text.setText((String) obj);
	}

	@PostConstruct
	public void buildPart(Shell shell) {
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		Composite textComposite = new Composite(shell, SWT.NONE);
		textComposite.setSize(400, 300);
		text = new Text(textComposite, SWT.NONE);
		text.setSize(300, 200);

		text.setText("Send");

	}

}
