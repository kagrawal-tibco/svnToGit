package com.tibco.cep.studio.wizard.as.presentation.views;

import com.tibco.cep.studio.wizard.as.commons.beans.presentation.IView;
import com.tibco.cep.studio.wizard.as.presentation.controllers.IASWizardPageController;
import com.tibco.cep.studio.wizard.as.presentation.models.IASWizardPageModel;

public interface IASWizardPageView<C extends IASWizardPageController<M>, M extends IASWizardPageModel> extends IView<C, M> {

}
