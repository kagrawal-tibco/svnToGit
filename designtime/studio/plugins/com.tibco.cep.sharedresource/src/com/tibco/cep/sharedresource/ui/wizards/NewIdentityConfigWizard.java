package com.tibco.cep.sharedresource.ui.wizards;

import com.tibco.cep.sharedresource.ui.util.Messages;

/*
@author ssailapp
@date Sep 25, 2009 4:33:52 PM
 */

public class NewIdentityConfigWizard extends NewSharedResourceWizard {

	private NewIdentityConfigWizardPage newIdentityConfigWizardPage;
	
	public NewIdentityConfigWizard() {
		setWindowTitle(Messages.getString("new.identityconfig.wizard.title"));
	}
	
	@Override
	public void addPages() {
		newIdentityConfigWizardPage = new NewIdentityConfigWizardPage(selection);
        addPage(newIdentityConfigWizardPage);
        setWizardPage(newIdentityConfigWizardPage);
	}
}
