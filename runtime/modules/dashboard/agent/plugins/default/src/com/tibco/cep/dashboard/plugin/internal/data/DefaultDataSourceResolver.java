/**
 *
 */
package com.tibco.cep.dashboard.plugin.internal.data;

import com.tibco.cep.dashboard.plugin.internal.DefaultPlugIn;
import com.tibco.cep.dashboard.psvr.common.query.QueryParams;
import com.tibco.cep.dashboard.psvr.data.DataException;
import com.tibco.cep.dashboard.psvr.data.DataSourceHandlerKey;
import com.tibco.cep.dashboard.psvr.data.IDataSourceResolver;
import com.tibco.cep.dashboard.psvr.data.Threshold;
import com.tibco.cep.dashboard.psvr.mal.model.MALSeriesConfig;
import com.tibco.cep.dashboard.psvr.plugin.ResolverType;
import com.tibco.cep.dashboard.psvr.plugin.SimpleResolverImpl;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * @author anpatil
 *
 */
public class DefaultDataSourceResolver extends SimpleResolverImpl implements IDataSourceResolver {

	public DefaultDataSourceResolver() {
		super(DefaultPlugIn.PLUGIN_ID,ResolverType.DATASOURCE_HANDLER,DefaultDataSourceHandler.class);
	}

	public String getQuery(MALSeriesConfig seriesConfig) throws DataException {
		return seriesConfig.getId();
	}

	public QueryParams getQueryParams(MALSeriesConfig seriesConfig) {
		return QueryParams.NO_QUERY_PARAMS;
	}

	public Threshold getThreshold(MALSeriesConfig seriesConfig) {
		return Threshold.EMPTY_THRESHOLD;
	}

	@Override
	public DataSourceHandlerKey getKey(Logger logger, MALSeriesConfig seriesConfig, PresentationContext pCtx) throws DataException {
		String query = getQuery(seriesConfig);
		QueryParams queryParams = getQueryParams(seriesConfig);
		Threshold threshold = getThreshold(seriesConfig);
		return new DataSourceHandlerKey(query,queryParams,threshold);
	}

}