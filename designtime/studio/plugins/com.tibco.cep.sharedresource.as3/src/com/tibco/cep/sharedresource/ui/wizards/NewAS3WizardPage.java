package com.tibco.cep.sharedresource.ui.wizards;

import org.eclipse.jface.viewers.IStructuredSelection;

import com.tibco.cep.sharedresource.ui.util.AS3SharedResourceImages;
import com.tibco.cep.sharedresource.ui.util.as3.Messages;

public class NewAS3WizardPage extends NewSharedResourceWizardPage {
	private static final String PAGE_NAME = "NewAS3WizardPage";

	public NewAS3WizardPage(IStructuredSelection selection) {
		super(PAGE_NAME, selection);
		setTitle(Messages.getString("new.as3.wizard.title", new Object[0]));
		setDescription(Messages.getString("new.as3.wizard.desc", new Object[0]));
		setImageDescriptor(AS3SharedResourceImages.getImageDescriptor(AS3SharedResourceImages.IMG_SHAREDRES_WIZ_AS3));
		setFileExtension("as3");
	}
}