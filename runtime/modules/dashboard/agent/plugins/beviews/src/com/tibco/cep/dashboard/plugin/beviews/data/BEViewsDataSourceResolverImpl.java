/**
 *
 */
package com.tibco.cep.dashboard.plugin.beviews.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.tibco.cep.dashboard.common.data.BuiltInTypes;
import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.management.MessageGenerator;
import com.tibco.cep.dashboard.plugin.beviews.BEViewsPlugIn;
import com.tibco.cep.dashboard.plugin.beviews.BEViewsProperties;
import com.tibco.cep.dashboard.plugin.beviews.mal.MALSourceElementCache;
import com.tibco.cep.dashboard.psvr.common.query.QueryParams;
import com.tibco.cep.dashboard.psvr.data.DataException;
import com.tibco.cep.dashboard.psvr.data.DataSourceHandlerKey;
import com.tibco.cep.dashboard.psvr.data.IDataSourceResolver;
import com.tibco.cep.dashboard.psvr.data.Threshold;
import com.tibco.cep.dashboard.psvr.mal.URIHelper;
import com.tibco.cep.dashboard.psvr.mal.model.MALActionRule;
import com.tibco.cep.dashboard.psvr.mal.model.MALDataSource;
import com.tibco.cep.dashboard.psvr.mal.model.MALElement;
import com.tibco.cep.dashboard.psvr.mal.model.MALQueryParam;
import com.tibco.cep.dashboard.psvr.mal.model.MALSeriesConfig;
import com.tibco.cep.dashboard.psvr.plugin.AbstractHandler;
import com.tibco.cep.dashboard.psvr.plugin.PluginException;
import com.tibco.cep.dashboard.psvr.plugin.PluginFinder;
import com.tibco.cep.dashboard.psvr.plugin.ResolverType;
import com.tibco.cep.dashboard.psvr.plugin.SimpleResolverImpl;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * @author anpatil
 *
 */
public class BEViewsDataSourceResolverImpl extends SimpleResolverImpl implements IDataSourceResolver {

	boolean demoModeOn;

	private Properties properties;

	private ArrayList<Class<? extends AbstractHandler>> nullQueryParamsHandler;

	public BEViewsDataSourceResolverImpl() {
		super(BEViewsPlugIn.PLUGIN_ID, ResolverType.DATASOURCE_HANDLER, QueryBasedDataSourceHandler.class);
		nullQueryParamsHandler = new ArrayList<Class<? extends AbstractHandler>>(1);
		nullQueryParamsHandler.add(EmptyDataSourceHandler.class);
	}

	@Override
	public void init(Logger logger, ExceptionHandler exceptionHandler, MessageGenerator messageGenerator) throws PluginException {
		super.init(logger, exceptionHandler, messageGenerator);
		properties = PluginFinder.getInstance().getPluginById(plugInID).getProperties();
		demoModeOn = (Boolean) BEViewsProperties.DEMO_MODE.getValue(properties);
	}

	@Override
	public boolean isAcceptable(MALElement element) {
		if (element instanceof MALSeriesConfig) {
			MALSeriesConfig seriesConfig = (MALSeriesConfig) element;
			MALActionRule actionRule = seriesConfig.getActionRule();
			if (actionRule != null) {
				return actionRule.getDataSource() != null;
			}
		}
		return false;
	}

	@Override
	public List<Class<? extends AbstractHandler>> resolve(MALElement element) throws PluginException {
		if (element instanceof MALSeriesConfig) {
			MALSeriesConfig seriesConfig = (MALSeriesConfig) element;
			QueryParams queryParams = getQueryParams(logger, seriesConfig);
			for (String paramName : queryParams.getParameterNames()) {
				if (queryParams.getParameter(paramName) == null) {
					return nullQueryParamsHandler;
				}
			}
		}
		return super.resolve(element);
	}

	@Override
	public DataSourceHandlerKey getKey(Logger logger, MALSeriesConfig seriesConfig, PresentationContext pCtx) throws DataException {
		String query = getQuery(logger, seriesConfig);
		QueryParams queryParams = getQueryParams(logger, seriesConfig);
		Threshold threshold = getThreshold(logger, seriesConfig);
		if (demoModeOn == true) {
			return new IdentifierDrivenDataSourceHandlerKey(URIHelper.getURI(seriesConfig), query, queryParams, threshold);
		}
		for (String paramName : queryParams.getParameterNames()) {
			if (queryParams.getParameter(paramName) == null) {
				try {
					Object resolvedReference = pCtx.resolveRef(seriesConfig.getActionRule().getDataSource().getSrcElement());
					String scopeName = MALSourceElementCache.getInstance().getMALSourceElement((Entity)resolvedReference).getScopeName();
					return new IdentifierDrivenDataSourceHandlerKey(scopeName, query, queryParams, threshold);
				} catch (Exception e) {
					throw new DataException("could not find source element used in "+URIHelper.getURI(seriesConfig.getActionRule()),e);
				}
			}
		}
		// now convert all bindings (including raw date-times) in the parameter values
		QueryParamConvertor queryParamConvertor = new QueryParamConvertor(logger, URIHelper.getURI(seriesConfig), properties, queryParams);
		queryParamConvertor.convert(pCtx);
		return new DataSourceHandlerKey(query, queryParams, threshold);
	}

	private String getQuery(Logger logger, MALSeriesConfig seriesConfig) throws DataException {
		MALActionRule actionRule = seriesConfig.getActionRule();
		if (actionRule == null) {
			throw new DataException(seriesConfig + " does not contain a action rule");
		}
		MALDataSource dataSource = actionRule.getDataSource();
		if (dataSource == null) {
			throw new DataException(seriesConfig + "/" + actionRule + " does not reference a datasource");
		}
		String query = dataSource.getQuery();

		return query;
	}

	private QueryParams getQueryParams(Logger logger, MALSeriesConfig seriesConfig) {
		MALActionRule actionRule = seriesConfig.getActionRule();
		if (actionRule == null) {
			return QueryParams.NO_QUERY_PARAMS;
		}
		MALDataSource dataSource = actionRule.getDataSource();
		if (dataSource == null) {
			return QueryParams.NO_QUERY_PARAMS;
		}
		if (dataSource.getParamsCount() == 0) {
			return QueryParams.NO_QUERY_PARAMS;
		}
		QueryParams params = new QueryParams();
		// first add all default values from data source
		MALQueryParam[] rawParams = dataSource.getParams();
		for (MALQueryParam queryParam : rawParams) {
			params.addParameter(queryParam.getName(), BuiltInTypes.resolve(queryParam.getType()), queryParam.getValue());
		}
		// now update all params with values from action rule
		rawParams = actionRule.getParams();
		for (MALQueryParam queryParam : rawParams) {
			params.addParameter(queryParam.getName(), BuiltInTypes.resolve(queryParam.getType()), queryParam.getValue());
		}
		return params;
	}

	private Threshold getThreshold(Logger logger, MALSeriesConfig seriesConfig) {
		MALActionRule actionRule = seriesConfig.getActionRule();
		if (actionRule == null) {
			return Threshold.EMPTY_THRESHOLD;
		}
		return new Threshold(actionRule.getThreshold(), actionRule.getThresholdUnit());
	}

	class IdentifierDrivenDataSourceHandlerKey extends DataSourceHandlerKey {

		private String identifier;

		public IdentifierDrivenDataSourceHandlerKey(String identifier, String query, QueryParams queryParams, Threshold threshold) {
			super(query, queryParams, threshold);
			this.identifier = identifier;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = super.hashCode();
			result = prime * result + ((identifier == null) ? 0 : identifier.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (!super.equals(obj))
				return false;
			if (getClass() != obj.getClass())
				return false;
			IdentifierDrivenDataSourceHandlerKey other = (IdentifierDrivenDataSourceHandlerKey) obj;
			if (identifier == null) {
				if (other.identifier != null)
					return false;
			} else if (!identifier.equals(other.identifier))
				return false;
			return true;
		}

		@Override
		public String toString() {
			StringBuilder buffer = new StringBuilder("DemoDataSourceHandlerKey[");
			buffer.append("identifier=");
			buffer.append(identifier);
			buffer.append(",query=");
			buffer.append(query);
			buffer.append(",params=");
			buffer.append(queryParams);
			buffer.append(",threshold=");
			buffer.append(threshold);
			buffer.append("]");
			return buffer.toString();
		};
	}
}