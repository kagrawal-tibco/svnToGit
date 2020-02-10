package com.tibco.cep.studio.wizard.as.presentation.controllers;

import com.tibco.cep.studio.wizard.as.commons.beans.presentation.AController;
import com.tibco.cep.studio.wizard.as.presentation.models.IASWizardPageModel;
import com.tibco.cep.studio.wizard.as.services.api.IASService;

abstract public class AASWizardPageController<M extends IASWizardPageModel> extends AController<M> implements IASWizardPageController<M> {

	private IASService service;

	protected AASWizardPageController(M model, IASService service) {
		this(null, model, service);
	}

	protected AASWizardPageController(IASWizardPageController<?> parent, M model, IASService service) {
		super(parent, model);
		this.service = service;
	}

	protected final IASService getASService() {
		return service;
	}

}
