package com.tibco.cep.studio.wizard.as.internal.presentation.controllers;

import org.eclipse.core.resources.IProject;

import com.tibco.as.space.SpaceDef;
import com.tibco.cep.designtime.core.model.event.SimpleEvent;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.studio.wizard.as.presentation.controllers.AASWizardPageController;
import com.tibco.cep.studio.wizard.as.presentation.controllers.IASEventWizardPageController;
import com.tibco.cep.studio.wizard.as.presentation.controllers.IASWizardPageController;
import com.tibco.cep.studio.wizard.as.presentation.models.IASEventWizardPageModel;
import com.tibco.cep.studio.wizard.as.services.api.IASService;

public class ASEventWizardPageController extends AASWizardPageController<IASEventWizardPageModel> implements IASEventWizardPageController {

	public ASEventWizardPageController(IASWizardPageController<?> parent, IASEventWizardPageModel model, IASService service) {
		super(parent, model, service);
	}

	@Override
	public SimpleEvent createSimpleEvent(Channel channel, SpaceDef spaceDef) {
		IASEventWizardPageModel model = getModel();
		IProject ownerProject = model.getOwnerProject();
		SimpleEvent event = getASService().createSimpleEvent(ownerProject, channel, spaceDef);
		model.setSimpleEvent(event);
		return event;
	}

}
