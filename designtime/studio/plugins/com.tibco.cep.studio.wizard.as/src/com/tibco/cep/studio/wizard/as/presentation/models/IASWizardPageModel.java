package com.tibco.cep.studio.wizard.as.presentation.models;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.resource.ImageDescriptor;

import com.tibco.cep.studio.wizard.as.commons.beans.presentation.IModel;
import com.tibco.cep.studio.wizard.as.commons.utils.IContext;
import com.tibco.cep.studio.wizard.as.wizard.IASWizardPage;

public interface IASWizardPageModel extends IModel {

	public static final String _PROP_NAME_WIZARD_PAGE_NAME                          = "wizardPageName";                      //$NON-NLS-1$
	public static final String _PROP_NAME_WIZARD_PAGE_TITLE                         = "wizardPageTitle";                     //$NON-NLS-1$
	public static final String _PROP_NAME_WIZARD_PAGE_TITLE_IMAGE                   = "wizardPageTitleImageDescriptor";      //$NON-NLS-1$
	public static final String _PROP_NAME_WIZARD_PAGE_DESCRIPTION                   = "wizardPageDescription";               //$NON-NLS-1$
	public static final String _PROP_NAME_WIZARD_PAGE_ERROR_MESSAGE                 = "wizardPageErrorMessage";              //$NON-NLS-1$
	public static final String _PROP_NAME_WIZARD_PAGE_ERROR_MESSAGE_BINDING_ENABLED = "wizardPageErrorMessageBindingEnabled"; //$NON-NLS-1$
	public static final String _PROP_NAME_CONTEXT                                   = "context";                             //$NON-NLS-1$
	public static final String _PROP_NAME_OWNER_PROJECT                             = "ownerProject";                        //$NON-NLS-1$
	public static final String _PROP_NAME_AS_WIZARD_PAGE                            = "aSWizardPage";                        //$NON-NLS-1$
	public static final String _PROP_NAME_ERRORS                                    = "errors";                              //$NON-NLS-1$


	String getWizardPageName();

	void setWizardPageName(String pageName);

	String getWizardPageTitle();

	void setWizardPageTitle(String title);

	String getWizardPageDescription();

	void setWizardPageDescription(String description);

	String getWizardPageErrorMessage();

	void setWizardPageErrorMessage(String description);

	ImageDescriptor getWizardPageTitleImageDescriptor();

	void setWizardPageTitleImageDescriptor(ImageDescriptor titleImage);

	IProject getOwnerProject();

	void setOwnerProject(IProject project);

	IContext getContext();

	void setContext(IContext context);

	IASWizardPage<?, ?> getASWizardPage();

	void setASWizardPage(IASWizardPage<?, ?> wizardPage);

	List<Exception> getErrors();

	void clearErrors();

	boolean addError(Exception error);

	boolean removeError(Exception error);

	boolean isWizardPageErrorMessageBindingEnabled();

	void setWizardPageErrorMessageBindingEnabled(boolean enabled);

}
