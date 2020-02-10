package com.tibco.cep.studio.dashboard.ui.chartcomponent.wizard.pages;

import com.tibco.cep.studio.dashboard.ui.chartcomponent.SlavePageFormsProvider;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.types.ChartType;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.wizard.ChartWizardChangeableSlavePage;

public class ChartOptionsWizardPage extends ChartWizardChangeableSlavePage {

	public ChartOptionsWizardPage() {
		super("options");
		setTitle("Select Layout Options");
		setMessage("Enter titles and chart size");
	}

	protected SlavePageFormsProvider getSlavePageFormsProvider(ChartType chartType) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		String optionsFormProvider = chartType.getWizardOptionsFormProvider();
		return (SlavePageFormsProvider) Class.forName(optionsFormProvider).newInstance();
	}

}