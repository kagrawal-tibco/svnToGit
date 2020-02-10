package com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model;

import com.tibco.cep.dashboard.common.data.FieldValue;
import com.tibco.cep.dashboard.common.data.TupleSchema;

/**
 * Represents an expression that would be evaluated to a value (SynObject).
 */
@SuppressWarnings("serial")
public abstract class QueryExpression extends QueryCondition {

	public QueryExpression(TupleSchema schema) {
		super(schema);
	}

	public abstract void validate() throws IllegalArgumentException;

	protected abstract FieldValue eval();

	@Override
	public abstract boolean equals(Object obj);

	@Override
	public abstract String toString();

}