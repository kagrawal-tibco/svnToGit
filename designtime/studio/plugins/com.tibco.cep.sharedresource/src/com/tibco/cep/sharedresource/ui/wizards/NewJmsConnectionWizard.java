package com.tibco.cep.sharedresource.ui.wizards;

import com.tibco.cep.sharedresource.ui.util.Messages;

/*
@author ssailapp
@date Sep 25, 2009 4:47:41 PM
 */

public class NewJmsConnectionWizard extends NewSharedResourceWizard {

	private NewJmsConnectionWizardPage newJmsConnectionWizardPage;
	
	public NewJmsConnectionWizard() {
		setWindowTitle(Messages.getString("new.jmsconnection.wizard.title"));
	}
	
	@Override
	public void addPages() {
		newJmsConnectionWizardPage = new NewJmsConnectionWizardPage(selection);
        addPage(newJmsConnectionWizardPage);
        setWizardPage(newJmsConnectionWizardPage);
	}
}
