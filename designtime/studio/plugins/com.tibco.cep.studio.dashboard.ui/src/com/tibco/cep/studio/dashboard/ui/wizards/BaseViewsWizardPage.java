package com.tibco.cep.studio.dashboard.ui.wizards;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;

public abstract class BaseViewsWizardPage extends WizardPage {
	
	private boolean populated;

	public BaseViewsWizardPage(String pageName, String title, ImageDescriptor titleImage) {
		super(pageName, title, titleImage);
	}

	public BaseViewsWizardPage(String pageName) {
		super(pageName);
	}
	
	public void setPopulated(boolean populated) {
		this.populated = populated;
	}
	
	public boolean isPopulated() {
		return populated;
	}

	public void populateControl(){
		
	}
}
