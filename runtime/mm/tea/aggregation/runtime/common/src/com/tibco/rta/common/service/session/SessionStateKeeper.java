package com.tibco.rta.common.service.session;

import com.tibco.rta.common.service.ServiceProviderManager;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.model.rule.RuleDef;
import com.tibco.rta.query.MetricResultTuple;
import com.tibco.rta.query.QueryDef;
import com.tibco.rta.query.QueryResultTuple;
import com.tibco.rta.query.QueryType;
import com.tibco.rta.queues.BatchJob;
import com.tibco.rta.queues.BatchSizeBufferCriterion;
import com.tibco.rta.queues.BatchedBlockingQueue;
import com.tibco.rta.queues.QueueException;
import com.tibco.rta.service.rules.RuleService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 6/2/13
 * Time: 12:11 PM
 * Maintain server session specific state here
 */
public class SessionStateKeeper {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_RUNTIME_SESSION.getCategory());

    private ServerSession ownerSession;

    /**
     * Key and value are same.
     */
    private ConcurrentHashMap<String, String> transactionIdsMap = new ConcurrentHashMap<String, String>();

    /**
     * All queries streaming registered by this user with unique query names.
     */
    private ConcurrentHashMap<String, QueryDef> registeredQueriesMap;

    /**
     * Map of registered query name to internal queue maintaining results for streaming queries
     */
    private ConcurrentHashMap<String, BatchedBlockingQueue> streamingQueryResultsMap;

    /**
     * Rules (streaming queries) made from this session.
     */
    private List<String> registeredRules;

    private CompoundExecutorService asyncPublisherService;


    public SessionStateKeeper(ServerSession ownerSession) {
        this.ownerSession = ownerSession;
        registeredQueriesMap = new ConcurrentHashMap<String, QueryDef>();
        streamingQueryResultsMap = new ConcurrentHashMap<String, BatchedBlockingQueue>();
        asyncPublisherService = new CompoundExecutorService(this);
        registeredRules = new ArrayList<String>();
    }


    public void registerQuery(QueryDef queryDef) {
        registeredQueriesMap.put(queryDef.getName(), queryDef);
    }


    public void registerRule(RuleDef ruleDef) {
        registeredRules.add(ruleDef.getName());
    }

    /**
     * Unregister a streaming query and remove its state
     *
     */
    public void unregisterQuery(QueryDef queryDef) {
        boolean removal = false;

        boolean b = registeredQueriesMap.remove(queryDef.getName()) != null;

        if (queryDef.getQueryType() == QueryType.STREAMING) {
            boolean bss = streamingQueryResultsMap.remove(queryDef.getName()) != null;
            registeredRules.remove(queryDef.getName());
            removal = b && bss;
        }
        if (removal) {
            if (LOGGER.isEnabledFor(Level.INFO)) {
                LOGGER.log(Level.INFO, "Removing Query : [%s]", queryDef.getName());
            }
        } else {
            if (LOGGER.isEnabledFor(Level.INFO)) {
                LOGGER.log(Level.INFO, "Could not find Query [%s] to remove", queryDef.getName());
            }
        }
    }

    public QueryDef getQueryByName(String name) {
        return registeredQueriesMap.get(name);
    }

    /**
     * Append to appropriate queue
     *
     */
    private void appendStreamingQueryTuple(QueryResultTuple queryResultTuple) {
        //Get query name
        String queryName = queryResultTuple.getQueryName();
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            MetricResultTuple metricResultTuple = queryResultTuple.getMetricResultTuple();
            if (metricResultTuple.getMetricNames().length > 0) {
            	LOGGER.log(Level.DEBUG, "Appending tuple [%s] for streaming query [%s]", metricResultTuple.getMetric(metricResultTuple.getMetricNames()[0]).getKey(), queryName);
            }
        }

        BatchedBlockingQueue blockingQueue = streamingQueryResultsMap.get(queryName);
        QueryDef queryDef = getQueryByName(queryName);

        if (queryDef == null) {
            //Do not append
            if (LOGGER.isEnabledFor(Level.INFO)) {
                LOGGER.log(Level.INFO, "No query definition with name [%s] found in this session", queryName);
            }
            return;
        }

        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.DEBUG, "Streaming query definition for queryname [%s] is [%s]", queryName, queryDef);
        }

        if (blockingQueue == null) {
            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "Creating new work queue for streaming query [%s]", queryName);
            }
            //TODO Take expiry time as config param
            blockingQueue = new BatchedBlockingQueue("", new BatchSizeBufferCriterion(queryDef.getBatchSize(), 5L));
            streamingQueryResultsMap.put(queryName, blockingQueue);
        }
        //TODO go through the queue manager
        try {
            blockingQueue.offer(new BatchJob(queryResultTuple));
            TuplePublisher tuplePublisher = new TuplePublisher(queryName, blockingQueue, ownerSession);
            asyncPublisherService.submit(tuplePublisher);
        } catch (QueueException e) {
            LOGGER.log(Level.ERROR, "Error appending streaming tuple", e);
        }
    }

    /**
     * Append query tuple to appropriate queue based on type
     *
     */
    public void appendQueryTuple(QueryResultTuple queryResultTuple) {
        QueryDef queryDef = getQueryByName(queryResultTuple.getQueryName());
        if (queryDef != null) {
            appendStreamingQueryTuple(queryResultTuple);
        }
    }


    public void addTransactionEventId(String transactionEventId) {
        transactionIdsMap.put(transactionEventId, transactionEventId);
    }

    public boolean isOwner(String transactionEventId) {
        return transactionIdsMap.get(transactionEventId) != null;
    }


    public boolean isQueryOwner(String queryName) {
        return registeredQueriesMap.get(queryName) != null;
    }

    /**
     * Cleanup all state
     */
    public void cleanup() {
        try {
            synchronized (this) {
                transactionIdsMap.clear();
                registeredQueriesMap.clear();
                streamingQueryResultsMap.clear();
                flushRules();
            }
            asyncPublisherService.shutdown();
            asyncPublisherService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "Error shutting down session", e);
        }
    }

    private void flushRules() throws Exception {
        RuleService ruleService = ServiceProviderManager.getInstance().getRuleService();
        for (String ruleName : registeredRules) {
            if (LOGGER.isEnabledFor(Level.INFO)) {
                LOGGER.log(Level.INFO, "Removing Streaming Query: %s", ruleName);
            }
            ruleService.deleteRule(ruleName);
        }
        registeredRules.clear();
    }

    public void connectionEstablished() {
        asyncPublisherService.connectionEstablished();
    }

    public void connectionFailure() {
        asyncPublisherService.connectionFailure();
    }
}
