package com.tibco.cep.dashboard.plugin.beviews.common.query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.tibco.cep.dashboard.common.data.BuiltInTypes;
import com.tibco.cep.dashboard.common.data.FieldValue;
import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.common.data.TupleSchema;
import com.tibco.cep.dashboard.common.data.TupleSchemaField;
import com.tibco.cep.dashboard.plugin.beviews.data.TupleSchemaFactory;
import com.tibco.cep.dashboard.plugin.beviews.mal.MALSourceElementCache;
import com.tibco.cep.dashboard.psvr.common.query.QueryException;
import com.tibco.cep.dashboard.psvr.mal.MALFieldMetaInfo;
import com.tibco.cep.dashboard.psvr.mal.model.MALSourceElement;

class GroupByResultSetImpl extends BaseResultSetImpl {

	private String typeid;

	private String groupByFieldName;

	private String functionFieldName;

	private Map<FieldValue, Integer> results;

	private Iterator<FieldValue> keyIterator;

	private TupleSchema tupleSchema;

	protected GroupByResultSetImpl(BaseViewsQueryExecutorImpl queryExecutor, String typeid, String groupByField, String functionField, Map<FieldValue, Integer> results) {
		super(queryExecutor);
		this.typeid = typeid;
		this.groupByFieldName = groupByField;
		this.functionFieldName = functionField;
		this.results = results;
		List<FieldValue> keys = new LinkedList<FieldValue>(results.keySet());
		Collections.sort(keys);
		keyIterator = keys.iterator();
		tupleSchema = createDynamicGroupBySchema(this.typeid, this.groupByFieldName, this.functionFieldName);
	}

	private TupleSchema createDynamicGroupBySchema(String typeID, String groupByField, String functionField) {
		TupleSchema tupleSchema = (TupleSchema) TupleSchemaFactory.getInstance().getDynamicBaseTupleSchema(typeID);
		MALSourceElement sourceElement = MALSourceElementCache.getInstance().getMALSourceElement(typeID);
		int fieldIdx = 0;
		MALFieldMetaInfo field = sourceElement.getField(groupByField);
		tupleSchema.addField(fieldIdx, field.getName(), field.getId(), field.getDataType(), false, Long.MAX_VALUE);
		fieldIdx++;
		tupleSchema.addField(fieldIdx, functionField, functionField, BuiltInTypes.INTEGER, false, Long.MAX_VALUE);
		fieldIdx++;
		return tupleSchema;
	}


	@Override
	public Tuple getTuple() throws QueryException {
		FieldValue groupByValue = keyIterator.next();
		Integer groupByValueCnt = results.get(groupByValue);
		Map<String,FieldValue> valuesByFieldIdMap = new HashMap<String, FieldValue>();
		//group by value
		TupleSchemaField groupBySchemaField = tupleSchema.getFieldByName(groupByFieldName);
		valuesByFieldIdMap.put(groupBySchemaField.getFieldID(), groupByValue);
		//group by value count
		TupleSchemaField functionSchemaField = tupleSchema.getFieldByName(functionFieldName);
		valuesByFieldIdMap.put(functionSchemaField.getFieldID(), new FieldValue(functionSchemaField.getFieldDataType(),groupByValueCnt));
		return new Tuple(tupleSchema, valuesByFieldIdMap);
	}

	@Override
	public List<Tuple> getTuples(int count) throws QueryException {
		List<Tuple> tuples = new ArrayList<Tuple>();
		int i = 0;
		while (next() && i < count){
			tuples.add(getTuple());
			i++;
		}
		return tuples;
	}

	@Override
	public boolean next() throws QueryException {
		return keyIterator.hasNext();
	}

	@Override
	protected void doClose() throws Exception {
		results.clear();
		keyIterator = results.keySet().iterator();
	}
}
