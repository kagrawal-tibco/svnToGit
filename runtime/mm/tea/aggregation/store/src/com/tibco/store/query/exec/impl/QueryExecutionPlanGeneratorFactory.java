package com.tibco.store.query.exec.impl;

import com.tibco.store.persistence.PersistenceStoreType;
import com.tibco.store.query.exec.QueryExecutionPlanGenerator;
import com.tibco.store.query.exec.impl.invm.CostBasedQueryExecutionPlanGenerator;
import com.tibco.store.query.exec.impl.invm.InMemoryQueryExecutionPlanGenerator;
import com.tibco.store.query.model.OptimizerHint;
import com.tibco.store.query.model.Query;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 28/11/13
 * Time: 12:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class QueryExecutionPlanGeneratorFactory {

    //TODO get projection
    public static QueryExecutionPlanGenerator<?> createExecutionPlanGenerator(PersistenceStoreType persistenceStoreType, Query<?> query) {
        switch (persistenceStoreType) {
            case MEMORY :
                if (query.getOptimizerHint() == OptimizerHint.NONE) {
                    return new InMemoryQueryExecutionPlanGenerator();
                } else if (query.getOptimizerHint() == OptimizerHint.COUNT) {
                    return new CostBasedQueryExecutionPlanGenerator();
                }

            default :
                throw new UnsupportedOperationException("TBD");
        }
    }
}
