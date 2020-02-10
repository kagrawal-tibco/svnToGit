package com.tibco.cep.studio.dashboard.ui.chartcomponent;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.studio.dashboard.ui.chartcomponent.types.ChartSubType;
import com.tibco.cep.studio.dashboard.ui.forms.BaseForm;

public interface SlavePageFormsProvider {
	
	public void createForm(FormToolkit formToolkit, Composite parent);
	
	public BaseForm[] getForms();

	public void setChartSubTypes(ChartSubType[] chartSubTypes);
	
}