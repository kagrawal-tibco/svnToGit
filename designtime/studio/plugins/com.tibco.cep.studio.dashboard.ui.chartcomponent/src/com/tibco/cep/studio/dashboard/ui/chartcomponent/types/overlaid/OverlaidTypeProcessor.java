package com.tibco.cep.studio.dashboard.ui.chartcomponent.types.overlaid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.model.LocalUnifiedSeriesConfig;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.model.LocalUnifiedVisualization;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.types.BaseChartComponentTypeProcessor;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.types.BaseTypeProcessor;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.types.ChartSubType;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.types.TypeProcessor;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.types.column.ColumnTypeProcessor;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.types.line.LineTypeProcessor;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.types.scatter.ScatterTypeProcessor;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class OverlaidTypeProcessor extends BaseChartComponentTypeProcessor {
	
	private static final Map<String,BaseTypeProcessor> PROCESSORS = new LinkedHashMap<String, BaseTypeProcessor>();
	
	static {
		PROCESSORS.put(BEViewsElementNames.BAR_CHART_VISUALIZATION,new ColumnTypeProcessor());
		PROCESSORS.put(BEViewsElementNames.LINE_CHART_VISUALIZATION,new LineTypeProcessor());
		PROCESSORS.put(BEViewsElementNames.SCATTER_CHART_VISUALIZATION,new ScatterTypeProcessor());
	}
	
	@Override
	public boolean isAcceptable(LocalElement localElement) throws Exception {
		Collection<String> types = getVizualizationTypes(localElement);
		if (types.size() == 3 && types.containsAll(PROCESSORS.keySet()) == true) {
			//PATCH there is a bug in the code due to which the orientation is not getting set properly
//			LocalElement visualization = localElement.getChildren(BEViewsElementNames.TEXT_CHART_VISUALIZATION).get(0);
//			return OrientationEnum.VERTICAL.getLiteral().equals(visualization.getPropertyValue("Orientation"));
			return true;
		}
		return false;
	}

	@Override
	public String[] getSubTypes(LocalElement localElement) throws Exception {
		List<String> subTypes = new ArrayList<String>();
		for (TypeProcessor processor : PROCESSORS.values()) {
			String[] processorsSubTypes = processor.getSubTypes(localElement);
			if (processorsSubTypes != null) {
				for (String subType : processorsSubTypes) {
					if (subType.equals(ChartSubType.NONE.getId()) == false) {
						subTypes.add(subType);
					}
				}
			}
		}
		return subTypes.toArray(new String[subTypes.size()]);
//		LocalElement visualization = localElement.getElement(LocalUnifiedVisualization.TYPE);
//		String[] subTypes = new String[2];
//		//column sub type
//		try {
//			int overlapPercentage = Integer.parseInt(visualization.getPropertyValue("OverLapPercentage"));
//			if (overlapPercentage == 100){
//				subTypes[0] = "Stacked";	
//			}
//			else if (overlapPercentage == 0){
//				subTypes[0] = "SideBySide";	
//			}
//			else {
//				subTypes[0] = "Overlapped";
//			}
//		} catch (NumberFormatException e) {
//			subTypes[0] = "Overlapped";
//		}
//		//line sub type 
//		String propertyValue = visualization.getPropertyValue("DataPoints");
//		if (propertyValue == null || propertyValue.trim().length() == 0){
//			subTypes[1] = "AllPoints";
//		}
//		else {
//			try {
//				DataPlottingEnum dataPlottingEnum = DataPlottingEnum.get(propertyValue.toLowerCase());
//				switch (dataPlottingEnum) {
//					case ALL:
//						subTypes[1] = "AllPoints";
//						break;
//					case EDGES:
//						subTypes[1] = "EndPoints";
//						break;
//					case NONE:
//						subTypes[1] = "NoPoints";
//						break;
//					default:
//						subTypes[1] = "AllPoints";
//						break;		
//				}
//			} catch (IllegalArgumentException e) {
//				subTypes[1] = "AllPoints";
//			}
//		}
//		//scatter has no sub types
//		return subTypes;
	}
	
	@Override
	public void prepareForEditing(LocalElement localElement) throws Exception {
		for (TypeProcessor processor : PROCESSORS.values()) {
			processor.prepareForEditing(localElement);
		}
		LocalElement visualization = localElement.getElement(LocalUnifiedVisualization.TYPE);
		List<LocalElement> seriesConfigs = visualization.getChildren(LocalUnifiedSeriesConfig.TYPE);
		for (LocalElement seriesConfig : seriesConfigs) {
			if (PROCESSORS.keySet().contains(seriesConfig.getPropertyValue(LocalUnifiedSeriesConfig.PROP_KEY_SERIES_TYPE)) == false) {
				seriesConfig.setPropertyValue(LocalUnifiedSeriesConfig.PROP_KEY_SERIES_TYPE, BEViewsElementNames.BAR_CHART_VISUALIZATION);
			}
		}
	}
	
	@Override
	public void subTypeChanged(LocalElement localElement, ChartSubType oldSubType, ChartSubType newSubType) throws Exception {
//		LocalElement visualization = localElement.getElement(LocalUnifiedVisualization.TYPE);
//		if (newSubType.getId().equals("EndPoints") == true){
//			visualization.setPropertyValue("DataPoints", DataPlottingEnum.EDGES.getLiteral());
//		}
//		else if (newSubType.getId().equals("NoPoints") == true){
//			visualization.setPropertyValue("DataPoints", DataPlottingEnum.NONE.getLiteral());
//		}
//		else if (newSubType.getId().equals("AllPoints") == true){
//			visualization.setPropertyValue("DataPoints", DataPlottingEnum.ALL.getLiteral());
//		}	
//		else if (newSubType.getId().equals("Stacked") == true){
//			visualization.setPropertyValue("OverLapPercentage", "100");
//		}
//		else if (newSubType.getId().equals("Overlapped") == true){
//			visualization.setPropertyValue("OverLapPercentage", "50");
//		}
//		else if (newSubType.getId().equals("SideBySide") == true){
//			visualization.setPropertyValue("OverLapPercentage", "0");
//		}
		for (TypeProcessor processor : PROCESSORS.values()) {
			processor.subTypeChanged(localElement, oldSubType, newSubType);
		}
	}

	@Override
	public LocalElement createNativeElement(LocalElement localElement) throws Exception {
		LocalElement nativeComponent = createNativeComponent(localElement.getRoot(), localElement.getName());
		transferComponentLevelDetails(localElement, nativeComponent);
		
		LocalElement localVisualization = localElement.getElement(LocalUnifiedVisualization.TYPE);
		List<LocalElement> seriesConfigs = localVisualization.getChildren(LocalUnifiedSeriesConfig.TYPE);
		
		for (String vizType : PROCESSORS.keySet()) {
			BaseTypeProcessor processor = PROCESSORS.get(vizType);
			LocalElement nativeVisualization = processor.createNativeVisualization(nativeComponent, nativeComponent.getNewName(vizType));
			transferProperties(localVisualization, nativeVisualization);
			for (LocalElement seriesConfig : seriesConfigs) {
				String seriesType = seriesConfig.getPropertyValue(LocalUnifiedSeriesConfig.PROP_KEY_SERIES_TYPE);
				if (vizType.equals(seriesType) == true){
					LocalElement nativeSeriesConfig = createNativeSeriesConfig(nativeVisualization, seriesConfig.getName());
					transferProperties(seriesConfig, nativeSeriesConfig);
					//transfer action rule 
					LocalElement actionRule = seriesConfig.getElement(BEViewsElementNames.ACTION_RULE);
					nativeSeriesConfig.addElement(BEViewsElementNames.ACTION_RULE, (LocalElement) actionRule.clone());
				}
			}
		}
//		
//		//create column , line and scatter visualizations 
//		LocalElement barVisualization = nativeComponent.createLocalElement(BEViewsElementNames.BAR_CHART_VISUALIZATION);
//		barVisualization.setName("barvisualization");
//		//set orientation to vertical 
//		barVisualization.setPropertyValue("Orientation", OrientationEnum.VERTICAL.getLiteral());
//		transferProperties(localVisualization, barVisualization);
//		
//		LocalElement lineVisualization = nativeComponent.createLocalElement(BEViewsElementNames.LINE_CHART_VISUALIZATION);
//		lineVisualization.setName("linevisualization");
//		transferProperties(localVisualization, lineVisualization);
//		
//		LocalElement scatterVisualization = nativeComponent.createLocalElement(BEViewsElementNames.SCATTER_CHART_VISUALIZATION);
//		scatterVisualization.setName("scattervisualization");
//		transferProperties(localVisualization, scatterVisualization);
//		
//		List<LocalElement> seriesConfigs = localVisualization.getChildren(LocalUnifiedSeriesConfig.TYPE);
//		for (LocalElement seriesConfig : seriesConfigs) {
//			LocalElement parentVisualization = barVisualization;
//			String seriesType = seriesConfig.getPropertyValue(LocalUnifiedSeriesConfig.PROP_KEY_SERIES_TYPE);
//			if (BEViewsElementNames.LINE_CHART_VISUALIZATION.equals(seriesType) == true){
//				parentVisualization = lineVisualization;
//			}
//			else if (BEViewsElementNames.SCATTER_CHART_VISUALIZATION.equals(seriesType) == true){
//				parentVisualization = scatterVisualization;
//			}
//			LocalElement nativeSeriesConfig = createNativeSeriesConfig(parentVisualization, seriesConfig.getName());
//			transferProperties(seriesConfig, nativeSeriesConfig);
//			//transfer action rule 
//			LocalElement actionRule = seriesConfig.getElement(BEViewsElementNames.ACTION_RULE);
//			nativeSeriesConfig.addElement(BEViewsElementNames.ACTION_RULE, (LocalElement) actionRule.clone());
//		}
		return nativeComponent;
	}

	@Override
	public LocalElement createNativeVisualization(LocalElement parent, String name) throws Exception {
		throw new UnsupportedOperationException();
	}
}
