package com.tibco.store.query.exec;

import com.tibco.store.query.model.ResultStream;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 27/11/13
 * Time: 10:35 AM
 *
 * Generates an execution plan for a query based on the filter criteria.
 * <p>
 *     An execution plan consists of filter evaluators and the order
 *     in which they would get executed for query predicates.
 *     It is hierarchical in nature to mimic the query AST for now.
 * </p>
 * <p>
 *     For query of the form service = "s1" and hitCount > 50, the plan would look like
 *
 *     Composite(and)
 *           |
 *    ---------------
 *    |              |
 *   Simple(service) Simple(hitCount)
 * </p>
 */
public interface QueryExecutionPlan {

    /**
     * Execute query plan. The execution follows something
     * equivalent of SAX parser where each execution evaluator
     * encountered sends a notification to interested listener
     * to be able to take action based on it.
     */
    public <R extends ResultStream, V extends PredicateEvaluationTreeVisitor> R execute(V visitor);
}
