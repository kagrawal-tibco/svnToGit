package com.tibco.cep.studio.dashboard.ui.statemachinecomponent.forms;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalStateSeriesConfig;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.helpers.StateMachineComponentHelper;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.forms.BaseForm;
import com.tibco.cep.studio.dashboard.ui.forms.DataSettingsForm;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class ContentDataSettingsForm extends BaseForm {
	
	private int orientation;

	private DataSettingsForm dataSettingsForm;
	private PropertyChangeListener dataSettingsFormChangeListener;
	
	private ContentValueFieldSettingsForm valueFieldSettingsForm;
	private PropertyChangeListener valueSettingsFormChangeListener;
	
	private boolean showOtherDataSettingsForm;
	private OtherDataSettingsForm otherDataSettingsForm;

	private LocalStateSeriesConfig localContentSeriesConfig;
	
	private MeasureChangeMediator measureChangeMediator;
	
	public ContentDataSettingsForm(FormToolkit formToolKit, Composite parent, int orientation) {
		super("Content Data Settings", formToolKit, parent, false);
		if (orientation != SWT.HORIZONTAL && orientation != SWT.VERTICAL){
			throw new IllegalArgumentException(orientation+" is invalid");
		}
		this.orientation = orientation;
		showOtherDataSettingsForm = true;
	}
	
	public void setOtherDataSettingsVisible(boolean visible) {
		showOtherDataSettingsForm = visible;
	}

	@Override
	public void init() {
		FillLayout layout = new FillLayout(orientation);
		layout.spacing = 5;
		formComposite.setLayout(layout);
		// data settings
		dataSettingsForm = new DataSettingsForm(formToolKit, formComposite, true);
		dataSettingsForm.init();
		addForm(dataSettingsForm);
		// value field settings
		valueFieldSettingsForm = new ContentValueFieldSettingsForm(formToolKit, formComposite, true);
		valueFieldSettingsForm.init();
		addForm(valueFieldSettingsForm);
		if (showOtherDataSettingsForm == true) {
			// other data settings form
			otherDataSettingsForm = new OtherDataSettingsForm(formToolKit, formComposite);
			otherDataSettingsForm.init();
			addForm(otherDataSettingsForm);
		}
	}
	
	@Override
	protected void inputChanged(LocalElement oldLocalElement, LocalElement newLocalElement) throws Exception {
		if (newLocalElement instanceof LocalStateSeriesConfig){
			if (StateMachineComponentHelper.isContentSeriesConfig((LocalStateSeriesConfig) newLocalElement) == true){
				localContentSeriesConfig = (LocalStateSeriesConfig) newLocalElement;
				measureChangeMediator = new MeasureChangeMediator(localContentSeriesConfig,getForms());
			}
			else {
				throw new IllegalArgumentException(newLocalElement+" is not a content based state series config");
			}
		}
		else {
			throw new IllegalArgumentException(newLocalElement+" is not a state series config");
		}
	}

	@Override
	protected void doEnableListeners() {
		// do nothing
		if (dataSettingsFormChangeListener == null) {
			dataSettingsFormChangeListener = new PropertyChangeListener() {

				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					measureChanged();
				}

			};
		}
		dataSettingsForm.addPropertyChangeListener(DataSettingsForm.PROP_KEY_MEASURE, dataSettingsFormChangeListener);
		
		if (valueSettingsFormChangeListener == null) {
			valueSettingsFormChangeListener = new PropertyChangeListener() {

				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					contentFormatTypeChanged();
				}

			};
		}
		valueFieldSettingsForm.addPropertyChangeListener(ContentValueFieldSettingsForm.PROP_KEY_FORMAT_TYPE, valueSettingsFormChangeListener);		
	}

	@Override
	protected void doDisableListeners() {
		dataSettingsForm.removePropertyChangeListener(BEViewsElementNames.METRIC, dataSettingsFormChangeListener);
		valueFieldSettingsForm.removePropertyChangeListener(ContentValueFieldSettingsForm.PROP_KEY_FORMAT_TYPE, valueSettingsFormChangeListener);
	}

	@Override
	public void refreshEnumerations() {
		// do nothing
	}

	@Override
	public void refreshSelections() {
		// do nothing
	}

	protected void measureChanged() {
		try {
			measureChangeMediator.updateSeriesConfig();
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR,getPluginId(),"could not process measure selection",e));
			disableAll();
		}
	}
	
	protected void contentFormatTypeChanged() {
		if (otherDataSettingsForm == null) {
			return;
		}
		try {
			otherDataSettingsForm.setInput(localContentSeriesConfig);
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR,getPluginId(),"could not process content type selection",e));
			disableAll();
		}
	}



}