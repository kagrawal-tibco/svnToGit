package com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.data;

import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;

public interface SeriesTypeProcessor {

	public boolean enableSeriesType();

	public String[] getSeriesTypes() throws Exception;

	public String getSeriesType(LocalElement localElement) throws Exception;

	public void setSeriesType(LocalElement localElement, String seriesType) throws Exception;

}