package com.tibco.store.query.exec.impl.invm;

import com.tibco.store.query.exec.PredicateEvaluationTreeVisitor;
import com.tibco.store.query.exec.PredicateEvaluator;
import com.tibco.store.query.exec.QueryExecutionPlan;
import com.tibco.store.query.model.impl.MutableResultStream;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 27/11/13
 * Time: 12:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class InMemoryQueryExecutionPlan implements QueryExecutionPlan {

    /**
     * Execution plan will evaluate context followed by predicate evaluation followed
     * by misc like order by etc.
     */
    private SimplePredicateEvaluationTree predicateEvaluationTree;


    public <P extends PredicateEvaluator> void addEvaluator(P predicateEvaluator) {
        if (predicateEvaluationTree == null) {
            predicateEvaluationTree = new SimplePredicateEvaluationTree();
        }
        predicateEvaluationTree.addEvaluator(predicateEvaluator);
    }

    @Override
    @SuppressWarnings("unchecked")
    public MutableResultStream execute(PredicateEvaluationTreeVisitor predicateEvaluationTreeVisitor) {
        return predicateEvaluationTree.accept(predicateEvaluationTreeVisitor);
    }
}
