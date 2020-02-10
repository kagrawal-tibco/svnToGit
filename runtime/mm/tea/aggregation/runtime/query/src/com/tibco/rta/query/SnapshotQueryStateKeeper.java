package com.tibco.rta.query;

import com.tibco.rta.common.ConfigProperty;
import com.tibco.rta.common.service.ServiceProviderManager;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 18/6/13
 * Time: 12:00 PM
 * Maintain all snapshot query state here.
 */
public class SnapshotQueryStateKeeper {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_SERVICES_QUERY.getCategory());

    /**
     * Singleton instance
     */
    public static final SnapshotQueryStateKeeper INSTANCE = new SnapshotQueryStateKeeper();

    /**
     * Timeout for snapshot queries
     */
    private long snapshotTimeout;

    /**
     * All snapshot queries registered.
     */
    private ConcurrentHashMap<String, QueryDef> registeredQueriesMap;

    /**
     * Query name to results queue.
     */
    private ConcurrentHashMap<String, LinkedBlockingQueue<QueryResultTuple>> snapshotQueryResultsMap;

    /**
     * Query Name to epoch time of last accessed (by next or hasNext)
     */
    private ConcurrentHashMap<String, Long> mostRecentlyAccessedMap;


    private SnapshotQueryStateKeeper() {
        registeredQueriesMap = new ConcurrentHashMap<String, QueryDef>();
        snapshotQueryResultsMap = new ConcurrentHashMap<String, LinkedBlockingQueue<QueryResultTuple>>();
        mostRecentlyAccessedMap = new ConcurrentHashMap<String, Long>();

        Map<?, ?> configuration = ServiceProviderManager.getInstance().getConfiguration();
        snapshotTimeout = Long.parseLong((String) ConfigProperty.RTA_SNAPSHOT_QUERY_TIMEOUT.getValue(configuration));
    }


    public void appendSnapshotQueryTuple(String queryID, QueryResultTuple queryResultTuple) {
        String queryName = queryResultTuple.getQueryName();
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            MetricResultTuple metricResultTuple = queryResultTuple.getMetricResultTuple();
            if (metricResultTuple.getMetricNames().length > 0) {
                LOGGER.log(Level.DEBUG, "Appending tuple [%s] for snapshot query [%s]", metricResultTuple.getMetric(metricResultTuple.getMetricNames()[0]).getKey(), queryName);
            }
        }
        LinkedBlockingQueue<QueryResultTuple> resultsQueue = snapshotQueryResultsMap.get(queryID);

        if (resultsQueue == null) {
            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "Creating new work queue for snapshot query [%s] with ID [%s]", queryName, queryID);
            }
            resultsQueue = new LinkedBlockingQueue<QueryResultTuple>();
            snapshotQueryResultsMap.put(queryID, resultsQueue);
        }
        resultsQueue.add(queryResultTuple);
    }


    public void registerQuery(String queryID, QueryDef queryDef) {
        registeredQueriesMap.put(queryID, queryDef);
        updateAccessedTimeStamp(queryID);
    }

    /**
     * Check if more results exist for this snapshot query.
     *
     */
    public boolean hasMoreResults(String queryID) {
        LinkedBlockingQueue<QueryResultTuple> queryResultTuples = snapshotQueryResultsMap.get(queryID);
        updateAccessedTimeStamp(queryID);
        return (queryResultTuples != null && !queryResultTuples.isEmpty());
    }

    /**
     * updates accessed time to particular query to current time
     *
     */
    private void updateAccessedTimeStamp(String queryID) {
        mostRecentlyAccessedMap.put(queryID, System.currentTimeMillis());
    }

    /**
     * Only applicable for snapshot queries
     *
     */
    public List<QueryResultTuple> getResults(String queryID) {
        //Get matching query def
        QueryDef queryDef = registeredQueriesMap.get(queryID);
        updateAccessedTimeStamp(queryID);
        if (queryDef == null) {
            //Possibly removed.
            if (LOGGER.isEnabledFor(Level.WARN)) {
                LOGGER.log(Level.WARN, "No snapshot query found for query ID [%s]", queryID);
            }
            return new ArrayList<QueryResultTuple>(0);
        } else {
            return getResults(queryID, queryDef.getBatchSize());
        }
    }


    /**
     * Unregister a snapshot query and remove its state
     *
     */
    public void unregisterQuery(String queryId, QueryDef queryDef) {
        boolean removal = false;

        boolean b = registeredQueriesMap.remove(queryId) != null;

        mostRecentlyAccessedMap.remove(queryId);
        if (queryDef.getQueryType() == QueryType.SNAPSHOT) {
            boolean bs = snapshotQueryResultsMap.remove(queryId) != null;
            removal = b && bs;
        }
        if (removal) {
            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "Removing Query : [%s] ID [%s]", queryDef.getName(), queryId);
            }
        } else {
            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "No query exists on session by name : [%s] ID [%s]", queryDef.getName(), queryId);
            }
        }
    }


    /**
     * Only applicable for snapshot queries
     *
     */
    public List<QueryResultTuple> getResults(String queryID, int batchSize) {
        LinkedBlockingQueue<QueryResultTuple> allQueryResultTuples = snapshotQueryResultsMap.get(queryID);
        LinkedList<QueryResultTuple> queryResultTuples = new LinkedList<QueryResultTuple>();
        if (allQueryResultTuples != null) {
            //Get matching query def
            QueryDef queryDef = registeredQueriesMap.get(queryID);
            if (queryDef != null && queryDef.getQueryType() == QueryType.SNAPSHOT) {
                //Drain to result queue
                allQueryResultTuples.drainTo(queryResultTuples, batchSize);
            }
        }
        //TODO proper exception is query def is incorrect
        return queryResultTuples;
    }

    public List<String> getTimedOutQueries() {
        List<String> timedOutQuries = new ArrayList<String>();
        long currentTime = System.currentTimeMillis();
        for (String queryID : registeredQueriesMap.keySet()) {
            long lastAccessedTime = mostRecentlyAccessedMap.get(queryID);
            if (currentTime - lastAccessedTime >= snapshotTimeout) {
                timedOutQuries.add(queryID);
            }
        }
        return timedOutQuries;
    }
}
