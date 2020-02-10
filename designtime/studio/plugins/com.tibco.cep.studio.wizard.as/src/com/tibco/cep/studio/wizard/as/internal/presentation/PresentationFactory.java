package com.tibco.cep.studio.wizard.as.internal.presentation;

import static com.tibco.cep.studio.wizard.as.ASConstants._K_CTX_AS_CONTROLLER_DESTINATION;
import static com.tibco.cep.studio.wizard.as.ASConstants._K_CTX_AS_CONTROLLER_EVENT;
import static com.tibco.cep.studio.wizard.as.ASConstants._K_CTX_AS_CONTROLLER_EVENT_AND_DESTINATION;
import static com.tibco.cep.studio.wizard.as.ASConstants._K_CTX_AS_CONTROLLER_SPACE_SELECTION;
import static com.tibco.cep.studio.wizard.as.ASConstants._K_CTX_WIZARD_CONTROLLER_NEW;

import org.eclipse.core.resources.IProject;

import com.tibco.cep.studio.wizard.as.ASPlugin;
import com.tibco.cep.studio.wizard.as.commons.beans.factory.IBeanFactory;
import com.tibco.cep.studio.wizard.as.commons.beans.factory.ReflectionBeanFactory;
import com.tibco.cep.studio.wizard.as.commons.commands.ICommand;
import com.tibco.cep.studio.wizard.as.commons.utils.IContext;
import com.tibco.cep.studio.wizard.as.internal.commands.ShowNextPageCommand;
import com.tibco.cep.studio.wizard.as.internal.presentation.controllers.ASDestinationWizardPageController;
import com.tibco.cep.studio.wizard.as.internal.presentation.controllers.ASEventAndDestinationWizardPageController;
import com.tibco.cep.studio.wizard.as.internal.presentation.controllers.ASEventWizardPageController;
import com.tibco.cep.studio.wizard.as.internal.presentation.controllers.ASSpaceSelectionWizardPageController;
import com.tibco.cep.studio.wizard.as.internal.presentation.controllers.NewASWizardController;
import com.tibco.cep.studio.wizard.as.internal.presentation.models.ASDestinationWizardPageModel;
import com.tibco.cep.studio.wizard.as.internal.presentation.models.ASEventAndDestinationWizardPageModel;
import com.tibco.cep.studio.wizard.as.internal.presentation.models.ASEventWizardPageModel;
import com.tibco.cep.studio.wizard.as.internal.presentation.models.ASSpaceSelectionWizardPageModel;
import com.tibco.cep.studio.wizard.as.internal.presentation.models.NewASWizardModel;
import com.tibco.cep.studio.wizard.as.internal.presentation.views.ASDestinationWizardPageView;
import com.tibco.cep.studio.wizard.as.internal.presentation.views.ASEventAndDestinationWizardPageView;
import com.tibco.cep.studio.wizard.as.internal.presentation.views.ASEventWizardPageView;
import com.tibco.cep.studio.wizard.as.internal.presentation.views.ASSpaceSelectionWizardPageView;
import com.tibco.cep.studio.wizard.as.internal.ui.components.ASDestinationWizardPageComponent;
import com.tibco.cep.studio.wizard.as.internal.ui.components.ASEventAndDestinationWizardPageComponent;
import com.tibco.cep.studio.wizard.as.internal.ui.components.ASEventWizardPageComponent;
import com.tibco.cep.studio.wizard.as.internal.ui.components.ASSpaceSelectionWizardPageComponent;
import com.tibco.cep.studio.wizard.as.presentation.controllers.IASDestinationWizardPageController;
import com.tibco.cep.studio.wizard.as.presentation.controllers.IASEventAndDestinationWizardPageController;
import com.tibco.cep.studio.wizard.as.presentation.controllers.IASEventWizardPageController;
import com.tibco.cep.studio.wizard.as.presentation.controllers.IASSpaceSelectionWizardPageController;
import com.tibco.cep.studio.wizard.as.presentation.controllers.INewASWizardController;
import com.tibco.cep.studio.wizard.as.presentation.models.IASDestinationWizardPageModel;
import com.tibco.cep.studio.wizard.as.presentation.models.IASEventAndDestinationWizardPageModel;
import com.tibco.cep.studio.wizard.as.presentation.models.IASEventWizardPageModel;
import com.tibco.cep.studio.wizard.as.presentation.models.IASSpaceSelectionWizardPageModel;
import com.tibco.cep.studio.wizard.as.presentation.models.INewASWizardModel;
import com.tibco.cep.studio.wizard.as.presentation.views.IASDestinationWizardPageView;
import com.tibco.cep.studio.wizard.as.presentation.views.IASEventAndDestinationWizardPageView;
import com.tibco.cep.studio.wizard.as.presentation.views.IASEventWizardPageView;
import com.tibco.cep.studio.wizard.as.presentation.views.IASSpaceSelectionWizardPageView;
import com.tibco.cep.studio.wizard.as.services.api.IASService;

public class PresentationFactory {

	public static IASSpaceSelectionWizardPageView createASSpaceSelectionWizardPageView(IASService service, IProject ownerProject, IContext context) {
		// Space Selection Page MVC
		IASSpaceSelectionWizardPageModel model = new ASSpaceSelectionWizardPageModel(
		        IASSpaceSelectionWizardPageModel._PROP_VALUE_WIZARD_PAGE_NAME,
		        IASSpaceSelectionWizardPageModel._PROP_VALUE_WIZARD_PAGE_TITLE,
		        IASSpaceSelectionWizardPageModel._PROP_VALUE_WIZARD_PAGE_DESCRIPTION,
//		        null,
		        ASPlugin.getImageDescriptor(IASSpaceSelectionWizardPageModel._PROP_VALUE_WIZARD_PAGE_IMAGE),
		        ownerProject, context);
		model.setWizardPageErrorMessageBindingEnabled(false);
		ICommand showNextPageCommand = new ShowNextPageCommand(IASSpaceSelectionWizardPageModel._PROP_VALUE_WIZARD_PAGE_NAME + ".showNextPageCommand"); //$NON-NLS-1$
		IASSpaceSelectionWizardPageController controller = new ASSpaceSelectionWizardPageController(model, service, showNextPageCommand);
//		IBeanFactory factory = new ReflectionBeanFactory(ASDebugWizardPageComponent.class);
		IBeanFactory factory = new ReflectionBeanFactory(ASSpaceSelectionWizardPageComponent.class);
		IASSpaceSelectionWizardPageView view = new ASSpaceSelectionWizardPageView(controller,
		        factory);

		context.bind(_K_CTX_AS_CONTROLLER_SPACE_SELECTION, controller);

		return view;
	}

	public static IASEventWizardPageView createASEventWizardPageView(IASService service, IProject ownerProject, IContext context, IASEventAndDestinationWizardPageController eventAndDestinationController) {
		// Event Page MVC
		IASEventWizardPageModel model = new ASEventWizardPageModel(
				IASEventWizardPageModel._PROP_VALUE_WIZARD_PAGE_NAME,
		        IASEventWizardPageModel._PROP_VALUE_WIZARD_PAGE_TITLE,
		        IASEventWizardPageModel._PROP_VALUE_WIZARD_PAGE_DESCRIPTION,
//		        null,
		        ASPlugin.getImageDescriptor(IASEventWizardPageModel._PROP_VALUE_WIZARD_PAGE_IMAGE),
		        ownerProject, context);
		IASEventWizardPageController controller = new ASEventWizardPageController(eventAndDestinationController, model, service);
//		IBeanFactory factory = new ReflectionBeanFactory(ASDebugWizardPageComponent.class);
		IBeanFactory factory = new ReflectionBeanFactory(ASEventWizardPageComponent.class);
		IASEventWizardPageView view = new ASEventWizardPageView(controller, factory);

		context.bind(_K_CTX_AS_CONTROLLER_EVENT, controller);

		return view;
	}

	public static IASDestinationWizardPageView createASDestinationWizardPageView(IASService service, IProject ownerProject, IContext context, IASEventAndDestinationWizardPageController eventAndDestinationController) {
		// Destination Page MVC
		IASDestinationWizardPageModel model = new ASDestinationWizardPageModel(
				IASDestinationWizardPageModel._PROP_VALUE_WIZARD_PAGE_NAME,
				IASDestinationWizardPageModel._PROP_VALUE_WIZARD_PAGE_TITLE,
				IASDestinationWizardPageModel._PROP_VALUE_WIZARD_PAGE_DESCRIPTION,
//		        null,
		        ASPlugin.getImageDescriptor(IASDestinationWizardPageModel._PROP_VALUE_WIZARD_PAGE_IMAGE),
		        ownerProject, context);
		IASDestinationWizardPageController controller = new ASDestinationWizardPageController(eventAndDestinationController, model, service);
//		IBeanFactory factory = new ReflectionBeanFactory(ASDebugWizardPageComponent.class);
		IBeanFactory factory = new ReflectionBeanFactory(ASDestinationWizardPageComponent.class);
		IASDestinationWizardPageView view = new ASDestinationWizardPageView(controller, factory);

		context.bind(_K_CTX_AS_CONTROLLER_DESTINATION, controller);


		return view;
	}

	public static IASEventAndDestinationWizardPageView createASEventAndDestinationWizardPageView(IASService service, IProject ownerProject, IContext context) {
		// Event and Destination Page MVC
		IASEventAndDestinationWizardPageModel model = new ASEventAndDestinationWizardPageModel(
				IASEventAndDestinationWizardPageModel._PROP_VALUE_WIZARD_PAGE_NAME,
				IASEventAndDestinationWizardPageModel._PROP_VALUE_WIZARD_PAGE_TITLE,
				IASEventAndDestinationWizardPageModel._PROP_VALUE_WIZARD_PAGE_DESCRIPTION,
//		        null,
		        ASPlugin.getImageDescriptor(IASEventAndDestinationWizardPageModel._PROP_VALUE_WIZARD_PAGE_IMAGE),
		        ownerProject, context);
		IASEventAndDestinationWizardPageController controller = new ASEventAndDestinationWizardPageController(model, service);
//		IBeanFactory factory = new ReflectionBeanFactory(ASDebugWizardPageComponent.class);
		IBeanFactory factory = new ReflectionBeanFactory(ASEventAndDestinationWizardPageComponent.class);
		IASEventAndDestinationWizardPageView view = new ASEventAndDestinationWizardPageView(controller, factory);

		context.bind(_K_CTX_AS_CONTROLLER_EVENT_AND_DESTINATION, controller);


		return view;
	}

	public static INewASWizardController createNewASWizardController(IASService service, IProject project, IContext context) {
		// Wizard MC
		INewASWizardModel newASWizardModel = new NewASWizardModel(service, project, context, INewASWizardModel._PROP_VALUE_WIZARD_WINDOW_TITLE);
		INewASWizardController newASWizardController = new NewASWizardController(newASWizardModel);
		context.bind(_K_CTX_WIZARD_CONTROLLER_NEW, newASWizardController);

		return newASWizardController;
	}

}
