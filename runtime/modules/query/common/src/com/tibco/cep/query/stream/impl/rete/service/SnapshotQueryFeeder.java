package com.tibco.cep.query.stream.impl.rete.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import com.tibco.cep.query.stream.cache.SharedObjectSourceRepository;
import com.tibco.cep.query.stream.core.ControllableResource;
import com.tibco.cep.query.stream.core.Registry;
import com.tibco.cep.query.stream.core.Stream;
import com.tibco.cep.query.stream.impl.rete.ReteEntitySource;
import com.tibco.cep.query.stream.impl.rete.expression.ReteEntityFilter;
import com.tibco.cep.query.stream.impl.rete.integ.EntityLoaderImpl;
import com.tibco.cep.query.stream.impl.rete.query.ReteQuery;
import com.tibco.cep.query.stream.monitor.Logger;
import com.tibco.cep.query.stream.monitor.Logger.LogLevel;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.query.snapshot.Bridge;
import com.tibco.cep.query.stream.util.CustomHashSet;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.impl.RuleServiceProviderImpl;
import com.tibco.cep.runtime.session.impl.RuleSessionManagerImpl;

/*
 * Author: Ashwin Jayaprakash Date: Nov 15, 2007 Time: 6:20:15 PM
 */

public class SnapshotQueryFeeder implements ControllableResource {
    protected ReteQuery query;

    protected Bridge bridge;

    protected ResourceId resourceId;

    protected SharedObjectSourceRepository sosRepo;

    protected EntityLoader[] entityLoaders;

    protected FeederJob feederJob;

    protected Future<?> feederJobHandle;

    protected SnapshotQueryCompletionListener completionListener;

    protected ExecutorService executorService;

    /**
     * @param query              {@link com.tibco.cep.query.stream.impl.rete.query.ReteQuery#isSynchronous()}
     *                           should return <code>true</code>.
     * @param bridge
     * @param completionListener
     * @param sosRepo
     * @param executorService
     */
    public SnapshotQueryFeeder(ReteQuery query, Bridge bridge,
                               SnapshotQueryCompletionListener completionListener,
                               SharedObjectSourceRepository sosRepo,
                               ExecutorService executorService) {
        this.query = query;
        this.bridge = bridge;
        this.completionListener = completionListener;
        this.sosRepo = sosRepo;
        this.feederJob = new FeederJob();
        this.resourceId = new ResourceId(query.getResourceId(), SnapshotQueryFeeder.class
                .getName());
        this.executorService = executorService;
    }

    public ReteQuery getQuery() {
        return query;
    }

    public ResourceId getResourceId() {
        return resourceId;
    }

    /**
     * Disables the rete-query's use of filters ({@link com.tibco.cep.query.stream.impl.rete.query.ReteQuery#disableReteEntityFilters()}).
     */
    public void start() {
        Collection<Class> classes = null;

        //Disable the filter usage in the ReteQuery. We'll use them from there directly.
        query.disableReteEntityFilters();

        boolean[] snapshotRequiredForSources = query.getSnapshotRequiredForSource();
        if (snapshotRequiredForSources != null) {
            LinkedHashMap<Class, Class[]> sourceAndHierarchy = query.getSourceClassAndHierarchy();

            classes = new CustomHashSet<Class>();

            int c = 0;
            for (Class key : sourceAndHierarchy.keySet()) {
                Class[] hierarchy = sourceAndHierarchy.get(key);

                if (snapshotRequiredForSources[c]) {
                    for (Class requiredClass : hierarchy) {
                        classes.add(requiredClass);
                    }
                }

                c++;
            }
        } else {
            classes = query.getReteEntityClasses();
        }

        //------------

        final int batchProcessSize = 500;
//        final Map<Class, ReteEntityFilter[]> classAndTheirFilters =
//                query.getReteEntityClassAndFilters();


        //**********************THIS IS IT I SUPPOSE**************************************

        if (!query.isSelfJoin()) {
            entityLoaders = new EntityLoader[classes.size()];
        } else {
            //Added the for loop for selfJoins where a single class can have more than 1 listener
            int entityLoaderSize = 0;
            for (Object mEntryObj : query.getReteEntityClassAndListeners().entrySet()) {
                Map.Entry mEntry = (Map.Entry) mEntryObj;
                entityLoaderSize = entityLoaderSize + ((Object[]) mEntry.getValue()).length;
            }
            entityLoaders = new EntityLoader[entityLoaderSize];
        }

        int i = 0;
        HashMap<String, ReteEntityFilter[]> reteEntityResourceIdAndFilters = (HashMap<String, ReteEntityFilter[]>) query.getReteEntityResourceIdAndFilters();

        for (Class klass : classes) {
            //ReteEntityFilter[] reteFilters = classAndTheirFilters.get(klass);
            //----------------------
            ReteEntitySource[] listeners = (ReteEntitySource[]) query.getReteEntityClassAndListeners().get(klass);
            for (ReteEntitySource reteEntitySource : listeners) {
                String resourceId = reteEntitySource.getResourceId().getId();
                ReteEntityFilter[] reteEntityFilters = reteEntityResourceIdAndFilters.get(resourceId);
                entityLoaders[i] = new EntityLoaderImpl(klass, query, sosRepo, batchProcessSize, reteEntityFilters);
                i++;
            }
            //----------------------
//            entityLoaders[i] =
//                    new EntityLoaderImpl(klass, query, sosRepo, batchProcessSize, reteFilters);
//            i++;
        }

        //------------

        feederJobHandle = executorService.submit(feederJob);
    }

    private void endLoaders() {
        for (EntityLoader entityLoader : entityLoaders) {
            entityLoader.end();
        }
    }

    private void jobStopped() {
        completionListener.onComplete(this);

        resourceId.discard();
    }

    public void stop() {
        if (feederJob == null) {
            //Already stopped.
            return;
        }

        feederJob.signalStop();
        feederJob = null;

        try {
            //Wait for job completion.
            feederJobHandle.get(60, TimeUnit.SECONDS);
        } catch (Exception e) {
            //Ignore.
        }
        feederJobHandle = null;

        entityLoaders = null;

        query = null;
        bridge = null;
        completionListener = null;

        resourceId.discard();
        resourceId = null;

        sosRepo = null;

        executorService = null;
    }

    // -----------

    protected class FeederJob implements Runnable {
        private final AtomicBoolean stopFlag;

        public FeederJob() {
            this.stopFlag = new AtomicBoolean(false);
        }

        public void signalStop() {
            stopFlag.set(true);
        }

        public void run() {
            final EntityLoader[] loaders = SnapshotQueryFeeder.this.entityLoaders;
            final long pauseCheckTimeMillis = 2000;
            final ReteQuery q = SnapshotQueryFeeder.this.query;
            final Bridge brd = SnapshotQueryFeeder.this.bridge;
            final int maxQuickLoop = 15;
            final boolean requireSyncPush = q.isSynchronous();

            //-----------

            RuleSession session = q.getRuleSession();
            if (session != null) {
                RuleServiceProviderImpl provider =
                        (RuleServiceProviderImpl) session.getRuleServiceProvider();

                RuleSessionManagerImpl sessionManager =
                        (RuleSessionManagerImpl) provider.getRuleSessionManager();

                sessionManager.setCurrentRuleSession(session);
            }

            //-----------

            while (stopFlag.get() == false) {
                if (q.isPaused()) {
                    do {
                        dozeOff(pauseCheckTimeMillis);
                    }
                    while (q.isPaused());
                }

                try {
                    for (int i = 0; i < maxQuickLoop && !stopFlag.get(); i++) {
                        boolean keepRunning = false;

                        for (EntityLoader loader : loaders) {
                            EntityLoaderImpl.BatchResult batchResult =
                                    loader.resumeOrStartBatchLoad();

                            keepRunning = keepRunning || batchResult.hasMore();
                        }

                        // ----------

                        for (; ;) {
                            if (requireSyncPush) {
                                q.performSyncWork();
                            }

                            long estWaitTime = q.calcEstimatedFinishTime();

                            /*
                            Break the loop either if the Query appears to have completed or if the
                            wait time is too long, in which case we can at least do more 
                            entity-loading.
                            */
                            if (estWaitTime < 0 || estWaitTime > 50) {
                                break;
                            }

                            dozeOff(estWaitTime);
                        }

                        // ----------

                        if (keepRunning == false || brd.canContinueStreamInput() == false) {
                            handleCompletion();

                            return;
                        }
                    }
                } catch (InterruptedException e) {
                    // Do nothing.
                } catch (Throwable t) {
                    Logger logger = Registry.getInstance().getComponent(Logger.class);
                    logger.log(LogLevel.ERROR, t);

                    break;
                }
            }

            if (session != null) {
                RuleServiceProviderImpl provider =
                        (RuleServiceProviderImpl) session.getRuleServiceProvider();

                RuleSessionManagerImpl sessionManager =
                        (RuleSessionManagerImpl) provider.getRuleSessionManager();

                sessionManager.unsetCurrentRuleSession();
            }
        }

        private void dozeOff(long millis) {
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                //Ignore.
            }
        }

        private void handleCompletion() throws Exception {
            ReteQuery rq = SnapshotQueryFeeder.this.query;

            performRemaningWork(rq);

            //------------

            SnapshotQueryFeeder.this.endLoaders();

            //------------

            Stream flusher = SnapshotQueryFeeder.this.bridge.getFlusher();

            rq.acquireQueryLock();
            try {
                //Schedule Flusher here.
                rq.getContext().getQueryContext().addStreamForNextCycle(flusher);
            } finally {
                rq.relinquishQueryLock();
            }

            performRemaningWork(rq);

            //------------

            SnapshotQueryFeeder.this.jobStopped();
        }

        private void performRemaningWork(ReteQuery rq) {
            if (rq.isSynchronous() == false) {
                rq.ping();

                return;
            }

            rq.performSyncWork();

            //Just in case there was some pending work.
            while (rq.calcEstimatedFinishTime() >= 0) {
                //Flusher gets invoked.
                rq.performSyncWork();
            }
        }
    }
}
