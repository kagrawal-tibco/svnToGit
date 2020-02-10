package com.tibco.store.query.exec;

import com.tibco.store.query.model.Predicate;
import com.tibco.store.query.model.Query;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 27/11/13
 * Time: 11:16 AM
 *
 * Generate an execution plan specific to the type of persistence store.
 */
public interface QueryExecutionPlanGenerator<T extends QueryExecutionPlan> {

    /**
     * Generate a hierarchical execution plan from query model.
     *
     */
    public <P extends Predicate> T generateExecutionPlan(Query<P> query);
}
