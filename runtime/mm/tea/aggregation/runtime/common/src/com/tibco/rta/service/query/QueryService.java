package com.tibco.rta.service.query;

import com.tibco.rta.common.service.StartStopService;
import com.tibco.rta.query.QueryDef;
import com.tibco.rta.query.QueryResultTuple;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 29/1/13
 * Time: 2:29 PM
 * To change this template use File | Settings | File Templates.
 */
public interface QueryService extends StartStopService {

    /**
     * Register a query with the metric engine.
     * @throws Exception
     */
    public String registerQuery(QueryDef queryDef) throws Exception;

    /**
     * Unregister a query with the metric engine.
     * @throws Exception
     */
    public QueryDef unregisterQuery(String queryId) throws Exception;

    /**
     * Used by snapshot queries to check if the associated
     * {@link com.tibco.rta.query.Browser} has anything to query.
     */
    public boolean hasNext(String correlationId) throws Exception;

    /**
     * Used by snapshot query to fetch next set of tuples.
     * @see com.tibco.rta.query.Browser
     */
    public List<QueryResultTuple> getNext(String correlationId) throws Exception;

    /**
     * Remove browser mapping and stop it.
     */
    public void removeBrowserMapping(String correlationId);
}
