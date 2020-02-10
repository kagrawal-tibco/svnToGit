package com.tibco.store.query.model.impl;

import com.tibco.store.persistence.model.MemoryTuple;
import com.tibco.store.query.model.BinaryOperator;
import com.tibco.store.query.model.QueryExpression;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 13/12/13
 * Time: 4:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class GreaterThanEqualToPredicate extends BinaryPredicateImpl {

    public GreaterThanEqualToPredicate(SimpleQueryExpression leftExpression,
                                       QueryExpression rightExpression,
                                       BinaryOperator op) {
        super(leftExpression, rightExpression, op);
        if (op != BinaryOperator.GE) {
            throw new IllegalArgumentException("Only >= operator allowed");
        }
    }

    @Override
    public boolean eval(MemoryTuple input) {
        //Evaluate ge
        GreaterThanPredicate greaterThanPredicate = new GreaterThanPredicate(leftExpression, rightExpression, BinaryOperator.GT);
        EqualsPredicate equalsPredicate = new EqualsPredicate(leftExpression, rightExpression, BinaryOperator.EQ);

        return greaterThanPredicate.eval(input) || equalsPredicate.eval(input);
    }

    @Override
    public String toString() {
        String pattern = "( %s >= %s )";
        return toString(pattern);
    }
}
