package com.tibco.cep.studio.dashboard.ui.chartcomponent.wizard;

import java.util.List;

import com.tibco.cep.designtime.core.model.beviewsconfig.SeriesAnchorEnum;
import com.tibco.cep.designtime.core.model.beviewsconfig.ThresholdUnitEnum;
import com.tibco.cep.studio.dashboard.core.insight.model.LocalConfig;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalActionRule;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalDataSource;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalSeriesConfig;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalECoreFactory;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.model.impl.attribute.LocalAttribute;
import com.tibco.cep.studio.dashboard.core.util.DisplayValueFormatProvider;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.model.LocalUnifiedComponent;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.model.LocalUnifiedSeriesConfig;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.model.LocalUnifiedVisualization;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.types.ChartTypeRegistry;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class ChartCreator {

	public static LocalUnifiedComponent createChart(LocalECoreFactory localECoreFactory, String projectName, String folder, String namespace, String name, List<LocalDataSource> datasources) throws Exception{
		//create a bar chart component
		LocalUnifiedComponent component = new LocalUnifiedComponent(localECoreFactory,name);
		component.setFolder(folder);
		component.setNamespace(namespace);
		component.setOwnerProject(projectName);
		//component level properties
		component.setPropertyValue(LocalConfig.PROP_KEY_DISPLAY_NAME, name);
		component.setPropertyValue("RowSpan", "1");
		component.setPropertyValue("ColSpan", "1");
		component.setPropertyValue("CategoryAxisHeaderName", "Categories");
		component.setPropertyValue("ValueAxisHeaderName", "Values");
		component.setPropertyValue(LocalUnifiedComponent.PROP_KEY_TYPE, "Column");
		component.setPropertyValue(LocalUnifiedComponent.PROP_KEY_SUBTYPES, "SideBySide");

		//create a visualization
		LocalUnifiedVisualization visualization = (LocalUnifiedVisualization) component.createLocalElement(LocalUnifiedVisualization.TYPE);
		visualization.setPropertyValue(LocalUnifiedVisualization.PROP_KEY_VIZ_TYPES,BEViewsElementNames.BAR_CHART_VISUALIZATION);

		for (LocalDataSource dataSource : datasources) {
			createNewSeries(visualization, dataSource);
		}

		//prepare bar chart for editing
		ChartTypeRegistry.getInstance().get("Column").getProcessor().prepareForEditing(component);

		return component;
	}

	public static LocalElement createNewSeries(LocalElement visualization, LocalDataSource datasource) throws Exception {
		//create a series config
		LocalUnifiedSeriesConfig seriesConfig = (LocalUnifiedSeriesConfig) visualization.createLocalElement(LocalUnifiedSeriesConfig.TYPE);

		//series config level properties
		seriesConfig.setPropertyValue(LocalSeriesConfig.PROP_KEY_DISPLAY_NAME, seriesConfig.getName());
		//anchor
		seriesConfig.setPropertyValue("Anchor", SeriesAnchorEnum.Q1.getLiteral());
		//category field
		List<Object> groupByFields = datasource.getEnumerations(LocalDataSource.ENUM_GROUP_BY_FIELD);
		String categoryField = String.valueOf(groupByFields.get(0));
		seriesConfig.setPropertyValue("CategoryAxisField", categoryField);
		//category field display label
		String dataType = datasource.getFieldByName(LocalDataSource.ENUM_GROUP_BY_FIELD, categoryField).getPropertyValue(LocalAttribute.PROP_KEY_DATA_TYPE);
		String categoryDisplayFormat = DisplayValueFormatProvider.getDefaultDisplayValueFormat(dataType).getDisplayValueFormat(categoryField, "");
		seriesConfig.setPropertyValue("CategoryAxisFieldDisplayFormat", categoryDisplayFormat);
		//value field
		String valueField = String.valueOf(datasource.getEnumerations(LocalDataSource.ENUM_NUMERIC_FIELD).get(0));
		seriesConfig.setPropertyValue("ValueAxisField", valueField);
		//value field display label
		dataType = datasource.getFieldByName(LocalDataSource.ENUM_NUMERIC_FIELD, valueField).getPropertyValue(LocalAttribute.PROP_KEY_DATA_TYPE);
		String valueDisplayFormat = DisplayValueFormatProvider.getDefaultDisplayValueFormat(dataType).getDisplayValueFormat(valueField, "");
		seriesConfig.setPropertyValue("ValueAxisFieldDisplayFormat", valueDisplayFormat);
		//tooltip
		seriesConfig.setPropertyValue("ValueAxisFieldTooltipFormat", categoryDisplayFormat+"="+valueDisplayFormat);

		//create the action rule
		LocalActionRule actionRule = (LocalActionRule) seriesConfig.createLocalElement(BEViewsElementNames.ACTION_RULE);

		//set action rule properties
		actionRule.setElement(BEViewsElementNames.DATA_SOURCE, datasource);
		actionRule.setPropertyValue(LocalActionRule.PROP_KEY_THRESHOLD, "0");
		actionRule.setPropertyValue(LocalActionRule.PROP_KEY_THRESHOLD_UNIT, ThresholdUnitEnum.COUNT.getLiteral());

		//add drillable fields
		for (Object groupByField : groupByFields) {
			actionRule.addElement(LocalActionRule.ELEMENT_KEY_DRILLABLE_FIELDS, datasource.getFieldByName(LocalDataSource.ENUM_GROUP_BY_FIELD, (String) groupByField));
		}
		return seriesConfig;
	}

}