package com.tibco.cep.sharedresource.ui.wizards;

import com.tibco.cep.sharedresource.ui.util.as3.Messages;

public class NewAS3Wizard extends NewSharedResourceWizard {
	private NewAS3WizardPage newAS3WizardPage;

	public NewAS3Wizard() {
		setWindowTitle(Messages.getString("new.as3.wizard.title", new Object[0]));
	}

	public void addPages() {
		newAS3WizardPage = new NewAS3WizardPage(this.selection);
		addPage(newAS3WizardPage);
		setWizardPage(newAS3WizardPage);
	}
}