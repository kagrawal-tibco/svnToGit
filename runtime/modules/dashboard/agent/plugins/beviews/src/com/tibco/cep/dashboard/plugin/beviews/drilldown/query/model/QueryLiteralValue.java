package com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model;

import com.tibco.cep.dashboard.common.data.BuiltInTypes;
import com.tibco.cep.dashboard.common.data.FieldValue;
import com.tibco.cep.dashboard.common.data.TupleSchema;
import com.tibco.cep.studio.dashboard.core.query.BEViewsQueryDateTypeInterpreter;

/**
 * Represents a literal value (Integer, String, Date, etc).
 */
public class QueryLiteralValue extends QueryExpression {

	static final long serialVersionUID = 340166438179266101L;

	protected FieldValue literalValue;

	public QueryLiteralValue(TupleSchema schema, FieldValue literalValue) {
		super(schema);
		this.literalValue = literalValue;
	}

	public FieldValue getLiteralValue() {
		return literalValue;
	}

	@Override
	protected FieldValue eval() {
		return literalValue;
	}

	@Override
	public void validate() throws IllegalArgumentException {
		//do nothing
	}

	@Override
	public boolean equals(Object obj) {
		return literalValue.equals(obj);
	}

	// toString() is used for hashcode calculation.
	@Override
	public String toString() {
		String value;
		if (literalValue.getDataType() == BuiltInTypes.DATETIME) {
			value = "'" + BEViewsQueryDateTypeInterpreter.DATETIME_CONVERTOR.format(literalValue.getValue()) + "'";
		} else if (literalValue.getDataType() == BuiltInTypes.STRING) {
			value = "\"" + literalValue.toString() + "\"";
		} else {
			value = literalValue.toString();
		}
		return value;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		QueryLiteralValue queryLiteralValue = new QueryLiteralValue(super.getSchema(), (FieldValue) literalValue.clone());
		queryLiteralValue.setAlias(mAlias);
		return queryLiteralValue;
	}

}