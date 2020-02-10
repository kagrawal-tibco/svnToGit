package com.tibco.cep.sharedresource.ui.wizards;

import com.tibco.cep.sharedresource.ui.util.ftl.Messages;

public class NewFTLWizard extends NewSharedResourceWizard {
	private NewFTLWizardPage newFTLWizardPage;

	public NewFTLWizard() {
		setWindowTitle(Messages.getString("new.ftl.wizard.title", new Object[0]));
	}

	public void addPages() {
		newFTLWizardPage = new NewFTLWizardPage(this.selection);
		addPage(newFTLWizardPage);
		setWizardPage(newFTLWizardPage);
	}
}