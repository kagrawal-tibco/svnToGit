package com.tibco.cep.query.stream._join2_.impl;

import com.tibco.cep.query.stream._join2_.api.Combiner;
import com.tibco.cep.query.stream._join2_.api.Evaluator;
import com.tibco.cep.query.stream._join2_.api.JoinHandler;
import com.tibco.cep.query.stream._join2_.api.View;
import com.tibco.cep.query.stream.util.CustomIterable;
import com.tibco.cep.query.stream.util.ReusableIterator;

/*
* Author: Ashwin Jayaprakash Date: May 29, 2009 Time: 2:34:58 PM
*/
public class SimpleNestedLoopJoiner<L extends View, R extends View>
        extends AbstractNestedLoopJoiner<L, R> {
    @Override
    public SimpleNestedLoopJoiner<L, R> setCombiner(Combiner combiner) {
        super.setCombiner(combiner);

        return this;
    }

    @Override
    public SimpleNestedLoopJoiner<L, R> setFilter(Evaluator<Object[], Boolean> filter) {
        super.setFilter(filter);

        return this;
    }

    @Override
    public SimpleNestedLoopJoiner<L, R> setJoinHandler(JoinHandler joinHandler) {
        super.setJoinHandler(joinHandler);

        return this;
    }

    @SuppressWarnings({"unchecked"})
    public void attemptJoin(L lhs, R rhs) {
        CustomIterable lhsIterable = lhs.getAll();
        CustomIterable rhsIterable = rhs.getAll();

        joinHandler.batchStart();
        try {
            ReusableIterator rhsIterator = rhsIterable.iterator();

            outerLoop:
            for (Object lhsItem : lhsIterable) {
                rhsIterator.reset();
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
        }
    }
}
