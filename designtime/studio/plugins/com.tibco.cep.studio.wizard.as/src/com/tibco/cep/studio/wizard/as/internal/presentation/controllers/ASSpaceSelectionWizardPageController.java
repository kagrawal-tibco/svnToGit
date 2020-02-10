package com.tibco.cep.studio.wizard.as.internal.presentation.controllers;

import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IProject;

import com.tibco.as.space.SpaceDef;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.studio.wizard.as.commons.commands.ICommand;
import com.tibco.cep.studio.wizard.as.presentation.controllers.AASWizardPageController;
import com.tibco.cep.studio.wizard.as.presentation.controllers.IASSpaceSelectionWizardPageController;
import com.tibco.cep.studio.wizard.as.presentation.models.IASSpaceSelectionWizardPageModel;
import com.tibco.cep.studio.wizard.as.services.api.IASService;

public class ASSpaceSelectionWizardPageController extends AASWizardPageController<IASSpaceSelectionWizardPageModel> implements
        IASSpaceSelectionWizardPageController {

	private ICommand showNextPageCommand;

	public ASSpaceSelectionWizardPageController(IASSpaceSelectionWizardPageModel model, IASService service, ICommand showNextPageCommand) {
		super(model, service);
		this.showNextPageCommand = showNextPageCommand;
	}

	@Override
	public void refresh() {
		IASSpaceSelectionWizardPageModel model = getModel();
		model.clearErrors();
		List<Channel> channels = Collections.emptyList();
		model.setChannels(channels); // clear
		IProject ownerProject = model.getOwnerProject();
		channels = getRawChannels(ownerProject);
		model.setChannels(channels); // update
	}

	private List<Channel> getRawChannels(IProject ownerProject) {
		List<Channel> channels = getASService().getASChannels(ownerProject);
		if (null == channels) {
			channels = Collections.emptyList();
		}
		return channels;
	}

	@Override
	public List<SpaceDef> getUserSpaceDefs(Channel channel) throws Exception {
		IProject ownerProject = getModel().getOwnerProject();
		return getASService().getUserSpaceDefs(ownerProject, channel);
	}

	@Override
	public void advanceToNextPage() throws Exception {
		showNextPageCommand.execute(this);
	}

}
