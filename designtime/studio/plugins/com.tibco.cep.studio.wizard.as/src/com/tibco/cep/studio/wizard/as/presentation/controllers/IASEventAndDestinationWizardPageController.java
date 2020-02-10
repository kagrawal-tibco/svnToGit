package com.tibco.cep.studio.wizard.as.presentation.controllers;

import com.tibco.cep.studio.wizard.as.presentation.models.IASEventAndDestinationWizardPageModel;

public interface IASEventAndDestinationWizardPageController extends IASWizardPageController<IASEventAndDestinationWizardPageModel> {

	IASDestinationWizardPageController getASDestinationWizardPageController();

	IASEventWizardPageController getASEventWizardPageController();

}
