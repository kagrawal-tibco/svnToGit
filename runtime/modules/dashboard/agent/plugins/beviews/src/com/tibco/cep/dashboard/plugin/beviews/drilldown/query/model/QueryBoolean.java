package com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model;

import com.tibco.cep.dashboard.common.data.TupleSchema;

/**
 * A single conditional boolean term. E.g. true or false. Translated into 1=1 or
 * 1=0
 */
public class QueryBoolean extends QueryCondition {

	static final long serialVersionUID = 340166438179266997L;

	private boolean mValue = true;

	public QueryBoolean(TupleSchema schema, boolean value) {
		super(schema);
		mValue = value;
	}

	public boolean getValue() {
		return mValue;
	}

	@Override
	public String toString() {
		return (mValue ? "true" : "false");
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj))
			return false;

		return mValue == ((QueryBoolean) obj).mValue;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		QueryBoolean queryBoolean = new QueryBoolean(super.getSchema(), mValue);
		return queryBoolean;
	}

}
