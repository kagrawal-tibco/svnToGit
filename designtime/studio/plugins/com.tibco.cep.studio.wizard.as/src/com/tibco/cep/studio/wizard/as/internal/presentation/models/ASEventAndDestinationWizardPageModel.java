package com.tibco.cep.studio.wizard.as.internal.presentation.models;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.resource.ImageDescriptor;

import com.tibco.cep.studio.wizard.as.commons.utils.IContext;
import com.tibco.cep.studio.wizard.as.presentation.models.AASWizardPageModel;
import com.tibco.cep.studio.wizard.as.presentation.models.IASEventAndDestinationWizardPageModel;

public class ASEventAndDestinationWizardPageModel extends AASWizardPageModel implements IASEventAndDestinationWizardPageModel {

	public ASEventAndDestinationWizardPageModel(String wizardPageName, String wizardPageTitle, String wizardPageDescription, ImageDescriptor wizardPageImage, IProject ownerProject, IContext context) {
		super(wizardPageName, wizardPageTitle, wizardPageDescription, wizardPageImage, ownerProject, context);
	}

}
