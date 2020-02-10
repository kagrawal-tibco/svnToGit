package com.tibco.cep.sharedresource.ui.wizards;

import com.tibco.cep.sharedresource.ui.util.Messages;

/*
@author ssailapp
@date Sep 25, 2009 4:47:41 PM
 */

public class NewJmsApplicationWizard extends NewSharedResourceWizard {

	private NewJmsApplicationWizardPage newJmsApplicationWizardPage;
	
	public NewJmsApplicationWizard() {
		setWindowTitle(Messages.getString("new.jmsapplication.wizard.title"));
	}
	
	@Override
	public void addPages() {
		newJmsApplicationWizardPage = new NewJmsApplicationWizardPage(selection);
        addPage(newJmsApplicationWizardPage);
        setWizardPage(newJmsApplicationWizardPage);
	}
}
