package com.tibco.cep.studio.wizard.as.presentation.controllers;

import org.eclipse.core.runtime.IProgressMonitor;

import com.tibco.cep.studio.wizard.as.commons.beans.presentation.IController;
import com.tibco.cep.studio.wizard.as.presentation.models.IASWizardPageModel;
import com.tibco.cep.studio.wizard.as.presentation.models.INewASWizardModel;
import com.tibco.cep.studio.wizard.as.services.spi.IStageParticipant;

public interface INewASWizardController extends IController<INewASWizardModel> {

	void generateASResources(IProgressMonitor monitor, IStageParticipant stageParticipant) throws Exception;

	IASWizardPageController<? extends IASWizardPageModel> getASWizardPageControllerByName(String pageName);

}
