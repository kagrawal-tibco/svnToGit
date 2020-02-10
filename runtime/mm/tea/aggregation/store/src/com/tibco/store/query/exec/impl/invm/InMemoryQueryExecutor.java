package com.tibco.store.query.exec.impl.invm;

import com.tibco.store.persistence.PersistenceStoreType;
import com.tibco.store.query.exec.QueryExecutor;
import com.tibco.store.query.exec.impl.QueryExecutionPlanGeneratorFactory;
import com.tibco.store.query.model.Predicate;
import com.tibco.store.query.model.Query;
import com.tibco.store.query.model.QueryResultSet;
import com.tibco.store.query.model.ResultStream;
import com.tibco.store.query.model.impl.DefaultQueryResultSet;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 28/11/13
 * Time: 12:07 PM
 *
 * Basic in memory query executor which will work of in memory db.
 */
public class InMemoryQueryExecutor implements QueryExecutor {

    @Override
    public QueryResultSet executeQuery(Query<Predicate> query) {
        if (query == null) {
            throw new IllegalArgumentException("Query parameter cannot be null");
        }
        InMemoryQueryExecutionPlanGenerator memoryQueryExecutionPlanGenerator =
                (InMemoryQueryExecutionPlanGenerator) QueryExecutionPlanGeneratorFactory.createExecutionPlanGenerator(PersistenceStoreType.MEMORY, query);
        InMemoryQueryExecutionPlan memoryQueryExecutionPlan = memoryQueryExecutionPlanGenerator.generateExecutionPlan(query);
        DefaultPredicateEvaluationTreeVisitor predicateEvaluationTreeVisitor = new DefaultPredicateEvaluationTreeVisitor();
        ResultStream resultStream = memoryQueryExecutionPlan.execute(predicateEvaluationTreeVisitor);
        return new DefaultQueryResultSet(resultStream);
    }
}
