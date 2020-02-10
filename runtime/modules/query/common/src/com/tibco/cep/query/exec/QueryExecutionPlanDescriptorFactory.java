package com.tibco.cep.query.exec;

import com.tibco.cep.query.exec.descriptors.QueryExecutionPlanDescriptor;
import com.tibco.cep.query.service.Query;

/*
 * User: nprade
 * Date: Nov 5, 2007
 * Time: 5:09:49 PM
 */

/**
 * Builds execution plan descriptors.
 */
public interface QueryExecutionPlanDescriptorFactory {


    /**
     * Creates a descriptor of execution plan for a query.
     *
     * @param query Query for which a plan descriptor will be built.
     * @param regionName String name of the region queried.
     * @throws Exception upon error.
     * @return QueryExecutionPlanDescriptor
     */
    QueryExecutionPlanDescriptor newQueryExecutionPlanDescriptor(
            Query query,
            String regionName)
            throws Exception;
    
}