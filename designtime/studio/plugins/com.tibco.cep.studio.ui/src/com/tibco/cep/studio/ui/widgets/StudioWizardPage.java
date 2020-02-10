package com.tibco.cep.studio.ui.widgets;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;

/**
 * 
 * @author sasahoo
 *
 */
public abstract class StudioWizardPage extends WizardPage implements IStudioWizardPage {

	protected StudioWizardPage(String pageName) {
		super(pageName);
		// TODO Auto-generated constructor stub
	}

	protected StudioWizardPage(String pageName, String title,
			ImageDescriptor titleImage) {
		super(pageName, title, titleImage);
		// TODO Auto-generated constructor stub
	}
}
