package com.tibco.cep.studio.wizard.as.internal.wizard;

import static com.tibco.cep.studio.wizard.as.presentation.models.IASDestinationWizardPageModel._PROP_NAME_DESTINATION;
import static com.tibco.cep.studio.wizard.as.presentation.models.IASEventWizardPageModel._PROP_NAME_SIMPLE_EVENT;
import static com.tibco.cep.studio.wizard.as.presentation.models.IASSpaceSelectionWizardPageModel._PROP_NAME_CURRENT_SELECTED_SPACEDEF;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.wizard.IWizardPage;

import com.tibco.cep.designtime.core.model.event.EventFactory;
import com.tibco.cep.designtime.core.model.service.channel.ChannelFactory;
import com.tibco.cep.studio.wizard.as.commons.beans.databinding.IDataBindingProvider;
import com.tibco.cep.studio.wizard.as.commons.beans.databinding.validators.StringNotEmptyValidator;
import com.tibco.cep.studio.wizard.as.commons.beans.databinding.validators.ValidatorGroup;
import com.tibco.cep.studio.wizard.as.commons.beans.presentation.IController;
import com.tibco.cep.studio.wizard.as.commons.utils.IContext;
import com.tibco.cep.studio.wizard.as.internal.beans.databinding.ASCompositePropertiesToPageCompleteWizardPageDataBindingProvider;
import com.tibco.cep.studio.wizard.as.internal.beans.databinding.ASNonNullToPageCompleteWizardPageDataBindingProvider;
import com.tibco.cep.studio.wizard.as.internal.beans.databinding.validators.ResourceNameValidator;
import com.tibco.cep.studio.wizard.as.internal.presentation.PresentationFactory;
import com.tibco.cep.studio.wizard.as.internal.utils.Messages;
import com.tibco.cep.studio.wizard.as.internal.wizard.handlers.ASSpaceSelectionWizardPageHandler;
import com.tibco.cep.studio.wizard.as.internal.wizard.handlers.WizardPageHandlerAdapter;
import com.tibco.cep.studio.wizard.as.internal.wizard.pages.ASEventAndDestinationWizardPage;
import com.tibco.cep.studio.wizard.as.internal.wizard.pages.ASGenericWizardPage;
import com.tibco.cep.studio.wizard.as.presentation.controllers.IASDestinationWizardPageController;
import com.tibco.cep.studio.wizard.as.presentation.controllers.IASEventAndDestinationWizardPageController;
import com.tibco.cep.studio.wizard.as.presentation.controllers.IASEventWizardPageController;
import com.tibco.cep.studio.wizard.as.presentation.controllers.IASSpaceSelectionWizardPageController;
import com.tibco.cep.studio.wizard.as.presentation.controllers.IASWizardPageController;
import com.tibco.cep.studio.wizard.as.presentation.models.IASSpaceSelectionWizardPageModel;
import com.tibco.cep.studio.wizard.as.presentation.models.IASWizardPageModel;
import com.tibco.cep.studio.wizard.as.presentation.views.IASDestinationWizardPageView;
import com.tibco.cep.studio.wizard.as.presentation.views.IASEventAndDestinationWizardPageView;
import com.tibco.cep.studio.wizard.as.presentation.views.IASEventWizardPageView;
import com.tibco.cep.studio.wizard.as.presentation.views.IASSpaceSelectionWizardPageView;
import com.tibco.cep.studio.wizard.as.services.api.IASService;
import com.tibco.cep.studio.wizard.as.wizard.IASWizardPage;
import com.tibco.cep.studio.wizard.as.wizard.handlers.IWizardPageHandler;

public class WizardFactory {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<IWizardPage> createDefaultNewASWizardPages(
	        IASService service, IProject ownerProject, IContext context) {
		// Space Selection Page
		IASSpaceSelectionWizardPageView spaceSelectionWizardPageView = PresentationFactory.createASSpaceSelectionWizardPageView(service,
		        ownerProject, context);
		// Space Selection Databinding Provider
		IASSpaceSelectionWizardPageController spaceSelectionWizardPageController = spaceSelectionWizardPageView.getController();
		IDataBindingProvider spaceSelectionWizardPageDataBindingProvider = new ASNonNullToPageCompleteWizardPageDataBindingProvider(
		        spaceSelectionWizardPageController, _PROP_NAME_CURRENT_SELECTED_SPACEDEF);
		// Space Selection WizardPage Handler
		IWizardPageHandler spaceSelectionWizardPageHandler = new ASSpaceSelectionWizardPageHandler();
		IASWizardPage<? extends IASWizardPageController<? extends IASWizardPageModel>, ?> spaceSelectionWizardPage = new ASGenericWizardPage<IASSpaceSelectionWizardPageController, IASSpaceSelectionWizardPageModel>(
		        spaceSelectionWizardPageView, spaceSelectionWizardPageDataBindingProvider, spaceSelectionWizardPageHandler);



		// Event and Destination Page
		IASEventAndDestinationWizardPageView eventAndDestinationWizardPageView = PresentationFactory.createASEventAndDestinationWizardPageView(service, ownerProject, context);
		IASEventAndDestinationWizardPageController eventAndDestinationWizardPageController = eventAndDestinationWizardPageView.getController();



		// Destination Page
		IASDestinationWizardPageView destinationWizardPageView = PresentationFactory
		        .createASDestinationWizardPageView(service, ownerProject, context, eventAndDestinationWizardPageController);
		// Destination Databinding Provider
		IASDestinationWizardPageController destinationWizardPageController = destinationWizardPageView.getController();



		// Event Page
		IASEventWizardPageView eventWizardPageView = PresentationFactory.createASEventWizardPageView(service, ownerProject, context, eventAndDestinationWizardPageController);
		// Event Databinding Provider
		IASEventWizardPageController eventWizardPageController = eventWizardPageView.getController();



		// Event and Destination Page(2), Databinding Provider
		IValidator eventStrNotEmptyValidator = new StringNotEmptyValidator(Messages.getString("ASEventWizardPageComponent.non_null_validation")); //$NON-NLS-1$
		IValidator eventResNameValidator = new ResourceNameValidator("ASEventWizardPageComponent.res_name_validation"); //$NON-NLS-1$
		IValidator eventCombinedValidator = new ValidatorGroup(Arrays.asList(eventStrNotEmptyValidator, eventResNameValidator));
		IValidator destStrNotEmptyValidator = new StringNotEmptyValidator(Messages.getString("ASDestinationWizardPageComponent.non_null_validation", Messages.getString("ASUtils.display_name"))); //$NON-NLS-1$ //$NON-NLS-2$
		IValidator destResNameValidator = new ResourceNameValidator("ASDestinationWizardPageComponent.res_name_validation"); //$NON-NLS-1$
		IValidator destCombinedValidator = new ValidatorGroup(Arrays.asList(destStrNotEmptyValidator, destResNameValidator));

		IController<?>[] controllers = {eventWizardPageController, destinationWizardPageController};
		String[] modelPropNamesToBeListened = {_PROP_NAME_SIMPLE_EVENT, _PROP_NAME_DESTINATION};
		String[][] propPropNamesToBeBound = {{"name"}, {"name"}}; //$NON-NLS-1$ //$NON-NLS-2$
		EList<?>[] allEMFAttributes = {EventFactory.eINSTANCE.getEventPackage().getSimpleEvent().getEAllAttributes(), ChannelFactory.eINSTANCE.getChannelPackage().getChannel().getEAllAttributes()};
		IValidator[][] propPropValueToBeBoundValidators = {{eventCombinedValidator}, {destCombinedValidator}};
		IDataBindingProvider eventAndDestinationPropsToPageCompleteWizardPageDataBindingProvider = new ASCompositePropertiesToPageCompleteWizardPageDataBindingProvider(
				controllers, modelPropNamesToBeListened, propPropNamesToBeBound, allEMFAttributes, propPropValueToBeBoundValidators);
		IWizardPageHandler eventAndDestinationWizardPageHandler = new WizardPageHandlerAdapter();
		IASWizardPage<? extends IASWizardPageController<? extends IASWizardPageModel>, ?> eventAndDestinationWizardPage = new ASEventAndDestinationWizardPage(
		        eventAndDestinationWizardPageView,
		        eventAndDestinationPropsToPageCompleteWizardPageDataBindingProvider,
		        eventAndDestinationWizardPageHandler,
		        eventWizardPageView, destinationWizardPageView);
		
		List<IWizardPage> pages = new ArrayList<IWizardPage>();
		pages.add(spaceSelectionWizardPage);
		pages.add(eventAndDestinationWizardPage);
		return Collections.unmodifiableList(pages);
	}
}
