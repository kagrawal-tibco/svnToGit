package com.tibco.cep.studio.wizard.as.presentation.controllers;

import com.tibco.as.space.SpaceDef;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.studio.wizard.as.presentation.models.IASDestinationWizardPageModel;

public interface IASDestinationWizardPageController extends IASWizardPageController<IASDestinationWizardPageModel> {

	Destination createDestination(Channel channel, SpaceDef spaceDef);

}
