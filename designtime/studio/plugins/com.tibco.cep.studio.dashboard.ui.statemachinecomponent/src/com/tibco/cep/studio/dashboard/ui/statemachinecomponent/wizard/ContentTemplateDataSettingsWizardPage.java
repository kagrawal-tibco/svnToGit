package com.tibco.cep.studio.dashboard.ui.statemachinecomponent.wizard;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.tibco.cep.studio.dashboard.ui.forms.BaseForm;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.DashboardStateMachineComponentPlugin;
import com.tibco.cep.studio.dashboard.ui.statemachinecomponent.forms.ContentDataSettingsForm;

public class ContentTemplateDataSettingsWizardPage extends AbstractTemplateSettingsWizardPage {

	public ContentTemplateDataSettingsWizardPage() {
		super(TYPE.CONTENT, "ContentDataSettings", "Template For Content Data Settings", null);
		setImageDescriptor(DashboardStateMachineComponentPlugin.getInstance().getImageRegistry().getDescriptor("contentdatasettings_wiz.png"));
	}

	@Override
	protected BaseForm createForm(Composite parent) {
		ContentDataSettingsForm dataSettingsForm = new ContentDataSettingsForm(null, parent, SWT.VERTICAL);
		dataSettingsForm.setOtherDataSettingsVisible(false);
		return dataSettingsForm;
	}

}