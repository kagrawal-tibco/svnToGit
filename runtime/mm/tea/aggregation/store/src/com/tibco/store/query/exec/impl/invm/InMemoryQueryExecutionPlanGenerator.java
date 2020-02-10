package com.tibco.store.query.exec.impl.invm;

import com.tibco.store.query.exec.PredicateEvaluator;
import com.tibco.store.query.exec.PredicateTreeVisitor;
import com.tibco.store.query.exec.QueryExecutionPlanGenerator;
import com.tibco.store.query.exec.impl.SimplePredicateTreeVisitor;
import com.tibco.store.query.model.Predicate;
import com.tibco.store.query.model.Query;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 27/11/13
 * Time: 1:28 PM
 *
 * Generate an execution plan for queries on data in memory.
 */
public class InMemoryQueryExecutionPlanGenerator implements QueryExecutionPlanGenerator<InMemoryQueryExecutionPlan> {

    @Override
    public <P extends Predicate> InMemoryQueryExecutionPlan generateExecutionPlan(Query<P> query) {
        //Root predicate can be null;
        InMemoryQueryExecutionPlan memoryQueryExecutionPlan = new InMemoryQueryExecutionPlan();
        P rootPredicate = query.getPredicate();

        if (rootPredicate != null) {
            generatePlan(rootPredicate, memoryQueryExecutionPlan);
        }
        return memoryQueryExecutionPlan;
    }

    /**
     * Create a plan using Breadth first traversal of predicates.
     *
     */
    protected <P extends Predicate> void generatePlan(P rootPredicate, InMemoryQueryExecutionPlan memoryQueryExecutionPlan) {
        PredicateTreeVisitor predicateTreeVisitor = new SimplePredicateTreeVisitor();
        rootPredicate.accept(predicateTreeVisitor);
        //Get result
        PredicateEvaluator rootPredicateEvaluator = predicateTreeVisitor.getResult();
        memoryQueryExecutionPlan.addEvaluator(rootPredicateEvaluator);
    }
}
