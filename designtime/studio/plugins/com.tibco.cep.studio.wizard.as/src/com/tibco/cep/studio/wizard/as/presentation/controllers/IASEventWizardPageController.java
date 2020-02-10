package com.tibco.cep.studio.wizard.as.presentation.controllers;

import com.tibco.as.space.SpaceDef;
import com.tibco.cep.designtime.core.model.event.SimpleEvent;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.studio.wizard.as.presentation.models.IASEventWizardPageModel;

public interface IASEventWizardPageController extends IASWizardPageController<IASEventWizardPageModel> {

	SimpleEvent createSimpleEvent(Channel channel, SpaceDef spaceDef);

}
