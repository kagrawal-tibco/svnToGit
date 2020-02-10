package com.tibco.cep.studio.wizard.as.presentation.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.resource.ImageDescriptor;

import com.tibco.cep.studio.wizard.as.commons.beans.presentation.AModel;
import com.tibco.cep.studio.wizard.as.commons.utils.IContext;
import com.tibco.cep.studio.wizard.as.wizard.IASWizardPage;

abstract public class AASWizardPageModel extends AModel implements IASWizardPageModel {

	private String              wizardPageName;
	private String              wizardPageTitle;
	private String              wizardPageDescription;
	private ImageDescriptor     wizardPageTitleImage;
	private IContext            context;

	private IProject            ownerProject;
	private IASWizardPage<?, ?> wizardPage;
	private List<Exception>     errors;
	private String              wizardPageErrorMessage;
	private boolean             errorMessageBindingEnabled;


	protected AASWizardPageModel(String wizardPageName, String wizardPageTitle, String wizardPageDescription, ImageDescriptor wizardPageTitleImage, IProject ownerProject, IContext context) {
		this.wizardPageName = wizardPageName;
		this.wizardPageTitle = wizardPageTitle;
		this.wizardPageDescription = wizardPageDescription;
		this.wizardPageTitleImage = wizardPageTitleImage;
		this.ownerProject = ownerProject;
		this.context = context;
		postConstruction();
	}

	private void postConstruction() {
		errors = new ArrayList<Exception>();
		errorMessageBindingEnabled = true;
    }

	@Override
	public String getWizardPageTitle() {
		return wizardPageTitle;
	}

	@Override
	public void setWizardPageTitle(String title) {
		String oldValue = this.wizardPageTitle;
		String newValue = this.wizardPageTitle = title;
		firePropertyChange(_PROP_NAME_WIZARD_PAGE_TITLE, oldValue, newValue);
	}

	@Override
	public ImageDescriptor getWizardPageTitleImageDescriptor() {
		return wizardPageTitleImage;
	}

	@Override
	public void setWizardPageTitleImageDescriptor(ImageDescriptor titleImage) {
		ImageDescriptor oldValue = this.wizardPageTitleImage;
		ImageDescriptor newValue = this.wizardPageTitleImage = titleImage;
		firePropertyChange(_PROP_NAME_WIZARD_PAGE_TITLE_IMAGE, oldValue, newValue);
	}

	@Override
	public String getWizardPageName() {
		return wizardPageName;
	}

	@Override
	public void setWizardPageName(String pageName) {
		String oldValue = this.wizardPageName;
		String newValue = this.wizardPageName = pageName;
		firePropertyChange(_PROP_NAME_WIZARD_PAGE_NAME, oldValue, newValue);
	}

	@Override
	public IProject getOwnerProject() {
		return ownerProject;
	}

	@Override
	public void setOwnerProject(IProject project) {
		IProject oldValue = this.ownerProject;
		IProject newValue = this.ownerProject = project;
		firePropertyChange(_PROP_NAME_OWNER_PROJECT, oldValue, newValue);
	}

	@Override
    public String getWizardPageDescription() {
	    return wizardPageDescription;
    }

	@Override
    public void setWizardPageDescription(String description) {
		String oldValue = this.wizardPageDescription;
		String newValue = this.wizardPageDescription = description;
		firePropertyChange(_PROP_NAME_WIZARD_PAGE_DESCRIPTION, oldValue, newValue);
    }

	@Override
    public IContext getContext() {
	    return context;
    }

	@Override
    public void setContext(IContext context) {
		IContext oldValue = this.context;
		IContext newValue = this.context = context;
		firePropertyChange(_PROP_NAME_CONTEXT, oldValue, newValue);
    }

	@Override
    public IASWizardPage<?, ?> getASWizardPage() {
	    return wizardPage;
    }

	@Override
    public void setASWizardPage(IASWizardPage<?, ?> wizardPage) {
		IASWizardPage<?, ?> oldValue = this.wizardPage;
		IASWizardPage<?, ?> newValue = this.wizardPage = wizardPage;
		firePropertyChange(_PROP_NAME_AS_WIZARD_PAGE, oldValue, newValue);
    }

	@Override
    public List<Exception> getErrors() {
	    return errors;
    }

	@Override
    public void clearErrors() {
		List<Exception> oldValue = Collections.unmodifiableList(this.errors);
		errors.clear();
		List<Exception> newValue = this.errors;
		firePropertyChange(_PROP_NAME_ERRORS, oldValue, newValue);
    }

	@Override
    public boolean addError(Exception error) {
		List<Exception> oldValue = Collections.unmodifiableList(this.errors);
		boolean changed = this.errors.add(error);
		List<Exception> newValue = this.errors;
		firePropertyChange(_PROP_NAME_ERRORS, oldValue, newValue);
		return changed;
    }

	@Override
    public boolean removeError(Exception error) {
		List<Exception> oldValue = Collections.unmodifiableList(this.errors);
		boolean contain = this.errors.remove(error);
		List<Exception> newValue = this.errors;
		firePropertyChange(_PROP_NAME_ERRORS, oldValue, newValue);
		return contain;
    }

	@Override
    public String getWizardPageErrorMessage() {
	    return              wizardPageErrorMessage;
    }

	@Override
    public void setWizardPageErrorMessage(String errorMessage) {
		String oldValue = this.wizardPageErrorMessage;
		String newValue = this.wizardPageErrorMessage = errorMessage;
		firePropertyChange(_PROP_NAME_WIZARD_PAGE_ERROR_MESSAGE, oldValue, newValue);
    }

	@Override
    public boolean isWizardPageErrorMessageBindingEnabled() {
	    return errorMessageBindingEnabled;
    }

	@Override
    public void setWizardPageErrorMessageBindingEnabled(boolean errorMessageBindingEnabled) {
		boolean oldValue = this.errorMessageBindingEnabled;
		boolean newValue = this.errorMessageBindingEnabled = errorMessageBindingEnabled;
		firePropertyChange(_PROP_NAME_WIZARD_PAGE_ERROR_MESSAGE_BINDING_ENABLED, oldValue, newValue);
    }

}
