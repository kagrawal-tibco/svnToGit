package com.tibco.cep.studio.dashboard.ui.chartcomponent.types.overlaid;

import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.data.SeriesTypeProcessor;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.model.LocalUnifiedSeriesConfig;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class OverlaidSeriesTypeProcessor implements SeriesTypeProcessor {

	@Override
	public boolean enableSeriesType() {
		return true;
	}

	@Override
	public String getSeriesType(LocalElement localElement) throws Exception {
		if (localElement == null){
			return "";
		}
		String propertyValue = localElement.getPropertyValue(LocalUnifiedSeriesConfig.PROP_KEY_SERIES_TYPE);
		if (propertyValue.equals(BEViewsElementNames.BAR_CHART_VISUALIZATION) == true){
			return "Column";
		}
		if (propertyValue.equals(BEViewsElementNames.LINE_CHART_VISUALIZATION) == true){
			return "Line";
		}
		if (propertyValue.equals(BEViewsElementNames.SCATTER_CHART_VISUALIZATION) == true){
			return "Scatter";
		}
		return "";
	}

	@Override
	public String[] getSeriesTypes() throws Exception {
		return new String[]{"Column","Line","Scatter"};
	}

	@Override
	public void setSeriesType(LocalElement localElement, String seriesType) throws Exception {
		if ("Column".equals(seriesType) == true) {
			localElement.setPropertyValue(LocalUnifiedSeriesConfig.PROP_KEY_SERIES_TYPE,BEViewsElementNames.BAR_CHART_VISUALIZATION);	
		}
		else if ("Line".equals(seriesType) == true) {
			localElement.setPropertyValue(LocalUnifiedSeriesConfig.PROP_KEY_SERIES_TYPE,BEViewsElementNames.LINE_CHART_VISUALIZATION);	
		}
		else if ("Scatter".equals(seriesType) == true) {
			localElement.setPropertyValue(LocalUnifiedSeriesConfig.PROP_KEY_SERIES_TYPE,BEViewsElementNames.SCATTER_CHART_VISUALIZATION);	
		}			
	}

}
