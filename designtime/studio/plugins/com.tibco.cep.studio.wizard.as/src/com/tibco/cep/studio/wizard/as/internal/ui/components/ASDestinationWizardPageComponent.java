package com.tibco.cep.studio.wizard.as.internal.ui.components;

import static com.tibco.cep.driver.as.ASConstants.K_AS_DEST_PROP_BROWSER_TYPE;
import static com.tibco.cep.driver.as.ASConstants.K_AS_DEST_PROP_CONSUMPTION_MODE;
import static com.tibco.cep.driver.as.ASConstants.K_V_AS_DEST_PROP_EXPIRE_EVENT;
import static com.tibco.cep.driver.as.ASConstants.K_V_AS_DEST_PROP_PUT_EVENT;
import static com.tibco.cep.driver.as.ASConstants.K_V_AS_DEST_PROP_TAKE_EVENT;
import static com.tibco.cep.studio.wizard.as.ASConstants._BEAN_PROP_GETTERS_DESTINATION;
import static com.tibco.cep.studio.wizard.as.internal.utils.ASUtils.getDestinationAdditionalProperty;
import static com.tibco.cep.studio.wizard.as.internal.utils.ASUtils.getDestinationAdditionalPropertyValue;
import static com.tibco.cep.studio.wizard.as.internal.utils.ASUtils.getDestinationUISchemas;
import static com.tibco.cep.studio.wizard.as.internal.utils.ASUtils.getDestinationUIUnitLabel;
import static com.tibco.cep.studio.wizard.as.internal.utils.ASUtils.getDestinationUIUnitModelToUIConversionMethod;
import static com.tibco.cep.studio.wizard.as.internal.utils.ASUtils.getDestinationUIUnitPropertyDisplayName;
import static com.tibco.cep.studio.wizard.as.internal.utils.ASUtils.getDestinationUIUnitPropertyLabelName;
import static com.tibco.cep.studio.wizard.as.internal.utils.ASUtils.getDestinationUIUnitPropertyKey;
import static com.tibco.cep.studio.wizard.as.internal.utils.ASUtils.getDestinationUIUnitPropertyName;
import static com.tibco.cep.studio.wizard.as.internal.utils.ASUtils.getDestinationUIUnitUIInitialValue;
import static com.tibco.cep.studio.wizard.as.internal.utils.ASUtils.getDestinationUIUnitUIType;
import static com.tibco.cep.studio.wizard.as.internal.utils.ASUtils.getDestinationUIUnitValueControl;
import static com.tibco.cep.studio.wizard.as.internal.utils.ASUtils.isDestinationUIUnitAdditionalProperty;
import static com.tibco.cep.studio.wizard.as.internal.utils.ASUtils.isDestinationUIUnitEnabeld;
import static com.tibco.cep.studio.wizard.as.internal.utils.ASUtils.isDestinationUIUnitNeedNonNullControlDecorator;
import static com.tibco.cep.studio.wizard.as.internal.utils.ASUtils.isNeedDataBinding;
import static com.tibco.cep.studio.wizard.as.internal.utils.PluginUtils.getEAttribute;
import static com.tibco.cep.studio.wizard.as.internal.utils.UIUtils.createDecorator;
import static com.tibco.cep.studio.wizard.as.presentation.models.IASDestinationWizardPageModel._PROP_NAME_DESTINATION;
import static org.eclipse.core.databinding.UpdateValueStrategy.POLICY_CONVERT;
import static org.eclipse.swt.SWT.BORDER;
import static org.eclipse.swt.SWT.CHECK;
import static org.eclipse.swt.SWT.FILL;
import static org.eclipse.swt.SWT.Modify;
import static org.eclipse.swt.SWT.NONE;
import static org.eclipse.swt.SWT.READ_ONLY;
import static org.eclipse.swt.SWT.SINGLE;
import static org.eclipse.swt.layout.GridData.HORIZONTAL_ALIGN_END;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.conversion.Converter;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.databinding.EMFObservables;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.designtime.core.model.ModelFactory;
import com.tibco.cep.designtime.core.model.SimpleProperty;
import com.tibco.cep.designtime.core.model.service.channel.ChannelFactory;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.studio.wizard.as.commons.beans.databinding.converters.BooleanToStringConverter;
import com.tibco.cep.studio.wizard.as.commons.beans.databinding.converters.CapitalizeConverter;
import com.tibco.cep.studio.wizard.as.commons.beans.databinding.converters.StringToBooleanConverter;
import com.tibco.cep.studio.wizard.as.commons.beans.databinding.converters.ToUpperCaseConverter;
import com.tibco.cep.studio.wizard.as.commons.beans.databinding.validators.StringNotEmptyValidator;
import com.tibco.cep.studio.wizard.as.commons.beans.databinding.validators.ValidatorGroup;
import com.tibco.cep.studio.wizard.as.internal.beans.databinding.validators.ResourceNameValidator;
import com.tibco.cep.studio.wizard.as.internal.utils.Messages;
import com.tibco.cep.studio.wizard.as.presentation.controllers.IASDestinationWizardPageController;

public class ASDestinationWizardPageComponent extends Composite {

	// Controller
	private IASDestinationWizardPageController           controller;

	// UI
	private Composite                                    container;
	private List<Object[]>                               uiUnits;
	private Map<String, Object[]>                        uiUnitMappings;
	private Map<Control, Map<String, ControlDecoration>> uiDecorations;

	// Bindings
	private DataBindingContext                           bc;
	private EList<EAttribute>                            allDestinationAttributes;
	private EList<EAttribute>                            allSimplePropertyAttributes;
	private List<Object[]>                               uiSchemas;
	private Combo                                        comboConsumptionMode;
	private Combo                                        comboBrowserType;
	private Button                                       checkPutEvent;
	private Button                                       checkTakeEvent;
	private Button                                       checkExpireEvent;
	private Map<String, Binding>                         uiGenericUnitBindings;
	private Map<String, Binding>                         uiSpecialUnitBindings;

	// Handler
	private Handler                                      handler;


	public ASDestinationWizardPageComponent(Composite parent, IASDestinationWizardPageController controller) {
		super(parent, NONE);
		this.controller = controller;
		initialize();
	}

	private void initialize() {
		initUI();
		attachBindings();
		initListeners();
		initData();
	}

	private void initUI() {
		FillLayout layout = new FillLayout();
		this.setLayout(layout);

		container = new Composite(this, NONE);
		createComponentsWithGridLayout();

		uiDecorations = new HashMap<Control, Map<String,ControlDecoration>>();
	}

	private void createComponentsWithGridLayout() {
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.horizontalSpacing = 8;
		layout.marginWidth = 10;
		layout.marginHeight = 10;
		container.setLayout(layout);

		this.uiUnits = new ArrayList<Object[]>();
		this.uiUnitMappings = new HashMap<String, Object[]>();
		uiSchemas = getDestinationUISchemas();
		for (Object[] uiSchema : uiSchemas) {
			Object[] uiUnit = createUIUnit(uiSchema);
			uiUnits.add(uiUnit);
			uiUnitMappings.put(getDestinationUIUnitPropertyKey(uiSchema), uiUnit);
		}
		Object[] uiUnitConsumptionMode = uiUnitMappings.get(K_AS_DEST_PROP_CONSUMPTION_MODE);
		// browser type combo
		Object[] uiUnitBrowserType = uiUnitMappings.get(K_AS_DEST_PROP_BROWSER_TYPE);
		// three event type checkbox
		Object[] uiUnitPutEvent = uiUnitMappings.get(K_V_AS_DEST_PROP_PUT_EVENT);
		Object[] uiUnitTakeEvent = uiUnitMappings.get(K_V_AS_DEST_PROP_TAKE_EVENT);
		Object[] uiUnitExpireEvent = uiUnitMappings.get(K_V_AS_DEST_PROP_EXPIRE_EVENT);

		comboConsumptionMode = (Combo) getDestinationUIUnitValueControl(uiUnitConsumptionMode);
		// browser type combo
		comboBrowserType = (Combo) getDestinationUIUnitValueControl(uiUnitBrowserType);
		// three event type checkbox
		checkPutEvent = (Button) getDestinationUIUnitValueControl(uiUnitPutEvent);
		checkTakeEvent = (Button) getDestinationUIUnitValueControl(uiUnitTakeEvent);
		checkExpireEvent = (Button) getDestinationUIUnitValueControl(uiUnitExpireEvent);
	}

	private Object[] createUIUnit(Object[] uiSchema) {
		Label label = new Label(container, NONE);
		GridData layoutData = new GridData(HORIZONTAL_ALIGN_END);
		layoutData.horizontalIndent = 20;
		label.setLayoutData(layoutData);
		Control valueControl = null;
		Class<?> uiClazz = (Class<?>) getDestinationUIUnitUIType(uiSchema);
		if (String.class.equals(uiClazz)) {
			valueControl = new Text(container, SINGLE | BORDER);
		}
		else if (Boolean.class.equals(uiClazz)) {
			valueControl = new Button(container, CHECK);
		} else if (List.class.equals(uiClazz)) {
			valueControl = new Combo(container, READ_ONLY);
		}
		layoutData = new GridData(FILL, FILL, true, false);
		valueControl.setLayoutData(layoutData);

		return new Object[] { label, valueControl };
	}

	private void attachBindings() {
		bc = new DataBindingContext();

		allDestinationAttributes = ChannelFactory.eINSTANCE.getChannelPackage().getDestination().getEAllAttributes();
		allSimplePropertyAttributes = ModelFactory.eINSTANCE.getModelPackage().getSimpleProperty().getEAllAttributes();
	}

	private void initListeners() {
		handler = new Handler();

		controller.getModel().addPropertyChangeListener(_PROP_NAME_DESTINATION, handler);
	}

	private void initData() {
		initLabels();
		initCombos();
	}

	private void initLabels() {
		for (Object[] uiSchema : uiSchemas) {
			String propKey = getDestinationUIUnitPropertyKey(uiSchema);
			String propLabelName = getDestinationUIUnitPropertyLabelName(uiSchema); //$NON-NLS-1$

			Object[] uiUnit = uiUnitMappings.get(propKey);
			Label label = getDestinationUIUnitLabel(uiUnit);
			label.setText(propLabelName);
		}
	}

	private void initCombos() {
		for (Object[] uiSchema : uiSchemas) {
			Class<?> uiClazz = (Class<?>) getDestinationUIUnitUIType(uiSchema);
			if (List.class.equals(uiClazz)) {
				String propKey = getDestinationUIUnitPropertyKey(uiSchema);
				String[] items = (String[]) getDestinationUIUnitUIInitialValue(uiSchema);
				Object[] uiUnit = uiUnitMappings.get(propKey);
				Combo combo = (Combo) getDestinationUIUnitValueControl(uiUnit);
				combo.setItems(items);
			}
		}
    }


	private void refreshNonDataBindingUIUnits(Destination newDestination) {
		for (Object[] uiSchema : uiSchemas) {
			String propKey = getDestinationUIUnitPropertyKey(uiSchema);
			boolean enabled = isDestinationUIUnitEnabeld(uiSchema);
			boolean needDataBinding = isNeedDataBinding(uiSchema);

			Object[] uiUnit = uiUnitMappings.get(propKey);
			Control valueControl = getDestinationUIUnitValueControl(uiUnit);
			if (!needDataBinding) {
				String propName = getDestinationUIUnitPropertyName(uiSchema);
				boolean isAdditionalProp = isDestinationUIUnitAdditionalProperty(uiSchema);
				String value = null;
				if (!isAdditionalProp) {
					Method propGetter = _BEAN_PROP_GETTERS_DESTINATION.get(propName);
					try {
						value = (String) propGetter.invoke(newDestination, new Object[0]);
					}
					catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				else {
					value = getDestinationAdditionalPropertyValue(newDestination, propName);
				}

				if (valueControl instanceof Text) {
					((Text) valueControl).setText(value);
				}
				else if (valueControl instanceof Button) {
					((Button) valueControl).setSelection(Boolean.valueOf(value));
				}
			}

			valueControl.setEnabled(enabled);
		}
	}

	private void reattachGenericUIUnitBindings(Destination destination) {
		unattachGenericUIUnitBindings();

		for (Object[] uiSchema : uiSchemas) {

			boolean needDataBinding = isNeedDataBinding(uiSchema);

			if (needDataBinding) {
				String propKey = getDestinationUIUnitPropertyKey(uiSchema);
				String propName = getDestinationUIUnitPropertyName(uiSchema);
				String displayName = getDestinationUIUnitPropertyDisplayName(uiSchema);
				boolean isAdditionalProp = isDestinationUIUnitAdditionalProperty(uiSchema);

				Object[] uiUnit = uiUnitMappings.get(propKey);

				// Model ObservableValue
				IObservableValue modelObValue = null;
				if (!isAdditionalProp) {
					EAttribute attributeToBeBound = getEAttribute(allDestinationAttributes, propName);
					modelObValue = EMFObservables.observeValue(destination, attributeToBeBound);
				}
				else {
					EAttribute attributeToBeBound = getEAttribute(allSimplePropertyAttributes, "value"); //$NON-NLS-1$
					SimpleProperty simpleProperty = getDestinationAdditionalProperty(destination, propName);
					modelObValue = EMFObservables.observeValue(simpleProperty, attributeToBeBound);
				}

				// UI Target ObservableValue
				IObservableValue controlObValue = null;
				Control valueControl = getDestinationUIUnitValueControl(uiUnit);
//				valueControl.setEnabled(enabled);

				if (valueControl instanceof Text) {
					controlObValue = SWTObservables.observeText(valueControl, Modify);
				}
				else if (valueControl instanceof Button) {
					controlObValue = SWTObservables.observeSelection(valueControl);
				} else if (valueControl instanceof Combo) {
					controlObValue = SWTObservables.observeSelection(valueControl);
				}

				// Binding
				UpdateValueStrategy target2ModelUpdateStrategy = new UpdateValueStrategy();
				UpdateValueStrategy model2TargetUpdateStrategy = new UpdateValueStrategy();
				if (isDestinationUIUnitNeedNonNullControlDecorator(uiSchema)) {
					ControlDecoration strNotEmptyDecoration = getDecorator(valueControl, Messages.getString("ASDestinationWizardPageComponent.non_null_validation", displayName)); //$NON-NLS-1$
					ControlDecoration resNameDecoration = getDecorator(valueControl, Messages.getString("ASDestinationWizardPageComponent.res_name_validation", displayName)); //$NON-NLS-1$
					IValidator strNotEmptyValidator = new StringNotEmptyValidator(Messages.getString("ASDestinationWizardPageComponent.non_null_validation", displayName), strNotEmptyDecoration);
					IValidator resNameValidator = new ResourceNameValidator("ASDestinationWizardPageComponent.res_name_validation", resNameDecoration);
					IValidator combinedValidator = new ValidatorGroup(Arrays.asList(strNotEmptyValidator, resNameValidator));
					target2ModelUpdateStrategy.setAfterConvertValidator(combinedValidator); //$NON-NLS-1$
				}
				if (valueControl instanceof Button) {
					target2ModelUpdateStrategy.setConverter(new BooleanToStringConverter());
					model2TargetUpdateStrategy.setConverter(new StringToBooleanConverter());
				} else if (valueControl instanceof Combo) {
					int m2uConversionMethod = getDestinationUIUnitModelToUIConversionMethod(uiSchema);
					if (1 == m2uConversionMethod) {
						target2ModelUpdateStrategy.setConverter(new ToUpperCaseConverter());
						model2TargetUpdateStrategy.setConverter(new CapitalizeConverter());
					}
				}
				Binding binding = bc.bindValue(controlObValue, modelObValue, target2ModelUpdateStrategy, model2TargetUpdateStrategy);
				uiGenericUnitBindings.put(propKey, binding);
			}
		}
	}

	private void unattachGenericUIUnitBindings() {
		if (null != uiGenericUnitBindings) {
			Set<Entry<String, Binding>> entries = uiGenericUnitBindings.entrySet();
			for (Entry<String, Binding> entry : entries) {
				Binding binding = entry.getValue();
				binding.dispose();
			}
			uiGenericUnitBindings.clear();
		} else {
			uiGenericUnitBindings = new HashMap<String, Binding>();
		}
	}


	public void reattachSpecialUIUnitBindings(Destination newDestination) {
		unattachSpecialUIUnitBindings();

		// Rule: Enable three-types event controls and disable BrowserType combo if ConsumptionMode is EventListener, vice versa.
		// step 1, find out related controls
		IObservableValue consumptionModeObValue = SWTObservables.observeSelection(comboConsumptionMode);
		IObservableValue browserTypeObValue = SWTObservables.observeEnabled(comboBrowserType);
		IObservableValue putEventObValue = SWTObservables.observeEnabled(checkPutEvent);
		IObservableValue takeEventObValue = SWTObservables.observeEnabled(checkTakeEvent);
		IObservableValue expireEventObValue = SWTObservables.observeEnabled(checkExpireEvent);

		// bind consumption mode and browser type
		UpdateValueStrategy consumptionModeToBrowserTypeUpdateStrategy = new UpdateValueStrategy();
		consumptionModeToBrowserTypeUpdateStrategy.setConverter(new ConsumptionModeBrowserTypeToBooleanConverter(false));
		uiSpecialUnitBindings.put(K_AS_DEST_PROP_BROWSER_TYPE, bc.bindValue(browserTypeObValue, consumptionModeObValue, new UpdateValueStrategy(POLICY_CONVERT), consumptionModeToBrowserTypeUpdateStrategy));

		// bind consumption mode and event types
		UpdateValueStrategy consumptionModeToEventTypeUpdateStrategy = new UpdateValueStrategy();
		consumptionModeToEventTypeUpdateStrategy.setConverter(new ConsumptionModeBrowserTypeToBooleanConverter(true));
		uiSpecialUnitBindings.put(K_V_AS_DEST_PROP_PUT_EVENT, bc.bindValue(putEventObValue, consumptionModeObValue, new UpdateValueStrategy(POLICY_CONVERT), consumptionModeToEventTypeUpdateStrategy));
		uiSpecialUnitBindings.put(K_V_AS_DEST_PROP_TAKE_EVENT, bc.bindValue(takeEventObValue, consumptionModeObValue, new UpdateValueStrategy(POLICY_CONVERT), consumptionModeToEventTypeUpdateStrategy));
		uiSpecialUnitBindings.put(K_V_AS_DEST_PROP_EXPIRE_EVENT, bc.bindValue(expireEventObValue, consumptionModeObValue, new UpdateValueStrategy(POLICY_CONVERT), consumptionModeToEventTypeUpdateStrategy));
    }

	private void unattachSpecialUIUnitBindings() {
		if (null != uiSpecialUnitBindings) {
			Set<Entry<String, Binding>> entries = uiSpecialUnitBindings.entrySet();
			for (Entry<String, Binding> entry : entries) {
				Binding binding = entry.getValue();
				binding.dispose();
			}
			uiSpecialUnitBindings.clear();
		} else {
			uiSpecialUnitBindings = new HashMap<String, Binding>();
		}
	}

	/**
	 * 
	 * @param control
	 * @param id use message as ID of decoration.
	 * @return
	 */
	private ControlDecoration getDecorator(Control control, String id) {
		Map<String, ControlDecoration> controlDecorations = uiDecorations.get(control);
		if (null == controlDecorations) {
			controlDecorations = new HashMap<String, ControlDecoration>();
			uiDecorations.put(control, controlDecorations);
		}
		ControlDecoration controlDecoration = controlDecorations.get(id);
		if (null == controlDecoration) {
			controlDecoration = createDecorator(control, id);
			controlDecorations.put(id, controlDecoration);
		}
		return controlDecoration;
	}


	private class Handler implements PropertyChangeListener {

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			Destination newDestination = (Destination) evt.getNewValue();
			if (null != newDestination) {
				refreshNonDataBindingUIUnits(newDestination);
				reattachGenericUIUnitBindings(newDestination);
				reattachSpecialUIUnitBindings(newDestination);
			}
		}

	}


	private class ConsumptionModeBrowserTypeToBooleanConverter extends Converter {

		private boolean evalNot;

		private ConsumptionModeBrowserTypeToBooleanConverter(boolean evalNot) {
	        super(String.class, Boolean.class);
	        this.evalNot = evalNot;
        }

		@Override
        public Object convert(Object fromObject) {
			boolean result = true;
			String consumptionMode = (String) fromObject;
			if (Messages.getString("ASUtils.consum_mode_entry_browser").equals(consumptionMode)) {
				result &= !evalNot ? true : false;
			} else {
				result &= evalNot ? true : false;
			}
	        return result;
        }

	}

}
