package com.tibco.cep.studio.wizard.as.internal.beans.databinding;

import java.beans.PropertyChangeEvent;

import com.tibco.cep.studio.wizard.as.presentation.controllers.IASDestinationWizardPageController;

public class ASDestinationWizardPageDataBindingProvider extends AASSimplePropertyHandlingWizardPageDataBindingProvider<IASDestinationWizardPageController> {

	public ASDestinationWizardPageDataBindingProvider(IASDestinationWizardPageController controller, String controllerCtxKey, String modelPropName) {
	    super(controller, controllerCtxKey, modelPropName);
    }

	@Override
	protected void handlePropertyChanged(IASDestinationWizardPageController controller, PropertyChangeEvent evt) {
		// enter this method means that the channel list has changed,
		// so we must clean up old destination
		controller.getModel().setDestination(null);
	}

}
