package com.tibco.cep.dashboard.plugin.beviews.querymgr;

import java.util.ArrayList;
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

/**
 * @author apatil
 *
 */
public class StringDataTypeHandler extends DataTypeHandler {

	private static final String ENDS_WITH_HIGH_LVL_OP = "Ends With";
	private static final String STARTS_WITH_HIGH_LVL_OP = "Starts With";
	private static final String IS_NOT_HIGH_LVL_OP = "Is Not";
	private static final String IS_HIGH_LVL_OP = "Is";
	private static final String CONTAINS_HIGH_LVL_OP = "Contains";
	private static final String IS_NULL_HIGH_LVL_OP = "Is Null";
	private static final String IS_NOT_NULL_HIGH_LVL_OP = "Is Not Null";

	List<String> highLvlOps;

	public StringDataTypeHandler() {
		super(BuiltInTypes.STRING);
		highLvlOps = new ArrayList<String>();
		highLvlOps.add(CONTAINS_HIGH_LVL_OP);
		highLvlOps.add(IS_HIGH_LVL_OP);
		highLvlOps.add(IS_NOT_HIGH_LVL_OP);
		highLvlOps.add(STARTS_WITH_HIGH_LVL_OP);
		highLvlOps.add(ENDS_WITH_HIGH_LVL_OP);
		highLvlOps.add(IS_NULL_HIGH_LVL_OP);
		highLvlOps.add(IS_NOT_NULL_HIGH_LVL_OP);
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
		if (selectedHighLevelOperator.equals(CONTAINS_HIGH_LVL_OP) == true) {
			return new QueryPredicate(tupleSchema, fieldName, QueryPredicate.LIKE, new FieldValue(dataType, "%" + values[0] + "%"));
		} else if (selectedHighLevelOperator.equals(IS_HIGH_LVL_OP) == true) {
			return new QueryPredicate(tupleSchema, fieldName, QueryPredicate.EQ, new FieldValue(dataType, values[0]));
		} else if (selectedHighLevelOperator.equals(IS_NOT_HIGH_LVL_OP) == true) {
			return new QueryPredicate(tupleSchema, fieldName, QueryPredicate.NE, new FieldValue(dataType, values[0]));
		} else if (selectedHighLevelOperator.equals(STARTS_WITH_HIGH_LVL_OP) == true) {
			return new QueryPredicate(tupleSchema, fieldName, QueryPredicate.LIKE, new FieldValue(dataType, values[0] + "%"));
		} else if (selectedHighLevelOperator.equals(ENDS_WITH_HIGH_LVL_OP) == true) {
			return new QueryPredicate(tupleSchema, fieldName, QueryPredicate.LIKE, new FieldValue(dataType, "%" + values[0]));
		}else if (selectedHighLevelOperator.equals(IS_NULL_HIGH_LVL_OP) == true) {
			return new QueryPredicate(tupleSchema, fieldName, QueryPredicate.IS_NULL, new FieldValue(dataType, true));
		}else if (selectedHighLevelOperator.equals(IS_NOT_NULL_HIGH_LVL_OP) == true) {
			return new QueryPredicate(tupleSchema, fieldName, QueryPredicate.IS_NOT_NULL, new FieldValue(dataType, false));
		}

		throw new QueryException(selectedHighLevelOperator + " is not valid for " + getDataTypeName());
	}

	@Override
	public String getQueryConditionAsString(Logger logger, QueryCondition queryCondition) throws QueryException {
		if ((queryCondition instanceof QueryPredicate) == false) {
			throw new QueryException("string fields can be used only in predicate");
		}
		QueryPredicate predicate = (QueryPredicate) queryCondition;
		String rawValue = getRawValue(predicate);
		if (rawValue == null) {
			return getHighLevelOperator(logger, predicate);
		}
		return getHighLevelOperator(logger, predicate) + " " + rawValue;
	}

	@Override
	public List<Map<String, Object>> getValues(Logger logger, QueryCondition condition) throws QueryException {
		String actualValue = getRawValue(condition);
		List<Map<String, Object>> values = new LinkedList<Map<String, Object>>();
		Map<String, Object> value = new HashMap<String, Object>();
		value.put("value", actualValue);
		values.add(value);
		return values;
	}

	private String getRawValue(QueryCondition condition) throws QueryException {
		if ((condition instanceof QueryPredicate) == false) {
			throw new QueryException("string fields can be used only in predicate");
		}
		QueryPredicate predicate = (QueryPredicate) condition;
		FieldValue value = getRightArgValue(predicate);
		if (value != null && value.isNull() == false) {
			String actualValue = value.toString();
			if (actualValue.startsWith("%") == true) {
				actualValue = actualValue.substring(1);
			}
			if (actualValue.endsWith("%") == true) {
				actualValue = actualValue.substring(0, actualValue.length() - 1);
			}
			return actualValue;
		}
		return null;
	}

	@Override
	public String getHighLevelOperator(Logger logger, QueryCondition condition) throws QueryException {
		if ((condition instanceof QueryPredicate) == false) {
			throw new QueryException("string fields can be used only in predicate");
		}
		QueryPredicate predicate = (QueryPredicate) condition;
		String realOperator = predicate.getComparison();
		if (realOperator.equals(QueryPredicate.LIKE) == true) {
			String value = getRightArgValue(predicate).toString();
			if (value.startsWith("%") == true && value.endsWith("%") == true) {
				return CONTAINS_HIGH_LVL_OP;
			} else if (value.startsWith("%") == true) {
				return ENDS_WITH_HIGH_LVL_OP;
			} else if (value.endsWith("%") == true) {
				return STARTS_WITH_HIGH_LVL_OP;
			}
			throw new QueryException("invalid operator[" + realOperator + "] value[" + value + "] combination");
		} else if (realOperator.equals(QueryPredicate.EQ) == true) {
			return IS_HIGH_LVL_OP;
		} else if (realOperator.equals(QueryPredicate.NE) == true) {
			return IS_NOT_HIGH_LVL_OP;
		}else if (realOperator.equals(QueryPredicate.IS_NULL) == true) {
			return IS_NULL_HIGH_LVL_OP;
		} else if (realOperator.equals(QueryPredicate.IS_NOT_NULL) == true) {
			return IS_NOT_NULL_HIGH_LVL_OP;
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