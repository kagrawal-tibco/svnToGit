package com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.data;

import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalDataSource;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.model.impl.attribute.LocalAttribute;
import com.tibco.cep.studio.dashboard.core.util.DisplayValueFormatProvider;
import com.tibco.cep.studio.dashboard.ui.forms.LocalElementLabelProvider;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class ChartDataWizardSettingsForm extends ChartDataSettingsForm {

	private Combo cmb_CategoryField;
	private ComboViewer cmbViewer_CategoryField;
	private ISelectionChangedListener cmb_CategoryFieldSelectionListener;

	private Combo cmb_ValueField;
	private ComboViewer cmbViewer_ValueField;
	private ISelectionChangedListener cmb_ValueFieldSelectionListener;

	public ChartDataWizardSettingsForm(FormToolkit formToolKit, Composite parent, SeriesTypeProcessor seriesTypeProcessor) {
		super(formToolKit, parent, seriesTypeProcessor);
	}

	@Override
	protected void createControl() {
		super.createControl();
		// value field Value
		createLabel(formComposite, "Value Field:", SWT.NONE);
		// value field drop down
		cmb_ValueField = createCombo(formComposite, SWT.DROP_DOWN | SWT.READ_ONLY);
		cmb_ValueField.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		// hook up control to model
		cmbViewer_ValueField = new ComboViewer(cmb_ValueField);
		cmbViewer_ValueField.setContentProvider(new ArrayContentProvider());
		cmbViewer_ValueField.setLabelProvider(new LocalElementLabelProvider(false));

		// Category field Value
		createLabel(formComposite, "Category Field:", SWT.NONE);
		// value field drop down
		cmb_CategoryField = createCombo(formComposite, SWT.DROP_DOWN | SWT.READ_ONLY);
		cmb_CategoryField.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		// hook up control to model
		cmbViewer_CategoryField = new ComboViewer(cmb_CategoryField);
		cmbViewer_CategoryField.setContentProvider(new ArrayContentProvider());
		cmbViewer_CategoryField.setLabelProvider(new LocalElementLabelProvider(false));
	}

	@Override
	protected void inputChanged(LocalElement oldLocalElement, LocalElement newLocalElement) throws Exception {
		super.inputChanged(oldLocalElement, newLocalElement);
	}

	@Override
	protected void doEnableListeners() {
		super.doEnableListeners();

		if (cmb_ValueFieldSelectionListener == null) {
			cmb_ValueFieldSelectionListener = new ISelectionChangedListener() {

				@Override
				public void selectionChanged(SelectionChangedEvent event) {
					try {
						disableListeners();
						valueFieldSelectionChanged();
					} finally {
						enableListeners();
					}
				}

			};
		}
		cmbViewer_ValueField.addSelectionChangedListener(cmb_ValueFieldSelectionListener);

		if (cmb_CategoryFieldSelectionListener == null) {
			cmb_CategoryFieldSelectionListener = new ISelectionChangedListener() {

				@Override
				public void selectionChanged(SelectionChangedEvent event) {
					try {
						disableListeners();
						categoryFieldSelectionChanged();
					} finally {
						enableListeners();
					}
				}

			};
		}
		cmbViewer_CategoryField.addSelectionChangedListener(cmb_CategoryFieldSelectionListener);
	}

	@Override
	protected void doDisableListeners() {
		super.doDisableListeners();
		cmbViewer_ValueField.removeSelectionChangedListener(cmb_ValueFieldSelectionListener);
		cmbViewer_CategoryField.removeSelectionChangedListener(cmb_CategoryFieldSelectionListener);
	}

	@Override
	public void refreshEnumerations() {
		super.refreshEnumerations();
		try {
			LocalDataSource localDataSource = (LocalDataSource) localActionRule.getElement(BEViewsElementNames.DATA_SOURCE);
			// set the value fields
			List<Object> valueFields = localDataSource == null ? Collections.emptyList() : localDataSource.getEnumerations(LocalDataSource.ENUM_FIELD);
			cmbViewer_ValueField.setInput(valueFields);
			// set the category fields
			List<Object> categoryFields = localDataSource == null ? Collections.emptyList() : localDataSource.getEnumerations(LocalDataSource.ENUM_GROUP_BY_FIELD);
			cmbViewer_CategoryField.setInput(categoryFields);
		} catch (Exception e) {
			log(new Status(IStatus.ERROR, getPluginId(), "could not refresh enumerations in " + this.getClass().getName(), e));
		}
	}

	@Override
	public void refreshSelections() {
		super.refreshSelections();
		try {
			//value field
			String fieldName = localElement.getPropertyValue(isTable == true ? "TextValueColumnField" : "ValueAxisField");
			if (fieldName == null) {
				cmbViewer_ValueField.setSelection(StructuredSelection.EMPTY);
			} else {
				cmbViewer_ValueField.setSelection(new StructuredSelection(fieldName));
			}
			//category field
			fieldName = localElement.getPropertyValue(isTable == true ? "CategoryColumnField" : "CategoryAxisField");
			if (fieldName == null) {
				cmbViewer_CategoryField.setSelection(StructuredSelection.EMPTY);
			} else {
				cmbViewer_CategoryField.setSelection(new StructuredSelection(fieldName));
			}
		} catch (Exception e) {
			log(new Status(IStatus.ERROR, getPluginId(), "could not refresh selections in " + this.getClass().getName(), e));
		}
	}

	protected void valueFieldSelectionChanged(){
		try {
			LocalDataSource localDataSource = (LocalDataSource) localActionRule.getElement(BEViewsElementNames.DATA_SOURCE);
			IStructuredSelection selection = (IStructuredSelection) cmbViewer_ValueField.getSelection();
			String fieldName = String.valueOf(selection.getFirstElement());
			localElement.setPropertyValue((isTable == true ? "TextValueColumnField" : "ValueAxisField"), fieldName);
			//update display format
			String dataType = localDataSource.getFieldByName(LocalDataSource.ENUM_FIELD, fieldName).getPropertyValue(LocalAttribute.PROP_KEY_DATA_TYPE);
			String valueDisplayFormat = DisplayValueFormatProvider.getDefaultDisplayValueFormat(dataType).getDisplayValueFormat(fieldName, "");
			localElement.setPropertyValue((isTable == true ? "TextValueColumnFieldDisplayFormat" : "ValueAxisFieldDisplayFormat"), valueDisplayFormat);
			//update the tool tip format
			String categoryDisplayFormat = localElement.getPropertyValue(isTable == true ? "CategoryColumnFieldDisplayFormat" : "CategoryAxisFieldDisplayFormat");
			localElement.setPropertyValue((isTable == true ? "TextValueColumnFieldTooltipFormat" : "ValueAxisFieldTooltipFormat"), categoryDisplayFormat+"="+valueDisplayFormat);
		} catch (Exception e) {
			logAndAlert(title, new Status(IStatus.ERROR,getPluginId(),"could not process value field change",e));
		}
	}

	protected void categoryFieldSelectionChanged(){
		try {
			LocalDataSource localDataSource = (LocalDataSource) localActionRule.getElement(BEViewsElementNames.DATA_SOURCE);
			IStructuredSelection selection = (IStructuredSelection) cmbViewer_CategoryField.getSelection();
			String fieldName = String.valueOf(selection.getFirstElement());
			localElement.setPropertyValue((isTable == true ? "CategoryColumnField" : "CategoryAxisField"), fieldName);
			//update display format
			String dataType = localDataSource.getFieldByName(LocalDataSource.ENUM_GROUP_BY_FIELD, fieldName).getPropertyValue(LocalAttribute.PROP_KEY_DATA_TYPE);
			String categoryDisplayFormat = DisplayValueFormatProvider.getDefaultDisplayValueFormat(dataType).getDisplayValueFormat(fieldName, "");
			localElement.setPropertyValue((isTable == true ? "CategoryColumnFieldDisplayFormat" : "CategoryAxisFieldDisplayFormat"), categoryDisplayFormat);
			//update the tool tip format
			String valueDisplayFormat = localElement.getPropertyValue(isTable == true ? "TextValueColumnFieldDisplayFormat" : "ValueAxisFieldDisplayFormat");
			localElement.setPropertyValue((isTable == true ? "TextValueColumnFieldTooltipFormat" : "ValueAxisFieldTooltipFormat"), categoryDisplayFormat+"="+valueDisplayFormat);
		} catch (Exception e) {
			logAndAlert(title, new Status(IStatus.ERROR,getPluginId(),"could not process category field change",e));
		}
	}

}
