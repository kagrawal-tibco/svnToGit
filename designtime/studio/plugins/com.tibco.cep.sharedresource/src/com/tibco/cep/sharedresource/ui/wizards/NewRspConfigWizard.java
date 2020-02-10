package com.tibco.cep.sharedresource.ui.wizards;

import com.tibco.cep.sharedresource.ui.util.Messages;

/*
@author ssailapp
@date Feb 22, 2010 7:05:06 PM
 */

public class NewRspConfigWizard extends NewSharedResourceWizard {

	private NewRspConfigWizardPage newRspConfigWizardPage;
	
	public NewRspConfigWizard() {
		setWindowTitle(Messages.getString("new.rspconfig.wizard.title"));
	}
	
	@Override
	public void addPages() {
		newRspConfigWizardPage = new NewRspConfigWizardPage(selection);
        addPage(newRspConfigWizardPage);
        setWizardPage(newRspConfigWizardPage);
	}
}
