package com.tibco.rta.service.persistence.memory.adapter.impl;

import static com.tibco.rta.service.persistence.memory.InMemoryConstant.CREATED_DATE_TIME_FIELD;
import static com.tibco.rta.service.persistence.memory.InMemoryConstant.FACT_TABLE_PREFIX;
import static com.tibco.rta.service.persistence.memory.InMemoryConstant.MEASUREMENT_ASSETSTATUS;
import static com.tibco.rta.service.persistence.memory.InMemoryConstant.METRIC_TABLE_PREFIX;
import static com.tibco.rta.service.persistence.memory.InMemoryConstant.RULE_SCHEDULED_TIME_FIELD;
import static com.tibco.rta.service.persistence.memory.InMemoryConstant.RULE_STATE_TABLE_PREFIX;
import static com.tibco.rta.service.persistence.memory.InMemoryConstant.SEP;
import static com.tibco.rta.service.persistence.memory.InMemoryConstant.UPDATED_DATE_TIME_FIELD;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

import com.tibco.rta.Fact;
import com.tibco.rta.MetricKey;
import com.tibco.rta.common.registry.ModelRegistry;
import com.tibco.rta.model.Attribute;
import com.tibco.rta.model.Cube;
import com.tibco.rta.model.Dimension;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.TimeDimension;
import com.tibco.rta.model.TimeUnits;
import com.tibco.rta.query.FilterKeyQualifier;
import com.tibco.rta.query.MetricFieldTuple;
import com.tibco.rta.query.MetricQualifier;
import com.tibco.rta.query.QueryByFilterDef;
import com.tibco.rta.query.QueryDef;
import com.tibco.rta.query.filter.AndFilter;
import com.tibco.rta.query.filter.EqFilter;
import com.tibco.rta.query.filter.Filter;
import com.tibco.rta.query.filter.GEFilter;
import com.tibco.rta.query.filter.LEFilter;
import com.tibco.rta.query.impl.QueryFactory;
import com.tibco.rta.runtime.model.MetricNode;
import com.tibco.store.persistence.model.MemoryTuple;
import com.tibco.store.persistence.service.invm.DataServiceFactory;
import com.tibco.store.persistence.service.invm.InMemoryDataStoreService;
import com.tibco.store.query.exec.QueryExecutor;
import com.tibco.store.query.exec.impl.invm.InMemoryQueryExecutor;
import com.tibco.store.query.model.BinaryOperator;
import com.tibco.store.query.model.OptimizerHint;
import com.tibco.store.query.model.Predicate;
import com.tibco.store.query.model.Query;
import com.tibco.store.query.model.QueryResultSet;
import com.tibco.store.query.model.impl.PredicateFactory;
import com.tibco.store.query.model.impl.QueryImpl;
import com.tibco.store.query.model.impl.SimpleQueryExpression;
import com.tibco.store.query.model.impl.ValueExpression;

public class QueryBuilder {

	public static QueryResultSet executeMetricQuery(QueryByFilterDef queryDef) {
		Filter filter = queryDef.getFilter();
		if (filter != null) {
			String tableName = TableGenerationUtility.getMemoryTableName(METRIC_TABLE_PREFIX, queryDef.getSchemaName(), queryDef.getCubeName(), queryDef.getHierarchyName());

			Predicate parentPredicate = FilterTransformer.transform(filter, tableName);
			return evaluatePredicate(parentPredicate);
		}
		return null;
	}

	public static QueryResultSet executeFactQuery(QueryByFilterDef queryDef) {
		Filter filter = queryDef.getFilter();
		if (filter != null) {
			String tableName = TableGenerationUtility.getUniqueIdentifier(FACT_TABLE_PREFIX, SEP, queryDef.getSchemaName());

			Predicate parentPredicate = FilterTransformer.transform(filter, tableName);
			return evaluatePredicate(parentPredicate);
		}
		return null;
	}

	private static QueryResultSet evaluatePredicate(Predicate predicate) {
		Query<Predicate> queryModel = new QueryImpl<Predicate>();
		queryModel.setPredicate(predicate);
		queryModel.setOptimizerHint(OptimizerHint.COUNT);
		QueryExecutor queryExecutor = new InMemoryQueryExecutor();
		return queryExecutor.executeQuery(queryModel);
	}

	public static QueryResultSet getScheduledActions(String schemaName, String cubeName, String hierarchyName, long currentTimeMillis) throws Exception {
		String tableName = TableGenerationUtility.getMemoryTableName(RULE_STATE_TABLE_PREFIX, schemaName, cubeName, hierarchyName);

		Predicate p1 = PredicateFactory.createBinaryPredicate(new SimpleQueryExpression(tableName, RULE_SCHEDULED_TIME_FIELD), new ValueExpression<Long>(currentTimeMillis),
				BinaryOperator.LE);
		Predicate p2 = PredicateFactory.createBinaryPredicate(new SimpleQueryExpression(tableName, RULE_SCHEDULED_TIME_FIELD), new ValueExpression<Long>(0L),
				BinaryOperator.GT);
		Predicate andPredicate = PredicateFactory.createAndPredicate(p1, p2);
		return evaluatePredicate(andPredicate);
	}

	private static void printTuple(MemoryTuple tuple) {
		if (tuple != null) {
			System.out.println("Key:\t[" + tuple.getMemoryKey() + "]");

			System.out.println("Attributes:");
			for (String attrName : tuple.getAttributeNames()) {
				System.out.print("\t" + attrName + "\t" + tuple.getAttributeValue(attrName) + "\n");
			}

			System.out.println();
		}
	}

	public static QueryDef buildQueryFromKey(MetricKey mKey) {
		QueryByFilterDef queryDef = QueryFactory.INSTANCE.newQueryByFilterDef(mKey.getSchemaName(), mKey.getCubeName(), mKey.getDimensionHierarchyName(), "");
		AndFilter andFilter = QueryFactory.INSTANCE.newAndFilter();

		if (mKey.getDimensionLevelName() != null) {
			EqFilter eqFilter = QueryFactory.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL, mKey.getDimensionLevelName());
			andFilter.addFilter(eqFilter);
		}
		for (String dimName : mKey.getDimensionNames()) {
			Object value = mKey.getDimensionValue(dimName);
			if (value != null) {
				EqFilter eqFilter = QueryFactory.INSTANCE.newEqFilter(FilterKeyQualifier.DIMENSION_NAME, dimName, value);
				andFilter.addFilter(eqFilter);
			}
		}
		queryDef.setFilter(andFilter);
		return queryDef;
	}

	public static QueryDef buildQueryForAsset(RtaSchema schema, Cube cube, DimensionHierarchy dh, Fact fact) {
		QueryByFilterDef queryDef = QueryFactory.INSTANCE.newQueryByFilterDef(schema.getName(), cube.getName(), dh.getName(), MEASUREMENT_ASSETSTATUS);

		AndFilter andFilter = QueryFactory.INSTANCE.newAndFilter();
		for (Dimension dm : dh.getDimensions()) {

			Object value = fact.getAttribute(dm.getAssociatedAttribute().getName());
			if (dm instanceof TimeDimension) {
				TimeDimension td = (TimeDimension) dm;
				long timestamp = 0;
				if (value == null) {
					timestamp = System.currentTimeMillis();
				} else if (value instanceof Long) {
					timestamp = (Long) value;
				}
				value = td.getTimeUnit().getTimeDimensionValue(timestamp);

				if (value != null) {
					EqFilter eqFilter = QueryFactory.INSTANCE.newEqFilter(FilterKeyQualifier.DIMENSION_NAME, dm.getName(), value);
					andFilter.addFilter(eqFilter);
				}
			}

			else if (value == null) {
				continue;
			} else {
				EqFilter eqFilter = QueryFactory.INSTANCE.newEqFilter(FilterKeyQualifier.DIMENSION_NAME, dm.getName(), value);
				andFilter.addFilter(eqFilter);
			}
		}
		queryDef.setFilter(andFilter);
		return queryDef;
	}

	private static void printTable(String tableName) {
		InMemoryDataStoreService dataService = DataServiceFactory.getInstance().getDataStoreService();
		Collection<MemoryTuple> tuples = dataService.getAllTuples(tableName);
		for (MemoryTuple tuple : tuples) {
			printTuple(tuple);
		}
	}

	public static QueryDef buildQueryForFactBrowser(MetricNode metricNode, List<MetricFieldTuple> orderByList) {
		long smallestTimeDimensionGranule = 0;
		TimeUnits tm = null;
		Dimension timeDimension = null;
		long SECONDS_IN_MILLIS = 1000;
		long MINUTES_IN_MILLIS = SECONDS_IN_MILLIS * 60;
		long HOURS_IN_MILLIS = MINUTES_IN_MILLIS * 60;
		long DAYS_IN_MILLIS = HOURS_IN_MILLIS * 24;
		long WEEK_IN_MILLIS = DAYS_IN_MILLIS * 7;

		MetricKey mKey = (MetricKey) metricNode.getKey();
		QueryByFilterDef queryDef = QueryFactory.INSTANCE.newQueryByFilterDef(mKey.getSchemaName(), mKey.getCubeName(), mKey.getDimensionHierarchyName(), "");
		RtaSchema schema = ModelRegistry.INSTANCE.getRegistryEntry(mKey.getSchemaName());
		AndFilter andFilter = QueryFactory.INSTANCE.newAndFilter();

		for (int i = 0; i < mKey.getDimensionNames().size(); i++) {
			String dimName = mKey.getDimensionNames().get(i);
			Object value = mKey.getDimensionValue(dimName);
			Dimension dim = schema.getDimension(dimName);
			Attribute attr = dim.getAssociatedAttribute();

			if (value != null) {
				if (dim instanceof TimeDimension) {
					long timeValue = (Long) value;
					if (smallestTimeDimensionGranule < timeValue) {
						timeDimension = dim;
						TimeDimension td = (TimeDimension) dim;
						smallestTimeDimensionGranule = timeValue;
						tm = td.getTimeUnit();
					}
				} else {
					EqFilter eqFilter = QueryFactory.INSTANCE.newEqFilter(FilterKeyQualifier.DIMENSION_NAME, attr.getName(), value);
					andFilter.addFilter(eqFilter);
				}
			}
		}
		if (tm != null) {
			long timeInterval = 0;
			switch (tm.getTimeUnit()) {
			case MILLISECOND: {
				timeInterval = 1;
			}
			case SECOND: {
				timeInterval = SECONDS_IN_MILLIS;
				break;
			}
			case MINUTE: {
				timeInterval = MINUTES_IN_MILLIS;
				break;
			}
			case HOUR: {
				timeInterval = HOURS_IN_MILLIS;
				break;
			}
			case DAY: {
				timeInterval = DAYS_IN_MILLIS;
				break;
			}
			case WEEK: {
				timeInterval = WEEK_IN_MILLIS;
				break;
			}
			default: {
				timeInterval = 1;
			}
			}

			timeInterval = timeInterval * tm.getMultiplier();

			LEFilter leFilter = QueryFactory.INSTANCE.newLEFilter(FilterKeyQualifier.DIMENSION_NAME, timeDimension.getAssociatedAttribute().getName(),
					(smallestTimeDimensionGranule + timeInterval - 1));
			GEFilter geFilter = QueryFactory.INSTANCE.newGEFilter(FilterKeyQualifier.DIMENSION_NAME, timeDimension.getAssociatedAttribute().getName(),
					smallestTimeDimensionGranule);
			andFilter.addFilter(leFilter);
			andFilter.addFilter(geFilter);
		}
		queryDef.setFilter(andFilter);

		return queryDef;
	}

	public static QueryResultSet purgeMetricsForHierarchy(String tableName, long purgeOlderThan) {
		Predicate purgePredicate = PredicateFactory.createBinaryPredicate(new SimpleQueryExpression(tableName, UPDATED_DATE_TIME_FIELD), new ValueExpression<Timestamp>(
				new Timestamp(System.currentTimeMillis() - purgeOlderThan)), BinaryOperator.LT);
		return evaluatePredicate(purgePredicate);
	}

	public static QueryResultSet purgeFacts(String tableName, long purgeOlderThan) {
		Predicate purgePredicate = PredicateFactory.createBinaryPredicate(new SimpleQueryExpression(tableName, CREATED_DATE_TIME_FIELD),
				new ValueExpression<Long>(System.currentTimeMillis() - purgeOlderThan), BinaryOperator.LT);
		return evaluatePredicate(purgePredicate);
	}

}
