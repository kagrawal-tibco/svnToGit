package com.tibco.cep.studio.dashboard.ui.chartcomponent.types;

import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalChartComponent;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public abstract class BaseChartComponentTypeProcessor extends BaseTypeProcessor {

	@Override
	protected LocalElement createNativeComponent(LocalElement parent, String name) throws Exception {
		return new LocalChartComponent(parent,name);
	}

	@Override
	public LocalElement createNativeSeriesConfig(LocalElement parent, String name) throws Exception {
		LocalElement seriesConfig = parent.createLocalElement(BEViewsElementNames.CHART_SERIES_CONFIG);
		seriesConfig.setName(name);
		return seriesConfig;
	}

}
