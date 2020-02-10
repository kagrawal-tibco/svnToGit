package com.tibco.cep.studio.dashboard.core.insight.model.configs;

import java.util.List;

import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class LocalChartSeriesConfig extends LocalTwoDimSeriesConfig {

	public LocalChartSeriesConfig() {
		super(BEViewsElementNames.CHART_SERIES_CONFIG);
	}

	public LocalChartSeriesConfig(LocalElement parentElement) {
		super(parentElement, BEViewsElementNames.CHART_SERIES_CONFIG);
	}

	public LocalChartSeriesConfig(LocalElement parentElement, String name) {
		super(parentElement, BEViewsElementNames.CHART_SERIES_CONFIG, name);
	}

	public LocalChartSeriesConfig(LocalElement parentElement, BEViewsElement mdElement) {
		super(parentElement, BEViewsElementNames.CHART_SERIES_CONFIG, mdElement);
	}

	@Override
	protected boolean isToBeValidated(SynProperty prop) {
		return true;
	}

	@Override
	protected String getCategoryDisplayLabelPropertyName() {
		return "CategoryAxisFieldDisplayFormat";
	}

	@Override
	protected String getCategoryFieldPropertyName() {
		return "CategoryAxisField";
	}

	@Override
	protected List<Object> getCategoryFieldEnumeration() {
		LocalDataSource dataSource = (LocalDataSource) getElement(BEViewsElementNames.ACTION_RULE).getElement(BEViewsElementNames.DATA_SOURCE);
		return dataSource.getEnumerations(LocalDataSource.ENUM_GROUP_BY_FIELD);
	}

	@Override
	protected String getValueFieldPropertyName() {
		return "ValueAxisField";
	}

	@Override
	protected String getValueDisplayLabelPropertyName() {
		return "ValueAxisFieldDisplayFormat";
	}

	@Override
	protected String getTooltipPropertyName() {
		return "ValueAxisFieldTooltipFormat";
	}

	@Override
	protected List<Object> getValueFieldEnumeration() {
		LocalDataSource dataSource = (LocalDataSource) getElement(BEViewsElementNames.ACTION_RULE).getElement(BEViewsElementNames.DATA_SOURCE);
		return dataSource.getEnumerations(LocalDataSource.ENUM_FIELD);
	}
}