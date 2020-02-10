package com.tibco.cep.sharedresource.ui.wizards;

import com.tibco.cep.sharedresource.ui.util.Messages;

/*
@author ssailapp
@date Sep 25, 2009 4:47:41 PM
 */

public class NewJndiConfigWizard extends NewSharedResourceWizard {

	private NewJndiConfigWizardPage newJndiConfigWizardPage;
	
	public NewJndiConfigWizard() {
		setWindowTitle(Messages.getString("new.jndiconfig.wizard.title"));
	}
	
	@Override
	public void addPages() {
		newJndiConfigWizardPage = new NewJndiConfigWizardPage(selection);
        addPage(newJndiConfigWizardPage);
        setWizardPage(newJndiConfigWizardPage);
	}
}
