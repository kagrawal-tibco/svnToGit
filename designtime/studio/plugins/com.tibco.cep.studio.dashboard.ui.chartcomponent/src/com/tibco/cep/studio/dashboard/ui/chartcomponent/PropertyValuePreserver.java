package com.tibco.cep.studio.dashboard.ui.chartcomponent;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.tibco.cep.studio.dashboard.core.insight.model.helpers.ViewsConfigReader;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.model.LocalUnifiedComponent;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.model.LocalUnifiedSeriesConfig;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.model.LocalUnifiedVisualization;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

class PropertyValuePreserver {

	private String componentType;

	private Map<String, Map<String, String>> preservedValues;

	PropertyValuePreserver(String type) {
		this.componentType = type;
		preservedValues = new LinkedHashMap<String, Map<String, String>>();
	}

	void preserve(LocalUnifiedComponent component) throws Exception {
		preservedValues.clear();
		// component level properties
		Map<String, String> preservedProperties = getPreservedProperties(component);
		// transfer the properties
		preservePropertyValues(component, componentType, preservedProperties);
		// visualization level properties 
		LocalUnifiedVisualization visualization = (LocalUnifiedVisualization) component.getElement(LocalUnifiedVisualization.TYPE);
		String[] types = getMappedTypes(LocalUnifiedVisualization.TYPE);
		preservedProperties = getPreservedProperties(visualization);
		for (String type : types) {
			preservePropertyValues(visualization, type, preservedProperties);
		}
		// series config properties 
		List<LocalElement> seriesConfigs = visualization.getChildren(LocalUnifiedSeriesConfig.TYPE);
		for (LocalElement seriesConfig : seriesConfigs) {
			preservedProperties = getPreservedProperties(seriesConfig);
			types = getMappedTypes(LocalUnifiedSeriesConfig.TYPE);
			for (String type : types) {
				preservePropertyValues(seriesConfig, type, preservedProperties);
			}			
		}
	}
	
	void restore(LocalUnifiedComponent component) throws Exception{
		// component level properties
		Map<String, String> preservedProperties = getPreservedProperties(component);
		for (Map.Entry<String, String> entry : preservedProperties.entrySet()) {
			component.setPropertyValue(entry.getKey(), entry.getValue());
		}
		// visualization level properties 
		LocalUnifiedVisualization visualization = (LocalUnifiedVisualization) component.getElement(LocalUnifiedVisualization.TYPE);
		preservedProperties = getPreservedProperties(visualization);
		for (Map.Entry<String, String> entry : preservedProperties.entrySet()) {
			visualization.setPropertyValue(entry.getKey(), entry.getValue());
		}	
		//series level properties 
		List<LocalElement> seriesConfigs = visualization.getChildren(LocalUnifiedSeriesConfig.TYPE);
		for (LocalElement seriesConfig : seriesConfigs) {
			preservedProperties = getPreservedProperties(seriesConfig);
			for (Map.Entry<String, String> entry : preservedProperties.entrySet()) {
				seriesConfig.setPropertyValue(entry.getKey(), entry.getValue());
			}			
		}
	}
	
	//PATCH this needs to be more generic and not HARD CODED
	private String[] getMappedTypes(String type){
		if (type.equals(LocalUnifiedVisualization.TYPE) == true) {
			if (this.componentType.equals(BEViewsElementNames.CHART_COMPONENT) == true) {
				 return new String[] { BEViewsElementNames.BAR_CHART_VISUALIZATION, BEViewsElementNames.LINE_CHART_VISUALIZATION,
							BEViewsElementNames.AREA_CHART_VISUALIZATION, BEViewsElementNames.RANGE_PLOT_CHART_VISUALIZATION, BEViewsElementNames.PIE_CHART_VISUALIZATION,
							BEViewsElementNames.SCATTER_CHART_VISUALIZATION };	
			}
			else if (this.componentType.equals(BEViewsElementNames.TEXT_COMPONENT) == true) {
				return new String[] { BEViewsElementNames.TEXT_VISUALIZATION };	
			}
		}
		else if (type.equals(LocalUnifiedSeriesConfig.TYPE) == true) {
			if (this.componentType.equals(BEViewsElementNames.CHART_COMPONENT) == true) {
				 return new String[] { BEViewsElementNames.CHART_SERIES_CONFIG, BEViewsElementNames.RANGE_PLOT_CHART_SERIES_CONFIG };	
			}
			else if (this.componentType.equals(BEViewsElementNames.TEXT_COMPONENT) == true) {
				return new String[] { BEViewsElementNames.TEXT_SERIES_CONFIG };	
			}
		}
		throw new IllegalArgumentException(type);
	}

	private Map<String, String> getPreservedProperties(LocalElement localElement) {
		Map<String, String> preservedProps = preservedValues.get(localElement.getID());
		if (preservedProps == null) {
			preservedProps = new TreeMap<String, String>();
			preservedValues.put(localElement.getID(), preservedProps);
		}
		return preservedProps;
	}

	private void preservePropertyValues(LocalElement localElement, String typeToUse, Map<String, String> preservedProperties) throws Exception {
		List<SynProperty> propertyList = ViewsConfigReader.getInstance().getPropertyList(typeToUse);
		for (SynProperty synProperty : propertyList) {
			preservedProperties.put(synProperty.getName(), localElement.getPropertyValue(synProperty.getName()));
		}
	}
}
