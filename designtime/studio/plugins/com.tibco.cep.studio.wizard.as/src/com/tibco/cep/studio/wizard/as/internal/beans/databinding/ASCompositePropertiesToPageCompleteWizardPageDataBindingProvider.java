package com.tibco.cep.studio.wizard.as.internal.beans.databinding;

import static com.tibco.cep.studio.wizard.as.internal.utils.PluginUtils.getEAttribute;
import static org.eclipse.core.databinding.UpdateValueStrategy.POLICY_NEVER;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.beans.PojoObservables;
import org.eclipse.core.databinding.observable.value.ComputedValue;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.databinding.EMFObservables;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.studio.wizard.as.commons.beans.IObservable;
import com.tibco.cep.studio.wizard.as.commons.beans.databinding.IDataBindingProvider;
import com.tibco.cep.studio.wizard.as.commons.beans.presentation.IController;
import com.tibco.cep.studio.wizard.as.commons.beans.presentation.IModel;
import com.tibco.cep.studio.wizard.as.internal.utils.Messages;
import com.tibco.cep.studio.wizard.as.wizard.IASWizardPage;

public class ASCompositePropertiesToPageCompleteWizardPageDataBindingProvider implements IDataBindingProvider {

	private IController<?>[]                           controllers;
	// Model -> String mapping
	private Map<IModel, String>       			   	   modelPropNamesToBeListened;
	// Model -> Object mapping
	private Map<IModel, Object>       			   	   modelPropValuesToBeListened;
	// Model -> String[] mapping
	private Map<IModel, String[]>       			   propPropNamesToBeBound;
	// Model -> EList<EAttribute> mapping
	private Map<IModel, EList<EAttribute>>             propAllEMFAttributes;
	// Model -> Map<Name -> IValidator> mapping
	private Map<IModel, Map<String, IValidator>>       propPropValueToBeBoundValidators;

	// WizardPage
	private IASWizardPage<?, ?>                        page;

	// Bindings
	private DataBindingContext                         bc;
	private Binding                                    pageCompleteBinding;
	private Map<IModel, Map<String, IObservableValue>> allPropObValues;

	// Handler
	private Handler                                    handler;

	@SuppressWarnings("unchecked")
    public ASCompositePropertiesToPageCompleteWizardPageDataBindingProvider(
			IController<?>[] controllers, String[] modelPropNamesToBeListened,
	        String[][] propPropNamesToBeBound, EList<?>[] allEMFAttributes, IValidator[][] propPropValueToBeBoundValidators) {
		if (controllers.length != modelPropNamesToBeListened.length
			|| controllers.length != propPropNamesToBeBound.length
			|| controllers.length != allEMFAttributes.length
			|| controllers.length != propPropValueToBeBoundValidators.length) {
			throw new IllegalArgumentException(Messages.getString("ASCompositePropertiesToPageCompleteWizardPageDataBindingProvider.constructor_params_not_match")); //$NON-NLS-1$
		}
		this.controllers = controllers;
		postConstruction(modelPropNamesToBeListened, propPropNamesToBeBound, (EList<EAttribute>[]) allEMFAttributes, propPropValueToBeBoundValidators);
	}

	private void postConstruction(String[] modelPropNamesToBeListened, String[][] propPropNamesToBeBound, EList<EAttribute>[] propAllEMFAttributes, IValidator[][] propPropValueToBeBoundValidators) {
		this.modelPropNamesToBeListened = new HashMap<IModel, String>();
		this.propPropNamesToBeBound = new HashMap<IModel, String[]>();
		this.propAllEMFAttributes = new HashMap<IModel, EList<EAttribute>>();
		this.propPropValueToBeBoundValidators = new HashMap<IModel, Map<String, IValidator>>();
		for (int i=0; i<controllers.length; i++) {
			IController<?> controller = controllers[i];
			IModel model = controller.getModel();
			String modelPropNameToBeListened = modelPropNamesToBeListened[i];
			EList<EAttribute> allEMFAttributes = propAllEMFAttributes[i];
			String[] propPropNames = propPropNamesToBeBound[i];
			IValidator[] propPropValueValidators = propPropValueToBeBoundValidators[i];

			Map<String, IValidator> nameValidatorMapping = new HashMap<String, IValidator>();
			for (int j=0; j<propPropNames.length; j++) {
				String propPropName = propPropNames[j];
				IValidator propPropValueValidator = propPropValueValidators[j];
				nameValidatorMapping.put(propPropName, propPropValueValidator);
			}

			this.modelPropNamesToBeListened.put(model, modelPropNameToBeListened);
			this.propPropNamesToBeBound.put(model, propPropNames);
			this.propAllEMFAttributes.put(model, allEMFAttributes);
			this.propPropValueToBeBoundValidators.put(model, nameValidatorMapping);
		}
    }

	@Override
	public void bind(DataBindingContext bc, Object... params) {
		this.bc = bc;
		this.page = (IASWizardPage<?, ?>) params[0];
		this.modelPropValuesToBeListened = new HashMap<IModel, Object>();
		this.allPropObValues = new HashMap<IModel, Map<String,IObservableValue>>();

		this.handler = new Handler();
		for (int i=0; i<controllers.length; i++) {
			IController<?> controller = controllers[i];
    		IModel model = controller.getModel();
    		String modelPropNameToBeListened = modelPropNamesToBeListened.get(model);
    		model.addPropertyChangeListener(modelPropNameToBeListened, handler);
		}
	}

	private void reattachPropertyBindings(IModel model, Object modelPropValue) {
		unattachSpecifiedBindings(model);

		if (null != modelPropValue) {
    		String[] propNames = propPropNamesToBeBound.get(model);
    		Map<String, IObservableValue> propObValues = new HashMap<String, IObservableValue>();
    		for (String propName : propNames) {
        		IObservableValue propObValue = null;
        		if (modelPropValue instanceof EObject) {
        			EObject ePropValue = (EObject) modelPropValue;
        			EList<EAttribute> allChannelAttributes = propAllEMFAttributes.get(model);
        			EAttribute attributeToBeBound = getEAttribute(allChannelAttributes, propName);
        			propObValue = EMFObservables.observeValue(ePropValue, attributeToBeBound);
        		} else if (modelPropValue instanceof IObservable) {
        			propObValue = BeansObservables.observeValue(modelPropValue, propName);
        		} else {
        			// pojo
        			propObValue = PojoObservables.observeValue(modelPropValue, propName);
        		}

        		propObValues.put(propName, propObValue);

    		}
    		modelPropValuesToBeListened.put(model, modelPropValue);
    		allPropObValues.put(model, propObValues);
		}

		// create binding with existing observable objects
		IObservableValue compositeObValue = new CompositeValue(controllers, allPropObValues, propPropValueToBeBoundValidators);
		IObservableValue pageCompleteObValue = PojoObservables.observeValue(page, "pageComplete"); //$NON-NLS-1$
		pageCompleteBinding = bc.bindValue(pageCompleteObValue, compositeObValue, new UpdateValueStrategy(POLICY_NEVER), null);
	}

	private void unattachSpecifiedBindings(IModel model) {
		allPropObValues.remove(model);
		if (null != pageCompleteBinding) {
			pageCompleteBinding.dispose();
			pageCompleteBinding = null;
		}
	}

	private class Handler implements PropertyChangeListener {

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			IModel model = (IModel) evt.getSource();
			Object newPropValue = evt.getNewValue();
			reattachPropertyBindings(model, newPropValue);
		}

	}

	private class CompositeValue extends ComputedValue {

		private IController<?>[]                           controllers;
		private Map<IModel, Map<String, IObservableValue>> allPropObValues;
		private Map<IModel, Map<String, IValidator>>       propPropValueToBeBoundValidators;

		private CompositeValue(
				IController<?>[] controllers,
				Map<IModel, Map<String, IObservableValue>> allPropObValues,
				Map<IModel, Map<String, IValidator>> propPropValueToBeBoundValidators) {
			super(Boolean.class);
			this.controllers = controllers;
			this.allPropObValues = allPropObValues;
			this.propPropValueToBeBoundValidators = propPropValueToBeBoundValidators;
		}

		@Override
        protected Object calculate() {
			boolean pageComplete = allPropObValues.size() == controllers.length;
			if (pageComplete) {
    			for (Map.Entry<IModel, Map<String, IObservableValue>> entry : allPropObValues.entrySet()) {
    				IModel model = entry.getKey();
    				Map<String, IObservableValue> propObValues = entry.getValue();
					Map<String, IValidator> validators = propPropValueToBeBoundValidators.get(model);
    				for (Map.Entry<String, IObservableValue> propObValueEntry : propObValues.entrySet()) {
        				String propPropName = propObValueEntry.getKey();
        				IObservableValue propPropObValue = propObValueEntry.getValue();
        				Object propValue = propPropObValue.getValue();
        				IValidator propValueValidator = validators.get(propPropName);
        				IStatus status = propValueValidator.validate(propValue);
        				pageComplete = status.isOK();
        				if (false == pageComplete) {
        					break;
        				}
    				}
    				if (false == pageComplete) {
    					break;
    				}
    			}
			}
			return pageComplete;
        }

	}

}
