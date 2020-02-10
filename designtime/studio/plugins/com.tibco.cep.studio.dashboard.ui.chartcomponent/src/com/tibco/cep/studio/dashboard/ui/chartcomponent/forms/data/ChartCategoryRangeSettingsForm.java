package com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.data;

import java.util.Arrays;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.beviewsconfig.ThresholdUnitEnum;
import com.tibco.cep.studio.dashboard.core.enums.InternalStatusEnum;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalActionRule;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalDataSource;
import com.tibco.cep.studio.dashboard.core.listeners.IMessageProvider;
import com.tibco.cep.studio.dashboard.core.listeners.ISynElementChangeListener;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynStringType;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.model.impl.attribute.LocalAttribute;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.model.LocalUnifiedComponent;
import com.tibco.cep.studio.dashboard.ui.forms.AbstractSelectionListener;
import com.tibco.cep.studio.dashboard.ui.forms.BaseForm;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class ChartCategoryRangeSettingsForm extends BaseForm {

	private static final String CATEGORY_AXIS_FIELD = "CategoryAxisField";
	private static final String CATEGORY_COLUMN_FIELD = "CategoryColumnField";

	private Button btn_Unconstrained;
	private SelectionListener btn_UnconstrainedSelectionListener;

	private Button btn_First;
	private SelectionListener btn_FirstSelectionListener;

	private Spinner spinner_First;
	private SelectionListener spinner_FirstSelectionListener;

	private Combo cmb_FirstUnit;
	private ComboViewer cmbViewer_FirstUnit;
	private ISelectionChangedListener cmbViewer_FirstUnitSelectionChangedListener;

	private Button btn_Last;
	private SelectionListener btn_LastSelectionListener;

	private Spinner spinner_Last;
	private SelectionListener spinner_LastSelectionListener;

	private Combo cmb_LastUnit;
	private ComboViewer cmbViewer_LastUnit;
	private ISelectionChangedListener cmbViewer_LastUnitSelectionChangedListener;

	private LocalActionRule localActionRule;
	private LocalDataSource localDataSource;

	private ISynElementChangeListener categoryFieldChangeListener;

	public ChartCategoryRangeSettingsForm(FormToolkit formToolKit, Composite parent) {
		super("Category Range Settings", formToolKit, parent, true);
		categoryFieldChangeListener = new CategoryFieldChangeListener();
	}

	@Override
	public void init() {
		formComposite.setLayout(new GridLayout(3, false));

		// unconstrained
		btn_Unconstrained = createButton(formComposite, "Unconstrained", SWT.RADIO);
		btn_Unconstrained.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));

		// first
		btn_First = createButton(formComposite, "First", SWT.RADIO);
		spinner_First = createSpinner(formComposite, SWT.BORDER);
		spinner_First.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		cmb_FirstUnit = createCombo(formComposite, SWT.DROP_DOWN | SWT.READ_ONLY);
		cmb_FirstUnit.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		cmbViewer_FirstUnit = new ComboViewer(cmb_FirstUnit);
		cmbViewer_FirstUnit.setContentProvider(new ArrayContentProvider());
		cmbViewer_FirstUnit.setLabelProvider(new LabelProvider(){

			@Override
			public String getText(Object element) {
				if (element instanceof ThresholdUnitEnum){
					ThresholdUnitEnum elementAsEnum = (ThresholdUnitEnum) element;
					if (elementAsEnum.compareTo(ThresholdUnitEnum.COUNT) == 0){
						return "count";
					}
					return elementAsEnum.getLiteral()+"(s) ago";
				}
				return super.getText(element);
			}
		});

		// last
		btn_Last = createButton(formComposite, "Last", SWT.RADIO);
		spinner_Last = createSpinner(formComposite, SWT.BORDER);
		spinner_Last.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		cmb_LastUnit = createCombo(formComposite, SWT.DROP_DOWN | SWT.READ_ONLY);
		cmb_LastUnit.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		cmbViewer_LastUnit = new ComboViewer(cmb_LastUnit);
		cmbViewer_LastUnit.setContentProvider(new ArrayContentProvider());
		cmbViewer_LastUnit.setLabelProvider(new LabelProvider(){

			@Override
			public String getText(Object element) {
				if (element instanceof ThresholdUnitEnum){
					ThresholdUnitEnum elementAsEnum = (ThresholdUnitEnum) element;
					if (elementAsEnum.compareTo(ThresholdUnitEnum.COUNT) == 0){
						return "count";
					}
					return elementAsEnum.getLiteral()+"(s) ago";
				}
				return super.getText(element);
			}
		});
	}

	@Override
	protected void inputChanged(LocalElement oldLocalElement, LocalElement newLocalElement) throws Exception {
		localActionRule = null;
		localDataSource = null;
		if (newLocalElement != null) {
			localActionRule = (LocalActionRule) newLocalElement.getElement(BEViewsElementNames.ACTION_RULE);
			SynStringType typeDefinition = (SynStringType) localActionRule.getProperty("ThresholdUnit").getTypeDefinition();
			typeDefinition.getEnumerations();
			localDataSource = (LocalDataSource) localActionRule.getElement(BEViewsElementNames.DATA_SOURCE);
		}
	}

	@Override
	protected void doEnableListeners() {
		if (btn_UnconstrainedSelectionListener == null) {
			btn_UnconstrainedSelectionListener = new AbstractSelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					unConstraintedButtonClicked();

				}

			};
		}
		btn_Unconstrained.addSelectionListener(btn_UnconstrainedSelectionListener);

		if (btn_FirstSelectionListener == null){
			btn_FirstSelectionListener = new AbstractSelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					firstButtonClicked();
				}

			};
		}
		btn_First.addSelectionListener(btn_FirstSelectionListener);

		if (spinner_FirstSelectionListener == null){
			spinner_FirstSelectionListener = new AbstractSelectionListener(){

				@Override
				public void widgetSelected(SelectionEvent e) {
					firstSpinnerClicked();
				}

			};
		}
		spinner_First.addSelectionListener(spinner_FirstSelectionListener);

		if (cmbViewer_FirstUnitSelectionChangedListener == null){
			cmbViewer_FirstUnitSelectionChangedListener = new ISelectionChangedListener() {

				@Override
				public void selectionChanged(SelectionChangedEvent event) {
					firstUnitComboSelectionChanged();
				}

			};
		}
		cmbViewer_FirstUnit.addSelectionChangedListener(cmbViewer_FirstUnitSelectionChangedListener);

		if (btn_LastSelectionListener == null){
			btn_LastSelectionListener = new AbstractSelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					lastButtonClicked();
				}

			};
		}
		btn_Last.addSelectionListener(btn_LastSelectionListener);

		if (spinner_LastSelectionListener == null){
			spinner_LastSelectionListener = new AbstractSelectionListener(){

				@Override
				public void widgetSelected(SelectionEvent e) {
					lastSpinnerClicked();
				}

			};
		}
		spinner_Last.addSelectionListener(spinner_LastSelectionListener);

		if (cmbViewer_LastUnitSelectionChangedListener == null){
			cmbViewer_LastUnitSelectionChangedListener = new ISelectionChangedListener() {

				@Override
				public void selectionChanged(SelectionChangedEvent event) {
					lastUnitComboSelectionChanged();
				}

			};
		}
		cmbViewer_LastUnit.addSelectionChangedListener(cmbViewer_LastUnitSelectionChangedListener);

		if (localElement != null) {
			localElement.subscribeToAll(categoryFieldChangeListener);
			localElement.subscribeForPropertyChange(categoryFieldChangeListener, CATEGORY_COLUMN_FIELD);
			localElement.subscribeForPropertyChange(categoryFieldChangeListener, CATEGORY_AXIS_FIELD);
		}

	}

	@Override
	protected void doDisableListeners() {
		btn_Unconstrained.removeSelectionListener(btn_UnconstrainedSelectionListener);

		btn_First.removeSelectionListener(btn_FirstSelectionListener);
		spinner_First.removeSelectionListener(spinner_FirstSelectionListener);
		cmbViewer_FirstUnit.removeSelectionChangedListener(cmbViewer_FirstUnitSelectionChangedListener);

		btn_Last.removeSelectionListener(btn_LastSelectionListener);
		spinner_Last.removeSelectionListener(spinner_LastSelectionListener);
		cmbViewer_LastUnit.removeSelectionChangedListener(cmbViewer_LastUnitSelectionChangedListener);

		localElement.unsubscribeForPropertyChange(categoryFieldChangeListener, CATEGORY_COLUMN_FIELD);
		localElement.unsubscribeForPropertyChange(categoryFieldChangeListener, CATEGORY_AXIS_FIELD);
		localElement.unsubscribeAll(categoryFieldChangeListener);
	}

	@Override
	public void refreshEnumerations() {
		spinner_First.setMinimum(1);
		spinner_First.setMaximum(Integer.MAX_VALUE);
		spinner_Last.setMinimum(1);
		spinner_Last.setMaximum(Integer.MAX_VALUE);
		cmbViewer_FirstUnit.setInput(Arrays.asList(ThresholdUnitEnum.COUNT));
		cmbViewer_LastUnit.setInput(Arrays.asList(ThresholdUnitEnum.COUNT));
		if (localElement != null && localDataSource != null) {
			String propertyName = CATEGORY_AXIS_FIELD;
			try {
				//get datatype of the category field
				String fieldName = localElement.getPropertyValue(propertyName);
				//seriesconfig's parent is visualization whose parent is
				if (localElement.getParent().getParent().getPropertyValue(LocalUnifiedComponent.PROP_KEY_TYPE).equals("Table") == true) {
					propertyName = CATEGORY_COLUMN_FIELD;
					fieldName = localElement.getPropertyValue(propertyName);
				}
				//find the field and its data types
				LocalElement field = localDataSource.getFieldByName(LocalDataSource.ENUM_GROUP_BY_FIELD, fieldName);
				if (field != null) {
					PROPERTY_TYPES dataType = PROPERTY_TYPES.get(field.getPropertyValue(LocalAttribute.PROP_KEY_DATA_TYPE));
					if (dataType.compareTo(PROPERTY_TYPES.DATE_TIME) == 0) {
						cmbViewer_FirstUnit.setInput(ThresholdUnitEnum.VALUES);
						cmbViewer_LastUnit.setInput(ThresholdUnitEnum.VALUES);
					}
				}
			} catch (Exception e) {
				logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not read '" + propertyName + "'", e));
			}
		}
	}

	@Override
	public void refreshSelections() {
		//disable all
		disableAll();
		btn_Unconstrained.setSelection(false);
		btn_First.setSelection(false);
		//disable(spinner_First, cmbViewer_FirstUnit);
		btn_Last.setSelection(false);
		//disable(spinner_Last, cmbViewer_LastUnit);
		if (localElement != null && localDataSource != null) {
			String propertyName = CATEGORY_AXIS_FIELD;
			try {
				//get category field
				String fieldName = localElement.getPropertyValue(propertyName);
				//seriesconfig's parent is visualization whose parent is component
				if (localElement.getParent().getParent().getPropertyValue(LocalUnifiedComponent.PROP_KEY_TYPE).equals("Table") == true) {
					propertyName = CATEGORY_COLUMN_FIELD;
					fieldName = localElement.getPropertyValue(propertyName);
				}
				//find the field and its data types
				LocalElement field = localDataSource.getFieldByName(LocalDataSource.ENUM_GROUP_BY_FIELD, fieldName);
				if (field != null) {
					propertyName = "Threshold";
					try {
						int threshold = Integer.parseInt(localActionRule.getPropertyValue(propertyName));
						btn_Unconstrained.setEnabled(true);
						btn_First.setEnabled(true);
						btn_Last.setEnabled(true);
						if (threshold > 0) {
							btn_First.setSelection(true);
							enable(spinner_First, cmbViewer_FirstUnit);
						} else if (threshold < 0) {
							btn_Last.setSelection(true);
							enable(spinner_Last, cmbViewer_LastUnit);
						} else {
							btn_Unconstrained.setSelection(true);
						}
					} catch (NumberFormatException e) {
						logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not parse '" + propertyName + "'", e));
					} catch (Exception e) {
						logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not read '" + propertyName + "'", e));
					}
				}
			} catch (Exception e) {
				logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not read '" + propertyName + "'", e));
			}
		}
	}

	private void unConstraintedButtonClicked() {
		disable(spinner_First, cmbViewer_FirstUnit);
		disable(spinner_Last, cmbViewer_LastUnit);
		String propertyName = "Threshold";
		try {
			// update the threshold count in the local action rule to 0
			localActionRule.setPropertyValue(propertyName, "0");
			// update the threshold unit to count
			propertyName = "ThresholdUnit";
			localActionRule.setPropertyValue(propertyName, ThresholdUnitEnum.COUNT.getLiteral());
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not update '" + propertyName + "'", e));
		}
	}

	private void firstButtonClicked(){
		enable(spinner_First, cmbViewer_FirstUnit);
		disable(spinner_Last, cmbViewer_LastUnit);
		firstSpinnerClicked();
		firstUnitComboSelectionChanged();
	}

	private void firstSpinnerClicked(){
		// update the threshold count in the local action rule to spinner_First's selection
		try {
			localActionRule.setPropertyValue("Threshold", String.valueOf(spinner_First.getSelection()));
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not update 'Threshold'", e));
		}
	}

	private void firstUnitComboSelectionChanged(){
		StructuredSelection selection = (StructuredSelection)cmbViewer_FirstUnit.getSelection();
		if (selection.isEmpty() == false) {
			try {
				localActionRule.setPropertyValue("ThresholdUnit", ((ThresholdUnitEnum)selection.getFirstElement()).getLiteral());
			} catch (Exception e) {
				logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not update 'ThresholdUnit'", e));
			}
		}
	}

	private void lastButtonClicked(){
		disable(spinner_First, cmbViewer_FirstUnit);
		enable(spinner_Last, cmbViewer_LastUnit);
		lastSpinnerClicked();
		lastUnitComboSelectionChanged();
	}

	private void lastSpinnerClicked(){
		// update the threshold count in the local action rule to spinner_First's selection
		try {
			localActionRule.setPropertyValue("Threshold", String.valueOf(-spinner_Last.getSelection()));
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not update 'Threshold'", e));
		}
	}

	private void lastUnitComboSelectionChanged(){
		StructuredSelection selection = (StructuredSelection)cmbViewer_LastUnit.getSelection();
		if (selection.isEmpty() == false) {
			try {
				localActionRule.setPropertyValue("ThresholdUnit", ((ThresholdUnitEnum) selection.getFirstElement()).getLiteral());
			} catch (Exception e) {
				logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not update 'ThresholdUnit'", e));
			}
		}
	}

	private void enable(Spinner spinner, ComboViewer comboViewer){
		String propertyName = "Threshold";
		try {
			spinner.setEnabled(true);
			int threshold = Integer.parseInt(localActionRule.getPropertyValue(propertyName));
			spinner.setSelection(threshold == 0 ? 1 : Math.abs(threshold));
			comboViewer.getCombo().setEnabled(true);
			propertyName = "ThresholdUnit";
			comboViewer.setSelection(new StructuredSelection(ThresholdUnitEnum.get(localActionRule.getPropertyValue(propertyName))));
		} catch (NumberFormatException e) {
			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not parse '" + propertyName + "'", e));
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not read '" + propertyName + "'", e));
		}
	}

	private void disable(Spinner spinner, ComboViewer comboViewer){
		spinner.setEnabled(false);
		spinner.setSelection(1);
		comboViewer.getCombo().setEnabled(false);
		comboViewer.setSelection(StructuredSelection.EMPTY);
	}


	class CategoryFieldChangeListener implements ISynElementChangeListener {

		@Override
		public void elementAdded(IMessageProvider parent, IMessageProvider newElement) {
		}

		@Override
		public void elementChanged(IMessageProvider parent, IMessageProvider changedElement) {
		}

		@Override
		public void elementRemoved(IMessageProvider parent, IMessageProvider removedElement) {
		}

		@Override
		public void elementStatusChanged(IMessageProvider parent, InternalStatusEnum status) {
		}

		@Override
		public String getName() {
			return title + " Category Field Change Listener";
		}

		@Override
		public void propertyChanged(IMessageProvider provider, SynProperty property, Object oldValue, Object newValue) {
			if (CATEGORY_AXIS_FIELD.equals(property.getName()) == true || CATEGORY_COLUMN_FIELD.equals(property.getName()) == true){
				try {
					if (oldValue == null) {
						oldValue = "";
					}
					LocalElement oldCategoryField = localDataSource.getFieldByName(LocalDataSource.ENUM_GROUP_BY_FIELD, oldValue.toString());
					String oldDataType = oldCategoryField == null ? null : oldCategoryField.getPropertyValue(LocalAttribute.PROP_KEY_DATA_TYPE);
					if (newValue == null) {
						newValue = "";
					}
					LocalElement newCategoryField = localDataSource.getFieldByName(LocalDataSource.ENUM_GROUP_BY_FIELD, newValue.toString());
					String newDataType = newCategoryField.getPropertyValue(LocalAttribute.PROP_KEY_DATA_TYPE);
					if (newDataType.equals(oldDataType) == false){
						//we have a change in the datatypes
						disableListeners();
						refreshEnumerations();
						refreshSelections();
						enableListeners();
					}
				} catch (Exception e) {
					logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not process '" + property.getName() + "' changes", e));
				}
			}
		}
	}
}