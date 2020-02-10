package com.tibco.cep.studio.wizard.as.internal.beans.databinding;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.core.databinding.DataBindingContext;

import com.tibco.cep.studio.wizard.as.commons.beans.databinding.IDataBindingProvider;
import com.tibco.cep.studio.wizard.as.commons.beans.presentation.IController;
import com.tibco.cep.studio.wizard.as.commons.beans.presentation.IModel;
import com.tibco.cep.studio.wizard.as.commons.utils.IContext;
import com.tibco.cep.studio.wizard.as.internal.utils.Messages;
import com.tibco.cep.studio.wizard.as.presentation.controllers.IASWizardPageController;
import com.tibco.cep.studio.wizard.as.presentation.models.IASWizardPageModel;

abstract public class AASSimplePropertyHandlingWizardPageDataBindingProvider<C extends IASWizardPageController<? extends IASWizardPageModel>>
        implements IDataBindingProvider {

	private C     controller;
	private String[]  controllerCtxKeys;
	private String[]  modelPropNames;

	// Handler
	private Handler handler;

	protected AASSimplePropertyHandlingWizardPageDataBindingProvider(C controller, String controllerCtxKey, String modelPropName) {
		this(controller, new String[] {controllerCtxKey}, new String[] {modelPropName});
	}

	protected AASSimplePropertyHandlingWizardPageDataBindingProvider(C controller, String[] controllerCtxKeys, String[] modelPropNames) {
		if (controllerCtxKeys.length != modelPropNames.length) {
			throw new IllegalArgumentException(Messages.getString("AASSimplePropertyHandlingWizardPageDataBindingProvider.ctx_n_prop_not_match")); //$NON-NLS-1$
		}
		this.controller = controller;
		this.controllerCtxKeys = controllerCtxKeys;
		this.modelPropNames = modelPropNames;
	}

	@Override
	public void bind(DataBindingContext bc, Object... params) {
		this.handler = new Handler();

		IContext context = controller.getModel().getContext();
		for (int i=0; i<controllerCtxKeys.length; i++) {
			String controllerCtxKey = controllerCtxKeys[i];
			String modelPropName = modelPropNames[i];
    		IController<?> tagetController = (IController<?>) context.get(controllerCtxKey);
    		IModel targetModel = tagetController.getModel();
    		targetModel.addPropertyChangeListener(modelPropName, handler);
		}
	}

	abstract protected void handlePropertyChanged(C controller, PropertyChangeEvent evt);

	private class Handler implements PropertyChangeListener {

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			handlePropertyChanged(controller, evt);
		}

	}

}
