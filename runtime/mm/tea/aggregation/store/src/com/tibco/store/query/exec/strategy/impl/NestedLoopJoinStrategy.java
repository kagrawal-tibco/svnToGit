package com.tibco.store.query.exec.strategy.impl;

import com.tibco.store.persistence.model.MemoryTuple;
import com.tibco.store.persistence.model.invm.InMemorySearchResult;
import com.tibco.store.persistence.service.invm.DataServiceFactory;
import com.tibco.store.query.exec.strategy.JoinStrategy;
import com.tibco.store.query.model.BinaryOperator;
import com.tibco.store.query.model.BinaryPredicate;
import com.tibco.store.query.model.ResultStream;
import com.tibco.store.query.model.impl.EqualsPredicate;
import com.tibco.store.query.model.impl.JoinKey;
import com.tibco.store.query.model.impl.MutableResultStream;
import com.tibco.store.query.model.impl.ValueExpression;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 6/1/14
 * Time: 10:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class NestedLoopJoinStrategy implements JoinStrategy {

    private BinaryPredicate<?> binaryPredicate;

    private JoinKey joinKey;

    public NestedLoopJoinStrategy(BinaryPredicate<?> binaryPredicate, JoinKey joinKey) {
        this.binaryPredicate = binaryPredicate;
        this.joinKey = joinKey;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R extends ResultStream> R filter(R inputResultStream) {
        MutableResultStream outputResultStream = new MutableResultStream();

        for (MemoryTuple tuple : inputResultStream.getTuples()) {
            Object attributeValue = tuple.getAttributeValue(joinKey.getColumnName());
            //Construct predicate on the fly
            EqualsPredicate equalsPredicate =
                    new EqualsPredicate(binaryPredicate.getLeftExpression(), new ValueExpression<Object>(attributeValue), BinaryOperator.EQ);
            InMemorySearchResult searchResult = DataServiceFactory.getInstance().getDataStoreService().lookup(equalsPredicate);
            //If match found we should get 1 result else 0        
            outputResultStream.addMemoryTuples(searchResult.getTuples());           
        }
        return (R) outputResultStream;
    }
}
