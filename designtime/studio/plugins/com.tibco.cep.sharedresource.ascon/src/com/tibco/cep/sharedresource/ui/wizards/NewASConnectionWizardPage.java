package com.tibco.cep.sharedresource.ui.wizards;

import org.eclipse.jface.viewers.IStructuredSelection;

import com.tibco.cep.sharedresource.ui.util.ASConnectionSharedResourceImages;
import com.tibco.cep.sharedresource.ui.util.ascon.Messages;

/*
@author Huabin Zhang (huzhang@tibco-support.com)
@date Feb 23, 2012 3:28:21 PM
 */

public class NewASConnectionWizardPage extends NewSharedResourceWizardPage {

	private static final String PAGE_NAME = "NewAsConfigWizardPage";
	
	public NewASConnectionWizardPage(IStructuredSelection selection) {
		super(PAGE_NAME, selection);
        setTitle(Messages.getString("new.asconnection.wizard.title"));
        setDescription(Messages.getString("new.asconnection.wizard.desc"));
        setImageDescriptor(ASConnectionSharedResourceImages.getImageDescriptor(ASConnectionSharedResourceImages.IMG_SHAREDRES_WIZ_AS_CON)); 
        setFileExtension("sharedascon");
	}

}
