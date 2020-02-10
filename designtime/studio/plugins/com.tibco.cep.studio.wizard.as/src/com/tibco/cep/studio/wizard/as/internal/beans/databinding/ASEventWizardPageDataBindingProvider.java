package com.tibco.cep.studio.wizard.as.internal.beans.databinding;

import java.beans.PropertyChangeEvent;

import com.tibco.cep.studio.wizard.as.presentation.controllers.IASEventWizardPageController;

public class ASEventWizardPageDataBindingProvider extends AASSimplePropertyHandlingWizardPageDataBindingProvider<IASEventWizardPageController> {

	public ASEventWizardPageDataBindingProvider(IASEventWizardPageController controller, String controllerCtxKey, String modelPropName) {
	    super(controller, controllerCtxKey, modelPropName);
    }

	@Override
	protected void handlePropertyChanged(IASEventWizardPageController controller, PropertyChangeEvent evt) {
		// enter this method means that the channel list has changed,
		// so we must clean up old destination
		controller.getModel().setSimpleEvent(null);
	}

}
