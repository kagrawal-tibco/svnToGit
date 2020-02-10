package com.tibco.store.query.exec.strategy.impl;

import com.tibco.store.persistence.model.MemoryTuple;
import com.tibco.store.query.exec.strategy.PredicateStrategy;
import com.tibco.store.query.exec.util.PredicateUtils;
import com.tibco.store.query.model.BinaryOperator;
import com.tibco.store.query.model.BinaryPredicate;
import com.tibco.store.query.model.Predicate;
import com.tibco.store.query.model.QueryExpression;
import com.tibco.store.query.model.ResultStream;
import com.tibco.store.query.model.impl.MutableResultStream;
import com.tibco.store.query.model.impl.PredicateFactory;
import com.tibco.store.query.model.impl.SimpleQueryExpression;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 12/12/13
 * Time: 10:40 AM
 *
 * Pipeline output of 1 as input to other.
 */
public class SimplePipelineStrategy implements PredicateStrategy {

    private Predicate predicateForFilter;

    public SimplePipelineStrategy(Predicate predicateForFilter) {
        this.predicateForFilter = predicateForFilter;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R extends ResultStream> R filter(R inputResultStream) {
        if (predicateForFilter instanceof BinaryPredicate) {
            BinaryPredicate binaryPredicate = (BinaryPredicate) predicateForFilter;
            MutableResultStream mutableResultStream = new MutableResultStream();
            mutableResultStream.addMemoryTuples(filterTuples(inputResultStream.getTuples(), binaryPredicate));

            return (R) mutableResultStream;
        }
        return null;
    }

    private Collection<MemoryTuple> filterTuples(Collection<MemoryTuple> inputTuples, final BinaryPredicate binaryPredicate) {
        final SimpleQueryExpression leftExpression = binaryPredicate.getLeftExpression();
        final QueryExpression rightExpression = binaryPredicate.getRightExpression();
        final BinaryOperator binaryOperator = binaryPredicate.getBinaryOperator();

        return PredicateUtils.filter(inputTuples, new com.google.common.base.Predicate<MemoryTuple>() {
            @Override
            public boolean apply(MemoryTuple input) {
                return PredicateFactory.createBinaryPredicate(leftExpression, rightExpression, binaryOperator).eval(input);
            }
        });
    }
}
