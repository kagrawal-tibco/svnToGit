package com.tibco.store.query.exec;

import com.tibco.store.query.model.Predicate;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 10/12/13
 * Time: 10:22 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractSimplePredicateEvaluator<P extends Predicate> implements PredicateEvaluator {

    protected P wrappedPredicate;

    protected AbstractSimplePredicateEvaluator(P wrappedPredicate) {
        this.wrappedPredicate = wrappedPredicate;
    }

    public P getWrappedPredicate() {
        return wrappedPredicate;
    }
}
