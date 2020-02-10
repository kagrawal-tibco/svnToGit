package com.tibco.store.query.model.impl;

import com.tibco.store.query.exec.PredicateTreeVisitor;
import com.tibco.store.query.model.LogicalPredicate;
import com.tibco.store.query.model.Predicate;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 10/12/13
 * Time: 10:32 AM
 * <p/>
 * As name suggests performs an AND operation.
 */
public class AndPredicate implements LogicalPredicate {

    private Predicate childPredicate1;

    private Predicate childPredicate2;

    AndPredicate() {
    }

    @Override
    public <P extends Predicate> void setChildPredicates(P predicate1, P predicate2) {
        this.childPredicate1 = predicate1;
        this.childPredicate2 = predicate2;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <P extends Predicate> P[] getChildPredicates() {
        Predicate[] childPredicates = new Predicate[2];
        childPredicates[0] = childPredicate1;
        childPredicates[1] = childPredicate2;

        return (P[]) childPredicates;
    }

    @Override
    public <V extends PredicateTreeVisitor> void accept(V predicateTreeVisitor) {
        predicateTreeVisitor.visit(this);
    }

    @Override
    public String toString() {
        return "(" + childPredicate1.toString() + " AND " + childPredicate2.toString() + ")";
    }
}
