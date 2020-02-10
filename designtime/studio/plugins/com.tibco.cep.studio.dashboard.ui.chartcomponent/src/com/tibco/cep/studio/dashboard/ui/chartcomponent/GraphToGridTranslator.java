package com.tibco.cep.studio.dashboard.ui.chartcomponent;

import java.util.List;

import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.model.LocalUnifiedComponent;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.model.LocalUnifiedSeriesConfig;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.model.LocalUnifiedVisualization;

public class GraphToGridTranslator implements Translator {

	@Override
	public void translate(LocalUnifiedComponent component) throws Exception {
		// no component level properties translation
		LocalUnifiedVisualization visualization = (LocalUnifiedVisualization) component.getElement(LocalUnifiedVisualization.TYPE);
		// visualization level properties translation
		// category column properties
		transferValue(visualization, "CategoryColumnHeaderName", component, "CategoryAxisHeaderName");
		transferValue(visualization, "CategoryColumnHeaderFontSize", component, "CategoryAxisHeaderFontSize");
		transferValue(visualization, "CategoryColumnHeaderFontStyle", component, "CategoryAxisHeaderFontStyle");
		transferValue(visualization, "CategoryColumnLabelFontSize", component, "CategoryAxisLabelFontSize");
		transferValue(visualization, "CategoryColumnLabelFontStyle", component, "CategoryAxisLabelFontStyle");
		transferValue(visualization, "CategoryColumnLabelSortOrder", component, "CategoryAxisLabelSortOrder");
		// value column properties
		transferValue(visualization, "ValueColumnHeaderFontSize", component, "ValueAxisLabelFontSize");
		transferValue(visualization, "ValueColumnHeaderFontStyle", component, "ValueAxisLabelFontStyle");
		// series level properties translation
		List<LocalElement> seriesConfigs = visualization.getChildren(LocalUnifiedSeriesConfig.TYPE);
		for (LocalElement seriesConfig : seriesConfigs) {
			// category field properties
			transferValue(seriesConfig, "CategoryColumnField", seriesConfig, "CategoryAxisField");
			transferValue(seriesConfig, "CategoryColumnFieldDisplayFormat", seriesConfig, "CategoryAxisFieldDisplayFormat");
			// value field properties
			boolean hasTextColumn = !seriesConfig.isPropertyValueSameAsDefault("TextValueColumnField");
			boolean hasIndicatorColumn = !seriesConfig.isPropertyValueSameAsDefault("IndicatorValueColumnField");
			if (hasTextColumn == false && hasIndicatorColumn == false) {
				hasTextColumn = true;
			}
			if (hasTextColumn == true) {
				// text column properties
				transferValue(seriesConfig, "TextValueColumnField", seriesConfig, "ValueAxisField");
				transferValue(seriesConfig, "TextShowDataValue", seriesConfig, "ShowDataValue");
				transferValue(seriesConfig, "TextValueColumnFieldDisplayFormat", seriesConfig, "ValueAxisFieldDisplayFormat");
				transferValue(seriesConfig, "TextValueColumnFieldDisplayFontSize", seriesConfig, "ValueLabelFontSize");
				transferValue(seriesConfig, "TextValueColumnFieldDisplayFontStyle", seriesConfig, "ValueLabelFontStyle");
				transferValue(seriesConfig, "TextValueColumnFieldTooltipFormat", seriesConfig, "ValueAxisFieldTooltipFormat");
			} else {
				// indicator column properties
				transferValue(seriesConfig, "IndicatorValueColumnField", seriesConfig, "ValueAxisField");
				transferValue(seriesConfig, "IndicatorShowDataValue", seriesConfig, "ShowDataValue");
				transferValue(seriesConfig, "IndicatorValueColumnFieldDisplayFormat", seriesConfig, "ValueAxisFieldDisplayFormat");
				transferValue(seriesConfig, "IndicatorValueColumnFieldDisplayFontSize", seriesConfig, "ValueLabelFontSize");
				transferValue(seriesConfig, "IndicatorValueColumnFieldDisplayFontStyle", seriesConfig, "ValueLabelFontStyle");
				transferValue(seriesConfig, "IndicatorValueColumnFieldTooltipFormat", seriesConfig, "ValueAxisFieldTooltipFormat");
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
