package com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model;


/**
 * Binary relational term. E.g. QueryPredicate AND QueryPredicate field1 = 10 AND field2 < 0
 *
 * E.g. RightPredicate AND QueryBinaryTerm field1 = 10 AND (field2 < 0 OR field2 > 10)
 *
 */
public class QueryBinaryTerm extends QueryCondition {

	static final long serialVersionUID = 2520409831175134608L;

	public final static String AND = "and";
	public final static String OR = "or";

	public static String[] sOperators = new String[] { AND, OR };

	private QueryCondition mLeftTerm;
	private String mOperator;
	private QueryCondition mRightTerm;

	public QueryBinaryTerm(QueryCondition leftTerm, String operator, QueryCondition rightTerm) {
		super(leftTerm == null ? null : leftTerm.getSchema());

		if (leftTerm == null || operator == null || rightTerm == null)
			throw new IllegalArgumentException();
		validateOperator(operator);
		if (!leftTerm.getSchema().getTypeID().equals(rightTerm.getSchema().getTypeID()))
			throw new IllegalArgumentException("Conditional terms have different schemas.");

		mLeftTerm = leftTerm;
		mOperator = operator;
		mRightTerm = rightTerm;
	}

	@Override
	protected void setAlias(String alias) {
		super.setAlias(alias);
		mLeftTerm.setAlias(alias);
		mRightTerm.setAlias(alias);
	}

	protected void validateOperator(String operator) {
		for (int i = 0; i < sOperators.length; i++) {
			if (operator.equals(sOperators[i]))
				return;
		}
		throw new IllegalArgumentException("Invalid operator " + operator);
	}

	public QueryCondition getLeftTerm() {
		return this.mLeftTerm;
	}

	public String getOperator() {
		return this.mOperator;
	}

	public QueryCondition getRightTerm() {
		return this.mRightTerm;
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj))
			return false;

		QueryBinaryTerm objAsQBinaryTerm = (QueryBinaryTerm) obj;
		if (this.mSchema.equals(objAsQBinaryTerm.mSchema) && this.mLeftTerm.equals(objAsQBinaryTerm.mLeftTerm) && this.mOperator.equals(objAsQBinaryTerm.mOperator) && this.mRightTerm.equals(objAsQBinaryTerm.mRightTerm)) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "(" + mLeftTerm + " " + mOperator + " " + mRightTerm + ")";
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		QueryCondition clonedLeftQueryCondition = (QueryCondition) this.mLeftTerm.clone();
		QueryCondition clonedRightQueryCondition = (QueryCondition) this.mRightTerm.clone();
		QueryBinaryTerm queryBinaryTerm = new QueryBinaryTerm(clonedLeftQueryCondition, this.mOperator, clonedRightQueryCondition);
		return queryBinaryTerm;
	}

}
