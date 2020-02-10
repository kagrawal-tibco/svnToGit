package com.tibco.cep.studio.wizard.as.internal.presentation.views;

import com.tibco.cep.studio.wizard.as.commons.beans.factory.IBeanFactory;
import com.tibco.cep.studio.wizard.as.presentation.controllers.IASDestinationWizardPageController;
import com.tibco.cep.studio.wizard.as.presentation.models.IASDestinationWizardPageModel;
import com.tibco.cep.studio.wizard.as.presentation.views.AASWizardPageView;
import com.tibco.cep.studio.wizard.as.presentation.views.IASDestinationWizardPageView;

public class ASDestinationWizardPageView extends AASWizardPageView<IASDestinationWizardPageController, IASDestinationWizardPageModel> implements
        IASDestinationWizardPageView {

	public ASDestinationWizardPageView(IASDestinationWizardPageController controller, IBeanFactory compFactory) {
		super(controller, compFactory);
	}

}
