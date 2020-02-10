package com.tibco.cep.studio.dashboard.ui.chartcomponent.wizard.pages;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.data.ChartDesignTimeDataForm;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.wizard.ChartWizardSlavePage;
import com.tibco.cep.studio.dashboard.ui.forms.BaseForm;

public class ChartDesignTimeDataWizardPage extends ChartWizardSlavePage {

	public ChartDesignTimeDataWizardPage() {
		super("previewdata");
		setTitle("Enter Chart Preview Options");
		setMessage("Enter the names of categories and data value ranges to show in preview. You can change this later");
	}

	@Override
	protected BaseForm[] createForms(Composite parent) {
		ChartDesignTimeDataForm designTimeForm = new ChartDesignTimeDataForm(null,parent, SWT.VERTICAL);
		designTimeForm.init();
		return new BaseForm[]{designTimeForm};
	}

}
