package com.tibco.store.query.model.impl;

import com.tibco.store.persistence.model.MemoryTuple;
import com.tibco.store.query.model.BinaryOperator;
import com.tibco.store.query.model.QueryExpression;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 13/12/13
 * Time: 4:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class LessThanOrEqualToPredicate extends BinaryPredicateImpl {

    public LessThanOrEqualToPredicate(SimpleQueryExpression leftExpression,
                                      QueryExpression rightExpression,
                                      BinaryOperator op) {
        super(leftExpression, rightExpression, op);
        if (op != BinaryOperator.LE) {
            throw new IllegalArgumentException("Only <= operator allowed");
        }
    }

    @Override
    public boolean eval(MemoryTuple input) {
        //Evaluate le
        LessThanPredicate lessThanPredicate = new LessThanPredicate(leftExpression, rightExpression, BinaryOperator.LT);
        EqualsPredicate equalsPredicate = new EqualsPredicate(leftExpression, rightExpression, BinaryOperator.EQ);

        return lessThanPredicate.eval(input) || equalsPredicate.eval(input);
    }
//
    @Override
    public String toString() {
        String pattern = "( %s <= %s )";
        return toString(pattern);
    }
}
