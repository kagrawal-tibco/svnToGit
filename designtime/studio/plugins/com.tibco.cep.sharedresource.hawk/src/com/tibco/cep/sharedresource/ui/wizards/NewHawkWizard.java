package com.tibco.cep.sharedresource.ui.wizards;

import com.tibco.cep.sharedresource.ui.util.hawk.Messages;

public class NewHawkWizard extends NewSharedResourceWizard {
	private NewHawkWizardPage newHawkWizardPage;

	public NewHawkWizard() {
		setWindowTitle(Messages.getString("new.hawk.wizard.title", new Object[0]));
	}

	public void addPages() {
		newHawkWizardPage = new NewHawkWizardPage(this.selection);
		addPage(newHawkWizardPage);
		setWizardPage(newHawkWizardPage);
	}
}