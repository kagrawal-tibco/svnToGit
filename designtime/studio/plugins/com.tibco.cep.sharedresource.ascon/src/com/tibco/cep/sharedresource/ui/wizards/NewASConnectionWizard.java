package com.tibco.cep.sharedresource.ui.wizards;

import com.tibco.cep.sharedresource.ui.util.ascon.Messages;

/*
@author Huabin Zhang (huzhang@tibco-support.com)
@date Feb 23, 2012 3:25:15 PM
 */

public class NewASConnectionWizard extends NewSharedResourceWizard {

	private NewASConnectionWizardPage newAsConfigWizardPage;
	
	public NewASConnectionWizard() {
		setWindowTitle(Messages.getString("new.asconnection.wizard.title"));
	}
	
	@Override
	public void addPages() {
		newAsConfigWizardPage = new NewASConnectionWizardPage(selection);
        addPage(newAsConfigWizardPage);
        setWizardPage(newAsConfigWizardPage);
	}
}

