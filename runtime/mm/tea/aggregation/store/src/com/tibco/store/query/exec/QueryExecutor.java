package com.tibco.store.query.exec;

import com.tibco.store.query.model.Predicate;
import com.tibco.store.query.model.Query;
import com.tibco.store.query.model.QueryResultSet;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 28/11/13
 * Time: 12:03 PM
 *
 * Generic query executor. Each Persistence layer will implement this.
 *
 */
public interface QueryExecutor {

    /**
     * Executes a query definition and returns a result set
     * Clients can query the result set using hasNext and next APIs.
     * @param
     * @return
     */
    public QueryResultSet executeQuery(Query<Predicate> query);
}
