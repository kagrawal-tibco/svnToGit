package com.tibco.cep.sharedresource.ui.wizards;

import org.eclipse.jface.viewers.IStructuredSelection;

import com.tibco.cep.sharedresource.ui.util.HawkSharedResourceImages;
import com.tibco.cep.sharedresource.ui.util.hawk.Messages;

public class NewHawkWizardPage extends NewSharedResourceWizardPage {
	private static final String PAGE_NAME = "NewHawkWizardPage";

	public NewHawkWizardPage(IStructuredSelection selection) {
		super(PAGE_NAME, selection);
		setTitle(Messages.getString("new.hawk.wizard.title", new Object[0]));
		setDescription(Messages.getString("new.hawk.wizard.desc", new Object[0]));
		setImageDescriptor(HawkSharedResourceImages.getImageDescriptor(HawkSharedResourceImages.IMG_SHAREDRES_WIZ_HAWK));
		setFileExtension("hawk");
	}
}