package com.tibco.cep.studio.wizard.as.internal.wizard.handlers;

import static com.tibco.cep.studio.wizard.as.ASConstants._EVENT_FOLDER;
import static com.tibco.cep.studio.wizard.as.internal.utils.PluginUtils.getEAttribute;
import static org.eclipse.core.databinding.UpdateValueStrategy.POLICY_NEVER;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.conversion.Converter;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.databinding.EMFObservables;
import org.eclipse.emf.ecore.EAttribute;

import com.tibco.as.space.SpaceDef;
import com.tibco.cep.designtime.core.model.event.EventFactory;
import com.tibco.cep.designtime.core.model.event.SimpleEvent;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.ChannelFactory;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.studio.wizard.as.ASPlugin;
import com.tibco.cep.studio.wizard.as.commons.utils.IContext;
import com.tibco.cep.studio.wizard.as.commons.utils.StringUtils;
import com.tibco.cep.studio.wizard.as.internal.utils.Messages;
import com.tibco.cep.studio.wizard.as.presentation.controllers.IASDestinationWizardPageController;
import com.tibco.cep.studio.wizard.as.presentation.controllers.IASEventAndDestinationWizardPageController;
import com.tibco.cep.studio.wizard.as.presentation.controllers.IASEventWizardPageController;
import com.tibco.cep.studio.wizard.as.presentation.controllers.IASSpaceSelectionWizardPageController;
import com.tibco.cep.studio.wizard.as.presentation.controllers.IASWizardPageController;
import com.tibco.cep.studio.wizard.as.presentation.models.IASEventAndDestinationWizardPageModel;
import com.tibco.cep.studio.wizard.as.presentation.models.IASSpaceSelectionWizardPageModel;
import com.tibco.cep.studio.wizard.as.presentation.models.IASWizardPageModel;
import com.tibco.cep.studio.wizard.as.wizard.IASWizardPage;
import com.tibco.cep.studio.wizard.as.wizard.handlers.IWizardPageHandler;

public class ASSpaceSelectionWizardPageHandler implements IWizardPageHandler<IASSpaceSelectionWizardPageController, IASSpaceSelectionWizardPageModel> {

	private DataBindingContext bc;
	private Binding            destinationNameToEventDestinationNameBinding;
	private Binding            eventNameToDestinatoinEventUriBinding;
	private EAttribute         eventNameAttribute;
	private EAttribute         eventDestinationNameAttribute;
	private EAttribute         destinationNameAttribute;
	private EAttribute         destinationEventUriAttribute;

	public ASSpaceSelectionWizardPageHandler() {
		initialize();
	}

	private void initialize() {
	    attacheBindings();
    }

	private void attacheBindings() {
	    this.bc = new DataBindingContext();

	    // cache EAttributes of Destination and SimpleEvent
		EList<EAttribute> allEventAttributes = EventFactory.eINSTANCE.getEventPackage().getSimpleEvent().getEAllAttributes();
		String propEventName = "name"; //$NON-NLS-1$
		this.eventNameAttribute = getEAttribute(allEventAttributes, propEventName);
		String propEventDestinationName = "destinationName"; //$NON-NLS-1$
		this.eventDestinationNameAttribute = getEAttribute(allEventAttributes, propEventDestinationName);

		EList<EAttribute> allDestinationAttributes = ChannelFactory.eINSTANCE.getChannelPackage().getDestination().getEAllAttributes();
		String propDestinationName = "name"; //$NON-NLS-1$
		this.destinationNameAttribute = getEAttribute(allDestinationAttributes, propDestinationName);
		String propDestinationEventUri = "eventURI"; //$NON-NLS-1$
		this.destinationEventUriAttribute = getEAttribute(allDestinationAttributes, propDestinationEventUri);
    }

	@Override
	public void handlePageChanging(IASWizardPage<? extends IASSpaceSelectionWizardPageController, ? extends IASSpaceSelectionWizardPageModel> currentPage,
	        IASWizardPage<? extends IASWizardPageController<? extends IASWizardPageModel>, ? extends IASWizardPageModel> targetPage, IContext context)
	        throws Exception {
		if (IASEventAndDestinationWizardPageModel._PROP_VALUE_WIZARD_PAGE_NAME.equals(targetPage.getASWizardPageController().getModel().getWizardPageName())) {
			/**
			 * We must create Destination and SimpleEvent at same time,
			 * because there's a failed in the following test case if we don't do like this:
			 * 1. Choose space named spaceA, and click Next.
			 * 2. Click Next.
			 * 3. Back to Channel/Space selection page.
			 * 4. Choose spaceB, and click Next.
			 * 5. Click Finish.
			 *
			 * The result is:
			 * 1. Destination is based on spaceB.
			 * 2. But SimpleEvent is based on spaceA.
			 */
			// create Destination
			IASSpaceSelectionWizardPageController spaceSelectionWizardPageController = currentPage.getASWizardPageController();
			Channel currentSelectedChannel = spaceSelectionWizardPageController.getModel().getCurrentSelectedChannel();
			SpaceDef currentSelectedSpaceDef = spaceSelectionWizardPageController.getModel().getCurrentSelectedSpaceDef();
			IASEventAndDestinationWizardPageController eventAndDestinationWizardPageController = (IASEventAndDestinationWizardPageController) targetPage
			        .getASWizardPageController();
			IASDestinationWizardPageController destinationWizardPageController = eventAndDestinationWizardPageController.getASDestinationWizardPageController();
			Destination destination = destinationWizardPageController.createDestination(currentSelectedChannel, currentSelectedSpaceDef);
			ASPlugin.log(Messages.getString("ASSpaceSelectionWizardPageHandler.create_dest", getClass().getSimpleName(), destination)); //$NON-NLS-1$

			// create simple event
			IASEventWizardPageController eventWizardPageController = eventAndDestinationWizardPageController.getASEventWizardPageController();
			SimpleEvent event = eventWizardPageController.createSimpleEvent(currentSelectedChannel, currentSelectedSpaceDef);
			ASPlugin.log(Messages.getString("ASSpaceSelectionWizardPageHandler.create_simple_event", getClass().getSimpleName(), destination)); //$NON-NLS-1$

			reAttachBindings(destination, event);
		}
	}

	private void reAttachBindings(Destination destination, SimpleEvent event) {
		unattachSpecifiedBindings();

		IObservableValue eventNameObValue = EMFObservables.observeValue(event, eventNameAttribute);
		IObservableValue eventDestinationNameObValue = EMFObservables.observeValue(event, eventDestinationNameAttribute);

		IObservableValue destinationNameObValue = EMFObservables.observeValue(destination, destinationNameAttribute);
		IObservableValue destinationEventUriObValue = EMFObservables.observeValue(destination, destinationEventUriAttribute);

		UpdateValueStrategy eventNameToDestinationEventUriUpdateStrategy = new UpdateValueStrategy();
		eventNameToDestinationEventUriUpdateStrategy.setConverter(new EventNameToDestinationEventUriConverter());
		eventNameToDestinatoinEventUriBinding = bc.bindValue(destinationEventUriObValue, eventNameObValue, new UpdateValueStrategy(POLICY_NEVER), eventNameToDestinationEventUriUpdateStrategy);
		destinationNameToEventDestinationNameBinding = bc.bindValue(eventDestinationNameObValue, destinationNameObValue, new UpdateValueStrategy(POLICY_NEVER), null);
    }

	private void unattachSpecifiedBindings() {
		if (null != destinationNameToEventDestinationNameBinding) {
			destinationNameToEventDestinationNameBinding.dispose();
			destinationNameToEventDestinationNameBinding = null;
		}
		if (null != eventNameToDestinatoinEventUriBinding) {
			eventNameToDestinatoinEventUriBinding.dispose();
			eventNameToDestinatoinEventUriBinding = null;
		}
	}

	@Override
	public void handlePageChanged(IASWizardPage<? extends IASSpaceSelectionWizardPageController, ? extends IASSpaceSelectionWizardPageModel> currentPage, IContext context)
	        throws Exception {
		// do nothing
	}


	private class EventNameToDestinationEventUriConverter extends Converter {

		private EventNameToDestinationEventUriConverter() {
	        super(String.class, String.class);
        }

		@Override
        public Object convert(Object fromObject) {
			String destinationEvenUri = _EVENT_FOLDER;
	        String eventName = (String) fromObject;
	        if (StringUtils.isNotBlank(eventName)) {
	        	destinationEvenUri += eventName;
	        }
	        return destinationEvenUri;
        }

	}

}
