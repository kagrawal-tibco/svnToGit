package com.tibco.cep.dashboard.plugin.beviews.drilldown.query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tibco.cep.dashboard.common.data.FieldValue;
import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.common.data.TupleSchema;
import com.tibco.cep.dashboard.common.data.TupleSchemaField;
import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.plugin.beviews.common.query.ViewsQuery;
import com.tibco.cep.dashboard.plugin.beviews.common.query.ViewsQueryExecutorFactory;
import com.tibco.cep.dashboard.plugin.beviews.common.query.BackingStoreQueryExecutorImpl;
import com.tibco.cep.dashboard.plugin.beviews.data.TupleSchemaFactory;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.model.NextInLine;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QueryCondition;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QueryPredicate;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QuerySpec;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.utils.DrillDownConfiguration;
import com.tibco.cep.dashboard.plugin.beviews.mal.EntityCache;
import com.tibco.cep.dashboard.psvr.biz.BizSession;
import com.tibco.cep.dashboard.psvr.biz.BizSessionBindingListener;
import com.tibco.cep.dashboard.psvr.common.query.QueryException;
import com.tibco.cep.dashboard.psvr.common.query.QueryExecutor;
import com.tibco.cep.dashboard.psvr.common.query.ResultSet;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.Metric;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * @author rajesh/anand
 *
 */
public class DrilldownProvider implements BizSessionBindingListener {

	private static final String NO_TUPLES_FOUND = "";

	private static final String NULL_FIELD_VALUE = "@NULL@NULL";

	public static final synchronized DrilldownProvider getInstance(BizSession session) throws QueryException {
		DrilldownProvider instance = (DrilldownProvider) session.getAttribute(DrilldownProvider.class.getName());
		if (instance == null) {
			instance = new DrilldownProvider(session);
			session.setAttribute(DrilldownProvider.class.getName(), instance);
		}
		return instance;
	}

	@SuppressWarnings("unused")
	private BizSession session;

	private Logger logger;

	private ExceptionHandler exceptionHandler;

	private QueryExecutor queryExecutor;

	private DrilldownResultSetCache drilldownResultSetCache;

	private DrilldownProvider(BizSession session) throws QueryException {
		this.session = session;
		logger = (Logger) session.getAttribute(Logger.class.getName());
		exceptionHandler = (ExceptionHandler) session.getAttribute(ExceptionHandler.class.getName());
		queryExecutor = ViewsQueryExecutorFactory.getInstance().createImplementation();
		drilldownResultSetCache = new DrilldownResultSetCache();
	}

	/**
	 * Get the count of instances using the query and additional filters, sort if any
	 *
	 * @param query
	 *            The base query to use
	 * @param fieldFilter
	 *            The additional filters to apply to the base query
	 * @param sortField
	 *            The sort field to be applied to the base query
	 * @param sortOrder
	 *            The sort order <code>true</code> for ascending else <code>false</code>
	 * @return A count of the instances
	 * @throws QueryException
	 *             if the query execution fails
	 */
	public int getInstanceCount(QuerySpec query, Map<String, String> fieldFilters, String sortField, boolean sortOrder) throws QueryException {
		QuerySpec querySpec = null;
		int querySpecHashCode = -1;
		try {
			querySpec = (QuerySpec) query.clone();
			querySpecHashCode = querySpec.hashCode();
			applyFilterSortGroupBy(querySpec, fieldFilters, sortField, sortOrder, null);

			if (logger.isEnabledFor(Level.INFO) == true) {
				logger.log(Level.INFO, "getInstanceCount::Firing [" + querySpec + "] with queryspec hashcode as " + querySpecHashCode);
			}
			long stime = System.currentTimeMillis();
			int count = queryExecutor.countQuery(new ViewsQuery(querySpec));
			long etime = System.currentTimeMillis();

			if (logger.isEnabledFor(Level.INFO) == true) {
				logger.log(Level.INFO, "getInstanceCount::Got count as [" + count + "] for QuerySpec[hashcode=" + querySpecHashCode + "] in " + (etime - stime) + " msecs...");
			}

			if (count == 0) {
				return 0;
			}
			return count;

		} catch (Exception e) {
			if (logger.isEnabledFor(Level.INFO) == true) {
				logger.log(Level.INFO, "getInstanceCount::Execution of QuerySpec[hashcode=" + querySpecHashCode + "] failed due to " + e);
			}
			if (e instanceof QueryException) {
				throw (QueryException) e;
			}
			throw new QueryException(e);
		}
	}

	/**
	 * Get the instances using the query and additional filters, sort if any
	 *
	 * @param query
	 *            The base query to use
	 * @param fieldFilter
	 *            The additional filters to apply to the base query
	 * @param sortField
	 *            The sort field to be applied to the base query
	 * @param sortOrder
	 *            The sort order <code>true</code> for ascending else <code>false</code>
	 * @return A result set id which can be used to scroll over the results
	 * @throws QueryException
	 *             if the query execution fails
	 * @see #getNextSet(String, int)
	 * @see #getPreviousSet(String, int)
	 */
	public String getInstanceData(QuerySpec query, Map<String, String> fieldFilters, String sortField, boolean sortOrder) throws QueryException {
		ResultSet resultSet = null;
		QuerySpec querySpec = null;
		int querySpecHashCode = -1;
		try {
			querySpec = (QuerySpec) query.clone();
			querySpecHashCode = querySpec.hashCode();
			applyFilterSortGroupBy(querySpec, fieldFilters, sortField, sortOrder, null);
			if (logger.isEnabledFor(Level.INFO) == true) {
				logger.log(Level.INFO, "getInstanceData::Firing [" + querySpec + "] with queryspec hashcode as " + querySpecHashCode);
			}
			long stime = System.currentTimeMillis();
			resultSet = queryExecutor.executeQuery(new ViewsQuery(querySpec));
			long etime = System.currentTimeMillis();

			if (logger.isEnabledFor(Level.INFO) == true) {
				logger.log(Level.INFO, "getInstanceData::Execution of QuerySpec[hashcode=" + querySpecHashCode + "] took " + (etime - stime) + " msecs with result set as [" + resultSet + "]");
			}
			return drilldownResultSetCache.addResultSets(resultSet);
		} catch (Exception e) {
			if (logger.isEnabledFor(Level.INFO) == true) {
				logger.log(Level.INFO, "getInstanceData::Execution of QuerySpec[hashcode=" + querySpecHashCode + "] failed due to " + e);
			}
			if (e instanceof QueryException) {
				throw (QueryException) e;
			}
			throw new QueryException(e);
		}
	}

	/**
	 * Returns the next set of {@link Tuple} given a result set id
	 *
	 * @param resultSetId
	 *            The id of the result set to fetch the tuples from
	 * @param pageSizeCount
	 *            The number of tuples to fetch
	 * @return A {@link List} of {@link Tuple}
	 * @throws QueryException
	 *             if reading of the result fails
	 */
	public List<Tuple> getNextSet(String resultSetId, int pageSizeCount) throws QueryException {
		assertParameter(resultSetId, "resultset id");
		if (resultSetId.intern() == NO_TUPLES_FOUND) {
			return Collections.emptyList();
		}
		List<Tuple> tuples = new ArrayList<Tuple>();
		int count = 0;
		DrilldownResultset drilldownResultset = drilldownResultSetCache.getResultSet(resultSetId);
		drilldownResultset.startNext();
		long stime = System.currentTimeMillis();
		while ((pageSizeCount == -1 || count < pageSizeCount)) {
			if (!drilldownResultset.nextTuple()) {
				break;
			}
			Tuple fromTuple = drilldownResultset.getTuple();
			tuples.add(fromTuple);
			count++;
		}
		long etime = System.currentTimeMillis();
		if (logger.isEnabledFor(Level.INFO) == true) {
			logger.log(Level.INFO, "getNextSet::Iterating " + count + " times on the result set for [" + resultSetId + "] took " + (etime - stime) + " msecs..");
		}
		drilldownResultset.stopNext();
		return tuples;
	}

	/**
	 * Returns the previous set of {@link Tuple} given a result set id
	 *
	 * @param resultSetId
	 *            The id of the result set to fetch the tuples from
	 * @param pageSizeCount
	 *            The number of tuples to fetch
	 * @return A {@link List} of {@link Tuple}
	 * @throws QueryException
	 *             if reading of the result fails
	 */
	public List<Tuple> getPreviousSet(String resultSetId, int pageSizeCount) throws QueryException {
		assertParameter(resultSetId, "result id");
		if (resultSetId.intern() == NO_TUPLES_FOUND) {
			return Collections.emptyList();
		}
		List<Tuple> tuples = new ArrayList<Tuple>();
		int count = 0;
		DrilldownResultset resultSet = drilldownResultSetCache.getResultSet(resultSetId);
		// pageSizeCount = Math.min(drilldownResultset.getNextIndex() -
		// drilldownResultset.getPrevIndex() - 1, pageSizeCount);
		resultSet.startPrevious();
		long stime = System.currentTimeMillis();
		while ((pageSizeCount == -1 || count < pageSizeCount)) {
			if (!resultSet.previousTuple()) {
				break;
			}
			Tuple fromTuple = resultSet.getTuple();
			tuples.add(fromTuple);
			count++;
		}
		long etime = System.currentTimeMillis();
		if (logger.isEnabledFor(Level.INFO) == true) {
			logger.log(Level.INFO, "getPreviousSet::Iterating the result set for [" + resultSetId + "] took " + (etime - stime) + " msecs..");
		}
		// Reverse to maintain the order
		Collections.reverse(tuples);
		resultSet.stopPrevious();
		return tuples;
	}

	/**
	 * Returns a {@link List} of {@link NextInLine} for a given type id and instance id
	 *
	 * @param toTypeId
	 *            The type id of the element whose {@link NextInLine} are needed
	 * @param toInstanceId
	 *            The instance id of the element whose {@link NextInLine} are needed
	 * @param additionalParams
	 *            Any additional parameters
	 * @return
	 * @throws QueryException
	 */
	public List<NextInLine> getNextInLines(String toTypeId, String toInstanceId, Map<String, String> additionalParams) throws QueryException {
		Tuple drillFromTuple = getTuple(toTypeId, toInstanceId);
		if (drillFromTuple == null) {
			throw new QueryException("The item you selected to drilldown does not exist any more. Please re-submit the query to start the drilldown or refresh your browser.");
		}
		if (logger.isEnabledFor(Level.INFO) == true) {
			logger.log(Level.INFO, "Finding Next In Line For " + drillFromTuple);
		}
		return getTypeSpecificDrilldownProvider(toTypeId).getNextInLines(logger, drillFromTuple, additionalParams, this);
	}

	private void applyFilterSortGroupBy(QuerySpec querySpecification, Map<String, String> filterFields, String sortField, boolean sortOrder, String groupByField) {
		// Anand 10/15/04 Added call to remove all existing order by fields on a query specification
		querySpecification.removeAllOrderByFields();
		querySpecification.removeAllGroupByFields();
		querySpecification.removeAllProjectionFields();
		if (groupByField == null) {
			if (sortField != null) {
				querySpecification.addOrderByField(sortField, sortOrder);
			} else {
				// we don't have any default ordering field in BE querySpecification.addOrderByField(TIMESTAMP, false);
			}
		} else {
			querySpecification.addOrderByField(groupByField, true);
		}

		if (filterFields != null && filterFields.size() > 0) {
			TupleSchema tupleSchema = querySpecification.getSchema();
			for (String fieldName : filterFields.keySet()) {
				String fieldValue = filterFields.get(fieldName);
				FieldValue synValue = convertToFieldValue(tupleSchema.getFieldByName(fieldName), fieldValue);
				QueryCondition filterCondition = null;
				if (synValue == null || synValue.isNull()) {
					filterCondition = new QueryPredicate(tupleSchema, fieldName, QueryPredicate.IS_NULL, synValue);
				} else {
					filterCondition = new QueryPredicate(tupleSchema, fieldName, QueryPredicate.EQ, synValue);
				}
				querySpecification.addAndCondition(filterCondition);
			}
		}

		if (groupByField != null) {
			querySpecification.addGroupByField(groupByField);
			// querySpecification.addProjectionField(new QueryProjectionField(querySpecification.getSchema(), groupByField));
			// querySpecification.addProjectionField(new QueryProjectionField(querySpecification.getSchema(), GROUPBY_CNT_FLD_ID, QueryProjectionField.COUNT));
		}
	}

	private FieldValue convertToFieldValue(TupleSchemaField field, String value) {
		if (value != null && value.equalsIgnoreCase(NULL_FIELD_VALUE)) {
			return new FieldValue(field.getFieldDataType(), true);
		}
		return new FieldValue(field.getFieldDataType(), field.getFieldDataType().valueOf(value));
	}

	public Tuple getTuple(String typeId, String instanceId) throws QueryException {
		ResultSet resultSet = null;
		QuerySpec querySpec = null;
		int querySpecHashCode = -1;
		try {
			TupleSchema tupleSchema = TupleSchemaFactory.getInstance().getTupleSchema(typeId);
			querySpec = new QuerySpec(tupleSchema);
			querySpecHashCode = querySpec.hashCode();

			TupleSchemaField idField = tupleSchema.getIDField();
			if (idField == null) {
				throw new QueryException("No id field set in " + tupleSchema);
			}

			FieldValue value = new FieldValue(idField.getFieldDataType(), idField.getFieldDataType().valueOf(instanceId));
			QueryCondition instanceIdCondition = new QueryPredicate(tupleSchema, idField.getFieldName(), QueryPredicate.EQ, value);
			querySpec.setCondition(instanceIdCondition);

			if (logger.isEnabledFor(Level.INFO) == true) {
				logger.log(Level.INFO, "getTuple::Firing [" + querySpec + "] with queryspec hashcode as " + querySpecHashCode);
			}

			long stime = System.currentTimeMillis();
			resultSet = queryExecutor.executeQuery(new ViewsQuery(querySpec));
			long etime = System.currentTimeMillis();
			if (logger.isEnabledFor(Level.INFO) == true) {
				logger.log(Level.INFO, "getTuple::Execution of QuerySpec[hashcode=" + querySpecHashCode + "] took " + (etime - stime) + " msecs...");
			}

			Tuple tuple = null;
			stime = System.currentTimeMillis();
			if (resultSet.next() == true) {
				// Get the first tuple
				tuple = resultSet.getTuple();
			}
			etime = System.currentTimeMillis();
			if (logger.isEnabledFor(Level.INFO) == true) {
				logger.log(Level.INFO, "getTuple::Traversal of QuerySpec[hashcode=" + querySpecHashCode + "] result set took " + (etime - stime) + " msecs...");
			}
			return tuple;
		} catch (QueryException e) {
			if (logger.isEnabledFor(Level.INFO) == true) {
				if (querySpecHashCode == -1) {
					logger.log(Level.INFO, "getTuple::Execution of QuerySpec[toTypeId=" + typeId + ",toInstanceId=" + instanceId + "] failed due to " + e);
				} else {
					logger.log(Level.INFO, "getTuple::Execution of QuerySpec[hashcode=" + querySpecHashCode + "] failed due to " + e);
				}
			}
			throw e;
		} finally {
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (QueryException ignore) {
				}
			}
		}
	}

	public List<NextInLine> getGroupInlines(QuerySpec query, Map<String, String> additionalParams, Map<String, String> fieldFilters, String groupByField) throws QueryException {
		return fillGroupInlines(query, null, null, additionalParams, fieldFilters, groupByField);
	}

	public List<NextInLine> getGroupInlines(String toTypeId, String toInstanceId, String typeId, Map<String, String> additionalParams, Map<String, String> fieldsFilter, String groupByField) throws QueryException {
		QuerySpec drillQuerySpec = getTypeSpecificDrilldownProvider(toTypeId).getDrillDownQuery(logger, getTuple(toTypeId, toInstanceId), typeId);
		if (drillQuerySpec == null) {
			return Collections.emptyList();
		}
		return fillGroupInlines(drillQuerySpec, toTypeId, toInstanceId, additionalParams, fieldsFilter, groupByField);
	}

	private List<NextInLine> fillGroupInlines(QuerySpec query, String toTypeId, String toInstanceId, Map<String, String> additionaParams, Map<String, String> fieldsFilter, String groupByField) throws QueryException {
		ResultSet resultSet = null;
		int querySpecHashCode = query.hashCode();
		try {
			applyFilterSortGroupBy(query, fieldsFilter, null, false, groupByField);
			if (logger.isEnabledFor(Level.INFO) == true) {
				logger.log(Level.INFO, "fillGroupInlines::Firing [" + query + "] with queryspec hashcode as " + querySpecHashCode);
			}
			long stime = System.currentTimeMillis();
			resultSet = queryExecutor.executeQuery(new ViewsQuery(query));
			long etime = System.currentTimeMillis();
			if (logger.isEnabledFor(Level.INFO) == true) {
				logger.log(Level.INFO, "fillGroupInlines::Executing of QuerySpec[hashcode=" + querySpecHashCode + "] took " + (etime - stime) + " msecs...");
			}
			List<NextInLine> lstGroupLines = new ArrayList<NextInLine>();
			stime = System.currentTimeMillis();
			int nextTupleCnt = 0;
			while (resultSet.next()) {
				Tuple tuple = resultSet.getTuple();
				NextInLine nextInLine = createGroupInline(tuple, tuple.getTypeId()/* "metric@" + query.getTypeID() */, toTypeId, toInstanceId, additionaParams, groupByField);
				lstGroupLines.add(nextInLine);
				nextTupleCnt++;
			}
			etime = System.currentTimeMillis();
			if (logger.isEnabledFor(Level.INFO) == true) {
				logger.log(Level.INFO, "fillGroupInlines::Traversing " + nextTupleCnt + " times of QuerySpec[hashcode=" + querySpecHashCode + "] took " + (etime - stime) + " msecs...");
			}
			return lstGroupLines;
		} catch (QueryException e) {
			if (logger.isEnabledFor(Level.INFO) == true) {
				logger.log(Level.INFO, "fillGroupInlines::Execution of QuerySpec[hashcode=" + querySpecHashCode + "] failed due to " + e);
			}
			throw e;
		} finally {
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (QueryException ignore) {
				}
			}
		}
	}

	private NextInLine createGroupInline(Tuple tuple, String typeId, String toTypeId, String toInstanceId, Map<String, String> additionalParams, String groupByField) {
		FieldValue groupByValue = tuple.getFieldValueByName(groupByField);
		FieldValue groupByCnt = tuple.getFieldValueByID(BackingStoreQueryExecutorImpl.GROUPBY_CNT_FLD_ID);
		NextInLine nextInLine = new NextInLine(toTypeId, toInstanceId, groupByValue.toString(), typeId, (Integer) groupByCnt.getValue(), additionalParams);
		nextInLine.setGroupFieldName(groupByField);
		if (groupByValue.isNull() == true) {
			nextInLine.setGroupFieldValue(NULL_FIELD_VALUE);
		} else {
			nextInLine.setGroupFieldValue(groupByValue);
		}
		return nextInLine;
	}

	private void assertParameter(String paramValue, String paramName) {
		if (paramValue == null) {
			throw new IllegalArgumentException("invalid " + paramName + " as parameter");
		}
	}

	public int[] getDisplaySpan(String resultSetID) {
		DrilldownResultset drilldownResultset = drilldownResultSetCache.getResultSet(resultSetID);
		if (drilldownResultset == null) {
			return new int[] { 1, 0 };
		}
		return new int[] { drilldownResultset.getPrevIndex() + 2, drilldownResultset.getNextIndex() };
	}

	public String getInstanceData(String toTypeId, String toInstanceId, String fromTypeId, Map<String, String> additionalParams, Map<String, String> fieldFilters, String sortField, boolean sortOrder) throws QueryException {
		QuerySpec drillQuerySpec = getTypeSpecificDrilldownProvider(toTypeId).getDrillDownQuery(logger, getTuple(toTypeId, toInstanceId), fromTypeId);
		if (drillQuerySpec == null) {
			return NO_TUPLES_FOUND;
		}
		return getInstanceData(drillQuerySpec, fieldFilters, sortField, sortOrder);
	}

	@Override
	public void valueBound(BizSession bizSession, String name) {
		// do nothing
	}

	@Override
	public void valueUnbound(BizSession bizSession, String name) {
		if (logger.isEnabledFor(Level.DEBUG) == true) {
			logger.log(Level.DEBUG, "Shutting down drilldown provider for " + bizSession + "...");
		}
		clear();
		queryExecutor.close();
	}

	public void close(String resultSetId) throws QueryException {
		DrilldownResultset resultset = drilldownResultSetCache.resultSets.remove(resultSetId);
		if (resultset != null) {
			resultset.resultSet.close();
		}
	}

	public void clear() {
		drilldownResultSetCache.clear();
	}

	public TypeSpecificDrilldownProvider getTypeSpecificDrilldownProvider(String typeid) {
		Entity entity = EntityCache.getInstance().getEntity(typeid);
		if (entity instanceof Metric) {
			return new MetricDrilldownProvider();
		}
		if (entity instanceof Concept) {
			return new ConceptDrilldownProvider();
		}
		return new TypeSpecificDrilldownProvider() {

			@Override
			public QuerySpec getDrillDownQuery(Logger logger, Tuple tuple, String typeId) {
				throw new UnsupportedOperationException("getDrillDownQuery");
			}

			@Override
			public List<NextInLine> getNextInLines(Logger logger, Tuple tuple, Map<String, String> additionalParameters, DrilldownProvider drilldownProvider) throws QueryException {
				return Collections.emptyList();
			}

		};
	}

	class DrilldownResultSetCache {

		private Map<String, DrilldownResultset> resultSets;

		DrilldownResultSetCache() {
			this.resultSets = new HashMap<String, DrilldownResultset>();
		}

		String addResultSets(ResultSet resultSet) {
			String id = resultSet.getId();
			if (DrillDownConfiguration.isPaginationInAppendMode() == true) {
				resultSets.put(id, new DrilldownUniDiResultset(resultSet));
			} else {
				resultSets.put(id, new DrilldownBiDiResultset(resultSet));
			}
			return id;
		}

		DrilldownResultset removeResult(String resultId) {
			return resultSets.remove(resultId);
		}

		DrilldownResultset getResultSet(String resultSetId) {
			return resultSets.get(resultSetId);
		}

		void clear() {
			for (DrilldownResultset drilldownResultSet : resultSets.values()) {
				try {
					if (logger.isEnabledFor(Level.DEBUG) == true) {
						logger.log(Level.DEBUG, "Closing " + drilldownResultSet.resultSet + "...");
					}
					drilldownResultSet.resultSet.close();
				} catch (QueryException e) {
					exceptionHandler.handleException("could not close " + drilldownResultSet.resultSet, e, Level.WARN);
				}
			}
		}
	}
}