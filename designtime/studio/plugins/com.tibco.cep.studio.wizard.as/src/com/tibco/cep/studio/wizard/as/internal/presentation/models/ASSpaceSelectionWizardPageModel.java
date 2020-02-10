package com.tibco.cep.studio.wizard.as.internal.presentation.models;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.resource.ImageDescriptor;

import com.tibco.as.space.SpaceDef;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.studio.wizard.as.commons.utils.IContext;
import com.tibco.cep.studio.wizard.as.presentation.models.AASWizardPageModel;
import com.tibco.cep.studio.wizard.as.presentation.models.IASSpaceSelectionWizardPageModel;

public class ASSpaceSelectionWizardPageModel extends AASWizardPageModel implements IASSpaceSelectionWizardPageModel {


	// Properties
	private List<Channel> channels;
	private SpaceDef      currentSelectedSpaceDef;
	private Channel       currentSelectedChannel;


	public ASSpaceSelectionWizardPageModel(String wizardPageName, String wizardPageTitle, String wizardPageDescription, ImageDescriptor wizardPageImage, IProject ownerProject, IContext context) {
		super(wizardPageName, wizardPageTitle, wizardPageDescription, wizardPageImage, ownerProject, context);
	}

	@Override
	public List<Channel> getChannels() {
		return channels;
	}

	@Override
	public void setChannels(List<Channel> channels) {
		List<Channel> oldValue = this.channels;
		List<Channel> newValue = this.channels = channels;
		firePropertyChange(_PROP_NAME_CHANNELS, oldValue, newValue);
	}

	@Override
	public SpaceDef getCurrentSelectedSpaceDef() {
		return currentSelectedSpaceDef;
	}

	@Override
	public void setCurrentSelectedSpaceDef(SpaceDef spaceDef) {
		SpaceDef oldValue = this.currentSelectedSpaceDef;
		SpaceDef newValue = this.currentSelectedSpaceDef = spaceDef;
		firePropertyChange(_PROP_NAME_CURRENT_SELECTED_SPACEDEF, oldValue, newValue);
	}

	@Override
	public Channel getCurrentSelectedChannel() {
		return currentSelectedChannel;
	}

	@Override
	public void setCurrentSelectedChannel(Channel channel) {
		Channel oldValue = this.currentSelectedChannel;
		Channel newValue = this.currentSelectedChannel = channel;
		firePropertyChange(_PROP_NAME_CURRENT_SELECTED_CHANNEL, oldValue, newValue);
	}

}
