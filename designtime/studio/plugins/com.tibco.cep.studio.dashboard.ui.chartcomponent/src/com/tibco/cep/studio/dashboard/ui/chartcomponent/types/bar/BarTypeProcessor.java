package com.tibco.cep.studio.dashboard.ui.chartcomponent.types.bar;

import java.util.Collection;

import com.tibco.cep.designtime.core.model.beviewsconfig.LineEnum;
import com.tibco.cep.designtime.core.model.beviewsconfig.OrientationEnum;
import com.tibco.cep.designtime.core.model.beviewsconfig.SeriesAnchorEnum;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.model.LocalUnifiedSeriesConfig;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.model.LocalUnifiedVisualization;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.types.BaseChartComponentTypeProcessor;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.types.ChartSubType;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class BarTypeProcessor extends BaseChartComponentTypeProcessor {
	
	@Override
	public boolean isAcceptable(LocalElement localElement) throws Exception {
		Collection<String> types = getVizualizationTypes(localElement);
		if (types.size() == 1 && types.contains(BEViewsElementNames.BAR_CHART_VISUALIZATION) == true){
			LocalElement visualization = localElement.getElement(LocalUnifiedVisualization.TYPE);
			return OrientationEnum.HORIZONTAL.getLiteral().equalsIgnoreCase(visualization.getPropertyValue("Orientation"));
		}
		return false;
	}

	@Override
	public String[] getSubTypes(LocalElement localElement) throws Exception {
		LocalElement visualization = localElement.getElement(LocalUnifiedVisualization.TYPE);
		try {
			int overlapPercentage = Integer.parseInt(visualization.getPropertyValue("OverLapPercentage"));
			if (overlapPercentage == 100){
				return new String[]{"Stacked"};	
			}
			else if (overlapPercentage == 0){
				return new String[]{"SideBySide"};	
			}
			return new String[]{"Overlapped"};
		} catch (NumberFormatException e) {
			return new String[]{"SideBySide"};
		}
	}
	
	@Override
	public void prepareForEditing(LocalElement localElement) throws Exception {
		LocalElement visualization = localElement.getElement(LocalUnifiedVisualization.TYPE);
		String existingOrientation = visualization.getPropertyValue("Orientation");
		visualization.setPropertyValue("Orientation", OrientationEnum.HORIZONTAL.getLiteral());
		//update grid style, if we are switching the orientation  
		if (OrientationEnum.VERTICAL.getLiteral().equalsIgnoreCase(existingOrientation) == true) {
			String value = localElement.getPropertyValue("GridStyle");
			if (LineEnum.VERTICAL.getLiteral().equalsIgnoreCase(value) == true) {
				localElement.setPropertyValue("GridStyle", LineEnum.HORIZONTAL.getLiteral());
			} else if (LineEnum.HORIZONTAL.getLiteral().equalsIgnoreCase(value) == true) {
				localElement.setPropertyValue("GridStyle", LineEnum.VERTICAL.getLiteral());
			}
		}
		for (LocalElement seriesConfig : visualization.getChildren(LocalUnifiedSeriesConfig.TYPE)) {
			String value = seriesConfig.getPropertyValue("Anchor");
			if (SeriesAnchorEnum.Q1.getLiteral().equals(value) == true){
				//do nothing
			}
			else if (SeriesAnchorEnum.Q4.getLiteral().equals(value) == true){
				//this is possibly a conversion from column to bar , change Q4 to Q2
				seriesConfig.setPropertyValue("Anchor", SeriesAnchorEnum.Q2.getLiteral());
			}
			else {
				//nothing is set or we have invalid value 
				seriesConfig.setPropertyValue("Anchor", SeriesAnchorEnum.Q1.getLiteral());
			}
		}
	}
	
	@Override
	public void subTypeChanged(LocalElement localElement, ChartSubType oldSubType, ChartSubType newSubType) throws Exception {
		if (newSubType == null || newSubType == ChartSubType.NONE) {
			return;
		}
		LocalElement visualization = localElement.getElement(LocalUnifiedVisualization.TYPE);
		if (newSubType.getId().equals("Stacked") == true){
			visualization.setPropertyValue("OverLapPercentage", "100");
		}
		else if (newSubType.getId().equals("Overlapped") == true){
			visualization.setPropertyValue("OverLapPercentage", "50");
		}
		else if (newSubType.getId().equals("SideBySide") == true){
			visualization.setPropertyValue("OverLapPercentage", "0");
		}		
	}

	@Override
	public LocalElement createNativeVisualization(LocalElement parent, String name) throws Exception {
		LocalElement visualization = parent.createLocalElement(BEViewsElementNames.BAR_CHART_VISUALIZATION);
		visualization.setName(name);
		visualization.setPropertyValue("Orientation", OrientationEnum.HORIZONTAL.getLiteral());
		return visualization;
	}

}