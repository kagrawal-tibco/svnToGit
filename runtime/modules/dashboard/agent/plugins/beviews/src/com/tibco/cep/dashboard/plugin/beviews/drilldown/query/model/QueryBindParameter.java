package com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model;

import com.tibco.cep.dashboard.common.data.BuiltInTypes;
import com.tibco.cep.dashboard.common.data.FieldValue;
import com.tibco.cep.dashboard.common.data.TupleSchema;

/**
 * Represents a bind-able parameter
 */
public class QueryBindParameter extends QueryExpression {

	private static final long serialVersionUID = -6512488786176077327L;

	private String bindParameter;

	public QueryBindParameter(TupleSchema schema, String bindParameter) {
		super(schema);
		this.bindParameter = bindParameter;
	}

	public String getBindParameter() {
		return bindParameter;
	}

	@Override
	protected FieldValue eval() {
		return new FieldValue(BuiltInTypes.STRING, bindParameter);
	}

	@Override
	public void validate() throws IllegalArgumentException {
		//do nothing
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return toString().equals(obj == null ? null : obj.toString());
	}

	// toString() is used for hashcode calculation.
	@Override
	public String toString() {
		return bindParameter;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		QueryBindParameter queryBindParameter = new QueryBindParameter(super.getSchema(), bindParameter);
		queryBindParameter.setAlias(mAlias);
		return queryBindParameter;
	}

}
