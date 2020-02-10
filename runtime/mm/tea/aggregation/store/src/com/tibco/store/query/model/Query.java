package com.tibco.store.query.model;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 18/12/13
 * Time: 10:20 AM
 * To change this template use File | Settings | File Templates.
 */
public interface Query<P extends Predicate> {

    public void setPredicate(P predicate);

    public P getPredicate();

    public void setOptimizerHint(OptimizerHint optimizerHint);

    public OptimizerHint getOptimizerHint();
}
