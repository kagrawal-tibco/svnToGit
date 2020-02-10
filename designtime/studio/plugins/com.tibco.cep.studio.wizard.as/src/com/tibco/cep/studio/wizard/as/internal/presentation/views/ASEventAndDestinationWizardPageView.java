package com.tibco.cep.studio.wizard.as.internal.presentation.views;

import com.tibco.cep.studio.wizard.as.commons.beans.factory.IBeanFactory;
import com.tibco.cep.studio.wizard.as.presentation.controllers.IASEventAndDestinationWizardPageController;
import com.tibco.cep.studio.wizard.as.presentation.models.IASEventAndDestinationWizardPageModel;
import com.tibco.cep.studio.wizard.as.presentation.views.AASWizardPageView;
import com.tibco.cep.studio.wizard.as.presentation.views.IASEventAndDestinationWizardPageView;

public class ASEventAndDestinationWizardPageView extends AASWizardPageView<IASEventAndDestinationWizardPageController, IASEventAndDestinationWizardPageModel>
        implements IASEventAndDestinationWizardPageView {

	public ASEventAndDestinationWizardPageView(IASEventAndDestinationWizardPageController controller, IBeanFactory compFactory) {
		super(controller, compFactory);
	}

}
