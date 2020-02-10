package com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model;

import com.tibco.cep.dashboard.common.data.FieldValue;
import com.tibco.cep.dashboard.common.data.TupleSchema;
import com.tibco.cep.dashboard.common.utils.ObjectUtil;
import com.tibco.cep.dashboard.common.utils.StringUtil;

/**
 * A single conditional predicate term. E.g. field1 = 10
 */
public class QueryPredicate extends QueryCondition {

	static final long serialVersionUID = 340166438179266950L;

	public final static String EQ = "=";
	public final static String GT = ">";
	public final static String GE = ">=";
	public final static String LT = "<";
	public final static String LE = "<=";
	public final static String NE = "<>";
	public final static String NEQ = "!=";
	public final static String LIKE = "like";
	public final static String IN = "in";
	public final static String IS_NULL = "is null";
	public final static String IS_NOT_NULL = "is not null";

	public static String[] sComparisons = new String[] { EQ, GT, GE, LT, LE, NE, NEQ, LIKE, IN, IS_NULL, IS_NOT_NULL };

	protected QueryExpression mLeftArgument;
	protected String mComparison;
	protected QueryExpression mRightArgument;

	protected QueryPredicate(TupleSchema schema) {
		super(schema);
	}

	public QueryPredicate(TupleSchema schema, String fieldName, String comparison) {
		this(schema, new QueryFieldName(schema, fieldName), comparison);
	}

	public QueryPredicate(TupleSchema schema, QueryExpression leftArgument, String comparison) {
		super(schema);
		if (!comparison.equals(IS_NULL) && !comparison.equals(IS_NOT_NULL))
			throw new IllegalArgumentException("Comparison " + comparison + " requires two fields.");
		validateArgs(schema, leftArgument, comparison, null);
		setLeftArgument(leftArgument);
		mComparison = comparison;
	}

	public QueryPredicate(TupleSchema schema, String fieldName, String comparison, FieldValue value) {
		this(schema, new QueryFieldName(schema, fieldName), comparison, new QueryLiteralValue(schema, value));
	}

	public QueryPredicate(TupleSchema schema, QueryExpression leftArgument, String comparison, QueryExpression rightArgument) {
		super(schema);
		validateArgs(schema, leftArgument, comparison, rightArgument);
		setLeftArgument(leftArgument);
		mComparison = comparison;
		//PATCH quick fix for BE-20075 Anand 2.16.2014 10:48 AM, need a better solution later
		if (comparison.equals(IS_NULL) != true && comparison.equals(IS_NOT_NULL) != true) {
			mRightArgument = rightArgument;
		}
	}

	protected void validateArgs(TupleSchema schema, QueryExpression leftArgument, String comparison, QueryExpression rightArgument) throws IllegalArgumentException {
		if (schema == null) {
			throw new IllegalArgumentException("Invalid Schema");
		}
		//validate the left argument
		leftArgument.validate();
		//validate the comparison
		validateComparision(leftArgument, comparison, rightArgument);
		//PATCH quick fix for BE-20075 Anand 2.16.2014 10:48 AM, need a better solution later
		//validate the null right argument for single value comparisons
//		if (comparison.equals(IS_NULL) == false && comparison.equals(IS_NOT_NULL) == false && rightArgument == null) {
//			throw new IllegalArgumentException("Invalid value[" + rightArgument + "] for field[" + leftArgument.toString() + "]");
//		}
		//validate the right argument if present
		if (rightArgument != null) {
			rightArgument.validate();
		}
	}

	protected void validateComparision(QueryExpression leftArgument, String comparison, QueryExpression rightArgument) {
		if (StringUtil.isEmptyOrBlank(comparison) == false) {
			for (int i = 0; i < sComparisons.length; i++) {
				if (comparison.equals(sComparisons[i]) == true) {
					return;
				}
			}
		}
		throw new IllegalArgumentException("Invalid comparison[" + comparison + "] for [" + leftArgument.toString() + "]");
	}

	private void setLeftArgument(QueryExpression leftArgument) {
		mLeftArgument = leftArgument;
		mLeftArgument.setAlias(mAlias);
	}

	public QueryExpression getLeftArgument() {
		return mLeftArgument;
	}

	public FieldValue evalLeftArgument() {
		return mLeftArgument.eval();
	}

	public void updateLeftArgument(QueryExpression expression) {
		if (expression == null) {
			throw new IllegalArgumentException();
		}
		if (expression.getSchema().equals(this.mSchema) == false) {
			throw new IllegalArgumentException();
		}
		expression.validate();
		this.mLeftArgument = expression;
	}

	public String getComparison() {
		return this.mComparison;
	}

	public QueryExpression getRightArgument() {
		return mRightArgument;
	}

	public FieldValue evalRightArgument() {
		return mRightArgument == null ? null : mRightArgument.eval();
	}

	public void updateRightArgument(QueryExpression expression) {
		if (expression == null) {
			throw new IllegalArgumentException();
		}
		if (expression.getSchema().equals(this.mSchema) == false) {
			throw new IllegalArgumentException();
		}
		expression.validate();
		mRightArgument = expression;
	}

	public void updateComparision(String comparision, QueryExpression rightArgument) {
		validateArgs(mSchema, mLeftArgument, comparision, rightArgument);
		mComparison = comparision;
		mRightArgument = rightArgument;
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj))
			return false;

		QueryPredicate castedObj = (QueryPredicate) obj;

		if (ObjectUtil.equalWithNull(this.mSchema, castedObj.mSchema)
				&& ObjectUtil.equalWithNull(this.mLeftArgument, castedObj.mLeftArgument)
				&& ObjectUtil.equalWithNull(this.mComparison, castedObj.mComparison)
				&& ObjectUtil.equalWithNull(this.mRightArgument, castedObj.mRightArgument)) {
			return true;
		}

		return false;
	}

	@Override
	protected void setAlias(String alias) {
		super.setAlias(alias);
		mLeftArgument.setAlias(alias);
		if (mRightArgument != null) {
			mRightArgument.setAlias(alias);
		}
	}

	// toString() is used for hashcode calculation.
	@Override
	public String toString() {
		if (mComparison.equals(IS_NULL) == true || mComparison.equals(IS_NOT_NULL) == true) {
			return mLeftArgument + " " + mComparison;
		}
		return mLeftArgument + " " + mComparison + " " + mRightArgument;
	}

	public Object clone() throws CloneNotSupportedException {
		try {
			QueryPredicate predicate;

			if (mComparison.equals(IS_NULL) || mComparison.equals(IS_NOT_NULL)) {
				predicate = new QueryPredicate(super.getSchema(), this.mLeftArgument, this.mComparison);
			} else {
				predicate = new QueryPredicate(super.getSchema(), this.mLeftArgument, this.mComparison, mRightArgument == null ? null : (QueryExpression) this.mRightArgument.clone());
			}
			predicate.setAlias(mAlias);
			return predicate;
		} catch (Exception e) {
			throw new CloneNotSupportedException("could not create a clone of the value");
		}
	}

}
