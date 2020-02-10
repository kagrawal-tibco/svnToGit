package com.tibco.cep.studio.wizard.as.internal.presentation.models;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.resource.ImageDescriptor;

import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.studio.wizard.as.commons.utils.IContext;
import com.tibco.cep.studio.wizard.as.presentation.models.AASWizardPageModel;
import com.tibco.cep.studio.wizard.as.presentation.models.IASDestinationWizardPageModel;

public class ASDestinationWizardPageModel extends AASWizardPageModel implements IASDestinationWizardPageModel {

	public ASDestinationWizardPageModel(String wizardPageName, String wizardPageTitle, String wizardPageDescription, ImageDescriptor wizardPageImage, IProject ownerProject, IContext context) {
		super(wizardPageName, wizardPageTitle, wizardPageDescription, wizardPageImage, ownerProject, context);
	}

	// Properties
	private Destination destination;

	@Override
	public Destination getDestination() {
		return destination;
	}

	@Override
	public void setDestination(Destination destination) {
		Destination oldValue = this.destination;
		Destination newValue = this.destination = destination;
		firePropertyChange(_PROP_NAME_DESTINATION, oldValue, newValue);
	}

}
