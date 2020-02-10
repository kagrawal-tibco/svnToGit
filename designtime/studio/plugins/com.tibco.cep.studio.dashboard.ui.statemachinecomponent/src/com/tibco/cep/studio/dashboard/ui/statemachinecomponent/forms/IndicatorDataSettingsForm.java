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

public class IndicatorDataSettingsForm extends BaseForm {

	private BaseForm dataSettingsForm;
	private IndicatorValueFieldSettingsForm valueFieldSettingsForm;
	
	private boolean showOtherDataSettingsForm;
	private OtherDataSettingsForm otherDataSettingsForm;
	
	private PropertyChangeListener dataSettingsFormChangeListener;
	private LocalStateSeriesConfig localIndicatorSeriesConfig;
	
	private int orientation;
	private MeasureChangeMediator measureChangeMediator;

	public IndicatorDataSettingsForm(FormToolkit formToolKit, Composite parent, int orientation) {
		super("Indicator Data Settings", formToolKit, parent, false);
		if (orientation != SWT.HORIZONTAL && orientation != SWT.VERTICAL){
			throw new IllegalArgumentException(orientation+" is invalid");
		}
		this.orientation = orientation;
		showOtherDataSettingsForm = true;
	}
	
	public void setOtherDataSettingsVisible(boolean visible){
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
		valueFieldSettingsForm = new IndicatorValueFieldSettingsForm(formToolKit, formComposite, true);
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
			if (StateMachineComponentHelper.isIndicatorSeriesConfig((LocalStateSeriesConfig) newLocalElement) == true){
				localIndicatorSeriesConfig = (LocalStateSeriesConfig) newLocalElement;
				measureChangeMediator = new MeasureChangeMediator(localIndicatorSeriesConfig,getForms());
			}
			else {
				throw new IllegalArgumentException(newLocalElement+" is not a indicator based state series config");
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
		dataSettingsForm.addPropertyChangeListener(BEViewsElementNames.METRIC, dataSettingsFormChangeListener);
	}

	@Override
	protected void doDisableListeners() {
		dataSettingsForm.removePropertyChangeListener(BEViewsElementNames.METRIC, dataSettingsFormChangeListener);
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

}
