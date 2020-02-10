package com.tibco.cep.studio.dashboard.ui.chartcomponent.wizard.pages;

import org.eclipse.swt.widgets.Composite;

import com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.type.ChartTypeForm;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.wizard.ChartWizardBasePage;
import com.tibco.cep.studio.dashboard.ui.forms.BaseForm;

public class ChartTypeWizardPage extends ChartWizardBasePage {

	public ChartTypeWizardPage() {
		super("type");
		setTitle("Select a Chart Type");
		setMessage("Select the chart type");
	}

	@Override
	protected BaseForm[] createForms(Composite parent) {
		ChartTypeForm chartTypeForm = new ChartTypeForm(null,parent, true);
		chartTypeForm.init();
		return new BaseForm[]{chartTypeForm};
	}

}
