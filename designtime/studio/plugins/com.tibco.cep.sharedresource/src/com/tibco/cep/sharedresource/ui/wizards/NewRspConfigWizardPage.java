package com.tibco.cep.sharedresource.ui.wizards;

import org.eclipse.jface.viewers.IStructuredSelection;

import com.tibco.cep.sharedresource.ui.util.Messages;
import com.tibco.cep.sharedresource.ui.util.SharedResourceImages;

/*
@author ssailapp
@date Feb 22, 2010 7:05:57 PM
 */

public class NewRspConfigWizardPage extends NewSharedResourceWizardPage {

	private static final String PAGE_NAME = "NewRspConfigWizardPage";
	
	public NewRspConfigWizardPage(IStructuredSelection selection) {
		super(PAGE_NAME, selection);
        setTitle(Messages.getString("new.rspconfig.wizard.title"));
        setDescription(Messages.getString("new.rspconfig.wizard.desc"));
        setImageDescriptor(SharedResourceImages.getImageDescriptor(SharedResourceImages.IMG_SHAREDRES_WIZ_RSP)); 
        setFileExtension("sharedrsp");
	}	
}
