package com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model;

import com.tibco.cep.dashboard.common.data.BuiltInTypes;
import com.tibco.cep.dashboard.common.data.FieldValue;
import com.tibco.cep.dashboard.common.data.TupleSchema;

/**
 * Represents a bind-able parameter
 */
public class QueryFunctionCall extends QueryExpression {

	private static final long serialVersionUID = 2709874639667275483L;

	private String functionalCall;

	public QueryFunctionCall(TupleSchema schema, String functionalCall) {
		super(schema);
		this.functionalCall = functionalCall;
	}

	public String getFunctionalCall() {
		return functionalCall;
	}

	@Override
	protected FieldValue eval() {
		return new FieldValue(BuiltInTypes.STRING, functionalCall);
	}

	@Override
	public void validate() throws IllegalArgumentException {
		//do nothing as of now
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return toString().equals(obj == null ? null : obj.toString());
	}

	@Override
	public String toString() {
		return functionalCall;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		QueryFunctionCall queryFunctionalCall = new QueryFunctionCall(super.getSchema(), functionalCall);
		queryFunctionalCall.setAlias(mAlias);
		return queryFunctionalCall;
	}

}
