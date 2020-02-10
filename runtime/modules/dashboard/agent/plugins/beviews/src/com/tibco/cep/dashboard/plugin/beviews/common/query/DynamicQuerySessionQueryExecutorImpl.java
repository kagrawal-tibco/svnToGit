package com.tibco.cep.dashboard.plugin.beviews.common.query;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.tibco.cep.dashboard.common.data.BuiltInTypes;
import com.tibco.cep.dashboard.common.data.DataType;
import com.tibco.cep.dashboard.common.data.FieldValue;
import com.tibco.cep.dashboard.common.data.TupleSchemaField;
import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.management.ServiceContext;
import com.tibco.cep.dashboard.plugin.beviews.BEViewsProperties;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QuerySpec;
import com.tibco.cep.dashboard.psvr.common.query.Query;
import com.tibco.cep.dashboard.psvr.common.query.QueryException;
import com.tibco.cep.dashboard.psvr.common.query.QueryParams;
import com.tibco.cep.dashboard.psvr.common.query.ResultSet;
import com.tibco.cep.dashboard.psvr.vizengine.VizEngineProperties;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.query.functions.QueryUtilFunctions;
import com.tibco.cep.query.service.QueryProperty;
import com.tibco.cep.runtime.util.SystemProperty;

public class DynamicQuerySessionQueryExecutorImpl extends BaseViewsQueryExecutorImpl {

	private boolean useLegacyDrillDownPage;

	private QueryStandardizer standardizer;

	private ServiceContext serviceContext;

	DynamicQuerySessionQueryExecutorImpl(ServiceContext serviceContext, Logger logger, ExceptionHandler exceptionHandler, Properties properties) {
		super(logger, exceptionHandler, properties);
		this.serviceContext = serviceContext;
		this.useLegacyDrillDownPage = (Boolean) VizEngineProperties.LEGACY_SEARCH_PAGE_ENABLE.getValue(properties);
		boolean enableBindings = (Boolean) BEViewsProperties.CACHE_QUERY_BINDINGS_ENABLE.getValue(properties);
		boolean usePosixRegExSyntax = Boolean.parseBoolean(properties.getProperty(SystemProperty.PROP_TUPLE_EXPLICIT.getPropertyName(), "false"));
		this.standardizer = new QueryStandardizer(enableBindings, usePosixRegExSyntax);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public int countQuery(Query query) throws QueryException {
		//log the original query
		logger.log(Level.INFO, "Executing %1s with binding params as %2s for count...",query.getQuery(), query.getParameters());
		//standardize the query
		ViewsQuery viewsQuery = standardizer.standardize((ViewsQuery) query);
		//are we dealing with a group by query
		QuerySpec querySpec = viewsQuery.getQuerySpec();
		if (querySpec != null && querySpec.getGroupByFields().isEmpty() == false) {
			// we are dealing with a group by query, count is not supported with group by queries
			throw new QueryException("count is not supported for group by queries");
		}
		try {
			//create a count query based on the original query
			String actualQuery = standardizer.createCountQuery(viewsQuery);
			//log the actual query we are firing along with the binding params
			logger.log(Level.INFO, "Executing %1s with binding params as %2s...",actualQuery,viewsQuery.getParameters());
			List results = (List) QueryUtilFunctions.executeInQuerySession(QueryProperty.NAME_DYNAMIC_QUERY_SESSION.getPropName(), actualQuery, convertParameters(viewsQuery.getParameters()), false, -1);
			if (results.isEmpty() == false) {
				return (Integer) results.get(0);
			}
			//we have no results , return 0
			return 0;
		} catch (CloneNotSupportedException e) {
			throw new QueryException(e);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ResultSet executeQuery(Query query) throws QueryException {
		//log the original query
		logger.log(Level.INFO, "Executing %1s with binding params as %2s...",query.getQuery(), query.getParameters());
		ViewsQuery origViewsQuery = (ViewsQuery) query;
		//standardize the query
		ViewsQuery viewsQuery = standardizer.standardize(origViewsQuery);
		//log the actual query we are firing along with the binding params
		logger.log(Level.INFO, "Executing %1s with binding params as %2s...",viewsQuery.getQuery(),viewsQuery.getParameters());
		List results = (List) QueryUtilFunctions.executeInQuerySession(QueryProperty.NAME_DYNAMIC_QUERY_SESSION.getPropName(), viewsQuery.getQuery(), convertParameters(viewsQuery.getParameters()), false, -1);
		ResultSet resultset = null;
		if (origViewsQuery.getQuerySpec() != null && origViewsQuery.getQuerySpec().getGroupByFields().isEmpty() == false) {
			//we are dealing with a group by query
			String groupByFieldName = origViewsQuery.getQuerySpec().getGroupByFields().get(0);
			TupleSchemaField groupByField = origViewsQuery.getQuerySpec().getSchema().getFieldByName(groupByFieldName);
			//extract data as map
			Map<FieldValue, Integer> parsedResults = extractGroupByResults(groupByField.getFieldDataType(), results);
			//create the appropriate result set
			if (useLegacyDrillDownPage == false) {
				resultset = new NextGenGroupByResultSetImpl(this, viewsQuery.getTypeID(), groupByFieldName, parsedResults);
			} else {
				resultset = new GroupByResultSetImpl(this, viewsQuery.getTypeID(), groupByFieldName, BackingStoreQueryExecutorImpl.GROUPBY_CNT_FLD_ID, parsedResults);
			}
		} else {
			resultset = new ConceptListResultSet(serviceContext, this, results);
		}
		//register the result set
		registerResultSet(resultset);
		return resultset;
	}

	private Map<String,Object> convertParameters(QueryParams parameters) {
		if (parameters == null || parameters.equals(QueryParams.NO_QUERY_PARAMS) == true) {
			return null;
		}
		Map<String,Object> params = new HashMap<String, Object>();
		List<String> parameterNames = parameters.getParameterNames();
		for (String parameterName : parameterNames) {
			DataType dataType = parameters.getParameterType(parameterName);
			Object value = dataType.valueOf(parameters.getParameter(parameterName));
			if (dataType.equals(BuiltInTypes.DATETIME) == true) {
				//datetime is expected as a calendar
				Calendar calendar = convertToCalendar(value);
				value = calendar;
			}
			params.put(parameterName, value);
		}
		return params;
	}

	private Calendar convertToCalendar(Object value) {
		Calendar calendar = Calendar.getInstance();
		long msecs = 0;
		if (value instanceof Long) {
			msecs = (Long) value;
		} else {
			msecs = ((Date) value).getTime();
		}
		calendar.setTimeInMillis(msecs);
		return calendar;
	}

	@SuppressWarnings("rawtypes")
	private Map<FieldValue, Integer> extractGroupByResults(DataType dataType, List results) {
		Map<FieldValue, Integer> map = new HashMap<FieldValue, Integer>();
		for (Object result : results) {
			Object[] actualResult = (Object[]) result;
			Object rawGroupByValue = actualResult[0];
			FieldValue groupByValue = null;
			if (rawGroupByValue == null) {
				groupByValue = new FieldValue(dataType, true);
			} else if (rawGroupByValue instanceof Calendar) {
				groupByValue = new FieldValue(dataType, ((Calendar) rawGroupByValue).getTime());
			}
			else {
				groupByValue = new FieldValue(dataType, (Comparable<?>) rawGroupByValue);
			}
			Integer count = (Integer) actualResult[1];
			map.put(groupByValue, count);
		}
		return map;
	}

}