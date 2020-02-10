package com.tibco.cep.dashboard.plugin.beviews.querymgr;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.tibco.cep.dashboard.common.data.BuiltInTypes;
import com.tibco.cep.dashboard.common.data.FieldValue;
import com.tibco.cep.dashboard.common.data.TupleSchema;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QueryCondition;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QueryPredicate;
import com.tibco.cep.dashboard.psvr.common.query.QueryException;
import com.tibco.cep.kernel.service.logging.Logger;

public class BooleanDataTypeHandler extends DataTypeHandler {

	private static final String IS_NOT_HIGH_LVL_OP = "Is Not";
	private static final String IS_HIGH_LVL_OP = "Is";
	
	protected BooleanDataTypeHandler() {
		super(BuiltInTypes.BOOLEAN);
	}
	
	@Override
	public List<String> getHighLevelOperators() {
		return Collections.emptyList();
	}

	@Override
	public QueryCondition createQueryCondition(Logger logger, TupleSchema tupleSchema, String fieldName, String selectedHighLevelOperator, String[] values) throws QueryException {
		return new QueryPredicate(tupleSchema, fieldName, QueryPredicate.EQ, new FieldValue(dataType, dataType.valueOf(values[0])));
	}

	@Override
	public String getDefaultHighLevelOperator() {
		return null;
	}

	@Override
	public List<Map<String, Object>> getDefaultValues() {
		return Collections.emptyList();
	}

	@Override
	public String getQueryConditionAsString(Logger logger, QueryCondition queryCondition) throws QueryException {
		if ((queryCondition instanceof QueryPredicate) == false) {
			throw new QueryException("boolean fields can be used only in predicate");
		}
		QueryPredicate predicate = (QueryPredicate) queryCondition;
		return " Is " + getRightArgValue(predicate);
	}

	@Override
	public List<Map<String, Object>> getValues(Logger logger, QueryCondition condition) throws QueryException {
		if (condition == null) {
			return Collections.emptyList();
		}
		if ((condition instanceof QueryPredicate) == false) {
			throw new QueryException("boolean fields can be used only in predicate");
		}
		QueryPredicate predicate = (QueryPredicate) condition;
		List<Map<String, Object>> values = new LinkedList<Map<String, Object>>();
		Map<String, Object> value = new HashMap<String, Object>();
		value.put("value", getRightArgValue(predicate).toString());
		values.add(value);
		return values;
	}

	@Override
	public String getHighLevelOperator(Logger logger, QueryCondition condition)  throws QueryException {
		QueryPredicate predicate = (QueryPredicate) condition;
		String operator = predicate.getComparison();
		if (operator.equals(QueryPredicate.EQ) == true) {
			return IS_HIGH_LVL_OP;
		} else if (operator.equals(QueryPredicate.NE) == true) {
			return IS_NOT_HIGH_LVL_OP;
		}
		throw new QueryException("cannot convert " + operator + " to high level operator for " + getDataTypeName());		
	}

	@Override
	public List<Map<String, Object>> getRawValues(Logger logger, QueryCondition queryCondition) throws QueryException {
		return getValues(logger, queryCondition);
	}

	@Override
	public List<Map<String, Object>> getDefaultRawValues() {
		return getDefaultValues();
	}
}