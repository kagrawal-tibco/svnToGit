package com.tibco.cep.studio.wizard.as.presentation.models;

import com.tibco.cep.designtime.core.model.event.SimpleEvent;
import com.tibco.cep.studio.wizard.as.ASConstants;
import com.tibco.cep.studio.wizard.as.internal.utils.Messages;

public interface IASEventWizardPageModel extends IASWizardPageModel {

	public static final String _PROP_VALUE_WIZARD_PAGE_NAME        = "com.tibco.cep.studio.wizard.as.wizard.page.event"; //$NON-NLS-1$
	public static final String _PROP_VALUE_WIZARD_PAGE_TITLE       = Messages.getString("IASEventWizardPageModel.page_title"); //$NON-NLS-1$
	public static final String _PROP_VALUE_WIZARD_PAGE_DESCRIPTION = Messages.getString("IASEventWizardPageModel.description"); //$NON-NLS-1$
	public static final String _PROP_VALUE_WIZARD_PAGE_IMAGE       = ASConstants._IMAGE_WIZBAN_SIMPLE_EVENT;


	public static final String _PROP_NAME_SIMPLE_EVENT             = "simpleEvent"; //$NON-NLS-1$

	SimpleEvent getSimpleEvent();

	void setSimpleEvent(SimpleEvent event);

}
