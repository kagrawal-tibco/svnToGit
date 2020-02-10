package com.tibco.cep.studio.wizard.as.internal.wizard.pages;

import java.util.Arrays;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.tibco.cep.studio.wizard.as.commons.beans.databinding.IDataBindingProvider;
import com.tibco.cep.studio.wizard.as.presentation.controllers.IASEventAndDestinationWizardPageController;
import com.tibco.cep.studio.wizard.as.presentation.models.IASEventAndDestinationWizardPageModel;
import com.tibco.cep.studio.wizard.as.presentation.views.IASDestinationWizardPageView;
import com.tibco.cep.studio.wizard.as.presentation.views.IASEventWizardPageView;
import com.tibco.cep.studio.wizard.as.presentation.views.IASWizardPageView;
import com.tibco.cep.studio.wizard.as.wizard.handlers.IWizardPageHandler;

public class ASEventAndDestinationWizardPage extends ASGenericWizardPage<IASEventAndDestinationWizardPageController, IASEventAndDestinationWizardPageModel> {

	private IASEventWizardPageView eventView;
	private IASDestinationWizardPageView destinationView;

	public ASEventAndDestinationWizardPage(IASWizardPageView<IASEventAndDestinationWizardPageController, IASEventAndDestinationWizardPageModel> view,
            IDataBindingProvider additionalDataBindingProvider,
            IWizardPageHandler<IASEventAndDestinationWizardPageController, IASEventAndDestinationWizardPageModel> wizardPageHandler,
            IASEventWizardPageView eventView, IASDestinationWizardPageView destinationView) {
	    super(view, additionalDataBindingProvider, wizardPageHandler);
		this.eventView = eventView;
		this.destinationView = destinationView;
    }

	@Override
	protected void initUI(Composite parent) {
		Object[] args = Arrays.asList(parent, view.getController(), eventView, destinationView).toArray();
		Control control = (Control) view.createComponent(args);
		setControl(control);
	}

}
