package com.tibco.cep.studio.dashboard.ui.statemachinecomponent.wizard;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.tibco.cep.studio.dashboard.ui.forms.BaseForm;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.DashboardStateMachineComponentPlugin;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.forms.IndicatorDataSettingsForm;

public class IndicatorTemplateDataSettingsWizardPage extends AbstractTemplateSettingsWizardPage {

	public IndicatorTemplateDataSettingsWizardPage() {
		super(TYPE.INDICATOR, "IndicatorDataSettings", "Template For Indicator Data Settings", null);
		setImageDescriptor(DashboardStateMachineComponentPlugin.getInstance().getImageRegistry().getDescriptor("indicatordatasettings_wiz.png"));
		
	}

	@Override
	protected BaseForm createForm(Composite parent) {
		IndicatorDataSettingsForm dataSettingsForm = new IndicatorDataSettingsForm(null, parent, SWT.VERTICAL);
		dataSettingsForm.setOtherDataSettingsVisible(false);
		return dataSettingsForm;
	}
}
