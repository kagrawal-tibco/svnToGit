package com.tibco.store.query.exec;

import com.tibco.store.query.model.ResultStream;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 29/11/13
 * Time: 10:38 AM
 *
 * Abstract representation of predicates in the query used by the
 * execution plan for query execution.
 */
public interface PredicateEvaluationTree {

    /**
     *
     * @param predicateEvaluator
     * @param <P>
     */
    public <P extends PredicateEvaluator> void addEvaluator(P predicateEvaluator);


    /**
     * Visitor for issuing callbacks when each node type
     * in the evaluation tree is visited.
     * @param visitor
     * @param <V>
     */
    public <V extends PredicateEvaluationTreeVisitor, R extends ResultStream> R accept(V visitor);
}
