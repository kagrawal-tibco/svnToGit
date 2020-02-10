package com.tibco.cep.studio.dashboard.ui.chartcomponent.wizard.pages;

import com.tibco.cep.studio.dashboard.ui.chartcomponent.SlavePageFormsProvider;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.types.ChartType;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.wizard.ChartWizardChangeableSlavePage;

public class ChartRunTimeDataWizardPage extends ChartWizardChangeableSlavePage {

	public ChartRunTimeDataWizardPage() {
		super("runtimedata");
		setTitle("Configure Data Options");
		setMessage("Enter names of series and optionally what data to show at run-time. You can change this later");
	}

	protected SlavePageFormsProvider getSlavePageFormsProvider(ChartType chartType) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		String optionsFormProvider = chartType.getWizardDataFormProvider();
		return (SlavePageFormsProvider) Class.forName(optionsFormProvider).newInstance();
	}

}
