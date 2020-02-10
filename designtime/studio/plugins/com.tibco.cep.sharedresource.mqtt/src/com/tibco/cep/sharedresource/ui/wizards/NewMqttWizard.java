package com.tibco.cep.sharedresource.ui.wizards;

import com.tibco.cep.sharedresource.ui.util.mqtt.Messages;

/**
 * @author ssinghal
 *
 */
public class NewMqttWizard extends NewSharedResourceWizard {
	
	private NewMqttWizardPage newMqttWizardPage;

	public NewMqttWizard() {
		setWindowTitle(Messages.getString("new.mqtt.wizard.title", new Object[0]));
	}

	public void addPages() {
		newMqttWizardPage = new NewMqttWizardPage(this.selection);
		addPage(newMqttWizardPage);
		setWizardPage(newMqttWizardPage);
	}

}
