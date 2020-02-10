package com.tibco.store.query.model.impl;

import com.tibco.store.persistence.model.MemoryTuple;
import com.tibco.store.query.model.BinaryOperator;
import com.tibco.store.query.model.QueryExpression;

public class NotPredicate extends EqualsPredicate {

    public NotPredicate(SimpleQueryExpression leftExpression,
                        QueryExpression rightExpression,
                        BinaryOperator op) {
        super(leftExpression, rightExpression, op);
        if (op != BinaryOperator.NOTEQ) {
            throw new IllegalArgumentException("Only NOT Equal operator allowed");
        }
    }

    @Override
    public boolean eval(MemoryTuple input) {
        return !super.eval(input);
    }

    @Override
    public String toString() {
        String pattern = "( %s != %s )";
        return toString(pattern);
    }
}
