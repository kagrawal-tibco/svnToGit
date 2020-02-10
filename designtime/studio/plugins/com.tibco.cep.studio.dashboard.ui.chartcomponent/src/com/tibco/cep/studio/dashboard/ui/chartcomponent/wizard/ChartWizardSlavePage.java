package com.tibco.cep.studio.dashboard.ui.chartcomponent.wizard;

import org.eclipse.jface.resource.ImageDescriptor;

import com.tibco.cep.studio.dashboard.ui.chartcomponent.SlavePage;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.types.ChartSubType;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.types.ChartType;

public abstract class ChartWizardSlavePage extends ChartWizardBasePage implements SlavePage {
	
	protected ChartType chartType;
	protected ChartSubType[] chartSubTypes;

	public ChartWizardSlavePage(String pageName, String title, ImageDescriptor titleImage) {
		super(pageName, title, titleImage);
	}

	public ChartWizardSlavePage(String pageName) {
		super(pageName);
	}

	@Override
	public ChartType getChartType() {
		return chartType;
	}

	@Override
	public void setChartType(ChartType chartType) throws Exception {
		this.chartType = chartType;
	}

	@Override
	public ChartSubType[] getChartSubTypes() {
		return chartSubTypes;
	}

	@Override
	public void setChartSubTypes(ChartSubType[] chartSubTypes) throws Exception {
		this.chartSubTypes = chartSubTypes;
	}

}