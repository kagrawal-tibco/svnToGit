package com.tibco.cep.studio.dashboard.ui.chartcomponent.types.range;

import java.util.Collection;
import java.util.List;

import com.tibco.cep.designtime.core.model.beviewsconfig.LineEnum;
import com.tibco.cep.designtime.core.model.beviewsconfig.OrientationEnum;
import com.tibco.cep.designtime.core.model.beviewsconfig.RangePlotChartSeriesTypeEnum;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.model.LocalUnifiedSeriesConfig;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.model.LocalUnifiedVisualization;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.types.BaseChartComponentTypeProcessor;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.types.ChartSubType;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class HorizontalRangePlotTypeProcessor extends BaseChartComponentTypeProcessor {
	
	@Override
	public boolean isAcceptable(LocalElement localElement) throws Exception {
		Collection<String> types = getVizualizationTypes(localElement);
		if (types.size() == 1 && types.contains(BEViewsElementNames.RANGE_PLOT_CHART_VISUALIZATION) == true){
			LocalElement visualization = localElement.getElement(LocalUnifiedVisualization.TYPE);
			return OrientationEnum.HORIZONTAL.getLiteral().equalsIgnoreCase(visualization.getPropertyValue("Orientation"));
		}
		return false;
	}

	@Override
	public String[] getSubTypes(LocalElement localElement) throws Exception {
		return new String[]{ChartSubType.NONE.getId()};
	}
	
	@Override
	public void prepareForEditing(LocalElement localElement) throws Exception {
		LocalElement visualization = localElement.getElement(LocalUnifiedVisualization.TYPE);
		String existingOrientation = visualization.getPropertyValue("Orientation");
		visualization.setPropertyValue("Orientation", OrientationEnum.HORIZONTAL.getLiteral());
		//update grid style if we are switching the orientation
		if (OrientationEnum.VERTICAL.getLiteral().equalsIgnoreCase(existingOrientation) == true) {
			String value = localElement.getPropertyValue("GridStyle");
			if (LineEnum.VERTICAL.getLiteral().equalsIgnoreCase(value) == true) {
				localElement.setPropertyValue("GridStyle", LineEnum.HORIZONTAL.getLiteral());
			} else if (LineEnum.HORIZONTAL.getLiteral().equalsIgnoreCase(value) == true) {
				localElement.setPropertyValue("GridStyle", LineEnum.VERTICAL.getLiteral());
			}
		}
		List<LocalElement> seriesConfigs = visualization.getChildren(LocalUnifiedSeriesConfig.TYPE);
		if (seriesConfigs.size() == 1){
			seriesConfigs.get(0).setPropertyValue("Type", RangePlotChartSeriesTypeEnum.CURRENT.getLiteral());
		}
		else {
			int i = 0 ;
			for (LocalElement seriesConfig : seriesConfigs) {
				if (seriesConfig.isPropertyValueSameAsDefault("Type") == true) {
					seriesConfig.setPropertyValue("Type", RangePlotChartSeriesTypeEnum.values()[i % 3].getLiteral());
				}
				i++;
			}
		}
	}
	
	@Override
	public LocalElement createNativeVisualization(LocalElement parent, String name) throws Exception {
		LocalElement visualization = parent.createLocalElement(BEViewsElementNames.RANGE_PLOT_CHART_VISUALIZATION);
		visualization.setName(name);
		visualization.setPropertyValue("Orientation", OrientationEnum.HORIZONTAL.getLiteral());
		return visualization;
	}
	
	@Override
	public LocalElement createNativeSeriesConfig(LocalElement parent, String name) throws Exception {
		LocalElement seriesConfig = parent.createLocalElement(BEViewsElementNames.RANGE_PLOT_CHART_SERIES_CONFIG);
		seriesConfig.setName(name);
		return seriesConfig;
	}

}
