package com.tibco.cep.dashboard.plugin.beviews.search;

import java.util.HashMap;
import java.util.Map;

import com.tibco.cep.dashboard.common.data.TupleSchema;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QueryBinaryTerm;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QueryCondition;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QueryPredicate;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QuerySpec;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QueryUnaryTerm;

public class QuerySpecModel {

	private TupleSchema tupleSchema;

	private Map<String, QueryCondition> conditions;

	public QuerySpecModel(QuerySpec querySpec) throws IncompatibleQueryException {
		conditions = new HashMap<String, QueryCondition>();
		setQuerySpec(querySpec);
	}

	public void setQuerySpec(QuerySpec querySpec) throws IncompatibleQueryException {
		if (querySpec == null) {
			throw new IllegalArgumentException("Query Spec cannot be null");
		}
		conditions.clear();
		tupleSchema = null;
		indexConditions(querySpec.getCondition());
		tupleSchema = querySpec.getSchema();
	}

	private void indexConditions(QueryCondition condition) throws IncompatibleQueryException {
		if (condition instanceof QueryPredicate) {
			registerCondition(((QueryPredicate) condition).evalLeftArgument().toString(), condition);
		}
		if (condition instanceof QueryBinaryTerm) {
			indexConditions(((QueryBinaryTerm) condition).getLeftTerm());
			indexConditions(((QueryBinaryTerm) condition).getRightTerm());
		}
		if (condition instanceof QueryUnaryTerm) {
			throw new IncompatibleQueryException("unary condition are not supported");
		}
	}

	private void registerCondition(String fieldName, QueryCondition condition) throws IncompatibleQueryException {
		QueryCondition existingCondition = conditions.get(fieldName);
		if (existingCondition == null) {
			conditions.put(fieldName, condition);
		} else if (existingCondition instanceof QueryBinaryTerm) {
			throw new IncompatibleQueryException(fieldName + " already has a binary condition");
		} else if (existingCondition instanceof QueryUnaryTerm) {
			throw new IncompatibleQueryException(fieldName + " already has a unary condition");
		} else {
			QueryBinaryTerm replacementCondition = new QueryBinaryTerm(existingCondition, QueryBinaryTerm.AND, condition);
			conditions.put(fieldName, replacementCondition);
		}
	}

	public QueryCondition getCondition(String fieldName) {
		return conditions.get(fieldName);
	}

	public void removeCondition(String fieldName) {
		conditions.remove(fieldName);
	}

	public void setCondition(String fieldName, QueryCondition condition) {
		if (condition.getSchema().equals(tupleSchema) == false){
			throw new IllegalArgumentException(condition+" does not belong to existing query spec");
		}
		conditions.put(fieldName, condition);
	}

	public QuerySpec getQuerySpec(){
		QuerySpec querySpec = new QuerySpec(tupleSchema);
		QueryCondition rootCondition = null;
		for (QueryCondition condition : conditions.values()) {
			if (rootCondition == null){
				rootCondition = condition;
			}
			else {
				rootCondition = new QueryBinaryTerm(rootCondition,QueryBinaryTerm.AND,condition);
			}
		}
		if (rootCondition != null){
			querySpec.setCondition(rootCondition);
		}
		return querySpec;
	}

	public String getTypeID() {
		return tupleSchema.getTypeID();
	}

	public TupleSchema getTupleSchema(){
		return tupleSchema;
	}
}
