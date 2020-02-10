package com.tibco.cep.sharedresource.ui.wizards;

import org.eclipse.jface.viewers.IStructuredSelection;

import com.tibco.cep.sharedresource.ui.util.FTLSharedResourceImages;
import com.tibco.cep.sharedresource.ui.util.ftl.Messages;

public class NewFTLWizardPage extends NewSharedResourceWizardPage {
	private static final String PAGE_NAME = "NewFTLWizardPage";

	public NewFTLWizardPage(IStructuredSelection selection) {
		super(PAGE_NAME, selection);
		setTitle(Messages.getString("new.ftl.wizard.title", new Object[0]));
		setDescription(Messages.getString("new.ftl.wizard.desc", new Object[0]));
		setImageDescriptor(FTLSharedResourceImages.getImageDescriptor(FTLSharedResourceImages.IMG_SHAREDRES_WIZ_FTL));
		setFileExtension("ftl");
	}
}