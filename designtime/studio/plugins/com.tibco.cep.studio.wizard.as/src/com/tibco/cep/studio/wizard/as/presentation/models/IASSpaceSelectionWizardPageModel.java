package com.tibco.cep.studio.wizard.as.presentation.models;

import java.util.List;

import com.tibco.as.space.SpaceDef;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.studio.wizard.as.ASConstants;
import com.tibco.cep.studio.wizard.as.internal.utils.Messages;

public interface IASSpaceSelectionWizardPageModel extends IASWizardPageModel {

	public static final String _PROP_VALUE_WIZARD_PAGE_NAME         = "com.tibco.cep.studio.wizard.as.wizard.page.spaceSelection"; //$NON-NLS-1$
	public static final String _PROP_VALUE_WIZARD_PAGE_TITLE        = Messages.getString("IASSpaceSelectionWizardPageModel.page_title"); //$NON-NLS-1$
	public static final String _PROP_VALUE_WIZARD_PAGE_DESCRIPTION  = Messages.getString("IASSpaceSelectionWizardPageModel.description"); //$NON-NLS-1$
	public static final String _PROP_VALUE_WIZARD_PAGE_IMAGE       = ASConstants._IMAGE_WIZBAN_CHANNEL;


	public static final String _PROP_NAME_CURRENT_SELECTED_SPACEDEF = "currentSelectedSpaceDef"; //$NON-NLS-1$
	public static final String _PROP_NAME_CURRENT_SELECTED_CHANNEL  = "currentSelectedChannel"; //$NON-NLS-1$
	public static final String _PROP_NAME_CHANNELS                  = "channels"; //$NON-NLS-1$

	SpaceDef getCurrentSelectedSpaceDef();

	void setCurrentSelectedSpaceDef(SpaceDef spaceDef);

	List<Channel> getChannels();

	void setChannels(List<Channel> channels);

	Channel getCurrentSelectedChannel();

	void setCurrentSelectedChannel(Channel channel);

}
