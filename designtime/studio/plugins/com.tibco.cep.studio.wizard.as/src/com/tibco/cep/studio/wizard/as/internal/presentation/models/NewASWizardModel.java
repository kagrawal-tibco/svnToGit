package com.tibco.cep.studio.wizard.as.internal.presentation.models;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.wizard.IWizardPage;

import com.tibco.cep.studio.wizard.as.commons.beans.presentation.AModel;
import com.tibco.cep.studio.wizard.as.commons.utils.IContext;
import com.tibco.cep.studio.wizard.as.internal.wizard.WizardFactory;
import com.tibco.cep.studio.wizard.as.presentation.models.INewASWizardModel;
import com.tibco.cep.studio.wizard.as.services.api.IASService;

public class NewASWizardModel extends AModel implements INewASWizardModel {

	// Properties
	private String                                                                                                             wizardWindowTitle;
	private List<IWizardPage> rawPages;

	// Model Context
	private IASService                                                                                                         service;
	private IProject                                                                                                           project;
	private IContext                                                                                                           context;

	public NewASWizardModel(IASService service, IProject project, IContext context, String wizardWindowTitle) {
		this.service = service;
		this.project = project;
		this.context = context;
		this.wizardWindowTitle = wizardWindowTitle;
		initialize();
	}

	private void initialize() {
		initData();
	}

	private void initData() {
		this.rawPages = WizardFactory.createDefaultNewASWizardPages(service, project, context);
	}

	@Override
	public String getWizardWindowTitle() {
		return wizardWindowTitle;
	}

	@Override
	public void setWizardWindowTitle(String title) {
		String oldValue = this.wizardWindowTitle;
		String newValue = this.wizardWindowTitle = title;
		firePropertyChange(_PROP_NAME_WIZARD_WINDOW_TITLE, oldValue, newValue);
	}

	@Override
	public List<IWizardPage> getAllRawWizardPages() {
		return rawPages;
	}

	/**
	 * This is a read-only property.
	 */
	@Override
	public boolean isNeedsProgressMonitor() {
		return true;
	}

	@Override
	public IContext getContext() {
		return context;
	}

	@Override
	public void setContext(IContext context) {
		IContext oldValue = context;
		IContext newValue = this.context = context;
		firePropertyChange(_PROP_NAME_CONTEXT, oldValue, newValue);
	}

	@Override
    public IASService getASService() {
	    return service;
    }

	@Override
    public IProject getOwnerProject() {
	    return project;
    }

}
