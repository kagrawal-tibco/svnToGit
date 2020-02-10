package com.tibco.cep.studio.sb.sharedresource.ui.wizards;

import org.eclipse.jface.viewers.IStructuredSelection;

import com.tibco.cep.sharedresource.ui.util.Messages;
import com.tibco.cep.sharedresource.ui.util.SharedResourceImages;
import com.tibco.cep.sharedresource.ui.wizards.NewSharedResourceWizardPage;

/*
 */

public class NewSBConfigWizardPage extends NewSharedResourceWizardPage {

	private static final String PAGE_NAME = "NewSBConfigWizardPage";
	
	public NewSBConfigWizardPage(IStructuredSelection selection) {
		super(PAGE_NAME, selection);
        setTitle(Messages.getString("new.sbconfig.wizard.title"));
        setDescription(Messages.getString("new.sbconfig.wizard.desc"));
        setImageDescriptor(SharedResourceImages.getImageDescriptor(SharedResourceImages.IMG_SHAREDRES_WIZ_SB)); 
        setFileExtension("sharedsb");
	}

}
