package com.tibco.cep.studio.wizard.as.internal.presentation.views;

import com.tibco.cep.studio.wizard.as.commons.beans.factory.IBeanFactory;
import com.tibco.cep.studio.wizard.as.presentation.controllers.IASSpaceSelectionWizardPageController;
import com.tibco.cep.studio.wizard.as.presentation.models.IASSpaceSelectionWizardPageModel;
import com.tibco.cep.studio.wizard.as.presentation.views.AASWizardPageView;
import com.tibco.cep.studio.wizard.as.presentation.views.IASSpaceSelectionWizardPageView;

public class ASSpaceSelectionWizardPageView extends AASWizardPageView<IASSpaceSelectionWizardPageController, IASSpaceSelectionWizardPageModel>
        implements IASSpaceSelectionWizardPageView {

	public ASSpaceSelectionWizardPageView(IASSpaceSelectionWizardPageController controller, IBeanFactory compFactory) {
		super(controller, compFactory);
	}

}
