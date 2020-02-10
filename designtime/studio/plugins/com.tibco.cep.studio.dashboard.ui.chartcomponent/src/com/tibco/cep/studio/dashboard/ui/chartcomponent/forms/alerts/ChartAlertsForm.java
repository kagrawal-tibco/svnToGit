package com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.alerts;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.List;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.studio.dashboard.core.enums.InternalStatusEnum;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalSeriesConfig;
import com.tibco.cep.studio.dashboard.core.listeners.IMessageProvider;
import com.tibco.cep.studio.dashboard.core.listeners.ISynElementChangeListener;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.model.LocalUnifiedSeriesConfig;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.model.LocalUnifiedVisualization;
import com.tibco.cep.studio.dashboard.ui.forms.AlertSettingsForm;
import com.tibco.cep.studio.dashboard.ui.forms.BaseForm;
import com.tibco.cep.studio.dashboard.ui.forms.LocalElementDisplayNameLabelProvider;

public class ChartAlertsForm extends BaseForm {

	private List lst_Serieses;
	private ListViewer lstViewer_Serieses;
	private ISelectionChangedListener lstViewerSeriesesSelectionChangedListener;

	private AlertSettingsForm alertSettingsForm;

	private LocalElement localUnifiedVisualization;

	private SeriesChangeListener seriesChangeListener;

	private LocalElement currSeriesConfig;

	public ChartAlertsForm(FormToolkit formToolKit, Composite parent) {
		super("Alerts", formToolKit, parent, false);
		seriesChangeListener = new SeriesChangeListener();
	}

	@Override
	public void init() {
		formComposite.setLayout(new GridLayout());

		// series list
		Group seriesGroup = createGroup(formComposite, "Series", SWT.NONE);
		FillLayout layout = new FillLayout();
		layout.marginHeight = 5;
		layout.marginWidth = 5;
		seriesGroup.setLayout(layout);
		lst_Serieses = createList(seriesGroup, SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);

		GridData seriesGroupLayoutData = new GridData(SWT.FILL, SWT.FILL, false, false);
		seriesGroupLayoutData.heightHint = 200;
		seriesGroup.setLayoutData(seriesGroupLayoutData);

		//alert settings form
		alertSettingsForm = new AlertSettingsForm(this.formToolKit,formComposite,SWT.VERTICAL);
		alertSettingsForm.init();
		alertSettingsForm.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		addForm(alertSettingsForm);

		// initiaize list viewer
		lstViewer_Serieses = new ListViewer(lst_Serieses);
		lstViewer_Serieses.setContentProvider(new ArrayContentProvider());
		lstViewer_Serieses.setLabelProvider(new LocalElementDisplayNameLabelProvider());
	}

	@Override
	protected void inputChanged(LocalElement oldLocalElement, LocalElement newLocalElement) throws Exception {
		localUnifiedVisualization = null;
		if (newLocalElement != null) {
			localUnifiedVisualization = newLocalElement.getElement(LocalUnifiedVisualization.TYPE);
		}
	}

	@Override
	protected void setChildrenFormInput(LocalElement localElement) throws Exception {
		//we want to control what gets set on the child forms
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
		localElement.subscribeToAll(seriesChangeListener);
		localElement.subscribe(seriesChangeListener, LocalUnifiedSeriesConfig.TYPE);
	}

	@Override
	protected void doDisableListeners() {
		lstViewer_Serieses.removeSelectionChangedListener(lstViewerSeriesesSelectionChangedListener);
		localElement.unsubscribe(seriesChangeListener, LocalUnifiedSeriesConfig.TYPE);
		localElement.unsubscribeAll(seriesChangeListener);
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
			java.util.List<LocalElement> seriesConfigs = localUnifiedVisualization.getChildren(LocalUnifiedSeriesConfig.TYPE);
			lstViewer_Serieses.setInput(seriesConfigs);
		} catch (Exception e) {
			log(new Status(IStatus.ERROR, getPluginId(), "could not read series configs", e));
		}
	}

	@Override
	public void refreshSelections() {
		try {
			java.util.List<LocalElement> seriesConfigs = localUnifiedVisualization.getChildren(LocalUnifiedSeriesConfig.TYPE);
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
					if (baseForm.getInput() == null) {
						baseForm.enableAll();
					}
					baseForm.setInput(selectedSeriesConfig);
				}
			} catch (Exception e) {
				logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not process series selection change", e));
			}
		}
	}

	class SeriesChangeListener implements ISynElementChangeListener {

		@Override
		public void elementAdded(IMessageProvider parent, IMessageProvider newElement) {
			if (newElement instanceof LocalSeriesConfig) {
				//store current selection
				IStructuredSelection selection = (IStructuredSelection) lstViewer_Serieses.getSelection();
				refreshEnumerations();
				//restore selection
				lstViewer_Serieses.setSelection(selection);
			}
		}

		@Override
		public void elementChanged(IMessageProvider parent, IMessageProvider changedElement) {
			//do nothing
		}

		@Override
		public void elementRemoved(IMessageProvider parent, IMessageProvider removedElement) {
			if (removedElement instanceof LocalSeriesConfig) {
				//store current selection
				IStructuredSelection selection = (IStructuredSelection) lstViewer_Serieses.getSelection();
				refreshEnumerations();
				//restore selection if different
				if (removedElement != selection.getFirstElement()){
					lstViewer_Serieses.setSelection(selection);
				}
				else {
					lstViewer_Serieses.setSelection(StructuredSelection.EMPTY);
					seriesListSelectionChanged();
				}
			}
		}

		@Override
		public void elementStatusChanged(IMessageProvider parent, InternalStatusEnum status) {
			//do nothing
		}

		@Override
		public String getName() {
			return title+" series change listener";
		}

		@Override
		public void propertyChanged(IMessageProvider provider, SynProperty property, Object oldValue, Object newValue) {
			//do nothing
		}

	}

}
