package com.tibco.cep.studio.wizard.as.internal.presentation.controllers;

import static com.tibco.cep.studio.wizard.as.ASConstants._K_CTX_AS_CONTROLLER_DESTINATION;
import static com.tibco.cep.studio.wizard.as.ASConstants._K_CTX_AS_CONTROLLER_EVENT;
import static com.tibco.cep.studio.wizard.as.ASConstants._K_CTX_AS_CONTROLLER_SPACE_SELECTION;
import static com.tibco.cep.studio.wizard.as.ASConstants._K_CTX_SERVICE_PERSISTENCE_EXISTING_RESOURCE;
import static com.tibco.cep.studio.wizard.as.ASConstants._K_CTX_SERVICE_PERSISTENCE_ORIGINAL_RESOURCE;
import static com.tibco.cep.studio.wizard.as.ASConstants._K_CTX_SERVICE_PERSISTENCE_TARGET_RESOURCE;
import static com.tibco.cep.studio.wizard.as.internal.services.spi.PersistenceStageParticipant._STAGE_EXISTING_DESTINATION;
import static com.tibco.cep.studio.wizard.as.internal.utils.ASUtils.checkExistingEntity;
import static com.tibco.cep.studio.wizard.as.internal.utils.PluginUtils.subMonitorFor;
import static org.eclipse.core.runtime.IStatus.CANCEL;
import static org.eclipse.core.runtime.Status.CANCEL_STATUS;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.wizard.IWizardPage;

import com.tibco.as.space.SpaceDef;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.event.SimpleEvent;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.studio.wizard.as.commons.beans.presentation.AController;
import com.tibco.cep.studio.wizard.as.commons.utils.IContext;
import com.tibco.cep.studio.wizard.as.internal.utils.Messages;
import com.tibco.cep.studio.wizard.as.presentation.controllers.IASDestinationWizardPageController;
import com.tibco.cep.studio.wizard.as.presentation.controllers.IASEventWizardPageController;
import com.tibco.cep.studio.wizard.as.presentation.controllers.IASSpaceSelectionWizardPageController;
import com.tibco.cep.studio.wizard.as.presentation.controllers.IASWizardPageController;
import com.tibco.cep.studio.wizard.as.presentation.controllers.INewASWizardController;
import com.tibco.cep.studio.wizard.as.presentation.models.IASWizardPageModel;
import com.tibco.cep.studio.wizard.as.presentation.models.INewASWizardModel;
import com.tibco.cep.studio.wizard.as.services.api.IASService;
import com.tibco.cep.studio.wizard.as.services.spi.IStageParticipant;
import com.tibco.cep.studio.wizard.as.wizard.IASWizardPage;

public class NewASWizardController extends AController<INewASWizardModel> implements INewASWizardController {

	public NewASWizardController(INewASWizardModel model) {
		super(model);
	}

	@Override
	public IASWizardPageController<? extends IASWizardPageModel> getASWizardPageControllerByName(String pageName) {
		IASWizardPageController<? extends IASWizardPageModel> result = null;
		List<IWizardPage> pages = getModel()
		        .getAllRawWizardPages();
		for (IWizardPage page : pages) {
			IASWizardPageController<? extends IASWizardPageModel> controller = ((IASWizardPage) page).getASWizardPageController();
			if (controller.getModel().getWizardPageName().equals(pageName)) {
				result = controller;
				break;
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void generateASResources(IProgressMonitor monitor, IStageParticipant stageParticipant) throws Exception {
		// step 1, start to persist the resources of AS
		monitor.beginTask(Messages.getString("NewASWizardController.generate_resources"), 100); //$NON-NLS-1$
		INewASWizardModel model = getModel();
		IContext context = model.getContext();
		monitor.worked(10);


		if (monitor.isCanceled()) {
			stageParticipant.setStatus(CANCEL_STATUS);
			return;
		}
		// step 2, create working environment
		IASService service = model.getASService();
		IProject ownerProject = model.getOwnerProject();
		IASSpaceSelectionWizardPageController spaceSelController = (IASSpaceSelectionWizardPageController) context
		        .get(_K_CTX_AS_CONTROLLER_SPACE_SELECTION);
		IASDestinationWizardPageController destinationController = (IASDestinationWizardPageController) context.get(_K_CTX_AS_CONTROLLER_DESTINATION);
		IASEventWizardPageController eventController = (IASEventWizardPageController) context.get(_K_CTX_AS_CONTROLLER_EVENT);
		monitor.worked(10);


		if (monitor.isCanceled()) {
			stageParticipant.setStatus(CANCEL_STATUS);
			return;
		}
		// step 3, get the resources to be persisted
		Channel channel = spaceSelController.getModel().getCurrentSelectedChannel();
		Destination destination = destinationController.getModel().getDestination();
		if (null == destination) {
			SpaceDef spaceDef = spaceSelController.getModel().getCurrentSelectedSpaceDef();
			destination = destinationController.createDestination(channel, spaceDef);
		}
		SimpleEvent simpleEvent = eventController.getModel().getSimpleEvent();
		if (null == simpleEvent) {
			SpaceDef spaceDef = spaceSelController.getModel().getCurrentSelectedSpaceDef();
			simpleEvent = eventController.createSimpleEvent(channel, spaceDef);
		}
		monitor.worked(10);


		if (monitor.isCanceled()) {
			stageParticipant.setStatus(CANCEL_STATUS);
			return;
		}
		// step 4, check existing entities
		// check destination
		EList<? extends Entity> existingDestinations = channel.getDriver().getDestinations();
		Object[] results = checkExistingEntity(destination, existingDestinations);
		boolean existing = (Boolean) results[0];
		if (existing) {
			Destination originalDestination = (Destination) results[1];
			context.bind(_K_CTX_SERVICE_PERSISTENCE_EXISTING_RESOURCE, true);
			context.bind(_K_CTX_SERVICE_PERSISTENCE_ORIGINAL_RESOURCE, originalDestination);
			context.bind(_K_CTX_SERVICE_PERSISTENCE_TARGET_RESOURCE, destination);
			boolean canContinue = stageParticipant.participate(_STAGE_EXISTING_DESTINATION, context);
			if (canContinue) {
				int destinationIndex = existingDestinations.indexOf(originalDestination);
				((EList<Destination>) existingDestinations).set(destinationIndex, destination);
			} else {
				stageParticipant.setStatus(CANCEL_STATUS);
				return;
			}
		} else {
			((EList<Destination>) existingDestinations).add(destination);
		}


		if (monitor.isCanceled()) {
			stageParticipant.setStatus(CANCEL_STATUS);
			return;
		}
		// step 5, call service api to persist resources
		IProgressMonitor subMonitor = subMonitorFor(monitor, 60);
		service.persist(channel, simpleEvent, ownerProject, stageParticipant, subMonitor);
		if (CANCEL == stageParticipant.getStatus().getCode()) {
			return;
		}
		monitor.done();
	}

}
