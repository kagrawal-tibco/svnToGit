package com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model;


/**
 * Unary conditional term. E.g. NOT QueryPredicate NOT (field1 = 10)
 *
 * E.g. NOT QueryBinaryTerm NOT (field2 < 0 OR field2 > 10)
 *
 */
public class QueryUnaryTerm extends QueryCondition {

	static final long serialVersionUID = 5047641046317633932L;

	public final static String NOT = "not";

	public static String[] sOperators = new String[] { NOT };

	private String mOperator;
	private QueryCondition mTerm;

	public QueryUnaryTerm(String operator, QueryCondition term) {
		super(term == null ? null : term.getSchema());
		validateOperator(operator);
		if (term == null) {
			throw new IllegalArgumentException("Invalid term ");
		}
		mOperator = operator;
		mTerm = term;
	}

	protected void validateOperator(String operator) {
		if (operator != null) {
			for (int i = 0; i < sOperators.length; i++) {
				if (operator.equals(sOperators[i]))
					return;
			}
		}
		throw new IllegalArgumentException("Invalid operator " + operator);
	}

	@Override
	protected void setAlias(String alias) {
		super.setAlias(alias);
		if (mTerm != null) {
			mTerm.setAlias(alias);
		}

	}

	public String getOperator() {
		return this.mOperator;
	}

	public QueryCondition getTerm() {
		return this.mTerm;
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj))
			return false;

		QueryUnaryTerm castedObj = (QueryUnaryTerm) obj;
		if (this.mOperator.equals(castedObj.mOperator) && this.mTerm.equals(castedObj.mTerm)) {
			return true;
		}
		return false;
	}

	// toString() is used for hashcode calculation.
	@Override
	public String toString() {
		if (mOperator == null)
			return mTerm.toString();
		else
			return "(" + mOperator + " " + mTerm.toString() + ")";
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		QueryUnaryTerm queryUnaryTerm = new QueryUnaryTerm(this.mOperator, (QueryCondition) this.mTerm.clone());
		return queryUnaryTerm;
	}
}
