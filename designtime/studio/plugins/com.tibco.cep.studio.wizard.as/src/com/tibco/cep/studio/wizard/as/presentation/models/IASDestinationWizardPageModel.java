package com.tibco.cep.studio.wizard.as.presentation.models;

import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.studio.wizard.as.ASConstants;
import com.tibco.cep.studio.wizard.as.internal.utils.Messages;

public interface IASDestinationWizardPageModel extends IASWizardPageModel {

	public static final String _PROP_VALUE_WIZARD_PAGE_NAME        = "com.tibco.cep.studio.wizard.as.wizard.page.destination"; //$NON-NLS-1$
	public static final String _PROP_VALUE_WIZARD_PAGE_TITLE       = Messages.getString("IASDestinationWizardPageModel.page_title"); //$NON-NLS-1$
	public static final String _PROP_VALUE_WIZARD_PAGE_DESCRIPTION = Messages.getString("IASDestinationWizardPageModel.description"); //$NON-NLS-1$
	public static final String _PROP_VALUE_WIZARD_PAGE_IMAGE       = ASConstants._IMAGE_WIZBAN_DESTINATION;

	public static final String _PROP_NAME_DESTINATION              = "destination"; //$NON-NLS-1$

	Destination getDestination();

	void setDestination(Destination destination);

}
