package com.tibco.cep.dashboard.plugin.beviews.common.query;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.tibco.be.util.GUIDGenerator;
import com.tibco.cep.dashboard.common.data.BuiltInTypes;
import com.tibco.cep.dashboard.common.data.DataType;
import com.tibco.cep.dashboard.common.data.FieldValue;
import com.tibco.cep.dashboard.common.data.TupleSchema;
import com.tibco.cep.dashboard.common.data.TupleSchemaField;
import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.plugin.beviews.BEViewsProperties;
import com.tibco.cep.dashboard.plugin.beviews.data.TupleSchemaFactory;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QuerySpec;
import com.tibco.cep.dashboard.psvr.common.query.Query;
import com.tibco.cep.dashboard.psvr.common.query.QueryException;
import com.tibco.cep.dashboard.psvr.common.query.QueryParams;
import com.tibco.cep.dashboard.psvr.common.query.ResultSet;
import com.tibco.cep.dashboard.psvr.vizengine.VizEngineProperties;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.query.api.QueryPolicy;
import com.tibco.cep.query.api.QueryResultSet;
import com.tibco.cep.query.api.QueryStatement;
import com.tibco.cep.query.api.impl.local.PlanGenerator;
import com.tibco.cep.query.api.impl.local.QueryConnectionImpl;
import com.tibco.cep.query.functions.ConnectionCache;
import com.tibco.cep.query.service.impl.QueryRuleSessionImpl;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.runtime.util.SystemProperty;

public class QueryAgentQueryExecutorImpl extends BaseViewsQueryExecutorImpl {

	private QueryRuleSessionImpl querySession;

	private boolean useLegacyDrillDownPage;

	private QueryStandardizer standardizer;

	QueryAgentQueryExecutorImpl(QueryRuleSessionImpl querySession, Logger logger, ExceptionHandler exceptionHandler, Properties properties) {
		super(logger, exceptionHandler, properties);
		this.useLegacyDrillDownPage = (Boolean) VizEngineProperties.LEGACY_SEARCH_PAGE_ENABLE.getValue(properties);
		this.querySession = querySession;
		boolean enableBindings = (Boolean) BEViewsProperties.CACHE_QUERY_BINDINGS_ENABLE.getValue(properties);
		boolean usePosixRegExSyntax = Boolean.parseBoolean(properties.getProperty(SystemProperty.PROP_TUPLE_EXPLICIT.getPropertyName(), "false"));
		this.standardizer = new QueryStandardizer(enableBindings, usePosixRegExSyntax);
	}

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
		QueryStatement statement = null;
		QueryResultSet resultSet = null;
		com.tibco.cep.query.service.Query queryObj = null;
		//store the current rule session
		RuleSession oldSession = RuleSessionManager.getCurrentRuleSession();
		try {
			//switch the rule session to the query session
			RuleSessionManager.currentRuleSessions.set(querySession);
			//create a count query based on the original query
			String actualQuery = standardizer.createCountQuery(viewsQuery);
			//log the actual query we are firing along with the binding params
			logger.log(Level.INFO, "Executing %1s with binding params as %2s...",actualQuery,viewsQuery.getParameters());
			//create the query object
			queryObj = querySession.createQuery(GUIDGenerator.getGUID(), actualQuery);
			//get the query connection
			//hate it when API is available only @ impl level
			QueryConnectionImpl connection = (QueryConnectionImpl) ConnectionCache.getInstance().get();
			//create a prepare statement
			statement = connection.prepareStatement(queryObj.getPlanGenerator());
			//bind all the parameters
			List<String> parameterNames = viewsQuery.getParameters().getParameterNames();
			for (String parameterName : parameterNames) {
				DataType dataType = viewsQuery.getParameters().getParameterType(parameterName);
				Object value = dataType.valueOf(viewsQuery.getParameters().getParameter(parameterName));
				if (dataType.equals(BuiltInTypes.DATETIME) == true) {
					//datetime is expected as a calendar
					Calendar calendar = convertToCalendar(value);
					value = calendar;
				}
				statement.setObject(parameterName, value);
			}
			//execute the query
			resultSet = statement.executeQuery(new QueryPolicy.Snapshot());
			if (resultSet.next() == true) {
				//extract the count
				return (Integer) resultSet.getObject(0);
			}
			//we have no results , return 0
			return 0;
		} catch (com.tibco.cep.query.api.QueryException e) {
			throw new QueryException(e);
		} catch (CloneNotSupportedException e) {
			throw new QueryException(e);
		} finally {
			//reset the original rule session
			RuleSessionManager.currentRuleSessions.set(oldSession);
			//close the resultset
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (Exception ex) {
					exceptionHandler.handleException("could not close resultset for " + viewsQuery.getQuery(), ex, Level.WARN);
				}
			}
			//close the statement
			if (statement != null) {
				statement.close();
			}
			//close the query object
			if (queryObj != null) {
				try {
					queryObj.close();
				} catch (com.tibco.cep.query.api.QueryException ex) {
					exceptionHandler.handleException("could not close query for " + viewsQuery.getQuery(), ex, Level.WARN);
				}
			}
		}
	}

	@Override
	public ResultSet executeQuery(Query query) throws QueryException {
		//log the original query
		logger.log(Level.INFO, "Executing %1s with binding params as %2s...",query.getQuery(), query.getParameters());
		ViewsQuery origViewsQuery = (ViewsQuery) query;
		//standardize the query
		ViewsQuery viewsQuery = standardizer.standardize(origViewsQuery);
		//store the current rule session
		RuleSession oldSession = RuleSessionManager.getCurrentRuleSession();
		try {
			//switch the rule session to the query session
			RuleSessionManager.currentRuleSessions.set(querySession);
			//log the actual query we are firing along with the binding params
			logger.log(Level.INFO, "Executing %1s with binding params as %2s...",viewsQuery.getQuery(),viewsQuery.getParameters());
			//create the query object
			com.tibco.cep.query.service.Query queryObj = querySession.createQuery(GUIDGenerator.getGUID(), viewsQuery.getQuery());
			//get the query connection
			//hate it when API are available only @ impl level
			QueryConnectionImpl connection = (QueryConnectionImpl) ConnectionCache.getInstance().get();
			PlanGenerator planGenerator = queryObj.getPlanGenerator();
			//create a prepare statement
			QueryStatement queryStatement = connection.prepareStatement(planGenerator);
			//bind all the parameters
			QueryParams queryParameters = viewsQuery.getParameters();
			List<String> parameterNames = queryParameters.getParameterNames();
			for (String parameterName : parameterNames) {
				DataType dataType = queryParameters.getParameterType(parameterName);
				Object value = dataType.valueOf(queryParameters.getParameter(parameterName));
				if (dataType.equals(BuiltInTypes.DATETIME) == true) {
					//datetime is expected as a calendar
					Calendar calendar = convertToCalendar(value);
					value = calendar;
				}
				queryStatement.setObject(parameterName, value);
			}
			ResultSet resultset = null;
			//execute the query
			QueryResultSet queryResultSet = queryStatement.executeQuery(new QueryPolicy.Snapshot());
			if (origViewsQuery.getQuerySpec() != null && origViewsQuery.getQuerySpec().getGroupByFields().isEmpty() == false) {
				//we are dealing with a group by query
				String groupByFieldName = origViewsQuery.getQuerySpec().getGroupByFields().get(0);
				TupleSchemaField groupByField = origViewsQuery.getQuerySpec().getSchema().getFieldByName(groupByFieldName);
				//extract data as map
				Map<FieldValue, Integer> results = extractGroupByResults(groupByField, viewsQuery.getQuery(), queryObj, queryResultSet);
				//create the appropriate result set
				if (useLegacyDrillDownPage == false) {
					resultset = new NextGenGroupByResultSetImpl(this, viewsQuery.getTypeID(), groupByFieldName, results);
				} else {
					resultset = new GroupByResultSetImpl(this, viewsQuery.getTypeID(), groupByFieldName, BackingStoreQueryExecutorImpl.GROUPBY_CNT_FLD_ID, results);
				}
			} else {
				TupleSchema schema = TupleSchemaFactory.getInstance().getTupleSchema(viewsQuery.getTypeID());
				String alias = viewsQuery.getQuerySpec() != null ? viewsQuery.getQuerySpec().getAlias() : schema.getSchemaSource().getTypeName();
				resultset = new QueryAgentEntityResultSet(this, schema, queryObj, queryResultSet);
			}
			//register the result set
			registerResultSet(resultset);
			return resultset;
		} catch (com.tibco.cep.query.api.QueryException e) {
			throw new QueryException(e);
		} finally {
			//reset the original rule session
			RuleSessionManager.currentRuleSessions.set(oldSession);
		}
	}

	private Map<FieldValue, Integer> extractGroupByResults(TupleSchemaField groupByField, String actualQuery, com.tibco.cep.query.service.Query query, QueryResultSet resultSet) {
		DataType dataType = groupByField.getFieldDataType();
		try {
			Map<FieldValue, Integer> map = new HashMap<FieldValue, Integer>();
			while (resultSet.next()) {
				Object rawGroupByValue = resultSet.getObject(0);
				FieldValue groupByValue = null;
				if (rawGroupByValue == null) {
					groupByValue = new FieldValue(dataType, true);
				} else if (rawGroupByValue instanceof Calendar) {
					groupByValue = new FieldValue(dataType, ((Calendar) rawGroupByValue).getTime());
				}
				else {
					groupByValue = new FieldValue(dataType, (Comparable<?>) rawGroupByValue);
				}
				Integer count = (Integer) resultSet.getObject(1);
				map.put(groupByValue, count);
			}
			return map;
		} finally {
			//close the result set
			try {
				resultSet.close();
			} catch (Exception ex) {
				exceptionHandler.handleException("could not close resultset for " + actualQuery, ex, Level.WARN);
			}
			//close the statement
			resultSet.getStatement().close();
			//close the query
			try {
				query.close();
			} catch (com.tibco.cep.query.api.QueryException ex) {
				exceptionHandler.handleException("could not close query for " + actualQuery, ex, Level.WARN);
			}
		}
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

}