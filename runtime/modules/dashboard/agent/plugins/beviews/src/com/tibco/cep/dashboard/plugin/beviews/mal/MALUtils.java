package com.tibco.cep.dashboard.plugin.beviews.mal;

import com.tibco.cep.dashboard.psvr.mal.model.MALCategoryGuidelineConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALChartComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALTextComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALTextVisualization;

public class MALUtils {

	public static MALCategoryGuidelineConfig getCategoryGuideLineConfig(MALComponent component) {
		MALCategoryGuidelineConfig categoryGuideLineConfig = null;
		if ((component.getDefinitionType().equals("ChartComponent") == true) || (component.getDefinitionType().equals("TrendChartComponent") == true)) {
			categoryGuideLineConfig = ((MALChartComponent) component).getCategoryGuidelineConfig();
		} else if (component.getDefinitionType().equals("TextComponent") == true) {
			MALTextComponent textComponent = (MALTextComponent) component;
			categoryGuideLineConfig = ((MALTextVisualization) textComponent.getVisualization(0)).getCategoryGuidelineConfig();
		}
		return categoryGuideLineConfig;
	}

}
