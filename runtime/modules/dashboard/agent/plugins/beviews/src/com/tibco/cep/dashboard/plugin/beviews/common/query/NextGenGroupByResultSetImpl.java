package com.tibco.cep.dashboard.plugin.beviews.common.query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.tibco.cep.dashboard.common.data.FieldValue;
import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.psvr.common.query.QueryException;

class NextGenGroupByResultSetImpl extends BaseResultSetImpl {

	private String groupByFieldName;

	private Map<FieldValue, Integer> results;

	private Iterator<FieldValue> keyIterator;

	private String entityId;

	protected NextGenGroupByResultSetImpl(BaseViewsQueryExecutorImpl queryExecutor, String typeid, String groupByField, Map<FieldValue, Integer> results) {
		super(queryExecutor);
		this.groupByFieldName = groupByField;
		this.results = results;
		List<FieldValue> keys = new LinkedList<FieldValue>(results.keySet());
		Collections.sort(keys);
		keyIterator = keys.iterator();
		this.entityId = typeid;
	}

	@Override
	public Tuple getTuple() throws QueryException {
		FieldValue groupByValue = keyIterator.next();
		Integer groupByValueAggregateValue = results.get(groupByValue);
		return new GroupByTuple(entityId, groupByFieldName, groupByValue, groupByValueAggregateValue);
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
