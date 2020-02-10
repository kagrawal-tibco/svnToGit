package com.tibco.store.query.model.impl;

import com.tibco.store.persistence.model.MemoryTuple;
import com.tibco.store.query.model.BinaryOperator;
import com.tibco.store.query.model.QueryExpression;

/**
 * Created by IntelliJ IDEA. User: aathalye Date: 13/12/13 Time: 4:01 PM To
 * change this template use File | Settings | File Templates.
 */
public class EqualsPredicate extends BinaryPredicateImpl {

	public EqualsPredicate(SimpleQueryExpression leftExpression,
			QueryExpression rightExpression, BinaryOperator op) {
		super(leftExpression, rightExpression, op);
		if (op != BinaryOperator.EQ && op != BinaryOperator.NOTEQ) {
			throw new IllegalArgumentException(
					"Only Equals or not equals operator allowed");
		}
	}

	@Override
	public boolean eval(MemoryTuple input) {
		// Evaluate equals
		SimpleQueryExpression leftExpression = getLeftExpression();
		final String operand = leftExpression.getOperand();

		if (rightExpression instanceof ValueExpression) {
			final Object value = ((ValueExpression) rightExpression).getValue();
			Object valueTobeCompared = input.getAttributeValue(operand);

            return (value == null && valueTobeCompared == null)
                    || (valueTobeCompared != null && valueTobeCompared.equals(value));
		}
		return false;
	}

	@Override
	public String toString() {
		String pattern = "( %s = %s )";
		return toString(pattern);
	}
}
