package com.tibco.cep.dashboard.plugin.beviews.common.query;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.tibco.cep.dashboard.common.data.DataType;
import com.tibco.cep.dashboard.common.data.FieldValue;
import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QuerySpec;
import com.tibco.cep.dashboard.plugin.beviews.mal.EntityCache;
import com.tibco.cep.dashboard.plugin.beviews.mal.EntityUtils;
import com.tibco.cep.dashboard.psvr.common.query.Query;
import com.tibco.cep.dashboard.psvr.common.query.QueryException;
import com.tibco.cep.dashboard.psvr.common.query.ResultSet;
import com.tibco.cep.dashboard.psvr.vizengine.VizEngineProperties;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.metric.query.QueryManager;
import com.tibco.cep.runtime.model.TypeManager.TypeDescriptor;

public class BackingStoreQueryExecutorImpl extends BaseViewsQueryExecutorImpl {

	public static final String GROUPBY_CNT_FLD_ID = "count";

	QueryManager queryManager;

	private boolean useLegacyDrillDownPage;

	BackingStoreQueryExecutorImpl(Logger logger, ExceptionHandler exceptionHandler, Properties properties) throws QueryException {
		super(logger, exceptionHandler, properties);
		this.useLegacyDrillDownPage = (Boolean) VizEngineProperties.LEGACY_SEARCH_PAGE_ENABLE.getValue(properties);
		try {
			queryManager = QueryManager.getInstance();
			queryManager.init();
		} catch (Exception e) {
			throw new QueryException(e.getMessage(), e);
		}
	}

	@Override
	public int countQuery(Query query) throws QueryException {
		logger.log(Level.INFO, "Executing %1s with binding params as %2s for count...",query.getQuery(), query.getParameters());
		ViewsQuery viewsQuery = (ViewsQuery) query;
		if (viewsQuery.getQuerySpec() != null && viewsQuery.getQuerySpec().getGroupByFields().isEmpty() == false) {
			// we are dealing with a group by query
			throw new QueryException("count is not supported for group by queries");
		}
		//use the non keyword escaped version if query spec is present
		if (viewsQuery.getQuerySpec() != null) {
			BackingStoreQuerySpecConvertor convertor = new BackingStoreQuerySpecConvertor(viewsQuery.getQuerySpec());
			query = new ViewsQuery(convertor.convert(), convertor.convertCondition(), viewsQuery.getParameters(), viewsQuery.getTypeID());
		}
		//bind the query
		String actualQuery = query.bindQuery();
		//escape the query
		actualQuery = escapeQuery(actualQuery, viewsQuery.getTypeID());
		logger.log(Level.INFO, "Executing %1s for count...",actualQuery);
		try {
			return queryManager.countQuery(actualQuery);
		} catch (Exception e) {
			throw new QueryException(e.getMessage(), e);
		}
	}

	@Override
	public ResultSet executeQuery(Query query) throws QueryException {
		logger.log(Level.INFO, "Executing %1s with binding params as %2s...",query.getQuery(), query.getParameters());
		ViewsQuery viewsQuery = (ViewsQuery) query;
		if (viewsQuery.getQuerySpec() != null) {
			BackingStoreQuerySpecConvertor convertor = new BackingStoreQuerySpecConvertor(viewsQuery.getQuerySpec());
			query = new ViewsQuery(convertor.convert(), convertor.convertCondition(), viewsQuery.getParameters(), viewsQuery.getTypeID());
		}		//bind the query
		String actualQuery = query.bindQuery();
		//escape the query
		actualQuery = escapeQuery(actualQuery, viewsQuery.getTypeID());
		logger.log(Level.INFO, "Executing %1s...",actualQuery);
		QuerySpec querySpec = viewsQuery.getQuerySpec();
		if (querySpec != null && querySpec.getGroupByFields().isEmpty() == false) {
			// we are dealing with a group by query
			try {
				String typeid = querySpec.getSchema().getTypeID();
				String groupByFieldName = querySpec.getGroupByFields().get(0);
				DataType groupByFieldDataType = querySpec.getSchema().getFieldByName(groupByFieldName).getFieldDataType();
				ResultSet resultset = null;
				//PATCH sort the values coming from groupByQuery
				if (useLegacyDrillDownPage == false) {
					resultset = new NextGenGroupByResultSetImpl(this, typeid, groupByFieldName, extractGroupByResults(groupByFieldDataType, queryManager.groupByQuery(actualQuery)));
				} else {
					resultset = new GroupByResultSetImpl(this, typeid, groupByFieldName, GROUPBY_CNT_FLD_ID, extractGroupByResults(groupByFieldDataType, queryManager.groupByQuery(actualQuery)));
				}
				registerResultSet(resultset);
				return resultset;
			} catch (Exception e) {
				throw new QueryException(e.getMessage(), e);
			}
		}
		try {
			ConceptIteratorBasedResultSetImpl resultset = new ConceptIteratorBasedResultSetImpl(this, queryManager.query(actualQuery));
			registerResultSet(resultset);
			return resultset;
		} catch (Exception e) {
			throw new QueryException(e.getMessage(), e);
		}
	}

	private Map<FieldValue, Integer> extractGroupByResults(DataType dataType, Map<String, Integer> results) {
		Map<FieldValue, Integer> convertedResults = new HashMap<FieldValue, Integer>();
		for (Map.Entry<String, Integer> entry : results.entrySet()) {
			FieldValue keyAsFieldValue = null;
			if (entry.getKey() == null) {
				keyAsFieldValue = new FieldValue(dataType, true);
			}
			else {
				keyAsFieldValue = new FieldValue(dataType, dataType.valueOf(entry.getKey()));
			}
			convertedResults.put(keyAsFieldValue, entry.getValue());
		}
		return convertedResults;
	}

	private String escapeQuery(String query, String typeId) {
		if (query.endsWith(";") == true) {
			query = query.substring(0, query.length() - 1);
		}
		//INFO replacing full path of the metric in the query with the generated class name
		Entity entity = EntityCache.getInstance().getEntity(typeId);
		if (EntityUtils.isDVM(entity) == true) {
			entity = EntityUtils.getParent(entity);
		}
		TypeDescriptor typeDescriptor = EntityCache.getInstance().getTypeDescriptor(entity);
		query = query.replace(entity.getFullPath(), typeDescriptor.getImplClass().getName());
		return query;
	}

}