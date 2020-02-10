package com.tibco.cep.studio.dashboard.ui.wizards.page;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.resource.ImageDescriptor;

import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.wizards.BaseViewsWizardPage;

public abstract class PageTemplateWizardPage extends BaseViewsWizardPage {

	protected IProject project;

	protected List<LocalElement> selectedComponents;

	public PageTemplateWizardPage(String pageName, String title, ImageDescriptor titleImage) {
		super(pageName, title, titleImage);
	}

	public PageTemplateWizardPage(String pageName) {
		super(pageName);
	}

	public void setSelectedComponents(List<LocalElement> selectedComponents) {
		this.selectedComponents = selectedComponents;
	}

	public IProject getProject() {
		return project;
	}

	public void setProject(IProject project) {
		this.project = project;
	}

	public boolean canFinish() {
		return true;
	}

}
