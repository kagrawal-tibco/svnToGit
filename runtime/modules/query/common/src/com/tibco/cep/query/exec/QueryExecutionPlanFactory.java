package com.tibco.cep.query.exec;

import java.util.List;

/*
 * User: nprade
 * Date: Nov 5, 2007
 * Time: 5:09:49 PM
 */

/**
 * Builds QueryExecutionPlan instances for a given query.
 */
public interface QueryExecutionPlanFactory {


    /**
     * Gets the names of the columns returned by this query.
     * @return List String names of the columns returned by this query.
     */
    List<String> getColumnNames();


    /**
     * Gets the names of the types of the columns returned by this query.
     * @return List String names of the types of the columns returned by this query.
     */
    List<String> getColumnTypeNames();


    /**
     * Gets the names of the sources used by this query.
     *
     * @return List String names of the sources used by this query.
     */
    List<String> getSourceNames();


    /**
     * Creates an execution plan for a query.
     *
     * @param name       String name of the plan.
     * @param continuous boolean true is the plan is for a continuous execution.
     * @return QueryExecutionPlan
     * @throws Exception upon error.
     */
    QueryExecutionPlan newQueryExecutionPlan(
            String name,
            boolean continuous)
            throws Exception;

}
