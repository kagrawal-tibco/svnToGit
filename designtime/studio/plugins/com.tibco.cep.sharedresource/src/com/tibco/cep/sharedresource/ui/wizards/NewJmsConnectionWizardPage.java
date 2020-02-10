package com.tibco.cep.sharedresource.ui.wizards;

import org.eclipse.jface.viewers.IStructuredSelection;

import com.tibco.cep.sharedresource.ui.util.Messages;
import com.tibco.cep.sharedresource.ui.util.SharedResourceImages;

/*
@author ssailapp
@date Sep 25, 2009 4:36:53 PM
 */

public class NewJmsConnectionWizardPage extends NewSharedResourceWizardPage {

	private static final String PAGE_NAME = "NewJmsConnectionWizardPage";
	
	public NewJmsConnectionWizardPage(IStructuredSelection selection) {
		super(PAGE_NAME, selection);
        setTitle(Messages.getString("new.jmsconnection.wizard.title"));
        setDescription(Messages.getString("new.jmsconnection.wizard.desc"));
        setImageDescriptor(SharedResourceImages.getImageDescriptor(SharedResourceImages.IMG_SHAREDRES_WIZ_JMS_CON)); 
        setFileExtension("sharedjmscon");
	}

}
