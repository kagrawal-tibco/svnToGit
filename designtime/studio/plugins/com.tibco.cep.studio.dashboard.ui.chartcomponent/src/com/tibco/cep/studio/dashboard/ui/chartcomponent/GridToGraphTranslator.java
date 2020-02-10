package com.tibco.cep.studio.dashboard.ui.chartcomponent;

import java.util.List;

import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.model.LocalUnifiedComponent;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.model.LocalUnifiedSeriesConfig;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.model.LocalUnifiedVisualization;

public class GridToGraphTranslator implements Translator {

	@Override
	public void translate(LocalUnifiedComponent component) throws Exception {
		//component level properties translation
		LocalUnifiedVisualization visualization = (LocalUnifiedVisualization) component.getElement(LocalUnifiedVisualization.TYPE);
		//category axis properties 
		transferValue(component, "CategoryAxisHeaderName", visualization, "CategoryColumnHeaderName");
		transferValue(component, "CategoryAxisHeaderFontSize", visualization, "CategoryColumnHeaderFontSize");
		transferValue(component, "CategoryAxisHeaderFontStyle", visualization, "CategoryColumnHeaderFontStyle");
		transferValue(component, "CategoryAxisLabelFontSize", visualization, "CategoryColumnLabelFontSize");
		transferValue(component, "CategoryAxisLabelFontStyle", visualization, "CategoryColumnLabelFontStyle");
		transferValue(component, "CategoryAxisLabelSortOrder", visualization, "CategoryColumnLabelSortOrder");
		//value axis properties 
		//we don't set the ValueAxisHeaderName
		transferValue(component, "ValueAxisLabelFontSize", visualization, "ValueColumnHeaderFontSize");
		transferValue(component, "ValueAxisLabelFontStyle", visualization, "ValueColumnHeaderFontStyle");
		//we don't set the ValueAxisScale
		//we don't set ValueAxisScale
		//visualization properties stays as there are across translations 
		//series level properties translation 
		List<LocalElement> seriesConfigs = visualization.getChildren(LocalUnifiedSeriesConfig.TYPE);
		for (LocalElement seriesConfig : seriesConfigs) {
			//category field properties 
			transferValue(seriesConfig, "CategoryAxisField", seriesConfig, "CategoryColumnField");
			transferValue(seriesConfig, "CategoryAxisFieldDisplayFormat", seriesConfig, "CategoryColumnFieldDisplayFormat");
			//value field properties 
			boolean hasTextColumn = !seriesConfig.getPropertyValue("TextValueColumnField").equals(((SynProperty)seriesConfig.getProperty("TextValueColumnField")).getDefault());
			boolean hasIndicatorColumn = !seriesConfig.getPropertyValue("IndicatorValueColumnField").equals(((SynProperty)seriesConfig.getProperty("IndicatorValueColumnField")).getDefault());
			if (hasTextColumn == false && hasIndicatorColumn == false){
				throw new IllegalArgumentException(component+"/"+visualization+"/"+seriesConfig+" has neither text column nor indicator column");
			}
			if (hasTextColumn == true){
				//transfer text column properties to chart properties  
				transferValue(seriesConfig, "ValueAxisField", seriesConfig, "TextValueColumnField");
				transferValue(seriesConfig, "ShowDataValue", seriesConfig, "TextShowDataValue");
				transferValue(seriesConfig, "ValueAxisFieldDisplayFormat", seriesConfig, "TextValueColumnFieldDisplayFormat");
				transferValue(seriesConfig, "ValueLabelFontSize", seriesConfig, "TextValueColumnFieldDisplayFontSize");
				transferValue(seriesConfig, "ValueLabelFontStyle", seriesConfig, "TextValueColumnFieldDisplayFontStyle");
				transferValue(seriesConfig, "ValueAxisFieldTooltipFormat", seriesConfig, "TextValueColumnFieldTooltipFormat");
			}
			else {
				//transfer indicator column properties to chart properties 
				transferValue(seriesConfig, "ValueAxisField", seriesConfig, "IndicatorValueColumnField");
				transferValue(seriesConfig, "ShowDataValue", seriesConfig, "IndicatorShowDataValue");
				transferValue(seriesConfig, "ValueAxisFieldDisplayFormat", seriesConfig, "IndicatorValueColumnFieldDisplayFormat");
				transferValue(seriesConfig, "ValueLabelFontSize", seriesConfig, "IndicatorValueColumnFieldDisplayFontSize");
				transferValue(seriesConfig, "ValueLabelFontStyle", seriesConfig, "IndicatorValueColumnFieldDisplayFontStyle");
				transferValue(seriesConfig, "ValueAxisFieldTooltipFormat", seriesConfig, "IndicatorValueColumnFieldTooltipFormat");
			}
		}
	}
	
	private void transferValue(LocalElement receivingElement, String receivingPropertyName, LocalElement originalElement, String originalPropertyName) throws Exception {
		SynProperty property = (SynProperty) originalElement.getProperty(originalPropertyName);
		if (property.isAlreadySet() == true) {
			String originalValue = originalElement.getPropertyValue(originalPropertyName);
			receivingElement.setPropertyValue(receivingPropertyName, originalValue);
		}		
	}

}
