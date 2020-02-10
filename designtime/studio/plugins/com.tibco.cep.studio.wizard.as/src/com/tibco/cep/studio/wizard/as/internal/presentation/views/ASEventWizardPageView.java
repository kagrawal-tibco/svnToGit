package com.tibco.cep.studio.wizard.as.internal.presentation.views;

import com.tibco.cep.studio.wizard.as.commons.beans.factory.IBeanFactory;
import com.tibco.cep.studio.wizard.as.presentation.controllers.IASEventWizardPageController;
import com.tibco.cep.studio.wizard.as.presentation.models.IASEventWizardPageModel;
import com.tibco.cep.studio.wizard.as.presentation.views.AASWizardPageView;
import com.tibco.cep.studio.wizard.as.presentation.views.IASEventWizardPageView;

public class ASEventWizardPageView extends AASWizardPageView<IASEventWizardPageController, IASEventWizardPageModel> implements IASEventWizardPageView {

	public ASEventWizardPageView(IASEventWizardPageController controller, IBeanFactory compFactory) {
		super(controller, compFactory);
	}

}
