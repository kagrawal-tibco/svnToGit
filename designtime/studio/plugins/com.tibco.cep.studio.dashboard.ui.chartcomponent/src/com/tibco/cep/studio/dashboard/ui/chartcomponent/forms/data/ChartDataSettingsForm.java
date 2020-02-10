package com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.data;

import java.util.Arrays;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.studio.dashboard.core.insight.model.LocalConfig;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.model.LocalUnifiedComponent;
import com.tibco.cep.studio.dashboard.ui.forms.AbstractSelectionListener;
import com.tibco.cep.studio.dashboard.ui.forms.DataSettingsForm;

public class ChartDataSettingsForm extends DataSettingsForm {

	public static final String PROP_KEY_SERIES_NAME = "SeriesName";

	private Text txt_SeriesName;
	private ModifyListener txt_SeriesNameModifyListener;

	private Combo cmb_SeriesType;
	private SelectionListener cmb_SeriesTypeSelectionListener;

	protected boolean isTable;

	protected SeriesTypeProcessor seriesTypeProcessor;

	public ChartDataSettingsForm(FormToolkit formToolKit, Composite parent, SeriesTypeProcessor seriesTypeProcessor) {
		super(formToolKit, parent, true);
		this.seriesTypeProcessor = seriesTypeProcessor;
	}

	@Override
	protected void createControl() {
		// series name label
		createLabel(formComposite, "Series Name:", SWT.NONE);
		// series name text
		txt_SeriesName = createText(formComposite, null, SWT.SINGLE);
		txt_SeriesName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		// series type label
		createLabel(formComposite, "Series Type:", SWT.NONE);
		// series type combo
		cmb_SeriesType = createCombo(formComposite, SWT.DROP_DOWN | SWT.READ_ONLY);
		cmb_SeriesType.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		super.createControl();
	}

	@Override
	protected void inputChanged(LocalElement oldLocalElement, LocalElement newLocalElement) throws Exception {
		super.inputChanged(oldLocalElement, newLocalElement);
		isTable = false;
		if (newLocalElement != null) {
			isTable = localElement.getParent().getParent().getPropertyValue(LocalUnifiedComponent.PROP_KEY_TYPE).equals("Table");
		}
	}

	@Override
	protected void doEnableListeners() {
		super.doEnableListeners();

		if (txt_SeriesNameModifyListener == null) {
			txt_SeriesNameModifyListener = new ModifyListener() {

				@Override
				public void modifyText(ModifyEvent e) {
					seriesNameTextChanged();
				}

			};
		}
		txt_SeriesName.addModifyListener(txt_SeriesNameModifyListener);

		if (cmb_SeriesTypeSelectionListener == null){
			cmb_SeriesTypeSelectionListener = new AbstractSelectionListener(){

				@Override
				public void widgetSelected(SelectionEvent e) {
					seriesTypeSelectionChanged();
				}

			};
		}

		cmb_SeriesType.addSelectionListener(cmb_SeriesTypeSelectionListener);
	}

	@Override
	protected void doDisableListeners() {
		super.doDisableListeners();
		txt_SeriesName.removeModifyListener(txt_SeriesNameModifyListener);
		cmb_SeriesType.removeSelectionListener(cmb_SeriesTypeSelectionListener);
	}

	@Override
	public void refreshEnumerations() {
		super.refreshEnumerations();
		try {
			cmb_SeriesType.setItems(seriesTypeProcessor.getSeriesTypes());
		} catch (Exception e) {
			log(new Status(IStatus.ERROR,getPluginId(),"could not read series types for "+localElement,e));
			disableAll();
		}
	}

	@Override
	public void refreshSelections() {
		super.refreshSelections();
		String propertyName = LocalConfig.PROP_KEY_DISPLAY_NAME;
		try {
			if (localElement != null) {
				String name = localElement.getPropertyValue(propertyName);
				if (name == null || name.trim().length() == 0){
					name = localElement.getName();
				}
				txt_SeriesName.setText(name);
			}
			propertyName = "series type";
			cmb_SeriesType.setEnabled(seriesTypeProcessor.enableSeriesType());
			if (seriesTypeProcessor.enableSeriesType() == true){
				cmb_SeriesType.select(Arrays.asList(seriesTypeProcessor.getSeriesTypes()).indexOf(seriesTypeProcessor.getSeriesType(localElement)));
			}
		} catch (Exception e) {
			log(new Status(IStatus.ERROR,getPluginId(),"could not read "+propertyName+" from "+localElement,e));
			disableAll();
		}
	}

	private void seriesNameTextChanged() {
		try {
			String oldName = localElement.getPropertyValue("Name");
			String newName = txt_SeriesName.getText();
			localElement.setPropertyValue(LocalConfig.PROP_KEY_DISPLAY_NAME, newName);
			//PATCH not sure if chart data settings should be updating the column name for table
			if (isTable == true) {
				boolean isIndicator = !localElement.isPropertyValueSameAsDefault("IndicatorValueColumnField");
				if (isIndicator == true) {
					localElement.setPropertyValue("IndicatorValueColumnFieldHeaderName", newName);
				}
				else {
					localElement.setPropertyValue("TextValueColumnFieldHeaderName", newName);
				}
			}
			propertyChangeSupport.firePropertyChange(PROP_KEY_SERIES_NAME, oldName, newName);
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not process series name change", e));
			disableAll();
		}
	}

	private void seriesTypeSelectionChanged(){
		try {
			int selectionIndex = cmb_SeriesType.getSelectionIndex();
			if (selectionIndex != -1){
				String seriesType = cmb_SeriesType.getItem(selectionIndex);
				seriesTypeProcessor.setSeriesType(localElement, seriesType);
			}
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not process series type change", e));
			disableAll();
		}
	}



}