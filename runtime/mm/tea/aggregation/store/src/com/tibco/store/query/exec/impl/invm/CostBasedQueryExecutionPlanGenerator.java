package com.tibco.store.query.exec.impl.invm;

import com.tibco.store.query.exec.PredicateEvaluator;
import com.tibco.store.query.exec.PredicateTreeVisitor;
import com.tibco.store.query.exec.impl.CostBasedPredicateTreeVisitor;
import com.tibco.store.query.model.Predicate;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 16/12/13
 * Time: 11:30 AM
 *
 * Generate plan based on cost of evaluation of a predicate.
 */
public class CostBasedQueryExecutionPlanGenerator extends InMemoryQueryExecutionPlanGenerator {

    /**
     * Create a plan using Depth first traversal of predicates.
     *
     */
    @Override
    protected <P extends Predicate> void generatePlan(P rootPredicate, InMemoryQueryExecutionPlan memoryQueryExecutionPlan) {
        PredicateTreeVisitor predicateTreeVisitor = new CostBasedPredicateTreeVisitor();
        rootPredicate.accept(predicateTreeVisitor);
        //Get result
        PredicateEvaluator rootPredicateEvaluator = predicateTreeVisitor.getResult();
        memoryQueryExecutionPlan.addEvaluator(rootPredicateEvaluator);
    }
}
