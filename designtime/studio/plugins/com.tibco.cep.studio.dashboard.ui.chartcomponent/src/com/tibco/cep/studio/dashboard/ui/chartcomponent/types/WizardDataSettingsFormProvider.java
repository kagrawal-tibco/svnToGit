package com.tibco.cep.studio.dashboard.ui.chartcomponent.types;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.studio.dashboard.ui.chartcomponent.SlavePageFormsProvider;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.data.ChartRunTimeDataWizardForm;
import com.tibco.cep.studio.dashboard.ui.forms.BaseForm;

public class WizardDataSettingsFormProvider implements SlavePageFormsProvider {
	
	private BaseForm[] forms;

	@Override
	public void createForm(FormToolkit formToolkit, Composite parent) {
		forms = new BaseForm[1];
		
		parent.setLayout(new GridLayout());
		
		ChartRunTimeDataWizardForm form = new ChartRunTimeDataWizardForm(formToolkit,parent);
		form.init();
		form.getControl().setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true));
		
		forms[0] = form;
	}

	@Override
	public BaseForm[] getForms() {
		return forms;
	}

	@Override
	public void setChartSubTypes(ChartSubType[] chartSubTypes) {
		//do nothing
	}

}
