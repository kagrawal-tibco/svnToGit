package com.tibco.cep.studio.dashboard.ui.statemachinecomponent.forms;

import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalActionRule;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalDataSource;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalStateSeriesConfig;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.forms.AbstractSelectionListener;
import com.tibco.cep.studio.dashboard.ui.forms.BaseForm;
import com.tibco.cep.studio.dashboard.ui.forms.LocalElementLabelProvider;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class IndicatorValueFieldSettingsForm extends BaseForm {

	protected Combo cmb_ValueField;
	protected ComboViewer cmbViewer_ValueField;
	protected AbstractSelectionListener cmb_ValueFieldSelectionListener;

	protected LocalStateSeriesConfig localStateSeriesConfig;
	protected LocalActionRule localActionRule;
	protected LocalDataSource localDataSource;

	public IndicatorValueFieldSettingsForm(FormToolkit formToolKit, Composite parent, boolean showGroup) {
		super("Value Field Settings", formToolKit, parent, showGroup);
	}

	@Override
	public void init() {
		formComposite.setLayout(new GridLayout(2, false));
		// measure label
		createLabel(formComposite, "Value Field:", SWT.NONE);
		// measure drop down
		cmb_ValueField = createCombo(formComposite, SWT.DROP_DOWN | SWT.READ_ONLY);
		cmb_ValueField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		// hook up control to model
		cmbViewer_ValueField = new ComboViewer(cmb_ValueField);
		cmbViewer_ValueField.setContentProvider(new ArrayContentProvider());
		cmbViewer_ValueField.setLabelProvider(new LocalElementLabelProvider(false));
	}

	@Override
	protected void inputChanged(LocalElement oldLocalElement, LocalElement newLocalElement) throws Exception {
		localStateSeriesConfig = null;
		localActionRule = null;
		localDataSource = null;
		if (newLocalElement instanceof LocalStateSeriesConfig){
			localStateSeriesConfig = (LocalStateSeriesConfig) newLocalElement;
			localActionRule = (LocalActionRule) newLocalElement.getElement(BEViewsElementNames.ACTION_RULE);
			localDataSource = (LocalDataSource) localActionRule.getElement(BEViewsElementNames.DATA_SOURCE);
		}
		else {
			throw new IllegalArgumentException(newLocalElement+" is not a state series config");
		}
	}

	@Override
	protected void doEnableListeners() {
		if (cmb_ValueFieldSelectionListener == null) {
			cmb_ValueFieldSelectionListener = new AbstractSelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					valueFieldChanged();
				}

			};
		}
		cmb_ValueField.addSelectionListener(cmb_ValueFieldSelectionListener);
	}

	@Override
	protected void doDisableListeners() {
		cmb_ValueField.removeSelectionListener(cmb_ValueFieldSelectionListener);
	}

	@Override
	public void refreshEnumerations() {
		try {
			// set the value fields
			List<Object> valueFields = localDataSource == null ? Collections.emptyList() : localDataSource.getEnumerations(LocalDataSource.ENUM_NUMERIC_FIELD);
			cmbViewer_ValueField.setInput(valueFields);
		} catch (Exception e) {
			log(new Status(IStatus.ERROR, getPluginId(), "could not refresh enumerations in " + this.getClass().getName(), e));
			disableAll();
		}
	}

	@Override
	public void refreshSelections() {
		cmbViewer_ValueField.setSelection(StructuredSelection.EMPTY);
		if (localStateSeriesConfig != null) {
			try {
				String valueFieldName = localStateSeriesConfig.getPropertyValue(getValueFieldPropertyName());
				if (valueFieldName != null) {
					cmbViewer_ValueField.setSelection(new StructuredSelection(valueFieldName));
				}
			} catch (Exception e) {
				log(new Status(IStatus.ERROR, getPluginId(), "could not refresh selections in " + this.getClass().getName(), e));
				disableAll();
			}
		}
	}

	protected String getValueFieldPropertyName() throws Exception {
		return "IndicatorValueField";
	}

	protected void valueFieldChanged() {
		try {
			String valueFieldName = (String) ((StructuredSelection)cmbViewer_ValueField.getSelection()).getFirstElement();
			localStateSeriesConfig.setPropertyValue(getValueFieldPropertyName(),valueFieldName);
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR,getPluginId(),"could not process value field selection",e));
		}
	}
}
