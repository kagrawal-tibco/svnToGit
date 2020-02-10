package com.tibco.cep.studio.dashboard.ui.chartcomponent.types.pie;

import java.util.Collection;

import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.types.BaseChartComponentTypeProcessor;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.types.ChartSubType;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class PieTypeProcessor extends BaseChartComponentTypeProcessor {
	
	@Override
	public boolean isAcceptable(LocalElement localElement) throws Exception {
		Collection<String> types = getVizualizationTypes(localElement);
		if (types.size() == 1 && types.contains(BEViewsElementNames.PIE_CHART_VISUALIZATION) == true){
			return true;
		}
		return false;
	}

	@Override
	public String[] getSubTypes(LocalElement localElement) throws Exception {
		return new String[]{ChartSubType.NONE.getId()};
	}
	
	@Override
	public LocalElement createNativeVisualization(LocalElement parent, String name) throws Exception {
		LocalElement visualization = parent.createLocalElement(BEViewsElementNames.PIE_CHART_VISUALIZATION);
		visualization.setName(name);
		return visualization;
	}

}
