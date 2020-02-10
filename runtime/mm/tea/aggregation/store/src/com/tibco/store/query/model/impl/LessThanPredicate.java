package com.tibco.store.query.model.impl;

import java.sql.Timestamp;

import com.tibco.store.persistence.model.MemoryTuple;
import com.tibco.store.persistence.model.invm.impl.CompareFactory;
import com.tibco.store.query.model.BinaryOperator;
import com.tibco.store.query.model.QueryExpression;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 13/12/13
 * Time: 3:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class LessThanPredicate extends BinaryPredicateImpl {

    public LessThanPredicate(SimpleQueryExpression leftExpression,
                             QueryExpression rightExpression,
                             BinaryOperator op) {
        super(leftExpression, rightExpression, op);
        if (op != BinaryOperator.LT) {
            throw new IllegalArgumentException("Only Less than operator allowed");
        }
    }

    public boolean eval(MemoryTuple input) {
        //Evaluate less than
        SimpleQueryExpression leftExpression = getLeftExpression();
        final String operand = leftExpression.getOperand();

        if (rightExpression instanceof ValueExpression) {
            Object value = ((ValueExpression)rightExpression).getValue();
            if (value instanceof Number) {
                //Only for numeric values this makes sense
                Object valueTobeCompared = input.getAttributeValue(operand);

                return (valueTobeCompared instanceof Number)
                        && CompareFactory.compareLT(valueTobeCompared, value);
            } else if (value instanceof Timestamp){
            	Object valueTobeCompared = input.getAttributeValue(operand);
            	
            	return (valueTobeCompared instanceof Timestamp)
                        && CompareFactory.compareLT(valueTobeCompared, value);
            }
        }
        return false;
    }

    @Override
    public String toString() {
        String pattern = "( %s <= %s )";
        return toString(pattern);
    }
}
