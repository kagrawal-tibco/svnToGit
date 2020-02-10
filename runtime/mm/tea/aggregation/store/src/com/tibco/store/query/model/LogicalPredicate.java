package com.tibco.store.query.model;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 10/12/13
 * Time: 10:30 AM
 * To change this template use File | Settings | File Templates.
 */
public interface LogicalPredicate extends Predicate {

    /**
     * Logical predicate only works on 2 child predicates at a time.
     * @param predicate1
     * @param predicate2
     */
    <P extends Predicate> void setChildPredicates(P predicate1, P predicate2);

    /**
     *
     * @return
     */
    <P extends Predicate> P[] getChildPredicates();
}
