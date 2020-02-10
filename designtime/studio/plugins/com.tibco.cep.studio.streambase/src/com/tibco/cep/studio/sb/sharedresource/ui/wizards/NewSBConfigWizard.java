package com.tibco.cep.studio.sb.sharedresource.ui.wizards;

import com.tibco.cep.sharedresource.ui.util.Messages;
import com.tibco.cep.sharedresource.ui.wizards.NewSharedResourceWizard;

/*
@author ssailapp
@date Sep 25, 2009 4:33:52 PM
 */

public class NewSBConfigWizard extends NewSharedResourceWizard {

	private NewSBConfigWizardPage newSBConfigWizardPage;
	
	public NewSBConfigWizard() {
		setWindowTitle(Messages.getString("new.sbconfig.wizard.title"));
	}
	
	@Override
	public void addPages() {
		newSBConfigWizardPage = new NewSBConfigWizardPage(selection);
        addPage(newSBConfigWizardPage);
        setWizardPage(newSBConfigWizardPage);
	}
}
