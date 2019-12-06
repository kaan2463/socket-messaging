package messaging.client.parts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import messaging.client.comm.listeners.ClientListener;
import messaging.client.comm.listeners.ClientSubject;

public class TypingAreaPart extends ClientListener {

	private Text messageBoard;
	private Text textBox;

	@Inject
	public TypingAreaPart(@Optional ClientSubject clientSubject) {
		super(clientSubject);
	}

	@Override
	public void receiveMessage(String msg) {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				messageBoard.append(msg);
				messageBoard.append("\n");
			}
		});

	}

	@PostConstruct
	public void buildPart(Composite parent) {
		if (clientSubject == null) {
			throw new NullPointerException("There may be injection problem!");
		}

		parent.setLayout(new GridLayout(1, false));

		Composite messageBoardComposite = new Composite(parent, SWT.BORDER);
		messageBoardComposite.setLayout(new FillLayout());
		messageBoardComposite.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		messageBoard = new Text(messageBoardComposite, SWT.MULTI | SWT.V_SCROLL);
		messageBoard.setEditable(false);

		Composite textBoxComposite = new Composite(parent, SWT.BORDER);
		textBoxComposite.setLayout(new GridLayout(3, false));
		textBoxComposite.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false));

		GridData boxData = new GridData(GridData.FILL, GridData.CENTER, true, false);
		textBox = new Text(textBoxComposite, SWT.BORDER);
		textBox.setLayoutData(boxData);

		textBox.addListener(SWT.Traverse, new Listener() {

			@Override
			public void handleEvent(Event event) {
				clientSubject.send(textBox.getText());
				messageBoard.append(textBox.getText());
				messageBoard.append("\n");
				textBox.setText("");
			}
		});

		Button sendButton = new Button(textBoxComposite, SWT.PUSH);
		sendButton.setText("Send");
		GridData buttonData = new GridData(SWT.NONE);
		sendButton.setLayoutData(buttonData);

		sendButton.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				if (textBox.getText() == null || textBox.getText().isBlank()) {
					return;
				}
				clientSubject.send(textBox.getText());
				messageBoard.append(textBox.getText());
				messageBoard.append("\n");
				textBox.setText("");
			}
		});

		Button clearButton = new Button(textBoxComposite, SWT.PUSH);
		clearButton.setText("clear");
		GridData clearButtonData = new GridData(SWT.NONE);
		clearButton.setLayoutData(clearButtonData);

		clearButton.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				messageBoard.setText("");
			}
		});

	}

}
