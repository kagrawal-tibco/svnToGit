package com.tibco.cep.studio.dashboard.ui.forms;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalActionRule;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalDataSource;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.model.impl.metric.LocalMetric;
import com.tibco.cep.studio.dashboard.ui.editors.views.queryparams.QueryParamEditor;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class DataSettingsForm extends BaseForm {

	public static final String PROP_KEY_MEASURE = BEViewsElementNames.METRIC;

	public static final String PROP_KEY_DATA_SOURCE = BEViewsElementNames.DATA_SOURCE;

	private Combo cmb_Measure;
	private ComboViewer cmbViewer_Measure;
	private AbstractSelectionListener cmb_MeasureSelectionListener;

	private Combo cmb_DataSource;
	private ComboViewer cmbViewer_DataSource;
	private AbstractSelectionListener cmb_DataSourceSelectionListener;

	private Text txt_Query;
	private QueryParamEditor queryParamEditor;

//	private LocalSeriesConfig localSeriesConfig;
	protected LocalActionRule localActionRule;


	public DataSettingsForm(FormToolkit formToolKit, Composite parent, boolean showGroup) {
		super("Data Settings", formToolKit, parent, showGroup);
	}

	@Override
	public void init() {
		//do layout
		GridLayout layout = new GridLayout(2, false);
		formComposite.setLayout(layout);
		createControl();
	}

	protected void createControl() {
		// measure label
		createLabel(formComposite, "Measure:", SWT.NONE);
		// measure drop down
		cmb_Measure = createCombo(formComposite, SWT.DROP_DOWN | SWT.READ_ONLY);
		cmb_Measure.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		// data source label
		createLabel(formComposite, "Data Source:", SWT.NONE);
		// data source drop down
		cmb_DataSource = createCombo(formComposite, SWT.DROP_DOWN | SWT.READ_ONLY);
		cmb_DataSource.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		// query text
		txt_Query = createText(formComposite, "", SWT.READ_ONLY | SWT.WRAP | SWT.V_SCROLL);
		GridData txt_QueryLayoutData = new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1);
		txt_QueryLayoutData.heightHint = 50;
		txt_Query.setLayoutData(txt_QueryLayoutData);
		// query parameters
		queryParamEditor = new QueryParamEditor(formToolKit == null ? new FormToolkit(Display.getCurrent()) : formToolKit, formComposite);
		GridData queryParamEditorData = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		queryParamEditorData.heightHint = 75;
		//PATCH added widthHint to compensate for ever widening form when switching selections due to % based layout  on param editor
		queryParamEditorData.widthHint = 100;
		queryParamEditor.setLayoutData(queryParamEditorData);

		//do widget to model hookups
		cmbViewer_Measure = new ComboViewer(cmb_Measure);
		cmbViewer_Measure.setLabelProvider(new LocalElementLabelProvider(false));
		cmbViewer_Measure.setContentProvider(new ArrayContentProvider());

		cmbViewer_DataSource = new ComboViewer(cmb_DataSource);
		cmbViewer_DataSource.setLabelProvider(new LocalElementLabelProvider(false));
		cmbViewer_DataSource.setContentProvider(new ArrayContentProvider());
	}

	@Override
	protected void inputChanged(LocalElement oldLocalElement, LocalElement newLocalElement) throws Exception {
//		if ((newLocalElement instanceof LocalSeriesConfig) == false){
//			throw new IllegalArgumentException(newLocalElement+" is not acceptable");
//		}
//		localSeriesConfig = (LocalSeriesConfig) localElement;
		localActionRule = null;
		if (localElement != null) {
			localActionRule = (LocalActionRule) localElement.getElement(BEViewsElementNames.ACTION_RULE);
		}
	}

	@Override
	protected void doEnableListeners() {
		if (cmb_MeasureSelectionListener == null){
			cmb_MeasureSelectionListener = new AbstractSelectionListener(){

				@Override
				public void widgetSelected(SelectionEvent e) {
					measureChanged();
				}

			};
		}
		cmb_Measure.addSelectionListener(cmb_MeasureSelectionListener);

		if (cmb_DataSourceSelectionListener == null){
			cmb_DataSourceSelectionListener = new AbstractSelectionListener(){

				@Override
				public void widgetSelected(SelectionEvent e) {
					dataSourceChanged();
				}

			};
		}
		cmb_DataSource.addSelectionListener(cmb_DataSourceSelectionListener);
	}

	@Override
	protected void doDisableListeners() {
		cmb_Measure.removeSelectionListener(cmb_MeasureSelectionListener);
		cmb_DataSource.removeSelectionListener(cmb_DataSourceSelectionListener);
	}

	@Override
	public void refreshEnumerations() {
		try {
			List<LocalElement> metrics = new LinkedList<LocalElement>();
			if (localActionRule != null) {
				metrics.addAll(localActionRule.getRoot().getChildren(BEViewsElementNames.METRIC));
			}
			ListIterator<LocalElement> metricsIterator = metrics.listIterator();
			while (metricsIterator.hasNext()) {
				LocalMetric localMetric = (LocalMetric) metricsIterator.next();
				if (localMetric.getEnumerations(BEViewsElementNames.DATA_SOURCE).isEmpty() == true){
					metricsIterator.remove();
				}
			}
			cmbViewer_Measure.setInput(metrics);
			cmbViewer_DataSource.setInput(Collections.emptyList());
			if (metrics.isEmpty() == true){
				disableAll();
			}
		} catch (Exception e) {
			log(new Status(IStatus.ERROR,getPluginId(),"could not refresh enumerations in "+this.getClass().getName(),e));
			disableAll();
		}
	}

	@Override
	public void refreshSelections() {
		try {
			//reset all controls
			cmbViewer_Measure.setSelection(StructuredSelection.EMPTY);
			cmbViewer_DataSource.setInput(Collections.emptyList());
			cmbViewer_DataSource.setSelection(StructuredSelection.EMPTY);
			txt_Query.setText("");
			queryParamEditor.setLocalElement(new LocalActionRule());
			if (localActionRule != null) {
				//we do not use com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalActionRule.getMeasure() since it is meant for chart editor only
				LocalElement dataSource = localActionRule.getElement(BEViewsElementNames.DATA_SOURCE);
				if (dataSource != null) {
					LocalElement metric = dataSource.getElement(LocalDataSource.ELEMENT_KEY_SRC_ELEMENT);
					//DOUBT not sure why metric would be null during refresh (see com.tibco.cep.studio.dashboard.ui.editors.AbstractEntityEditorPage.handleOutsideElementChange(int, LocalElement))
					if (metric != null) {
						cmbViewer_Measure.setSelection(new StructuredSelection(metric));
						cmbViewer_DataSource.setInput(metric.getEnumerations(BEViewsElementNames.DATA_SOURCE));
						cmbViewer_DataSource.setSelection(new StructuredSelection(dataSource));
						txt_Query.setText(dataSource.getPropertyValue(LocalDataSource.PROP_KEY_QUERY));
						queryParamEditor.setLocalElement(localActionRule);
					}
				}
			}
		} catch (Exception e) {
			log(new Status(IStatus.ERROR,getPluginId(),"could not refresh selections in "+this.getClass().getName(),e));
			disableAll();
		}
	}

	protected void measureChanged() {
		try {
			LocalElement oldDataSource = localActionRule.getElement(BEViewsElementNames.DATA_SOURCE);
			LocalElement oldMeasure = oldDataSource == null ? null : oldDataSource.getElement(LocalDataSource.ELEMENT_KEY_SRC_ELEMENT);
			//get measure
			LocalElement metric = (LocalElement) ((IStructuredSelection)cmbViewer_Measure.getSelection()).getFirstElement();
			//we do not use com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalActionRule.setMeasure() since it is meant for chart editor only
			//get data sources for the metric
			List<Object> dataSources = metric.getEnumerations(BEViewsElementNames.DATA_SOURCE);
			//update the action rule
				//set the data source (localactionrule code updates the query parameters)
			LocalElement newDataSource = (LocalElement) dataSources.get(0);
			localActionRule.setElement(BEViewsElementNames.DATA_SOURCE, newDataSource);
				//remove any drillable fields
			localActionRule.removeChildren(LocalActionRule.ELEMENT_KEY_DRILLABLE_FIELDS);
			//update the datasource combo
			cmbViewer_DataSource.setInput(dataSources);
				//set data source selection
			cmbViewer_DataSource.setSelection(new StructuredSelection(newDataSource));
				//set txt_query
			txt_Query.setText(newDataSource.getPropertyValue(LocalDataSource.PROP_KEY_QUERY));
				//update the query params
			queryParamEditor.setLocalElement(localActionRule);
			//fire the property change event, let the listener handle updating dependent properties
			propertyChangeSupport.firePropertyChange(PROP_KEY_MEASURE, oldMeasure, metric);
		} catch (Exception e) {
			disableAll();
			logAndAlert(new Status(IStatus.ERROR,getPluginId(),"could not process measure selection",e));
		}
	}

	protected void dataSourceChanged() {
		try {
			LocalElement oldDataSource = localActionRule.getElement(BEViewsElementNames.DATA_SOURCE);
			LocalElement dataSource = (LocalElement) ((IStructuredSelection)cmbViewer_DataSource.getSelection()).getFirstElement();
			localActionRule.setElement(BEViewsElementNames.DATA_SOURCE, dataSource);
			txt_Query.setText(dataSource.getPropertyValue(LocalDataSource.PROP_KEY_QUERY));
			queryParamEditor.setLocalElement(localActionRule);
			propertyChangeSupport.firePropertyChange(PROP_KEY_DATA_SOURCE, oldDataSource, dataSource);
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR,getPluginId(),"could not process datasource selection",e));
		}
	}

	@Override
	public void disableAll() {
		super.disableAll();
		queryParamEditor.getTable().setEnabled(false);
	}

	@Override
	public void enableAll() {
		super.enableAll();
		queryParamEditor.getTable().setEnabled(true);
	}
}
