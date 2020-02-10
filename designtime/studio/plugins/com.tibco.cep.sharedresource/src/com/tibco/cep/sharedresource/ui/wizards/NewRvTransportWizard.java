package com.tibco.cep.sharedresource.ui.wizards;

import com.tibco.cep.sharedresource.ui.util.Messages;

/*
@author ssailapp
@date Sep 25, 2009 4:33:52 PM
 */

public class NewRvTransportWizard extends NewSharedResourceWizard {

	private NewRvTransportWizardPage newRvTransportWizardPage;
	
	public NewRvTransportWizard() {
		setWindowTitle(Messages.getString("new.rvtransport.wizard.title"));
	}
	
	@Override
	public void addPages() {
		newRvTransportWizardPage = new NewRvTransportWizardPage(selection);
        addPage(newRvTransportWizardPage);
        setWizardPage(newRvTransportWizardPage);
	}
}
