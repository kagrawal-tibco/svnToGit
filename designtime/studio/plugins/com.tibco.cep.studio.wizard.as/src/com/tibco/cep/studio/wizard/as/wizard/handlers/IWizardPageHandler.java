package com.tibco.cep.studio.wizard.as.wizard.handlers;

import com.tibco.cep.studio.wizard.as.commons.utils.IContext;
import com.tibco.cep.studio.wizard.as.presentation.controllers.IASWizardPageController;
import com.tibco.cep.studio.wizard.as.presentation.models.IASWizardPageModel;
import com.tibco.cep.studio.wizard.as.wizard.IASWizardPage;

public interface IWizardPageHandler<C extends IASWizardPageController<M>, M extends IASWizardPageModel> {

	void handlePageChanging(IASWizardPage<? extends C, ? extends M> currentPage,
	        IASWizardPage<? extends IASWizardPageController<? extends IASWizardPageModel>, ? extends IASWizardPageModel> targetPage, IContext context)
	        throws Exception;

	void handlePageChanged(IASWizardPage<?extends C, ? extends M> currentPage, IContext context) throws Exception;

}
