package com.tibco.cep.studio.wizard.as.presentation.views;

import com.tibco.cep.studio.wizard.as.commons.beans.factory.IBeanFactory;
import com.tibco.cep.studio.wizard.as.commons.beans.presentation.AView;
import com.tibco.cep.studio.wizard.as.presentation.controllers.IASWizardPageController;
import com.tibco.cep.studio.wizard.as.presentation.models.IASWizardPageModel;

abstract public class AASWizardPageView<C extends IASWizardPageController<M>, M extends IASWizardPageModel> extends AView<C, M> implements
        IASWizardPageView<C, M> {

	protected AASWizardPageView(C controller, IBeanFactory compFactory) {
		super(controller, compFactory);
	}

}
