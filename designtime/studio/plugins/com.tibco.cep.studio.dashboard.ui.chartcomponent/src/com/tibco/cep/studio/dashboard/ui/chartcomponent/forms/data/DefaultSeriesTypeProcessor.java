package com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.data;

import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;

public class DefaultSeriesTypeProcessor implements SeriesTypeProcessor {

	@Override
	public boolean enableSeriesType() {
		return false;
	}

	@Override
	public String getSeriesType(LocalElement localElement) throws Exception {
		return "";
	}

	@Override
	public String[] getSeriesTypes() throws Exception {
		return new String[0];
	}

	@Override
	public void setSeriesType(LocalElement localElement, String seriesType) throws Exception {
		//do nothing
	}

}
