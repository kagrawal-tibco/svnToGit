package com.tibco.cep.studio.wizard.as.presentation.models;

import com.tibco.cep.studio.wizard.as.ASConstants;
import com.tibco.cep.studio.wizard.as.internal.utils.Messages;

public interface IASEventAndDestinationWizardPageModel extends IASWizardPageModel {

	public static final String _PROP_VALUE_WIZARD_PAGE_NAME        = "com.tibco.cep.studio.wizard.as.wizard.page.eventAndDestination"; //$NON-NLS-1$
	public static final String _PROP_VALUE_WIZARD_PAGE_TITLE       = Messages.getString("IASEventAndDestinationWizardPageModel.page_title"); //$NON-NLS-1$
	public static final String _PROP_VALUE_WIZARD_PAGE_DESCRIPTION = Messages.getString("IASEventAndDestinationWizardPageModel.description"); //$NON-NLS-1$
	public static final String _PROP_VALUE_WIZARD_PAGE_IMAGE       = ASConstants._IMAGE_WIZBAN_EVENT_AND_DESTINATION;

}
