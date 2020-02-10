package com.tibco.cep.studio.wizard.as.presentation.controllers;

import java.util.List;

import com.tibco.as.space.SpaceDef;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.studio.wizard.as.presentation.models.IASSpaceSelectionWizardPageModel;

public interface IASSpaceSelectionWizardPageController extends IASWizardPageController<IASSpaceSelectionWizardPageModel> {

	void refresh();

	List<SpaceDef> getUserSpaceDefs(Channel channel) throws Exception;

	void advanceToNextPage() throws Exception;

}
