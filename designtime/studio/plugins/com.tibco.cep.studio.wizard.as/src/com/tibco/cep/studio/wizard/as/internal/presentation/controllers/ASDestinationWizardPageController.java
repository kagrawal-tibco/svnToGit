package com.tibco.cep.studio.wizard.as.internal.presentation.controllers;

import org.eclipse.core.resources.IProject;

import com.tibco.as.space.SpaceDef;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.studio.wizard.as.presentation.controllers.AASWizardPageController;
import com.tibco.cep.studio.wizard.as.presentation.controllers.IASDestinationWizardPageController;
import com.tibco.cep.studio.wizard.as.presentation.controllers.IASWizardPageController;
import com.tibco.cep.studio.wizard.as.presentation.models.IASDestinationWizardPageModel;
import com.tibco.cep.studio.wizard.as.services.api.IASService;

public class ASDestinationWizardPageController extends AASWizardPageController<IASDestinationWizardPageModel> implements IASDestinationWizardPageController {

	public ASDestinationWizardPageController(IASWizardPageController<?> parent, IASDestinationWizardPageModel model, IASService service) {
	    super(parent, model, service);
    }

	@Override
    public Destination createDestination(Channel channel, SpaceDef spaceDef) {
		IASDestinationWizardPageModel model = getModel();
		IProject ownerProject = model.getOwnerProject();
		Destination destination = getASService().createDestination(ownerProject, channel, spaceDef);
		model.setDestination(destination);
		return destination;
    }

}
