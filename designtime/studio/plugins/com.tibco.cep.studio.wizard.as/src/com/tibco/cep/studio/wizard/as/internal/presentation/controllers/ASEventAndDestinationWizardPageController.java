package com.tibco.cep.studio.wizard.as.internal.presentation.controllers;

import com.tibco.cep.studio.wizard.as.commons.beans.presentation.IController;
import com.tibco.cep.studio.wizard.as.presentation.controllers.AASWizardPageController;
import com.tibco.cep.studio.wizard.as.presentation.controllers.IASDestinationWizardPageController;
import com.tibco.cep.studio.wizard.as.presentation.controllers.IASEventAndDestinationWizardPageController;
import com.tibco.cep.studio.wizard.as.presentation.controllers.IASEventWizardPageController;
import com.tibco.cep.studio.wizard.as.presentation.controllers.IASWizardPageController;
import com.tibco.cep.studio.wizard.as.presentation.models.IASEventAndDestinationWizardPageModel;
import com.tibco.cep.studio.wizard.as.services.api.IASService;

public class ASEventAndDestinationWizardPageController extends AASWizardPageController<IASEventAndDestinationWizardPageModel> implements IASEventAndDestinationWizardPageController {

	public ASEventAndDestinationWizardPageController(IASEventAndDestinationWizardPageModel model, IASService service) {
		super(model, service);
	}

	@Override
    public IASDestinationWizardPageController getASDestinationWizardPageController() {
	    return (IASDestinationWizardPageController) getController(IASDestinationWizardPageController.class);
    }

	@Override
    public IASEventWizardPageController getASEventWizardPageController() {
	    return (IASEventWizardPageController) getController(IASEventWizardPageController.class);
    }

	private IASWizardPageController<?> getController(Class<?> clazz) {
		IController<?> result = null;
		IController<?>[] controllers = getControllers();
		for (IController<?> controller : controllers) {
			if (clazz.isInstance(controller)) {
				result = controller;
			}
		}
		return (IASWizardPageController<?>) result;
	}

}
