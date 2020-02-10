package com.tibco.cep.sharedresource.ui.wizards;

import com.tibco.cep.sharedresource.ui.util.Messages;

/*
@author ssailapp
@date Sep 25, 2009 4:33:52 PM
 */

public class NewHttpConfigWizard extends NewSharedResourceWizard {

	private NewHttpConfigWizardPage newHttpConfigWizardPage;
	
	public NewHttpConfigWizard() {
		setWindowTitle(Messages.getString("new.httpconfig.wizard.title"));
	}
	
	@Override
	public void addPages() {
		newHttpConfigWizardPage = new NewHttpConfigWizardPage(selection);
        addPage(newHttpConfigWizardPage);
        setWizardPage(newHttpConfigWizardPage);
	}
}
