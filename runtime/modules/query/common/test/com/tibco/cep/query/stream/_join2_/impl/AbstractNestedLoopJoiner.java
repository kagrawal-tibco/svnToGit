package com.tibco.cep.query.stream._join2_.impl;

import com.tibco.cep.query.stream._join2_.api.*;

/*
* Author: Ashwin Jayaprakash Date: May 29, 2009 Time: 2:34:58 PM
*/
public abstract class AbstractNestedLoopJoiner<L extends View, R extends View>
        implements Joiner<L, R> {
    protected Combiner combiner;

    protected Evaluator<Object[], Boolean> filter;

    protected JoinHandler joinHandler;

    public Combiner getCombiner() {
        return combiner;
    }

    public AbstractNestedLoopJoiner<L, R> setCombiner(Combiner combiner) {
        this.combiner = combiner;

        return this;
    }

    public Evaluator<Object[], Boolean> getFilter() {
        return filter;
    }

    /**
     * @param filter Can be <code>null</code>.
     * @return
     */
    public AbstractNestedLoopJoiner<L, R> setFilter(Evaluator<Object[], Boolean> filter) {
        this.filter = filter;

        return this;
    }

    public JoinHandler getJoinHandler() {
        return joinHandler;
    }

    public AbstractNestedLoopJoiner<L, R> setJoinHandler(JoinHandler joinHandler) {
        this.joinHandler = joinHandler;

        return this;
    }
}