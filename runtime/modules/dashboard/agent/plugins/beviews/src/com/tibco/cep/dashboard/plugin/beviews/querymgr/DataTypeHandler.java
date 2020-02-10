package com.tibco.cep.dashboard.plugin.beviews.querymgr;

import java.util.List;
import java.util.Map;

import com.tibco.cep.dashboard.common.data.DataType;
import com.tibco.cep.dashboard.common.data.FieldValue;
import com.tibco.cep.dashboard.common.data.TupleSchema;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QueryCondition;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QueryPredicate;
import com.tibco.cep.dashboard.psvr.common.query.QueryException;
import com.tibco.cep.kernel.service.logging.Logger;

public abstract class DataTypeHandler {

	DataType dataType;

	protected DataTypeHandler(DataType dataType) {
		this.dataType = dataType;
	}

	public String getDataTypeName() {
		return dataType.getDataTypeID();
	}

	public abstract String getDefaultHighLevelOperator();

	public abstract List<Map<String, Object>> getDefaultRawValues();

	public abstract List<Map<String, Object>> getDefaultValues();

	public abstract List<String> getHighLevelOperators();

	public abstract QueryCondition createQueryCondition(Logger logger, TupleSchema tupleSchema, String fieldName, String selectedHighLevelOperator, String[] values) throws QueryException;

	public abstract List<Map<String, Object>> getValues(Logger logger, QueryCondition condition) throws QueryException;

	public abstract String getHighLevelOperator(Logger logger, QueryCondition condition) throws QueryException;

	public abstract String getQueryConditionAsString(Logger logger, QueryCondition queryCondition) throws QueryException;

	// Added by Nikhil on 17 July 2011: get values required for QueryManager XML passed to front-end
	public abstract List<Map<String,Object>> getRawValues(Logger logger, QueryCondition queryCondition) throws QueryException;

	protected FieldValue getRightArgValue(QueryPredicate predicate) throws QueryException {
		try {
			return predicate.evalRightArgument();
		} catch (Exception e) {
			throw new QueryException(e);
		}
	}
}