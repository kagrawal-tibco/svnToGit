package com.tibco.cep.studio.wizard.as.internal.ui.components;

import static org.eclipse.swt.SWT.FILL;
import static org.eclipse.swt.SWT.NULL;
import static org.eclipse.swt.SWT.SHADOW_ETCHED_OUT;

import java.util.Arrays;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import com.tibco.cep.studio.wizard.as.commons.beans.presentation.IController;
import com.tibco.cep.studio.wizard.as.commons.beans.presentation.IView;
import com.tibco.cep.studio.wizard.as.internal.utils.Messages;
import com.tibco.cep.studio.wizard.as.presentation.controllers.IASEventAndDestinationWizardPageController;
import com.tibco.cep.studio.wizard.as.presentation.views.IASDestinationWizardPageView;
import com.tibco.cep.studio.wizard.as.presentation.views.IASEventWizardPageView;

public class ASEventAndDestinationWizardPageComponent extends Composite {

	// Controller
	private IASEventAndDestinationWizardPageController controller;

	// View
	private IASEventWizardPageView                     eventView;
	private IASDestinationWizardPageView               destinationView;

	// UI
	private Composite                                  container;
	private Group                                      sectionSimpleEvent;
	private Group                                      sectionDestination;

	public ASEventAndDestinationWizardPageComponent(Composite parent, IASEventAndDestinationWizardPageController controller,
	        IASEventWizardPageView eventView, IASDestinationWizardPageView destinationView) {
		super(parent, NULL);
		this.controller = controller;
		this.eventView = eventView;
		this.destinationView = destinationView;
		initialize();
	}

	private void initialize() {
		initUI();
		initData();
	}

	private void initUI() {
		FillLayout fillLayout = new FillLayout();
		fillLayout.marginHeight = 10;
		fillLayout.marginWidth = 10;
		this.setLayout(new FillLayout());

		container = new Composite(this, NULL);

		createComponentsWithGridLayout();
	}

	/**
	 * +------------------------------+
	 * |                              | <-- This View
	 * | New Event and Destination    |     |
	 * |                              |     |
	 * +------------------------------+     |
	 * |                              |     |
	 * | +-SimpleEvent--------------+ |     <-- SimpleEvent View
	 * | |                          | |     |
	 * | | Event Name:[testEvent ]  | |     |
	 * | |                          | |     |
	 * | +--------------------------+ |     |
	 * |                              |     |
	 * | +-Destination -------------+ |     <-- Destination View
	 * | |                          | |
	 * | |      Name: [testDest  ]  | |
	 * | | Event URI: /Events/...   | |
	 * | | ... ...                  | |
	 * | |                          | |
	 * | +--------------------------+ |
	 * |                              |
	 * +------------------------------+
	 * |                              |
	 * |   <PREVIOUS> <NEXT> <FINISH> |
	 * |                              |
	 * +------------------------------+
	 */
	private void createComponentsWithGridLayout() {
		GridLayout layout = new GridLayout();
		container.setLayout(layout);

		// simple event section
		sectionSimpleEvent = createSection(container, eventView, controller.getASEventWizardPageController());

		// destination section
		sectionDestination = createSection(container, destinationView, controller.getASDestinationWizardPageController());
    }


	private Group createSection(Composite parent, IView<?, ?> clientView, IController<?> clientController) {
		Group section = new Group(parent, SHADOW_ETCHED_OUT);
		GridData layoutData = new GridData(FILL, FILL, true, false);
		section.setLayoutData(layoutData);
		section.setLayout(new FillLayout());
		createClientComponents(section, clientView, clientController);
		return section;
	}

	private void createClientComponents(Composite parent, IView<?, ?> clientView, IController<?> clientController) {
		Object[] args = Arrays.asList(parent, clientController).toArray();
		clientView.createComponent(args);
	}

	private void initData() {
		sectionSimpleEvent.setText(Messages.getString("ASEventAndDestinationWizardPageComponent.section_title_simple_event")); //$NON-NLS-1$
		sectionDestination.setText(Messages.getString("ASEventAndDestinationWizardPageComponent.section_title_dest")); //$NON-NLS-1$
	}

}