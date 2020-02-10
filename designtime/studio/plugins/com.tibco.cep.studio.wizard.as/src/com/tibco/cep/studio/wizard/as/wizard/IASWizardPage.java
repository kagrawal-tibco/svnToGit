package com.tibco.cep.studio.wizard.as.wizard;

import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.IWizardPage;

import com.tibco.cep.studio.wizard.as.presentation.controllers.IASWizardPageController;
import com.tibco.cep.studio.wizard.as.presentation.models.IASWizardPageModel;
import com.tibco.cep.studio.wizard.as.wizard.handlers.IWizardPageHandler;

public interface IASWizardPage<C extends IASWizardPageController<M>, M extends IASWizardPageModel> extends IWizardPage {

	IWizardPageHandler<C, M> getPageHandler();

	C getASWizardPageController();

	void setPageComplete(boolean complete);

	IWizardContainer getWizardContainer();

	void setErrorMessage(String errorMessage);

}
