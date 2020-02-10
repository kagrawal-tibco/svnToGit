package com.tibco.cep.studio.dashboard.core.insight.model.configs;

import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class LocalRangePlotChartSeriesConfig extends LocalChartSeriesConfig {

	public LocalRangePlotChartSeriesConfig() {
		super();
	}

	public LocalRangePlotChartSeriesConfig(LocalElement parentElement, BEViewsElement mdElement) {
		super(parentElement, mdElement);
	}

	public LocalRangePlotChartSeriesConfig(LocalElement parentElement, String name) {
		super(parentElement, name);
	}

	public LocalRangePlotChartSeriesConfig(LocalElement parentElement) {
		super(parentElement);
	}

	@Override
	public String getInsightType() {
		return BEViewsElementNames.RANGE_PLOT_CHART_SERIES_CONFIG;
	}
}
