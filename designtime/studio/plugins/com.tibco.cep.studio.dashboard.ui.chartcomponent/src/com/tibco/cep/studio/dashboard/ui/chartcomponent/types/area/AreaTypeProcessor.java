package com.tibco.cep.studio.dashboard.ui.chartcomponent.types.area;

import java.util.Collection;

import com.tibco.cep.designtime.core.model.beviewsconfig.DataPlottingEnum;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.model.LocalUnifiedVisualization;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.types.BaseChartComponentTypeProcessor;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.types.ChartSubType;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class AreaTypeProcessor extends BaseChartComponentTypeProcessor {
	
	@Override
	public boolean isAcceptable(LocalElement localElement) throws Exception {
		Collection<String> types = getVizualizationTypes(localElement);
		if (types.size() == 1 && types.contains(BEViewsElementNames.AREA_CHART_VISUALIZATION) == true){
			return true;
		}
		return false;
	}

	@Override
	public String[] getSubTypes(LocalElement localElement) throws Exception {
		LocalElement visualization = localElement.getElement(LocalUnifiedVisualization.TYPE);
		String propertyValue = visualization.getPropertyValue("DataPoints");
		if (propertyValue == null || propertyValue.trim().length() == 0){
			return new String[]{"AllPoints"};
		}
		try {
			DataPlottingEnum dataPlottingEnum = DataPlottingEnum.get(propertyValue.toLowerCase());
			switch (dataPlottingEnum) {
				case ALL:
					return new String[]{"AllPoints"};	
				case EDGES:
					return new String[]{"EndPoints"};	
				case NONE:
					return new String[]{"NoPoints"};
				default:
					return new String[]{"AllPoints"};		
			}
		} catch (IllegalArgumentException e) {
			return new String[]{"AllPoints"};
		}
	}
	
	@Override
	public void subTypeChanged(LocalElement localElement, ChartSubType oldSubType, ChartSubType newSubType) throws Exception {
		DataPlottingEnum dataPlottingEnum = DataPlottingEnum.ALL;
		if (newSubType.getId().equals("EndPoints") == true){
			dataPlottingEnum = DataPlottingEnum.EDGES;
		}
		else if (newSubType.getId().equals("NoPoints") == true){
			dataPlottingEnum = DataPlottingEnum.NONE;
		}
		localElement.getElement(LocalUnifiedVisualization.TYPE).setPropertyValue("DataPoints", dataPlottingEnum.getLiteral());
	}

	@Override
	public LocalElement createNativeVisualization(LocalElement parent, String name) throws Exception {
		LocalElement visualization = parent.createLocalElement(BEViewsElementNames.AREA_CHART_VISUALIZATION);
		visualization.setName(name);
		return visualization;
	}

}