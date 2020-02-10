package com.tibco.cep.studio.wizard.as.internal.presentation.models;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.resource.ImageDescriptor;

import com.tibco.cep.designtime.core.model.event.SimpleEvent;
import com.tibco.cep.studio.wizard.as.commons.utils.IContext;
import com.tibco.cep.studio.wizard.as.presentation.models.AASWizardPageModel;
import com.tibco.cep.studio.wizard.as.presentation.models.IASEventWizardPageModel;

public class ASEventWizardPageModel extends AASWizardPageModel implements IASEventWizardPageModel {

	public ASEventWizardPageModel(String wizardPageName, String wizardPageTitle, String wizardPageDescription, ImageDescriptor wizardPageImage, IProject ownerProject, IContext context) {
		super(wizardPageName, wizardPageTitle, wizardPageDescription, wizardPageImage, ownerProject, context);
	}

	// Properties
	private SimpleEvent event;

	@Override
	public SimpleEvent getSimpleEvent() {
		return event;
	}

	@Override
	public void setSimpleEvent(SimpleEvent event) {
		SimpleEvent oldValue = this.event;
		SimpleEvent newValue = this.event = event;
		firePropertyChange(_PROP_NAME_SIMPLE_EVENT, oldValue, newValue);
	}

}
