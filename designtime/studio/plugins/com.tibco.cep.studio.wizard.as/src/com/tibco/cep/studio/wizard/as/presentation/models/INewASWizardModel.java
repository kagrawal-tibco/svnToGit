package com.tibco.cep.studio.wizard.as.presentation.models;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.wizard.IWizardPage;

import com.tibco.cep.studio.wizard.as.commons.beans.presentation.IModel;
import com.tibco.cep.studio.wizard.as.commons.utils.IContext;
import com.tibco.cep.studio.wizard.as.internal.utils.Messages;
import com.tibco.cep.studio.wizard.as.presentation.controllers.IASWizardPageController;
import com.tibco.cep.studio.wizard.as.services.api.IASService;
import com.tibco.cep.studio.wizard.as.wizard.IASWizardPage;

public interface INewASWizardModel extends IModel {

	public static final String _PROP_VALUE_WIZARD_WINDOW_TITLE     = Messages.getString("INewASWizardModel.wizard_window_title"); //$NON-NLS-1$

	// Property Name
	public static final String _PROP_NAME_WIZARD_WINDOW_TITLE = "wizardWindowTitle"; //$NON-NLS-1$
	public static final String _PROP_NAME_CONTEXT             = "context"; //$NON-NLS-1$

	IASService getASService();

	IProject getOwnerProject();

	String getWizardWindowTitle();

	void setWizardWindowTitle(String title);

	List<IWizardPage> getAllRawWizardPages();

	boolean isNeedsProgressMonitor();

	IContext getContext();

	void setContext(IContext context);

}
