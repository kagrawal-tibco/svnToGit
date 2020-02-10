package com.tibco.cep.studio.dashboard.ui.chartcomponent;

import com.tibco.cep.studio.dashboard.ui.chartcomponent.types.ChartSubType;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.types.ChartType;

public interface SlavePage extends ControllablePage {
	
	public void setChartType(ChartType chartType) throws Exception;
	
	public void setChartSubTypes(ChartSubType[] chartSubTypes) throws Exception;
	
	public ChartType getChartType();
	
	public ChartSubType[] getChartSubTypes();

	public String getTitle();
	
}
