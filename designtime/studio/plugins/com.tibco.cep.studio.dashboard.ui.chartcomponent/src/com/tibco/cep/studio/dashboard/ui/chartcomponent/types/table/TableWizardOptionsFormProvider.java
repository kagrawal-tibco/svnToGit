package com.tibco.cep.studio.dashboard.ui.chartcomponent.types.table;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.studio.dashboard.ui.chartcomponent.SlavePageFormsProvider;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.options.ChartSizeForm;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.options.ChartTableTitleWizardForm;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.types.ChartSubType;
import com.tibco.cep.studio.dashboard.ui.forms.BaseForm;

public class TableWizardOptionsFormProvider implements SlavePageFormsProvider {
	
	private BaseForm[] forms;

	@Override
	public void createForm(FormToolkit formToolkit, Composite parent) {
		parent.setLayout(new GridLayout());
		
		forms = new BaseForm[2];
		
		//create the titles form 
		ChartTableTitleWizardForm titlesForm = new ChartTableTitleWizardForm(formToolkit,parent, true);
		titlesForm.init();
		titlesForm.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		forms[0] = titlesForm;
		
		//create the size form 
		ChartSizeForm sizeForm = new ChartSizeForm(formToolkit,parent, true);
		sizeForm.init();
		sizeForm.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		forms[1] = sizeForm;
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
