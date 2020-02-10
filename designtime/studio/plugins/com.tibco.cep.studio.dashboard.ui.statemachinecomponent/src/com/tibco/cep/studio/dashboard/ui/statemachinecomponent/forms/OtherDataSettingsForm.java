package com.tibco.cep.studio.dashboard.ui.statemachinecomponent.forms;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.studio.dashboard.ui.forms.BaseForm;
import com.tibco.cep.studio.dashboard.ui.forms.DrillableFieldsSettingsForm;
import com.tibco.cep.studio.dashboard.ui.forms.ScreenTipSettingsForm;

class OtherDataSettingsForm extends BaseForm {
	
	private ScreenTipSettingsForm screenTipSettingsForm;
	
	private DrillableFieldsSettingsForm drillableFieldsSettingsForm;

	public OtherDataSettingsForm(FormToolkit formToolKit, Composite parent) {
		super("Other Settings", formToolKit, parent, false);
	}
	
	@Override
	public void init() {
		FillLayout layout = new FillLayout(SWT.VERTICAL);
		layout.spacing = 5;
		formComposite.setLayout(layout);
		// screen tip settings
		screenTipSettingsForm = new SMComponentScreenTipSettingsForm(formToolKit, formComposite, true);
		screenTipSettingsForm.init();
		addForm(screenTipSettingsForm);
		// drillable fields settings
		drillableFieldsSettingsForm = new DrillableFieldsSettingsForm(formToolKit, formComposite, true);
		drillableFieldsSettingsForm.init();
		addForm(drillableFieldsSettingsForm);
	}	
	
	@Override
	protected void doDisableListeners() {
		//do nothing
	}

	@Override
	protected void doEnableListeners() {
		//do nothing
	}


	@Override
	public void refreshEnumerations() {
		//do nothing
	}

	@Override
	public void refreshSelections() {
		//do nothing
	}

}
