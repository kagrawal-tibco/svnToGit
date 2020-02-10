package com.tibco.rta.query;

import com.tibco.rta.common.service.ServiceProviderManager;
import com.tibco.rta.common.service.impl.AbstractStartStopServiceImpl;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.model.rule.ActionFunctionDescriptor;
import com.tibco.rta.model.rule.ActionFunctionsRepository;
import com.tibco.rta.model.rule.InvokeConstraint;
import com.tibco.rta.model.rule.InvokeConstraint.Constraint;
import com.tibco.rta.model.rule.RuleFactory;
import com.tibco.rta.model.rule.mutable.MutableRuleDef;
import com.tibco.rta.runtime.model.MetricNode;
import com.tibco.rta.runtime.model.rule.Rule;
import com.tibco.rta.service.persistence.PersistenceService;
import com.tibco.rta.service.query.QueryService;
import com.tibco.rta.service.query.QueryUtils;
import com.tibco.rta.service.rules.RuleService;
import com.tibco.rta.util.InvalidQueryException;
import com.tibco.rta.util.LoggerUtil;

import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by IntelliJ IDEA. User: aathalye Date: 29/1/13 Time: 2:24 PM To change this template use File | Settings | File Templates.
 */
public class QueryServiceImpl extends AbstractStartStopServiceImpl implements QueryService {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_SERVICES_QUERY.getCategory());

    private static final Logger LOGGER_DTLS = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_SERVICES_QUERY_DETAILS.getCategory());

    private PersistenceService persistenceService;

    private ConcurrentHashMap<String, QueryMap> proxyToBrowserMap = new ConcurrentHashMap<String, QueryMap>();

    private final ReentrantLock mainLock = new ReentrantLock();

    @Override
    public void init(Properties configuration) throws Exception {       
        LoggerUtil.log(LOGGER, Level.INFO, "Initializing Query service..");
        super.init(configuration);

        persistenceService = ServiceProviderManager.getInstance().getPersistenceService();
        LoggerUtil.log(LOGGER, Level.INFO, "Initializing Query service Complete.");
    }

    @Override
    synchronized public void start() throws Exception {
        
    	LoggerUtil.log(LOGGER, Level.INFO, "Starting Query service..");        
        super.start();
        startSnapshotQueryLivenessScanner();       
        LoggerUtil.log(LOGGER, Level.INFO, "Starting Query service Complete.");        
    }

    private void startSnapshotQueryLivenessScanner() {
        ScheduledExecutorService snapshotScanner = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {

            private static final String NAME_PREFIX = "Snapshot-Liveness-Thread";

            @Override
            public Thread newThread(Runnable runnable) {
                return new Thread(runnable, NAME_PREFIX);
            }
        });
        snapshotScanner.scheduleWithFixedDelay(new SnapshotQueryScanner(), 0, 5L, TimeUnit.SECONDS);
    }

    @Override
    synchronized public void stop() throws Exception {
    	
        LoggerUtil.log(LOGGER, Level.INFO, "Stopping Query service..");        
        super.stop();        
        LoggerUtil.log(LOGGER, Level.INFO, "Stopping Query service Complete.");
        
    }

    @Override
    public String registerQuery(QueryDef queryDef) throws Exception {
        QueryType queryType = queryDef.getQueryType();

        switch (queryType) {
            case SNAPSHOT: {
                return registerSnapshotQuery(queryDef);
            }
            case STREAMING: {
                return registerStreamingQuery(queryDef);
            }
        }
        return null;
    }

    @Override
    public QueryDef unregisterQuery(String queryID) throws Exception {
        QueryDef queryDef = null;
        RuleService ruleService = ServiceProviderManager.getInstance().getRuleService();
        Rule rule = ruleService.getRule(queryID);
        if (rule == null) {
        	QueryMap removedMap = proxyToBrowserMap.get(queryID);
        	 if (removedMap != null) {
        		 queryDef = removedMap.getQueryDef();
        		 LoggerUtil.log(LOGGER, Level.INFO, "Stopping browser for query [%s]", queryID);
        		 removeBrowserMapping(queryID);
        	 }
        	
        } else {
        	queryDef = rule.getRuleDef().getSetCondition();
        	ruleService.deleteRule(queryID);
        }        
        return queryDef;
    }

    private String registerSnapshotQuery(QueryDef queryDef) throws Exception {        
		LoggerUtil.log(LOGGER, Level.DEBUG, "Registering snapshot query [%s]", queryDef.getName());
		String id = UUID.randomUUID().toString();
		SnapshotQueryStateKeeper.INSTANCE.registerQuery(id, queryDef);
		Browser<?> remoteBrowser = persistenceService.getSnapshotMetricNodeBrowser(queryDef);

		proxyToBrowserMap.putIfAbsent(id, new QueryMap(queryDef, remoteBrowser));
		return id;
    }

    private String registerStreamingQuery(QueryDef queryDef) throws Exception {
        RuleFactory factory = new RuleFactory();
        MutableRuleDef ruleDef = factory.newRuleDef(queryDef.getName());
        ruleDef.setSetCondition(queryDef);
        ruleDef.setAsStreamingQuery(true);

        InvokeConstraint invokeConstraint = factory.newInvokeConstraint(Constraint.ALWAYS);
        ActionFunctionDescriptor afd = ActionFunctionsRepository.INSTANCE.getFunctionDescriptor("Query-Action");
        factory.newSetActionDef(ruleDef, afd, invokeConstraint);
        ReentrantLock lock = mainLock;

        RuleService ruleService = ServiceProviderManager.getInstance().getRuleService();
        lock.lock();

        try {
            ruleService.createRule(ruleDef);
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
        return null;
    }

    @Override
    public boolean hasNext(String queryID) throws InvalidQueryException {
        boolean hasNext;
        QueryMap queryMap = proxyToBrowserMap.get(queryID);
        if (queryMap == null) {
            //throw new InvalidQueryException("No Query Exists for Browser ID [" + queryID + "]");
        	return false;
        } else {
            hasNext = SnapshotQueryStateKeeper.INSTANCE.hasMoreResults(queryID);
            // If no locally cached results go to Persistence service.
            if (!hasNext) {
                Browser<?> remoteBrowser = queryMap.getResultBrowser();
                hasNext = remoteBrowser.hasNext();
				if (!hasNext) {
					LoggerUtil.log(LOGGER, Level.DEBUG, "No more results for query id %s", queryID);
					removeBrowserMapping(queryID);
				}
            }
        }
        return hasNext;
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<QueryResultTuple> getNext(String queryID) throws Exception {
        List<QueryResultTuple> results;

        QueryMap queryMap = proxyToBrowserMap.get(queryID);
        if (queryMap == null) {
            throw new InvalidQueryException("No Query Exists for Browser ID [" + queryID + "]");
        }
       
        LoggerUtil.log(LOGGER, Level.DEBUG, "Requesting getNext() operation for getting next result on query [%s] for browser correlation id [%s]", queryMap.getQueryDef().getName(), queryID);
        SnapshotQueryStateKeeper snapshotQueryStateKeeper = SnapshotQueryStateKeeper.INSTANCE;
        results = snapshotQueryStateKeeper.getResults(queryID);
        // If results < batchsize go to persistence service for more
        int batchSize = queryMap.getQueryDef().getBatchSize();
        int cnt = 0;

        while (results.size() < batchSize) {
            Browser<?> remoteBrowser = queryMap.getResultBrowser();
            if (remoteBrowser.hasNext()) {
                MetricNode metricNode = (MetricNode) remoteBrowser.next();

                QueryResultTuple queryResultTuple = new QueryResultTuple();
                queryResultTuple.setQueryName(queryMap.getQueryDef().getName());
                MetricResultTuple resultTuple = QueryUtils.convertMetricNodeToMetricResultTuple(metricNode);
                queryResultTuple.setMetricResultTuple(resultTuple);
                try {
                    snapshotQueryStateKeeper.appendSnapshotQueryTuple(queryID, queryResultTuple);
                } catch (Exception e) {
                    LOGGER.log(Level.ERROR, "", e);
                }

            } else {
                // Nothing from persistence service too. Break
                break;
            }
            // Fetch results only equal to diff
            results.addAll(snapshotQueryStateKeeper.getResults(queryID, batchSize - results.size()));
            cnt++;
        }

        LoggerUtil.log(LOGGER_DTLS, Level.TRACE, "Requesting getNext() operation for getting next result on query [%s] for browser correlation id [%s], db result count [%d]", queryMap.getQueryDef().getName(), queryID, cnt);
        
        return results;
    }

    public void removeBrowserMapping(String queryID) {
        QueryMap queryMap = proxyToBrowserMap.remove(queryID);

        if (queryMap != null) {           
            LoggerUtil.log(LOGGER, Level.DEBUG, "Stopping browser for correlationid [%s]", queryID);            
            queryMap.getResultBrowser().stop();            
            LoggerUtil.log(LOGGER, Level.DEBUG, "Unregistering query [%s] for browser correlationid [%s]", queryMap.getQueryDef().getName(), queryID);
            
            SnapshotQueryStateKeeper.INSTANCE.unregisterQuery(queryID, queryMap.getQueryDef());
        }
    }

    /**
     * For snapshot queries
     */
    private class QueryMap {

        private QueryDef queryDef;

        private Browser<?> resultBrowser;

        private QueryMap(QueryDef queryDef, Browser<?> resultBrowser) {
            this.queryDef = queryDef;
            this.resultBrowser = resultBrowser;
        }

        public QueryDef getQueryDef() {
            return queryDef;
        }

        public Browser<?> getResultBrowser() {
            return resultBrowser;
        }
    }

    private class SnapshotQueryScanner implements Runnable {

        @Override
        public void run() {
            // TODO Auto-generated method stub            
            LoggerUtil.log(LOGGER, Level.DEBUG, "Checking for Snapshot Query Liveness");
            
            List<String> timedoutQueryNames = SnapshotQueryStateKeeper.INSTANCE.getTimedOutQueries();
            for (String queryID : timedoutQueryNames) {
				try {
					LoggerUtil.log(LOGGER, Level.INFO, "Timed Out Query ID [%s]", queryID);
					unregisterQuery(queryID);
				} catch (Exception e) {
					LOGGER.log(Level.ERROR, "", e);
				}
            }
        }
    }
}
