package com.tibco.store.query.model.impl;


import com.tibco.store.query.model.OptimizerHint;
import com.tibco.store.query.model.Predicate;
import com.tibco.store.query.model.Query;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 18/12/13
 * Time: 10:23 AM
 * To change this template use File | Settings | File Templates.
 */
public class QueryImpl<P extends Predicate> implements Query<P> {

    private P rootPredicate;

    private OptimizerHint optimizerHint = OptimizerHint.NONE;

    @Override
    public void setPredicate(P predicate) {
        this.rootPredicate = predicate;
    }

    @Override
    public void setOptimizerHint(OptimizerHint optimizerHint) {
        this.optimizerHint = optimizerHint;
    }

    @Override
    public P getPredicate() {
        return rootPredicate;
    }

    @Override
    public OptimizerHint getOptimizerHint() {
        return optimizerHint;
    }

    @Override
    public String toString() {
        return "{" +
                "rootPredicate=" + rootPredicate +
                '}';
    }
}
