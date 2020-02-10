package com.tibco.cep.dashboard.psvr.data;

import com.tibco.cep.dashboard.psvr.mal.model.MALSeriesConfig;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;

public interface DataCacheListener {

	public void dataSourceHandlerAdded(MALSeriesConfig seriesConfig, DataSourceHandler handler, PresentationContext pCtx);

	public void dataSourceHandlerRemoved(MALSeriesConfig seriesConfig, DataSourceHandler handler, PresentationContext pCtx);

}
