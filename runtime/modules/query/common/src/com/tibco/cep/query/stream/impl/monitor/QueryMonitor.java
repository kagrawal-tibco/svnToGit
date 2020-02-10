package com.tibco.cep.query.stream.impl.monitor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;

import com.tibco.cep.query.service.QueryProperty;
import com.tibco.cep.query.stream.cache.Cache;
import com.tibco.cep.query.stream.cache.CacheBuilder;
import com.tibco.cep.query.stream.context.Context;
import com.tibco.cep.query.stream.context.DefaultQueryContext;
import com.tibco.cep.query.stream.core.Component;
import com.tibco.cep.query.stream.core.Registry;
import com.tibco.cep.query.stream.impl.rete.integ.Manager;
import com.tibco.cep.query.stream.impl.rete.query.HealthReporter;
import com.tibco.cep.query.stream.impl.rete.query.ReteQuery;
import com.tibco.cep.query.stream.impl.rete.service.CacheScout;
import com.tibco.cep.query.stream.impl.rete.service.RegionManager;
import com.tibco.cep.query.stream.impl.rete.service.ReteEntityDispatcher;
import com.tibco.cep.query.stream.impl.rete.service.SnapshotQueryManager;
import com.tibco.cep.query.stream.monitor.CustomThreadGroup;
import com.tibco.cep.query.stream.monitor.QueryConfig;
import com.tibco.cep.query.stream.monitor.QueryWatcher;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.monitor.ThreadCentral;

/*
* Author: Ashwin Jayaprakash Date: May 7, 2008 Time: 6:09:05 PM
*/
public class QueryMonitor implements Component {
    protected static final int MAX_TRACE_ITEMS = 1000;

    protected final ResourceId resourceId;

    public QueryMonitor() {
        this.resourceId = new ResourceId(QueryMonitor.class.getName());
    }

    public void init(Properties properties) throws Exception {
    }

    public void start() throws Exception {
    }

    public void stop() throws Exception {
    }

    public void discard() throws Exception {
        resourceId.discard();
    }

    public ResourceId getResourceId() {
        return resourceId;
    }

    //-------------

    public CustomThreadGroup getRootThreadGroup() {
        ThreadCentral threadCentral = Registry.getInstance().getComponent(ThreadCentral.class);

        return threadCentral.getRootThreadGroup();
    }

    //-------------

    /**
     * Expensive operation.
     *
     * @return
     */
    public Collection<String> listRegionNames() {
        Registry registry = Registry.getInstance();

        Manager manager = registry.getComponent(Manager.class);
        Map<String, RegionManager> regionManagers = manager.getRegionManagers();

        LinkedList<String> names = new LinkedList<String>();
        for (RegionManager regionManager : regionManagers.values()) {
            names.add(regionManager.getRegionName());
        }

        return names;
    }

    private static RegionManager getRegionManager(String regionName) {
        Registry registry = Registry.getInstance();

        Manager manager = registry.getComponent(Manager.class);
        Map<String, RegionManager> regionManagers = manager.getRegionManagers();

        RegionManager regionManager = regionManagers.get(regionName);
        return regionManager;
    }

    //-------------

    public Object getValue(QueryProperty property, String regionName) {
        RegionManager regionManager = getRegionManager(regionName);
        if (regionManager == null) {
            return null;
        }

        Object value = null;

        switch (property) {
            case LOCALCACHE_EVICTSECONDS: {
                CacheBuilder cacheBuilder = Registry.getInstance().getComponent(CacheBuilder.class);
                CacheBuilder.BuilderInput input = cacheBuilder.getBuilderInput();
                int seconds = (int) input.getPrimaryExpiryTimeMillis() / 1000;
                value = seconds;
            }
            break;

            case LOCALCACHE_MAXELEMENTS: {
                CacheBuilder cacheBuilder = Registry.getInstance().getComponent(CacheBuilder.class);
                CacheBuilder.BuilderInput input = cacheBuilder.getBuilderInput();
                value = input.getPrimaryMaxItems();
            }
            break;

            case LOCALCACHE_PREFETCHAGGRESSIVE: {
                CacheScout cacheScout = regionManager.getCacheScout();
                value = cacheScout.isAggressivePrefetchSupported();
            }
            break;
        }

        return value;
    }

    public Object getOtherValue(OtherProperty property, String regionName) {
        RegionManager regionManager = getRegionManager(regionName);
        if (regionManager == null) {
            return null;
        }

        Object value = null;

        switch (property) {
            case LOCALCACHE_SIZE: {
                Cache cache = regionManager.getPrimaryCache();
                value = cache.size();
            }
            break;
        }

        return value;
    }

    //------------

    /**
     * @param regionName
     * @return Collection of information - where each element has information of a query "run" in
     *         XML.
     */
    public Collection<String> fetchCacheProcessorTraceOutput(String regionName) {
        RegionManager regionManager = getRegionManager(regionName);

        ReteEntityDispatcher dispatcher = regionManager.getDispatcher();

        LinkedList<ReteEntityDispatcher.Run> capturedRuns =
                new LinkedList<ReteEntityDispatcher.Run>();

        //Quickly, capture the entire chain of runs before it gets truncated.
        ReteEntityDispatcher.Run run = null;
        int counter = 0;
        while ((run = dispatcher.pollOldestRun()) != null) {
            capturedRuns.add(run);

            counter++;
            if (counter == MAX_TRACE_ITEMS) {
                break;
            }
        }

        ArrayList<String> trace = new ArrayList<String>(capturedRuns.size());

        for (ReteEntityDispatcher.Run capturedRun : capturedRuns) {
            long runStartTime = capturedRun.getStartTimeMillis();
            int additions = capturedRun.getNewEntities();
            int modifications = capturedRun.getModifiedEntities();
            int deletes = capturedRun.getDeletedEntities();
            Throwable error = capturedRun.getError();
            long runEndTime = capturedRun.getEndTimeMillis();
            long timeBWRuns = capturedRun.getTimeBetweenRunsMillis();
            double tps = capturedRun.getTps();

            StringBuilder s = new StringBuilder("<run>");

            s.append("<start-time>").append(new Timestamp(runStartTime)).append("</start-time>");

            s.append("<end-time>").append((runEndTime <= 0) ? "-" : new Timestamp(runEndTime))
                    .append("</end-time>");

            s.append("<num-of-additions>").append(additions).append("</num-of-additions>");

            s.append("<num-of-modifications>").append(modifications)
                    .append("</num-of-modifications>");

            s.append("<num-of-deletions>").append(deletes).append("</num-of-deletions>");

            s.append("<time-bw-runs-seconds>").append(timeBWRuns).append("</time-bw-runs-seconds>");

            s.append("<tps>").append(tps).append("</tps>");

            s.append("<error>").append((error == null) ? "-" : error.toString())
                    .append("</error>");

            s.append("</run>");

            trace.add(s.toString());
        }

        return trace;
    }

    /**
     * @param regionName
     * @return Collection of information - where each element has information of a query "run" in
     *         XML.
     */
    public Collection<ReteEntityDispatcher.Run> fetchCacheProcessorTrace(String regionName) {
        RegionManager regionManager = getRegionManager(regionName);

        ReteEntityDispatcher dispatcher = regionManager.getDispatcher();

        LinkedList<ReteEntityDispatcher.Run> capturedRuns =
                new LinkedList<ReteEntityDispatcher.Run>();

        //Quickly, capture the entire chain of runs before it gets truncated.
        ReteEntityDispatcher.Run run = null;
        int counter = 0;
        while ((run = dispatcher.pollOldestRun()) != null) {
            capturedRuns.add(run);

            counter++;
            if (counter == MAX_TRACE_ITEMS) {
                break;
            }
        }

        return capturedRuns;
    }

    //-------------

    /**
     * Expensive operation.
     *
     * @param regionName
     * @return
     */
    public Collection<ReteQuery> listSnapshotQueries(String regionName) {
        RegionManager regionManager = getRegionManager(regionName);
        SnapshotQueryManager ssQueryManager = regionManager.getSSQueryManager();

        return ssQueryManager.getRegisteredQueries();
    }

    /**
     * Expensive operation.
     *
     * @param regionName
     * @return
     */
    public Collection<ReteQuery> listContinuousQueries(String regionName) {
        RegionManager regionManager = getRegionManager(regionName);
        ReteEntityDispatcher entityDispatcher = regionManager.getDispatcher();

        return entityDispatcher.getRegisteredQueries();
    }

    //------------

    /**
     * Tries to prod and wake up a stuck query.
     *
     * @param reteQuery
     */
    public void pingQuery(ReteQuery reteQuery) {
        reteQuery.ping();
    }

    public void pauseQuery(ReteQuery reteQuery) {
        reteQuery.pause();
    }

    public boolean isQueryPaused(ReteQuery reteQuery) {
        return reteQuery.isPaused();
    }

    public void resumeQuery(ReteQuery reteQuery) {
        reteQuery.resume();
    }

    public boolean hasQueryStopped(ReteQuery reteQuery) {
        return reteQuery.hasStopped();
    }

    /**
     * @param reteQuery
     * @return The approximate number of entity notifications that are pending processing by the
     *         query.
     */
    public int getPendingMainQueueCount(ReteQuery reteQuery) {
        HealthReporter healthReporter = new HealthReporter(reteQuery);

        return healthReporter.getPendingEntityCount();
    }

    /**
     * To get an approximate total of all of events that are pending, use: {@link
     * #getPendingMainQueueCount(com.tibco.cep.query.stream.impl.rete.query.ReteQuery) Main} +
     * {@link #getPendingLiveQueueCount(com.tibco.cep.query.stream.impl.rete.query.ReteQuery)
     * Pending live}.
     *
     * @param reteQuery
     * @return If the query was created with "SS-then-CQ" mode then, if the query is still
     *         processing the Snapshot items, then the notifications that arrive from the live
     *         changes are accumulated. This returns the count of those pending/accumulated items.
     */
    public int getPendingLiveQueueCount(ReteQuery reteQuery) {
        HealthReporter healthReporter = new HealthReporter(reteQuery);

        return healthReporter.getAccumulatedEntityCountDuringSS();
    }

    //------------

    private static QueryConfig extractQueryConfig(ReteQuery reteQuery) {
        Context context = reteQuery.getContext();
        DefaultQueryContext queryContext = context.getQueryContext();
        QueryConfig queryConfig = queryContext.getQueryConfig();

        return queryConfig;
    }

    public void enableTracing(ReteQuery reteQuery) {
        QueryConfig queryConfig = extractQueryConfig(reteQuery);

        queryConfig.setTracingEnabled(true);
    }

    public boolean isTracingEnabled(ReteQuery reteQuery) {
        QueryConfig queryConfig = extractQueryConfig(reteQuery);

        return queryConfig.isTracingEnabled();
    }

    /**
     * @param reteQuery
     * @return Collection of information - where each element has information of a query "run" in
     *         XML.
     */
    public Collection<String> fetchTraceOutput(ReteQuery reteQuery) {
        Context context = reteQuery.getContext();
        DefaultQueryContext queryContext = context.getQueryContext();
        QueryWatcher queryWatcher = queryContext.getQueryWatcher();

        LinkedList<QueryWatcher.Run> capturedRuns = new LinkedList<QueryWatcher.Run>();

        //Quickly, capture the entire chain of runs before it gets truncated.
        QueryWatcher.Run run = queryWatcher.pollOldestRun();
        int counter = 0;
        while (run != null) {
            capturedRuns.add(run);

            run = queryWatcher.pollOldestRun();

            counter++;
            if (counter == MAX_TRACE_ITEMS) {
                break;
            }
        }

        ArrayList<String> trace = new ArrayList<String>(capturedRuns.size());

        for (QueryWatcher.Run capturedRun : capturedRuns) {
            long runStartTime = capturedRun.getStartTime();
            QueryWatcher.Status status = capturedRun.getStatus();
            Throwable error = capturedRun.getError();
            long runEndTime = capturedRun.getEndTime();

            StringBuilder s = new StringBuilder("<run>");

            s.append("<start-time>").append(new Timestamp(runStartTime)).append("</start-time>");

            s.append("<end-time>").append((runEndTime <= 0) ? "-" : new Timestamp(runEndTime))
                    .append("</end-time>");

            s.append("<status>").append(status).append("</status>");

            s.append("<error>").append((error == null) ? "-" : error.toString())
                    .append("</error>");

            QueryWatcher.Step step = capturedRun.getFirstStep();

            while (step != null) {
                long stepStartTime = step.getStartTime();
                String stepId = step.getResourceId().generateSequenceToParentString();
                long stepEndTime = step.getEndTime();

                s.append("<step>").append(stepId);
                s.append("<start-time>").append(new Timestamp(stepStartTime))
                        .append("</start-time>");
                s.append("<end-time>").append((stepEndTime <= 0) ? "-" : new Timestamp(stepEndTime))
                        .append("</end-time>");
                s.append("</step>");

                step = step.getNextStep();
            }

            s.append("</run>");

            trace.add(s.toString());
        }

        return trace;
    }

    /**
     * @param reteQuery
     * @return Collection of information - where each element has information of a query "run".
     * @throws Exception
     */
    public Collection<QueryWatcher.Run> fetchTrace(ReteQuery reteQuery) throws Exception {
        Context context = reteQuery.getContext();
        DefaultQueryContext queryContext = context.getQueryContext();
        QueryWatcher queryWatcher = queryContext.getQueryWatcher();

        LinkedList<QueryWatcher.Run> capturedRuns = new LinkedList<QueryWatcher.Run>();

        //Quickly, capture the entire chain of runs before it gets truncated.
        QueryWatcher.Run run = queryWatcher.pollOldestRun();
        int counter = 0;
        while (run != null) {
            capturedRuns.add(run);

            run = queryWatcher.pollOldestRun();

            counter++;
            if (counter == MAX_TRACE_ITEMS) {
                break;
            }
        }

        return capturedRuns;
    }

    public void disableTracing(ReteQuery reteQuery) {
        QueryConfig queryConfig = extractQueryConfig(reteQuery);

        queryConfig.setTracingEnabled(false);
    }

    //-------------

    public static enum OtherProperty {
        LOCALCACHE_SIZE;
    }
}
