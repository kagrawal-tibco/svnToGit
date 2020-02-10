package com.tibco.cep.dashboard.plugin.beviews.querymgr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tibco.cep.dashboard.common.data.DataType;
import com.tibco.cep.dashboard.common.data.FieldValue;
import com.tibco.cep.dashboard.common.data.TupleSchema;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QueryBinaryTerm;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QueryCondition;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QueryPredicate;
import com.tibco.cep.dashboard.psvr.common.query.QueryException;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * @author apatil
 *
 */
public class NumericDataTypeHandler extends DataTypeHandler {

	protected static final String IS_IN_THE_RANGE_HIGH_LVL_OP = "Is In The Range";
	protected static final String IS_LESS_THAN_HIGH_LVL_OP = "Is Less Than";
	protected static final String IS_LESS_THAN_EQUAL_TO_HIGH_LVL_OP = "Is Less Than Equal To";
	protected static final String IS_GREATER_THAN_HIGH_LVL_OP = "Is Greater Than";
	protected static final String IS_GREATER_THAN_EQUAL_TO_HIGH_LVL_OP = "Is Greater Than Equal To";
	protected static final String IS_NOT_HIGH_LVL_OP = "Is Not";
	protected static final String IS_HIGH_LVL_OP = "Is";

	protected List<String> highLvlOps;

	public NumericDataTypeHandler(DataType dataType) {
		super(dataType);
		highLvlOps = new ArrayList<String>();
		highLvlOps.add(IS_HIGH_LVL_OP);
		highLvlOps.add(IS_NOT_HIGH_LVL_OP);
		highLvlOps.add(IS_GREATER_THAN_EQUAL_TO_HIGH_LVL_OP);
		highLvlOps.add(IS_GREATER_THAN_HIGH_LVL_OP);
		highLvlOps.add(IS_LESS_THAN_EQUAL_TO_HIGH_LVL_OP);
		highLvlOps.add(IS_LESS_THAN_HIGH_LVL_OP);
		highLvlOps.add(IS_IN_THE_RANGE_HIGH_LVL_OP);
	}

	@Override
	public List<String> getHighLevelOperators() {
		return highLvlOps;
	}

	@Override
	public String getDefaultHighLevelOperator() {
		return IS_HIGH_LVL_OP;
	}

	@Override
	public List<Map<String, Object>> getDefaultValues() {
		return Collections.emptyList();

	}

	@Override
	public QueryCondition createQueryCondition(Logger logger, TupleSchema tupleSchema, String fieldName, String selectedHighLevelOperator, String[] values) throws QueryException {
		if (selectedHighLevelOperator.equals(IS_HIGH_LVL_OP) == true) {
			return new QueryPredicate(tupleSchema, fieldName, QueryPredicate.EQ, new FieldValue(dataType, dataType.valueOf(values[0])));
		} else if (selectedHighLevelOperator.equals(IS_NOT_HIGH_LVL_OP) == true) {
			return new QueryPredicate(tupleSchema, fieldName, QueryPredicate.NE, new FieldValue(dataType, dataType.valueOf(values[0])));
		} else if (selectedHighLevelOperator.equals(IS_GREATER_THAN_EQUAL_TO_HIGH_LVL_OP) == true) {
			return new QueryPredicate(tupleSchema, fieldName, QueryPredicate.GE, new FieldValue(dataType, dataType.valueOf(values[0])));
		} else if (selectedHighLevelOperator.equals(IS_GREATER_THAN_HIGH_LVL_OP) == true) {
			return new QueryPredicate(tupleSchema, fieldName, QueryPredicate.GT, new FieldValue(dataType, dataType.valueOf(values[0])));
		} else if (selectedHighLevelOperator.equals(IS_LESS_THAN_EQUAL_TO_HIGH_LVL_OP) == true) {
			return new QueryPredicate(tupleSchema, fieldName, QueryPredicate.LE, new FieldValue(dataType, dataType.valueOf(values[0])));
		} else if (selectedHighLevelOperator.equals(IS_LESS_THAN_HIGH_LVL_OP) == true) {
			return new QueryPredicate(tupleSchema, fieldName, QueryPredicate.LT, new FieldValue(dataType, dataType.valueOf(values[0])));
		} else if (selectedHighLevelOperator.equals(IS_IN_THE_RANGE_HIGH_LVL_OP) == true) {
			QueryCondition firstCondition = new QueryPredicate(tupleSchema, fieldName, QueryPredicate.GE, new FieldValue(dataType, dataType.valueOf(values[0])));
			QueryCondition secondCondition = new QueryPredicate(tupleSchema, fieldName, QueryPredicate.LE, new FieldValue(dataType, dataType.valueOf(values[1])));
			return new QueryBinaryTerm(firstCondition, QueryBinaryTerm.AND, secondCondition);
		}
		throw new QueryException(selectedHighLevelOperator + " is not valid for " + getDataTypeName());
	}

	@Override
	public String getQueryConditionAsString(Logger logger, QueryCondition queryCondition) throws QueryException {
		if (queryCondition instanceof QueryBinaryTerm) {
			QueryBinaryTerm bTerm = (QueryBinaryTerm) queryCondition;
			QueryCondition lhscondition = bTerm.getLeftTerm();
			QueryCondition rhscondition = bTerm.getRightTerm();
			if ((lhscondition instanceof QueryPredicate) == false || (rhscondition instanceof QueryPredicate) == false) {
				throw new QueryException(getDataTypeName() + " can have an AND condition only between two predicates");
			}
			QueryPredicate lhspredicate = (QueryPredicate) lhscondition;
			QueryPredicate rhspredicate = (QueryPredicate) rhscondition;
			return "is between " + getRightArgValue(lhspredicate).toString() + " and " + getRightArgValue(rhspredicate).toString();
		}
		QueryPredicate predicate = (QueryPredicate) queryCondition;
		return getHighLevelOperator(logger, predicate) + " " + getRightArgValue(predicate);
	}

	@Override
	public List<Map<String, Object>> getValues(Logger logger, QueryCondition condition) throws QueryException {
		Comparable<?>[] rawValues = getRawValues(condition);
		List<Map<String, Object>> values = new ArrayList<Map<String, Object>>(rawValues.length);
		for (Comparable<?> rawValue : rawValues) {
			Map<String, Object> value = new HashMap<String, Object>();
			value.put("value", rawValue);
			values.add(value);
		}
		return values;
	}

	private Comparable<?>[] getRawValues(QueryCondition condition) throws QueryException {
		if (condition instanceof QueryBinaryTerm) {
			QueryBinaryTerm bTerm = (QueryBinaryTerm) condition;
			QueryCondition lhscondition = bTerm.getLeftTerm();
			QueryCondition rhscondition = bTerm.getRightTerm();
			if ((lhscondition instanceof QueryPredicate) == false || (rhscondition instanceof QueryPredicate) == false) {
				throw new QueryException("integer can have a and condition between two predicates");
			}
			QueryPredicate lhspredicate = (QueryPredicate) lhscondition;
			QueryPredicate rhspredicate = (QueryPredicate) rhscondition;
			return new Comparable<?>[] { getRightArgValue(lhspredicate), getRightArgValue(rhspredicate) };
		}
		QueryPredicate predicate = (QueryPredicate) condition;
		return new Comparable<?>[] { getRightArgValue(predicate) };
	}

	@Override
	public String getHighLevelOperator(Logger logger, QueryCondition condition) throws QueryException {
		if (condition instanceof QueryBinaryTerm) {
			return IS_IN_THE_RANGE_HIGH_LVL_OP;
		}
		QueryPredicate predicate = (QueryPredicate) condition;
		String realOperator = predicate.getComparison();
		if (realOperator.equals(QueryPredicate.LT) == true) {
			return IS_LESS_THAN_HIGH_LVL_OP;
		} else if (realOperator.equals(QueryPredicate.LE) == true) {
			return IS_LESS_THAN_EQUAL_TO_HIGH_LVL_OP;
		} else if (realOperator.equals(QueryPredicate.GT) == true) {
			return IS_GREATER_THAN_HIGH_LVL_OP;
		} else if (realOperator.equals(QueryPredicate.GE) == true) {
			return IS_GREATER_THAN_EQUAL_TO_HIGH_LVL_OP;
		} else if (realOperator.equals(QueryPredicate.EQ) == true) {
			return IS_HIGH_LVL_OP;
		} else if (realOperator.equals(QueryPredicate.NE) == true) {
			return IS_NOT_HIGH_LVL_OP;
		}
		throw new QueryException("cannot convert " + realOperator + " to high level operator for " + getDataTypeName());
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