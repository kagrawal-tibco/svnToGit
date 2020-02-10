package com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model;

import com.tibco.cep.dashboard.common.data.FieldValue;
import com.tibco.cep.dashboard.common.data.TupleSchema;

/**
 * A single HAVING conditional predicate term. E.g. select field1, SUM(field1)
 * from foo where bar GROUP BY field1 HAVING SUM(field1) > 100
 */
public class QueryHavingPredicate extends QueryPredicate {

	static final long serialVersionUID = 340166438179266948L;

	private QueryProjectionField mProjectionField;

	public QueryHavingPredicate(TupleSchema schema, QueryProjectionField projectionField, String comparison, FieldValue rightArgValue) {
		super(schema);
		super.mRightArgument = new QueryLiteralValue(schema, rightArgValue);
		validateArgs(schema, projectionField, comparison, super.mRightArgument);
		mProjectionField = projectionField;
		super.mComparison = comparison;
	}

	public QueryProjectionField getProjectionField() {
		return this.mProjectionField;
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj))
			return false;

		QueryHavingPredicate castedObj = (QueryHavingPredicate) obj;

		if (this.mProjectionField.equals(castedObj.mProjectionField)) {
			return true;
		}

		return false;
	}

	@Override
	public String toString() {
		return "(" + mProjectionField + " " + super.mComparison + " " + super.mRightArgument + ")";
	}

}