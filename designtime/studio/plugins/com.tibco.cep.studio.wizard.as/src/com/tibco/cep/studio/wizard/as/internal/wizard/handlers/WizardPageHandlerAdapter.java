package com.tibco.cep.studio.wizard.as.internal.wizard.handlers;

import com.tibco.cep.studio.wizard.as.commons.utils.IContext;
import com.tibco.cep.studio.wizard.as.presentation.controllers.IASWizardPageController;
import com.tibco.cep.studio.wizard.as.presentation.models.IASWizardPageModel;
import com.tibco.cep.studio.wizard.as.wizard.IASWizardPage;
import com.tibco.cep.studio.wizard.as.wizard.handlers.IWizardPageHandler;

public class WizardPageHandlerAdapter<C extends IASWizardPageController<M>, M extends IASWizardPageModel> implements IWizardPageHandler<C, M> {

	@Override
	public void handlePageChanging(IASWizardPage<? extends C, ? extends M> currentPage,
	        IASWizardPage<? extends IASWizardPageController<? extends IASWizardPageModel>, ? extends IASWizardPageModel> targetPage, IContext context)
	        throws Exception {
		// do nothing
	}

	@Override
	public void handlePageChanged(IASWizardPage<? extends C, ? extends M> currentPage, IContext context) throws Exception {
		// do nothing
	}

}
