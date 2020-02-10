package com.tibco.store.query.model.impl;

import com.tibco.store.persistence.model.MemoryTuple;
import com.tibco.store.query.model.BinaryOperator;
import com.tibco.store.query.model.BinaryPredicate;
import com.tibco.store.query.model.Predicate;
import com.tibco.store.query.model.QueryExpression;
import com.tibco.store.query.model.UnaryExpression;
import com.tibco.store.query.model.UnaryOperator;
import com.tibco.store.query.model.UnaryPredicate;

public class PredicateFactory {

    public static <P extends Predicate> AndPredicate createAndPredicate(P childPredicate1, P childPredicate2) {
        if (childPredicate1 == null || childPredicate2 == null) {
            throw new IllegalArgumentException("Child predicates for AND cannot be null");
        }
        AndPredicate andPredicate = new AndPredicate();
        andPredicate.setChildPredicates(childPredicate1, childPredicate2);
        return andPredicate;
    }

    public static <P extends Predicate> OrPredicate createOrPredicate(P childPredicate1, P childPredicate2) {
        if (childPredicate1 == null || childPredicate2 == null) {
            throw new IllegalArgumentException("Child predicates for AND cannot be null");
        }
        OrPredicate orPredicate = new OrPredicate();
        orPredicate.setChildPredicates(childPredicate1, childPredicate2);
        return orPredicate;
    }

    public static <Q extends QueryExpression> BinaryPredicate<MemoryTuple> createBinaryPredicate(SimpleQueryExpression leftExpression,
                                                                                                 Q rightExpression,
                                                                                                 BinaryOperator binaryOperator) {
        if (leftExpression == null) {
            throw new IllegalArgumentException("Binary expression cannot be null");
        }
        if (binaryOperator == null) {
            throw new IllegalArgumentException("Binary operator cannot be null");
        }

        switch (binaryOperator) {
            case LT :
               return new LessThanPredicate(leftExpression, rightExpression, binaryOperator);
            case GT :
               return new GreaterThanPredicate(leftExpression, rightExpression, binaryOperator);
            case EQ :
               return new EqualsPredicate(leftExpression, rightExpression, binaryOperator);
            case NEQ :
                return new NEqualsPredicate(leftExpression, rightExpression, binaryOperator);
            case LE :
                return new LessThanOrEqualToPredicate(leftExpression, rightExpression, binaryOperator);
            case GE :
                return new GreaterThanEqualToPredicate(leftExpression, rightExpression, binaryOperator);
            case NOTEQ :                
            	return new NotPredicate(leftExpression, rightExpression, binaryOperator);
        }
        throw new IllegalArgumentException("Illegal predicate type");
    }

    public static <U extends UnaryExpression> UnaryPredicate createUnaryPredicate(U unaryExpression, UnaryOperator unaryOperator) {
        if (unaryExpression == null) {
            throw new IllegalArgumentException("Unary expression cannot be null");
        }
        if (unaryOperator == null) {
            throw new IllegalArgumentException("Unary operator cannot be null");
        }
        //TODO
        throw new UnsupportedOperationException("To Be Done");
    }
}
