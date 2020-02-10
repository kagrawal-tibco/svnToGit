package com.tibco.cep.studio.dashboard.ui.statemachinecomponent.wizard;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.tibco.cep.studio.dashboard.ui.forms.AlertSettingsForm;
import com.tibco.cep.studio.dashboard.ui.forms.BaseForm;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.DashboardStateMachineComponentPlugin;

public class ContentTemplateAlertSettingsWizardPage extends AbstractTemplateSettingsWizardPage {

	public ContentTemplateAlertSettingsWizardPage() {
		super(TYPE.CONTENT, "ContentAlertSettings", "Template For Content Alert Settings", null);
		setImageDescriptor(DashboardStateMachineComponentPlugin.getInstance().getImageRegistry().getDescriptor("contentalertsettings_wiz.png"));
	}

	@Override
	protected BaseForm createForm(Composite parent) {
		AlertSettingsForm alertSettingsForm = new AlertSettingsForm(null, parent, SWT.VERTICAL);
		return alertSettingsForm;
	}

}