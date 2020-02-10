package com.tibco.cep.query.stream._join2_.impl;

import com.tibco.cep.query.stream._join2_.api.*;
import com.tibco.cep.query.stream.util.CustomIterable;
import com.tibco.cep.query.stream.util.ReusableIterator;

/*
* Author: Ashwin Jayaprakash Date: May 29, 2009 Time: 5:41:38 PM
*/
public class IndexedNestedLoopJoiner<L extends View, R extends IndexedView>
        extends AbstractNestedLoopJoiner<L, R> {
    Evaluator<Object, Comparable> leftSearchKeyExtractor;

    IndexRetriever<Comparable, Object> rightValueRetriever;

    @Override
    public IndexedNestedLoopJoiner<L, R> setCombiner(Combiner combiner) {
        super.setCombiner(combiner);

        return this;
    }

    @Override
    public IndexedNestedLoopJoiner<L, R> setFilter(Evaluator<Object[], Boolean> filter) {
        super.setFilter(filter);

        return this;
    }

    @Override
    public IndexedNestedLoopJoiner<L, R> setJoinHandler(JoinHandler joinHandler) {
        super.setJoinHandler(joinHandler);

        return this;
    }

    public Evaluator<Object, Comparable> getLeftSearchKeyExtractor() {
        return leftSearchKeyExtractor;
    }

    public IndexedNestedLoopJoiner<L, R> setLeftSearchKeyExtractor(
            Evaluator<Object, Comparable> leftSearchKeyExtractor) {
        this.leftSearchKeyExtractor = leftSearchKeyExtractor;

        return this;
    }

    public IndexRetriever<Comparable, Object> getRightValueRetriever() {
        return rightValueRetriever;
    }

    public IndexedNestedLoopJoiner<L, R> setRightValueRetriever(
            IndexRetriever<Comparable, Object> rightValueRetriever) {
        this.rightValueRetriever = rightValueRetriever;

        return this;
    }

    //---------------

    @SuppressWarnings({"unchecked"})
    public void attemptJoin(L lhs, R rhs) {
        CustomIterable lhsIterable = lhs.getAll();

        rightValueRetriever.batchStart(rhs);
        joinHandler.batchStart();
        try {
            outerLoop:
            for (Object lhsItem : lhsIterable) {
                Comparable leftSearchKey = leftSearchKeyExtractor.evaluate(lhsItem);

                ReusableIterator rhsIterator = rightValueRetriever.retrieve(leftSearchKey);
                while (rhsIterator.hasNext()) {
                    Object rhsItem = rhsIterator.next();

                    Object[] joinedItems = combiner.combine(lhsItem, rhsItem);

                    boolean pass = filter == null || filter.evaluate(joinedItems);
                    if (pass) {
                        joinHandler.onJoin(joinedItems);

                        if (joinHandler.continueBatch() == false) {
                            break outerLoop;
                        }
                    }
                }
            }
        }
        finally {
            joinHandler.batchEnd();
            rightValueRetriever.batchEnd();
        }
    }
}