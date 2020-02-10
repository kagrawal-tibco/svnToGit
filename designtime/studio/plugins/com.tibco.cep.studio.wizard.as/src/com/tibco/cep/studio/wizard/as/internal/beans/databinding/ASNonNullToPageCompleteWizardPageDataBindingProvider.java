package com.tibco.cep.studio.wizard.as.internal.beans.databinding;

import static org.eclipse.core.databinding.UpdateValueStrategy.POLICY_NEVER;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.beans.PojoObservables;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.value.IObservableValue;

import com.tibco.cep.studio.wizard.as.commons.beans.databinding.IDataBindingProvider;
import com.tibco.cep.studio.wizard.as.commons.beans.databinding.converters.NonNullToBooleanConverter;
import com.tibco.cep.studio.wizard.as.presentation.controllers.IASWizardPageController;
import com.tibco.cep.studio.wizard.as.presentation.models.IASWizardPageModel;
import com.tibco.cep.studio.wizard.as.wizard.IASWizardPage;

public class ASNonNullToPageCompleteWizardPageDataBindingProvider implements IDataBindingProvider {

	private IASWizardPageController<? extends IASWizardPageModel> controller;
	private String                                      modelPropName;

	public ASNonNullToPageCompleteWizardPageDataBindingProvider(IASWizardPageController<? extends IASWizardPageModel> controller, String modelPropName) {
		this.controller = controller;
		this.modelPropName = modelPropName;
	}

	@Override
	public void bind(DataBindingContext bc, Object... params) {
		IASWizardPage<?, ?> page = (IASWizardPage<?, ?>) params[0];

		IASWizardPageModel model = controller.getModel();
		// Property pageComplete
		IObservableValue modelObValue = BeansObservables.observeValue(model, modelPropName);
		IObservableValue pageCompleteObValue = PojoObservables.observeValue(page, "pageComplete"); //$NON-NLS-1$

		UpdateValueStrategy strategy = new UpdateValueStrategy();
		IConverter converter = new NonNullToBooleanConverter();
		strategy.setConverter(converter);

		bc.bindValue(pageCompleteObValue, modelObValue,
		        // Model -> View one-way update strategy
		        new UpdateValueStrategy(POLICY_NEVER), strategy);

	}

}
