package com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.data;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.List;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.studio.dashboard.core.insight.model.LocalConfig;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalActionRule;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalDataSource;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.helpers.StateMachineComponentHelper;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalParticle;
import com.tibco.cep.studio.dashboard.core.model.impl.attribute.LocalAttribute;
import com.tibco.cep.studio.dashboard.core.util.DisplayValueFormat;
import com.tibco.cep.studio.dashboard.core.util.DisplayValueFormatProvider;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.model.LocalUnifiedComponent;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.model.LocalUnifiedSeriesConfig;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.model.LocalUnifiedVisualization;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.wizard.ChartCreator;
import com.tibco.cep.studio.dashboard.ui.forms.AbstractSelectionListener;
import com.tibco.cep.studio.dashboard.ui.forms.BaseForm;
import com.tibco.cep.studio.dashboard.ui.forms.DataSettingsForm;
import com.tibco.cep.studio.dashboard.ui.forms.DrillableFieldsSettingsForm;
import com.tibco.cep.studio.dashboard.ui.forms.LocalElementDisplayNameLabelProvider;
import com.tibco.cep.studio.dashboard.ui.forms.ScreenTipSettingsForm;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class ChartRunTimeDataForm extends BaseForm {

	private List lst_Serieses;
	private ListViewer lstViewer_Serieses;
	private ISelectionChangedListener lstViewerSeriesesSelectionChangedListener;

	private Button btn_Add;
	private SelectionListener btn_AddSelectionListener;
	private Button btn_Delete;
	private SelectionListener btn_DeleteSelectionListener;
	private Button btn_Up;
	private SelectionListener btn_UpSelectionListener;
	private Button btn_Down;
	private SelectionListener btn_DownSelectionListener;

	private ChartDataSettingsForm dataSettingsForm;
	private PropertyChangeListener seriesNameChangeListener;
	private PropertyChangeListener metricChangeListener;

	private ChartValueFieldSettingsForm valueFieldSettings;

	private ChartCategoryFieldSettingsForm categoryFieldSettings;

	private ChartCategoryRangeSettingsForm categoryRangeSettings;

	private ScreenTipSettingsForm screenTipSettingsForm;

	private DrillableFieldsSettingsForm drillableFieldsSettingsForm;

	private LocalElement currSeriesConfig;

	public ChartRunTimeDataForm(FormToolkit formToolKit, Composite parent) {
		super("Series Settings", formToolKit, parent, false);
	}

	@Override
	public void init() {
		formComposite.setLayout(new GridLayout(3, false));

		// series list
		lst_Serieses = createList(formComposite, SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		GridData lst_SeriesesLayoutData = new GridData(SWT.FILL, SWT.FILL, false, false);
		lst_SeriesesLayoutData.widthHint = 90;
		lst_Serieses.setLayoutData(lst_SeriesesLayoutData);

		// buttons
		Composite buttonsComposite = createComposite(formComposite, SWT.NONE);
		FillLayout buttonsCompositeLayout = new FillLayout(SWT.VERTICAL);
		buttonsCompositeLayout.spacing = 5;
		buttonsComposite.setLayout(buttonsCompositeLayout);
		btn_Add = createButton(buttonsComposite, "Add", SWT.PUSH);
		btn_Delete = createButton(buttonsComposite, "Delete", SWT.PUSH);
		btn_Up = createButton(buttonsComposite, "Up", SWT.PUSH);
		btn_Down = createButton(buttonsComposite, "Down", SWT.PUSH);
		GridData buttonsCompositeLayoutData = new GridData(SWT.CENTER, SWT.CENTER, false, false);
		buttonsCompositeLayoutData.widthHint = 75;
		buttonsComposite.setLayoutData(buttonsCompositeLayoutData);

		Composite seriesDetailsComposite = createComposite(formComposite, SWT.NONE);
		seriesDetailsComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		GridLayout seriesDetailsCompositeLayout = new GridLayout(2,true);
		seriesDetailsCompositeLayout.marginHeight = 0;
		seriesDetailsCompositeLayout.marginWidth = 0;
		seriesDetailsComposite.setLayout(seriesDetailsCompositeLayout);

		// data and value field settings
		Composite dataAndValueFieldSettingsComposite = createComposite(seriesDetailsComposite, SWT.NONE);
		FillLayout dataAndValueFieldSettingsCompositeLayout = new FillLayout(SWT.VERTICAL);
		dataAndValueFieldSettingsCompositeLayout.spacing = 5;
		dataAndValueFieldSettingsComposite.setLayout(dataAndValueFieldSettingsCompositeLayout);
		// data settings form
		dataSettingsForm = createDataSettingsForm(formToolKit, dataAndValueFieldSettingsComposite);
		dataSettingsForm.init();
		addForm(dataSettingsForm);
		// value field settings
		valueFieldSettings = createValueFieldSettingsForm(formToolKit, dataAndValueFieldSettingsComposite);
		valueFieldSettings.init();
		addForm(valueFieldSettings);
		dataAndValueFieldSettingsComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		// remaining settings
		Composite remainingSettingsComposite = createComposite(seriesDetailsComposite, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		remainingSettingsComposite.setLayout(layout);
		// category field settings
		categoryFieldSettings = new ChartCategoryFieldSettingsForm(formToolKit, remainingSettingsComposite);
		categoryFieldSettings.init();
		addForm(categoryFieldSettings);
		categoryFieldSettings.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		// category range settings
		categoryRangeSettings = new ChartCategoryRangeSettingsForm(formToolKit, remainingSettingsComposite);
		categoryRangeSettings.init();
		addForm(categoryRangeSettings);
		categoryRangeSettings.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		// screen tip
		screenTipSettingsForm = new ChartComponentScreenTipSettingsForm(formToolKit, remainingSettingsComposite, true);
		screenTipSettingsForm.init();
		addForm(screenTipSettingsForm);
		screenTipSettingsForm.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		// drillable fields
		drillableFieldsSettingsForm = new DrillableFieldsSettingsForm(formToolKit, remainingSettingsComposite, true);
		drillableFieldsSettingsForm.init();
		addForm(drillableFieldsSettingsForm);
		drillableFieldsSettingsForm.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		remainingSettingsComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		// initiaize list viewer
		lstViewer_Serieses = new ListViewer(lst_Serieses);
		lstViewer_Serieses.setContentProvider(new ArrayContentProvider());
		lstViewer_Serieses.setLabelProvider(new LocalElementDisplayNameLabelProvider());

	}

	protected ChartDataSettingsForm createDataSettingsForm(FormToolkit formToolkit, Composite parent) {
		return new ChartDataSettingsForm(formToolkit, parent, new DefaultSeriesTypeProcessor());
	}

	protected ChartValueFieldSettingsForm createValueFieldSettingsForm(FormToolkit formToolkit, Composite parent) {
		return new ChartValueFieldSettingsForm(formToolkit, parent);
	}

	@Override
	public void setInput(LocalElement localElement) throws Exception {
		super.setInput(localElement.getElement(LocalUnifiedVisualization.TYPE));
	}

	@Override
	protected void setChildrenFormInput(LocalElement localElement) throws Exception {
		// we want to control how the input is set on the child forms
	}

	@Override
	protected void doEnableListeners() {
		if (lstViewerSeriesesSelectionChangedListener == null) {
			lstViewerSeriesesSelectionChangedListener = new ISelectionChangedListener() {

				@Override
				public void selectionChanged(SelectionChangedEvent event) {
					seriesListSelectionChanged();
				}

			};
		}
		lstViewer_Serieses.addSelectionChangedListener(lstViewerSeriesesSelectionChangedListener);

		if (btn_AddSelectionListener == null) {
			btn_AddSelectionListener = new AbstractSelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					addButtonClicked();
				}

			};
		}
		btn_Add.addSelectionListener(btn_AddSelectionListener);

		if (btn_DeleteSelectionListener == null) {
			btn_DeleteSelectionListener = new AbstractSelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					deleteButtonClicked();
				}

			};
		}
		btn_Delete.addSelectionListener(btn_DeleteSelectionListener);

		if (btn_UpSelectionListener == null) {
			btn_UpSelectionListener = new AbstractSelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					upButtonClicked();
				}

			};
		}
		btn_Up.addSelectionListener(btn_UpSelectionListener);

		if (btn_DownSelectionListener == null) {
			btn_DownSelectionListener = new AbstractSelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					downButtonClicked();
				}

			};
		}
		btn_Down.addSelectionListener(btn_DownSelectionListener);

		if (seriesNameChangeListener == null) {
			seriesNameChangeListener = new PropertyChangeListener() {

				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					seriesNameChanged();
				}

			};
		}
		dataSettingsForm.addPropertyChangeListener(ChartDataSettingsForm.PROP_KEY_SERIES_NAME, seriesNameChangeListener);

		if (metricChangeListener == null) {
			metricChangeListener = new PropertyChangeListener() {

				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					measureChanged(String.valueOf(evt.getOldValue()), String.valueOf(evt.getNewValue()));
				}

			};
		}
		dataSettingsForm.addPropertyChangeListener(DataSettingsForm.PROP_KEY_MEASURE, metricChangeListener);
	}

	@Override
	protected void doDisableListeners() {
		lstViewer_Serieses.removeSelectionChangedListener(lstViewerSeriesesSelectionChangedListener);
		btn_Add.removeSelectionListener(btn_AddSelectionListener);
		btn_Delete.removeSelectionListener(btn_DeleteSelectionListener);
		btn_Up.removeSelectionListener(btn_UpSelectionListener);
		btn_Down.removeSelectionListener(btn_DownSelectionListener);
		dataSettingsForm.removePropertyChangeListener(ChartDataSettingsForm.PROP_KEY_SERIES_NAME, seriesNameChangeListener);
		dataSettingsForm.removePropertyChangeListener(DataSettingsForm.PROP_KEY_MEASURE, metricChangeListener);
	}

	@Override
	public void refreshEnumerations() {
		//store current selected series config
		IStructuredSelection selection = (IStructuredSelection) lstViewer_Serieses.getSelection();
		if (selection.isEmpty() == true) {
			currSeriesConfig = null;
		}
		else {
			currSeriesConfig = (LocalElement) selection.getFirstElement();
		}
		try {
			// set the input for the series list
			java.util.List<LocalElement> seriesConfigs = localElement.getChildren(LocalUnifiedSeriesConfig.TYPE);
			lstViewer_Serieses.setInput(seriesConfigs);
		} catch (Exception e) {
			log(new Status(IStatus.ERROR, getPluginId(), "could not read series configs", e));
		}
	}

	@Override
	public void refreshSelections() {
		try {
			java.util.List<LocalElement> seriesConfigs = localElement.getChildren(LocalUnifiedSeriesConfig.TYPE);
			if (seriesConfigs.isEmpty() == false) {
				if (currSeriesConfig == null || seriesConfigs.indexOf(currSeriesConfig) == -1) {
					currSeriesConfig = seriesConfigs.get(0);
				}
				lstViewer_Serieses.setSelection(new StructuredSelection(currSeriesConfig));
				seriesListSelectionChanged();
			}
		} catch (Exception e) {
			log(new Status(IStatus.ERROR, getPluginId(), "could not read series configs", e));
		} finally {
			currSeriesConfig = null;
		}
	}

	private void seriesListSelectionChanged() {
		IStructuredSelection selection = (IStructuredSelection) lstViewer_Serieses.getSelection();
		if (selection.isEmpty() == true) {
			// disable all the children forms
			for (BaseForm baseForm : getForms()) {
				try {
					baseForm.setInput(null);
				} catch (Exception ignore) {
				} finally {
					baseForm.disableAll();
				}
			}
		} else {
			try {
				LocalUnifiedSeriesConfig selectedSeriesConfig = (LocalUnifiedSeriesConfig) selection.getFirstElement();
				// enable and set input on all children forms
				for (BaseForm baseForm : getForms()) {
					baseForm.setInput(selectedSeriesConfig);
				}
			} catch (Exception e) {
				logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not process series selection change", e));
			}
		}
		updateSeriesButtonState();
	}

	private void addButtonClicked() {
		try {
			IStructuredSelection selection = (IStructuredSelection) lstViewer_Serieses.getSelection();
			LocalElement newSeriesConfig = null;
			if (selection.isEmpty() == true) {
				//PATCH need to extract the default data source finding code properly
				LocalElement localECoreFactory = localElement.getRoot();
				LocalDataSource defaultDataSource = StateMachineComponentHelper.getDefaultDataSource(localECoreFactory);
				if (defaultDataSource == null){
					if (localECoreFactory.getChildren(BEViewsElementNames.METRIC).isEmpty() == true){
						MessageDialog.openError(Display.getCurrent().getActiveShell(), title, "No metrics were found...");
					}
					else {
						MessageDialog.openError(Display.getCurrent().getActiveShell(), title, "No data sources were found...");
					}
					return;
				}
				newSeriesConfig = ChartCreator.createNewSeries(localElement, defaultDataSource);
			} else {
				LocalElement selectedSeries = (LocalElement) selection.getFirstElement();
				// clone the series
				newSeriesConfig = (LocalElement) selectedSeries.clone();
				// update the name
				String newSeriesName = localElement.getNewName(LocalUnifiedSeriesConfig.TYPE,BEViewsElementNames.SERIES_CONFIG);
				newSeriesConfig.setName(newSeriesName);
				//Modified by Anand on 02/18/2011 to fix BE-11320
				// update the display name
				newSeriesConfig.setPropertyValue(LocalConfig.PROP_KEY_DISPLAY_NAME, newSeriesName);
				// add it to the series
				localElement.addElement(LocalUnifiedSeriesConfig.TYPE, newSeriesConfig);
			}
			// reset the range list
			lstViewer_Serieses.setInput(localElement.getChildren(LocalUnifiedSeriesConfig.TYPE));
			// select it
			lstViewer_Serieses.setSelection(new StructuredSelection(newSeriesConfig),true);
			// fire series selection changed
			seriesListSelectionChanged();
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not add new series", e));
		}
	}

	private void deleteButtonClicked() {
		try {
			// retrieve existing selection and it's index
			LocalElement selectedSeries = (LocalElement) ((IStructuredSelection) lstViewer_Serieses.getSelection()).getFirstElement();
			int selectedIndex = localElement.getChildren(LocalUnifiedSeriesConfig.TYPE).indexOf(selectedSeries);
			// remove range from action rule
			localElement.removeElementByID(LocalUnifiedSeriesConfig.TYPE, selectedSeries.getID(), selectedSeries.getFolder());
			java.util.List<LocalElement> serieses = localElement.getChildren(LocalUnifiedSeriesConfig.TYPE);
			// update the list with new list
			lstViewer_Serieses.setInput(serieses);
			// update the series list selection to new series value
			// update selection to series below the deleted series
			// except when the deleted series is last, in that case select the series above the deleted series as
			if (selectedIndex == serieses.size()) {
				selectedIndex--;
			}
			if (selectedIndex >= 0) {
				lstViewer_Serieses.setSelection(new StructuredSelection(serieses.get(selectedIndex)));
			}
			// fire range selection to update the range details & visual alert settings forms
			seriesListSelectionChanged();
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not remove selected series", e));
		}
	}

	protected void upButtonClicked() {
		try {
			// get selected series
			IStructuredSelection selection = (IStructuredSelection) lstViewer_Serieses.getSelection();
			LocalElement selectedSeries = (LocalElement) selection.getFirstElement();

			LocalParticle seriesParticle = localElement.getParticle(LocalUnifiedSeriesConfig.TYPE);
			java.util.List<LocalElement> series = seriesParticle.getElements(true);

			// get the series above the selected series
			LocalElement seriesAboveSelection = series.get(series.indexOf(selectedSeries) - 1);
			// swap select series with selection above it , resulting in upward movement of selection
			seriesParticle.swapElements(selectedSeries, seriesAboveSelection);
			// reset the range list
			lstViewer_Serieses.setInput(localElement.getChildren(LocalUnifiedSeriesConfig.TYPE));
			// update the range list selection to the existing selection (since setInput wipes out selection)
			lstViewer_Serieses.setSelection(selection, true);
			// only update the series buttons , since the series detail forms are already showing the selected range
			updateSeriesButtonState();
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not move selected series up", e));
		}
	}

	protected void downButtonClicked() {
		try {
			// get selected series
			IStructuredSelection selection = (IStructuredSelection) lstViewer_Serieses.getSelection();
			LocalElement selectedSeries = (LocalElement) selection.getFirstElement();

			LocalParticle seriesParticle = localElement.getParticle(LocalUnifiedSeriesConfig.TYPE);
			java.util.List<LocalElement> ranges = seriesParticle.getElements(true);

			// get the series below the selected series
			LocalElement seriesBelowSelection = ranges.get(ranges.indexOf(selectedSeries) + 1);
			// swap select series with selection below it , resulting in downward movement of selection
			seriesParticle.swapElements(selectedSeries, seriesBelowSelection);
			// reset the range list
			lstViewer_Serieses.setInput(localElement.getChildren(LocalUnifiedSeriesConfig.TYPE));
			// update the series list selection to the existing selection (since setInput wipes out selection)
			lstViewer_Serieses.setSelection(selection);
			// only update the series buttons , since the series detail forms are already showing the selected range
			updateSeriesButtonState();
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not move selected range down", e));
		}
	}

	@SuppressWarnings("unchecked")
	private void updateSeriesButtonState() {
		// btn_Add is always enabled
		ISelection selection = lstViewer_Serieses.getSelection();
		if (selection.isEmpty() == true) {
			// we got empty selection, disable all buttons except btn_Add
			btn_Delete.setEnabled(false);
			btn_Up.setEnabled(false);
			btn_Down.setEnabled(false);
			return;
		}
		java.util.List<LocalElement> serieses = (java.util.List<LocalElement>) lstViewer_Serieses.getInput();
		if (serieses.size() == 1) {
			// we have only one series , disable up and down and delete button
			btn_Delete.setEnabled(false);
			btn_Up.setEnabled(false);
			btn_Down.setEnabled(false);
			return;
		}
		// we have a selection in a multi-series list
		// enable remove button
		btn_Delete.setEnabled(true);
		LocalElement selectedRange = (LocalElement) ((StructuredSelection) selection).getFirstElement();
		// enable up button for any range which is not at the top
		btn_Up.setEnabled(serieses.indexOf(selectedRange) != 0);
		// enable down button for any range which is not at the bottom
		btn_Down.setEnabled(serieses.indexOf(selectedRange) != serieses.size() - 1);
	}

	private void seriesNameChanged() {
		lstViewer_Serieses.refresh();
//		// remove lstViewer_Serieses listener
//		lstViewer_Serieses.removeSelectionChangedListener(lstViewerSeriesesSelectionChangedListener);
//		try {
//
//			// get selection
//			ISelection selection = lstViewer_Serieses.getSelection();
//			// reset the range list
//			lstViewer_Serieses.setInput(localElement.getChildren(LocalUnifiedSeriesConfig.TYPE));
//			// update the range list selection to the existing selection (since setInput wipes out selection)
//			lstViewer_Serieses.setSelection(selection);
//			// nothing else needs update , since the selection has not changed
//		} catch (Exception e) {
//			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not process series name changes", e));
//		} finally {
//			lstViewer_Serieses.addSelectionChangedListener(lstViewerSeriesesSelectionChangedListener);
//		}
	}

	private void measureChanged(String oldMetric, String newMetric){
		//get selected series config
		try {
			//disable all child listeners
			for (BaseForm childForm : getForms()) {
				if (childForm != dataSettingsForm ){
					childForm.disableListeners();
				}
			}
			LocalElement seriesConfig = (LocalElement) ((IStructuredSelection)lstViewer_Serieses.getSelection()).getFirstElement();
			LocalElement actionRule = seriesConfig.getElement(BEViewsElementNames.ACTION_RULE);
			LocalDataSource dataSource = (LocalDataSource) actionRule.getElement(BEViewsElementNames.DATA_SOURCE);
			java.util.List<Object> numericFields = dataSource.getEnumerations(LocalDataSource.ENUM_NUMERIC_FIELD);
			java.util.List<Object> categoryFields = dataSource.getEnumerations(LocalDataSource.ENUM_GROUP_BY_FIELD);
			String defaultCategoryField = String.valueOf(categoryFields.get(0));
			String defaultValueField = String.valueOf(numericFields.get(0));
			//find the type of the chart
			// seriesconfig's parent is visualization whose parent is
			if (seriesConfig.getParent().getParent().getPropertyValue(LocalUnifiedComponent.PROP_KEY_TYPE).equals("Table") == true) {
				//update text properties

				//update the category column field
				updateFieldAndDisplayLabel(seriesConfig, dataSource, "CategoryColumnField", "CategoryColumnFieldDisplayFormat",defaultCategoryField);

				//update the value column field
				String fieldPropertyName = "TextValueColumnField";
				String fieldDisplayLabelPropertyName = "TextValueColumnFieldDisplayFormat";
				String fieldTooltipPropertyName = "TextValueColumnFieldTooltipFormat";
				if (((SynProperty)seriesConfig.getProperty("IndicatorValueColumnField")).getDefault().equals(seriesConfig.getPropertyValue("IndicatorValueColumnField")) == false){
					fieldPropertyName = "IndicatorValueColumnField";
					fieldDisplayLabelPropertyName = "IndicatorValueColumnFieldDisplayFormat";
					fieldTooltipPropertyName = "IndicatorValueColumnFieldTooltipFormat";
				}
				updateFieldAndDisplayLabel(seriesConfig, dataSource, fieldPropertyName, fieldDisplayLabelPropertyName,defaultValueField);

				//update the tool tip format
				seriesConfig.setPropertyValue(fieldTooltipPropertyName, "{"+defaultCategoryField+"}={"+defaultValueField+"}");
			} else {
				//update Chart Properties

				//update the category axis field
				updateFieldAndDisplayLabel(seriesConfig, dataSource, "CategoryAxisField", "CategoryAxisFieldDisplayFormat",defaultCategoryField);

				//update the value axis field
				updateFieldAndDisplayLabel(seriesConfig, dataSource, "ValueAxisField", "ValueAxisFieldDisplayFormat",defaultValueField);

				//update the tool tip format
				seriesConfig.setPropertyValue("ValueAxisFieldTooltipFormat", "{"+defaultCategoryField+"}={"+defaultValueField+"}");
			}
			//update drillable fields
			actionRule.removeChildren(LocalActionRule.ELEMENT_KEY_DRILLABLE_FIELDS);

			//by default we don't add any drillable fields
			//refresh all non data settings
			for (BaseForm childForm : getForms()) {
				if (childForm != dataSettingsForm ){
					childForm.setInput(seriesConfig);
				}
			}
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not process measure changes", e));
		}
	}

	private void updateFieldAndDisplayLabel(LocalElement seriesConfig, LocalDataSource dataSource, String fieldPropertyName, String displayLabelPropertyName, String newFieldName) throws Exception {
//		String existingField = seriesConfig.getPropertyValue(fieldPropertyName);
//		String existingFieldDataType = dataSource.getFieldByName(existingField).getPropertyValue(LocalAttribute.PROP_KEY_DATA_TYPE);
		String newFieldDataType = dataSource.getFieldByName(LocalDataSource.ENUM_FIELD, newFieldName).getPropertyValue(LocalAttribute.PROP_KEY_DATA_TYPE);
		// update the field
		seriesConfig.setPropertyValue(fieldPropertyName, newFieldName);
		DisplayValueFormat displayValueFormat = DisplayValueFormatProvider.getDefaultDisplayValueFormat(newFieldDataType);
		String pattern = "";
//		if (existingFieldDataType.equals(newFieldDataType) == true) {
//			// we have matching field data types , reuse the display form
//			String existingDisplayFormat = seriesConfig.getPropertyValue(displayLabelPropertyName);
//			displayValueFormat = DisplayValueFormatProvider.parse(existingDisplayFormat);
//			pattern = displayValueFormat.getPattern(existingDisplayFormat);
//		}
		if (displayValueFormat != null) {
			seriesConfig.setPropertyValue(displayLabelPropertyName, displayValueFormat.getDisplayValueFormat(newFieldName, pattern));
		}
		else {
			seriesConfig.setPropertyValue(displayLabelPropertyName, "{"+newFieldName+"}");
		}
	}
}