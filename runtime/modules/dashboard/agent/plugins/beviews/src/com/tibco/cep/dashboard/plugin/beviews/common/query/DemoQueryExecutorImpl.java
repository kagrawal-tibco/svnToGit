package com.tibco.cep.dashboard.plugin.beviews.common.query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import com.tibco.cep.dashboard.common.data.BuiltInTypes;
import com.tibco.cep.dashboard.common.data.DataType;
import com.tibco.cep.dashboard.common.data.FieldValue;
import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.common.data.TupleSchema;
import com.tibco.cep.dashboard.common.data.TupleSchemaField;
import com.tibco.cep.dashboard.logging.LoggingService;
import com.tibco.cep.dashboard.plugin.beviews.BEViewsProperties;
import com.tibco.cep.dashboard.plugin.beviews.data.TupleSchemaFactory;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.OrderBySpec;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QueryBinaryTerm;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QueryCondition;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QueryPredicate;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QueryProjectionField;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QuerySpec;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QueryUnaryTerm;
import com.tibco.cep.dashboard.plugin.beviews.mal.MALSourceElementCache;
import com.tibco.cep.dashboard.psvr.common.query.Query;
import com.tibco.cep.dashboard.psvr.common.query.QueryException;
import com.tibco.cep.dashboard.psvr.common.query.QueryExecutor;
import com.tibco.cep.dashboard.psvr.common.query.ResultSet;
import com.tibco.cep.dashboard.psvr.mal.MALFieldMetaInfo;
import com.tibco.cep.dashboard.psvr.mal.model.MALSourceElement;
import com.tibco.cep.dashboard.psvr.util.ChangeableInteger;
import com.tibco.cep.kernel.service.logging.Level;

public class DemoQueryExecutorImpl implements QueryExecutor {

	private Map<String, SeriesDataGenerator> seriesDataGenerators;

	private Map<String, List<Tuple>> completeMetricDemoData;

	private long timeIncrement;

	public DemoQueryExecutorImpl(Properties properties) {
		seriesDataGenerators = new HashMap<String, SeriesDataGenerator>();
		completeMetricDemoData = new HashMap<String, List<Tuple>>();
		timeIncrement = (Long) BEViewsProperties.DEMO_MODE_TIME_INCREMENT.getValue(properties);
	}

	@Override
	public synchronized int countQuery(Query query) throws QueryException {
		int count = 0;
		ResultSet resultSet = null;
		try {
			resultSet = (TupleBasedResultSetImpl) executeQuery(query);
			count = ((TupleBasedResultSetImpl) resultSet).tuples.size();
		} finally {
			if (resultSet != null) {
				resultSet.close();
			}
		}
		LoggingService.getRuntimeLogger().log(Level.INFO, "Returning " + count + " for " + query);
		return count;
	}

	@Override
	public synchronized ResultSet executeQuery(Query query) throws QueryException {
		List<Tuple> data = Collections.emptyList();
		SeriesDataGenerationConfiguration generatorConfig = SeriesDataGenerationConfiguration.getInstanceInCurrentThread();
		QuerySpec querySpec = ((ViewsQuery)query).getQuerySpec();
		if (generatorConfig != null) {
			List<Tuple> completeMetricData = completeMetricDemoData.get(generatorConfig.getSourceElement().getId());
			if (completeMetricData == null) {
				completeMetricData = new LinkedList<Tuple>();
				completeMetricDemoData.put(generatorConfig.getSourceElement().getId(), completeMetricData);
			}
			// we are dealing with seriesConfig
			String seriesCfgId = generatorConfig.getSeriesConfig().getId();
			SeriesDataGenerator dataGenerator = seriesDataGenerators.get(seriesCfgId);
			if (dataGenerator == null) {
				dataGenerator = new SeriesDataGenerator(generatorConfig, timeIncrement);
				data = dataGenerator.generateData();
				seriesDataGenerators.put(seriesCfgId, dataGenerator);
				completeMetricData.addAll(data);
			}
			else {
				data = dataGenerator.changeData();
			}
		} else if (querySpec != null) {
			List<Tuple> metricData = completeMetricDemoData.get(querySpec.getSchema().getTypeID());
			if (metricData == null) {
				metricData = createQueryBasedData(querySpec);
				completeMetricDemoData.put(querySpec.toString(), metricData);
			}
			if (metricData != null) {
				metricData = new LinkedList<Tuple>(metricData);
				if (querySpec.getCondition() != null) {
					filterTuples(metricData, querySpec.getCondition());
				}
				if (querySpec.getOrderByFields() != null) {
					orderByTuples(metricData, querySpec.getOrderByFields());
				}
				if (querySpec.getGroupByFields().isEmpty() == false) {
					// Its a group by query
					// tuples will have two fields group by field and count
					List<String> groupByFields = querySpec.getGroupByFields();
					String groupByField = groupByFields.get(0);
					TupleSchema dynamicGroupByTupleSchema = createDynamicGroupBySchema(querySpec.getSchema().getTypeID(), groupByField, QueryProjectionField.COUNT);
					Map<FieldValue, ChangeableInteger> mapValues = new TreeMap<FieldValue, ChangeableInteger>();
					for (Tuple tuple : metricData) {
						FieldValue fieldValue = tuple.getFieldValueByName(groupByField);
						ChangeableInteger count = mapValues.get(fieldValue);
						if (count == null) {
							count = new ChangeableInteger(0);
							mapValues.put(fieldValue, count);
						}
						count.increment();
					}
					List<Tuple> groupTuples = new ArrayList<Tuple>();
					for (Iterator<Map.Entry<FieldValue, ChangeableInteger>> itMapValues = mapValues.entrySet().iterator(); itMapValues.hasNext();) {
						Map.Entry<FieldValue, ChangeableInteger> entry = itMapValues.next();
						Map<String, FieldValue> values = new HashMap<String, FieldValue>();
						values.put(dynamicGroupByTupleSchema.getFieldIDByName(groupByField), entry.getKey());
						DataType countFldDataType = dynamicGroupByTupleSchema.getFieldDataTypeByName(QueryProjectionField.COUNT);
						FieldValue value = new FieldValue(countFldDataType, entry.getValue().intValue());
						values.put(dynamicGroupByTupleSchema.getFieldIDByName(QueryProjectionField.COUNT), value);
						Tuple tuple = new Tuple(dynamicGroupByTupleSchema, values);
						groupTuples.add(tuple);
					}
					metricData = groupTuples;
				}
				data = metricData;
			}
		}
		return new TupleBasedResultSetImpl(data);
	}

	private List<Tuple> createQueryBasedData(QuerySpec querySpec) {
		TupleSchema schema = querySpec.getSchema();
		Map<String, FieldValue> fieldNamesAndValuesMap = new HashMap<String, FieldValue>();
		extractFieldAndValues(querySpec.getCondition(), fieldNamesAndValuesMap);
		SampleValuesGenerator generator = new SampleValuesGenerator();
		int count = 100 + (int) (Math.random() * 200);
		List<Tuple> tuples = new ArrayList<Tuple>();
		for (int i = 0; i < count; i++) {
			Map<String, FieldValue> values = new HashMap<String, FieldValue>();
			int fieldCount = schema.getFieldCount();
			for (int fIndex = 0; fIndex < fieldCount; fIndex++) {
				TupleSchemaField schemaField = schema.getFieldByPosition(fIndex);
				if (fieldNamesAndValuesMap.containsKey(schemaField.getFieldName()) == true) {
					values.put(schemaField.getFieldID(), fieldNamesAndValuesMap.get(schemaField.getFieldName()));
				} else {
					DataType dataType = schemaField.getFieldDataType();
					values.put(schemaField.getFieldID(), generator.valueFor(dataType, fIndex));
				}
			}
			Tuple tuple = new Tuple(schema, values);
			tuples.add(tuple);
		}
		return tuples;
	}

	private void extractFieldAndValues(QueryCondition condition, Map<String, FieldValue> fieldNamesAndValuesMap) {
		if (condition instanceof QueryBinaryTerm) {
			QueryBinaryTerm binaryTerm = (QueryBinaryTerm) condition;
			extractFieldAndValues(binaryTerm.getLeftTerm(), fieldNamesAndValuesMap);
			extractFieldAndValues(binaryTerm.getRightTerm(), fieldNamesAndValuesMap);
		} else if (condition instanceof QueryUnaryTerm) {
			QueryUnaryTerm unaryTerm = (QueryUnaryTerm) condition;
			extractFieldAndValues(unaryTerm.getTerm(), fieldNamesAndValuesMap);
		} else if (condition instanceof QueryPredicate) {
			QueryPredicate predicate = (QueryPredicate) condition;
			String fieldName = predicate.evalLeftArgument().toString();
			FieldValue fieldValue = predicate.evalRightArgument();
			fieldNamesAndValuesMap.put(fieldName, fieldValue);
		}
	}

	@Override
	public void close() {
	}

	private void filterTuples(List<Tuple> tuples, QueryCondition condition) {
		if (condition instanceof QueryBinaryTerm) {
			QueryBinaryTerm binary = (QueryBinaryTerm) condition;
			filterTuples(tuples, binary.getLeftTerm());
			filterTuples(tuples, binary.getRightTerm());
		} else if (condition instanceof QueryPredicate) {
			QueryPredicate predicate = (QueryPredicate) condition;
			String fieldName = predicate.evalLeftArgument().toString();
			FieldValue rightArgValue = predicate.evalRightArgument();
			if (condition.getSchema().getIDField() != null && condition.getSchema().getIDField().getFieldName().equals(fieldName) == true) {
				for (Iterator<Tuple> itTuples = tuples.iterator(); itTuples.hasNext();) {
					Tuple tuple = itTuples.next();
					if (rightArgValue.toString().equals(tuple.getId()) == false) {
						itTuples.remove();
					}
				}
			} else {
				for (Iterator<Tuple> itTuples = tuples.iterator(); itTuples.hasNext();) {
					Tuple tuple = itTuples.next();
					FieldValue fieldValue = tuple.getFieldValueByName(fieldName);
					if (rightArgValue.equals(fieldValue) == false) {
						itTuples.remove();
					}
				}
			}
		} else {

		}
	}

	private void orderByTuples(List<Tuple> tuples, final ArrayList<OrderBySpec> orderByFields) {
		if (orderByFields.isEmpty())
			return;
		Collections.sort(tuples, new Comparator<Tuple>() {
			@Override
			public int compare(Tuple t1, Tuple t2) {
				for (OrderBySpec orderBySpec : orderByFields) {
					FieldValue t1Value = t1.getFieldValueByName(orderBySpec.getOrderByField());
					FieldValue t2Value = t2.getFieldValueByName(orderBySpec.getOrderByField());
					int compareTo = t1Value.compareTo(t2Value);
					if (compareTo == 0) {
						// Check next field
					} else {
						if (orderBySpec.getAscending()) {
							return compareTo;
						} else {
							return -compareTo;
						}
					}
				}
				return 0;
			}
		});
	}

	private TupleSchema createDynamicGroupBySchema(String typeID, String groupByField, String functionField) {
		TupleSchema tupleSchema = TupleSchemaFactory.getInstance().getDynamicBaseTupleSchema(typeID);
		MALSourceElement sourceElement = MALSourceElementCache.getInstance().getMALSourceElement(typeID);
		int fieldIdx = 0;
		MALFieldMetaInfo field = sourceElement.getField(groupByField);
		tupleSchema.addField(fieldIdx, field.getName(), field.getId(), field.getDataType(), false, Long.MAX_VALUE);
		fieldIdx++;
		tupleSchema.addField(fieldIdx, functionField, functionField, BuiltInTypes.INTEGER, false, Long.MAX_VALUE);
		fieldIdx++;
		return tupleSchema;
	}
}
