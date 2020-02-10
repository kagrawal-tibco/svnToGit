package com.tibco.cep.studio.dashboard.ui.chartcomponent.types.range;

import com.tibco.cep.designtime.core.model.beviewsconfig.RangePlotChartSeriesTypeEnum;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.data.SeriesTypeProcessor;

public class RangeSeriesTypeProcessor implements SeriesTypeProcessor {

	@Override
	public boolean enableSeriesType() {
		return true;
	}

	@Override
	public String getSeriesType(LocalElement localElement) throws Exception {
		if (localElement == null){
			return "";
		}
		String value = localElement.getPropertyValue("Type");
		if (RangePlotChartSeriesTypeEnum.MIN.getLiteral().equalsIgnoreCase(value) == true) {
			return "Min";	
		}
		else if (RangePlotChartSeriesTypeEnum.CURRENT.getLiteral().equalsIgnoreCase(value) == true) {
			return "Current";	
		}
		else if (RangePlotChartSeriesTypeEnum.MAX.getLiteral().equalsIgnoreCase(value) == true) {
			return "Max";	
		}
		return "";
	}

	@Override
	public String[] getSeriesTypes() throws Exception {
		return new String[]{"Min","Current","Max"};
	}

	@Override
	public void setSeriesType(LocalElement localElement, String seriesType) throws Exception {
		if ("Min".equals(seriesType) == true) {
			localElement.setPropertyValue("Type",RangePlotChartSeriesTypeEnum.MIN.getLiteral());	
		}
		else if ("Current".equals(seriesType) == true) {
			localElement.setPropertyValue("Type",RangePlotChartSeriesTypeEnum.CURRENT.getLiteral());	
		}
		else if ("Max".equals(seriesType) == true) {
			localElement.setPropertyValue("Type",RangePlotChartSeriesTypeEnum.MAX.getLiteral());	
		}
	}

}
