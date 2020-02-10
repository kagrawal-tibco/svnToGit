package com.tibco.cep.studio.wizard.hawk.pages;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.studio.wizard.hawk.HawkNewWizard;
import com.tibco.cep.studio.wizard.hawk.HawkWizardConstants;
import com.tibco.cep.studio.wizard.hawk.util.Messages;

public class SetNamePage extends HawkWizardPage {
	private boolean showDestination = false;
	private String destinationName = "NewDestination_0";
	private String eventName = "NewEvent_0";
	private String eventFolderName;

	private Label destLabel;
	private Text destinationText;

	public SetNamePage(String pageName) {
		super(pageName);
		this.setTitle(Messages.getString("hawk.wizard.page.setName.title"));
		this.setDescription(Messages.getString("hawk.wizard.page.setName.desc"));
		this.setPageComplete(false);
	}

	public String getEventFolderName() {
		return eventFolderName;
	}

	public void setEventFolderName(String eventFolderName) {
		this.eventFolderName = eventFolderName;
	}

	public String getDestinationName() {
		return destinationName;
	}

	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public void createControl(Composite parent) {

		Composite container = new Composite(parent, SWT.NULL);

		GridLayout layout = new GridLayout();
		layout.numColumns = 2;

		container.setLayout(layout);

		GridData gd = new GridData(GridData.FILL_HORIZONTAL);

		destLabel = new Label(container, SWT.LEFT);
		destLabel.setText("Destination Name:");
		destLabel.setVisible(showDestination);

		destinationText = new Text(container, SWT.BORDER);
		destinationText.setLayoutData(gd);
		destinationText.setText(destinationName);
		destinationText.setVisible(showDestination);

		Label eventFolderLabel = new Label(container, SWT.LEFT);
		eventFolderLabel.setText("Event Folder:");

		final Text eventFolderText = new Text(container, SWT.BORDER);
		eventFolderText.setLayoutData(gd);
		eventFolderText.setText(eventFolderName);

		Label eventLabel = new Label(container, SWT.LEFT);
		eventLabel.setText("Event Name:");

		final Text eventText = new Text(container, SWT.BORDER);
		eventText.setLayoutData(gd);
		eventText.setText(eventName);

		ModifyListener listener = new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {

				Text text = (Text) e.getSource();

				String name = text.getText();

				if (text == destinationText) {
					destinationName = name;
				} else if (text == eventText) {
					eventName = name;
				} else if (text == eventFolderText) {
					eventFolderName = name;
				}

				if (validateString(destinationText.getText()) && validateString(eventText.getText())
						&& validateString(eventFolderText.getText())) {
					updateStatus(null);
				} else {
					updateStatus(Messages.getString("hawk.wizard.page.setName.tip"));
				}
			}

		};

		destinationText.addModifyListener(listener);
		eventText.addModifyListener(listener);
		eventFolderText.addModifyListener(listener);

		setControl(container);
	}

	public void prepareData() {

		int choice = ((HawkNewWizard) getWizard()).getWizardType();
		if (choice == 0) {
			showDestination = true;
			eventFolderName = HawkWizardConstants.EVENT_FOLDER;
		} else {
			showDestination = false;
			eventFolderName = HawkWizardConstants.ARGUMENTS_FOLDER;
		}
		if (destinationText != null) {
			destLabel.setVisible(showDestination);
			destinationText.setVisible(showDestination);
		}
		setPageComplete(true);
		setPrepared(true);
	}

	private boolean validateString(String str) {
		if (str != null&&str.length()>0) {
			char[] chars = str.toCharArray();
			if (!Character.isLetter(chars[0]) && chars[0] != '_')
				return false;
			for (int i = 1; i < chars.length; i++) {
				char ch = chars[i];

				if (!Character.isLetterOrDigit(ch) && ch != '_') {
					return false;
				}
			}
			return true;
		}else{
			return false;
		}
		
	}

}
