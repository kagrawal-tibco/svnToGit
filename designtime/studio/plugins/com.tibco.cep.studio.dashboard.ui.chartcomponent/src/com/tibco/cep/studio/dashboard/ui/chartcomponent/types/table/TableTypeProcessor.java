package com.tibco.cep.studio.dashboard.ui.chartcomponent.types.table;

import java.util.HashSet;
import java.util.List;

import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalTextComponent;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.model.LocalUnifiedComponent;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.model.LocalUnifiedSeriesConfig;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.model.LocalUnifiedVisualization;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.types.BaseTypeProcessor;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.types.ChartSubType;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class TableTypeProcessor extends BaseTypeProcessor {

	@Override
	public boolean isAcceptable(LocalElement localElement) throws Exception {
		HashSet<String> types = new HashSet<String>(getVizualizationTypes(localElement));
		if (types.size() == 1 && types.contains(BEViewsElementNames.TEXT_VISUALIZATION) == true){
			return true;
		}
		return false;
	}

	@Override
	public String[] getSubTypes(LocalElement localElement) throws Exception {
		SynProperty subTypeProperty = (SynProperty) localElement.getProperty("subType");
		if (subTypeProperty.isAlreadySet() == true) {
			String value = localElement.getPropertyValue("subType");
			if ("MultiMeasure".equals(value) == true) {
				return new String[]{"MultiMeasure"};
			}
		}
		return new String[]{"SingleMeasure"};
	}

	@Override
	public void prepareForEditing(LocalElement localElement) throws Exception {
		new TableColumnSizer(localElement).updateWidths();
	}

	@Override
	public void subTypeChanged(LocalElement localElement, ChartSubType oldSubType, ChartSubType newSubType) throws Exception {
		new TableColumnSizer(localElement).updateWidths();
	}

	@Override
	public void seriesAdded(LocalElement localElement, LocalElement series) throws Exception {
		prepareForEditing(localElement);
	}

	@Override
	public void seriesRemoved(LocalElement localElement, LocalElement series) throws Exception {
		prepareForEditing(localElement);
	}

	@Override
	public LocalElement createNativeElement(LocalElement localElement) throws Exception {
		String subType = localElement.getPropertyValue(LocalUnifiedComponent.PROP_KEY_SUBTYPES);
		if ("SingleMeasure".equals(subType) == true) {
			//we have single measure which means one visualization with many series
			//base can handle this
			return super.createNativeElement(localElement);
		}
		else {
			//we have multi measure which means one visualization PER series
			//create the component
			LocalElement nativeComponent = createNativeComponent(localElement.getRoot(), localElement.getName());
			transferComponentLevelDetails(localElement, nativeComponent);
			//get visualization
			LocalElement visualization = localElement.getElement(LocalUnifiedVisualization.TYPE);
			//get visualizations
			List<LocalElement> seriesConfigs = visualization.getChildren(LocalUnifiedSeriesConfig.TYPE);
			for (LocalElement seriesConfig : seriesConfigs) {
				//create a new visualization
				String visualizationName = nativeComponent.getNewName(BEViewsElementNames.TEXT_VISUALIZATION,BEViewsElementNames.VISUALIZATION);
				LocalElement nativeVisualization = createNativeVisualization(nativeComponent, visualizationName);
				//transfer the properties of the visualization
				transferProperties(visualization, nativeVisualization);
				//reset the name , since transferProperties will set the name to the one from visualization which is "visualization_"
				nativeVisualization.setName(visualizationName);
				//create a new series config
				LocalElement nativeSeriesConfig = createNativeSeriesConfig(nativeVisualization, seriesConfig.getName());
				transferProperties(seriesConfig, nativeSeriesConfig);
				//transfer action rule
				LocalElement actionRule = seriesConfig.getElement(BEViewsElementNames.ACTION_RULE);
				nativeSeriesConfig.addElement(BEViewsElementNames.ACTION_RULE, (LocalElement) actionRule.clone());
			}
			return nativeComponent;
		}
	}

	@Override
	protected LocalElement createNativeComponent(LocalElement parent, String name) throws Exception {
		return new LocalTextComponent(parent,name);
	}

	@Override
	protected void transferComponentLevelDetails(LocalElement localElement, LocalElement nativeComponent) throws Exception {
		super.transferComponentLevelDetails(localElement, nativeComponent);
		//also transfer LocalUnifiedComponent.PROP_KEY_SUBTYPES[0] to subtype
		nativeComponent.setPropertyValue("subType", localElement.getPropertyValue(LocalUnifiedComponent.PROP_KEY_SUBTYPES));
	}

	@Override
	public LocalElement createNativeVisualization(LocalElement parent, String name) throws Exception {
		LocalElement visualization = parent.createLocalElement(BEViewsElementNames.TEXT_VISUALIZATION);
		visualization.setName(name);
		return visualization;
	}

	@Override
	public LocalElement createNativeSeriesConfig(LocalElement parent, String name) throws Exception {
		LocalElement seriesConfig = parent.createLocalElement(BEViewsElementNames.TEXT_SERIES_CONFIG);
		seriesConfig.setName(name);
		return seriesConfig;
	}

}