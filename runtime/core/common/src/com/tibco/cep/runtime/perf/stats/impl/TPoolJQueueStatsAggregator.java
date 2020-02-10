package com.tibco.cep.runtime.perf.stats.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.ConcurrentHashMap;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.metrics.Data;
import com.tibco.cep.runtime.perf.stats.StatsAggregator;
import com.tibco.cep.runtime.perf.stats.TPoolJQueueStats;
import com.tibco.cep.runtime.service.ServiceRegistry;
import com.tibco.cep.runtime.service.basic.AsyncWorkerServiceWatcher;
import com.tibco.cep.runtime.service.basic.AsyncWorkerServiceWatcher.AsyncWorkerService;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.util.FQName;

public class TPoolJQueueStatsAggregator extends Observable implements StatsAggregator{

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(TPoolJQueueStatsAggregator.class);

    private static int MATCHING_TERM_LOCATION = 4;

    private static String MATCHING_TERM_VALUE = AsyncWorkerServiceWatcher.AsyncWorkerService.class.getSimpleName();

    private Map<FQName, TPoolJQueueStats> statsMap;

    public TPoolJQueueStatsAggregator(RuleSession rulesession) {
        ServiceRegistry serviceRegistry = ServiceRegistry.getSingletonServiceRegistry();
        Collection<AsyncWorkerService> workerServices = serviceRegistry.getWorkManagerWatcher().getRegisteredWorkerServices();
        statsMap = new ConcurrentHashMap<FQName, TPoolJQueueStats>();
        for (AsyncWorkerService asyncWorkerService : workerServices) {
            FQName name = asyncWorkerService.getName();
            TPoolJQueueStats stats = new TPoolJQueueStats();
            stats.setName(name.getComponentNames()[name.getComponentNames().length-1]);
            statsMap.put(name, stats);
        }
    }

    public Collection<TPoolJQueueStats> getThreadPoolJobQueueStats(){
        return Collections.unmodifiableCollection(statsMap.values());
    }

    public TPoolJQueueStats getThreadPoolJobQueueStat(FQName name) {
        return statsMap.get(name);
    }

    @Override
    public boolean willAccept(FQName name) {
        String[] names = name.getComponentNames();
        //do we have enough names ?
        if (names.length > MATCHING_TERM_LOCATION) {
            //yes, we do !! is the matching term matching ?
            if (names[MATCHING_TERM_LOCATION].equals(MATCHING_TERM_VALUE) == true) {
                //does the statsmap contain a stats object for it
                if (statsMap.containsKey(name) == false) {
                    //no, add a new one
                    TPoolJQueueStats stats = new TPoolJQueueStats();
                    stats.setName(name.getComponentNames()[name.getComponentNames().length-1]);
                    statsMap.put(name, stats);
                    try {
                        setChanged();
                        notifyObservers(name);
                    } finally {
                        clearChanged();
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public void aggregate(long time, FQName name, Data data) {
        TPoolJQueueStats stats = statsMap.get(name);
        if (stats != null) {
            stats.setMaximumThreads((Integer) data.getColumns()[0]);
            stats.setActiveThreads((Integer) data.getColumns()[1]);
            stats.setQueueCapacity((Integer) data.getColumns()[2]);
            stats.setQueueSize((Integer) data.getColumns()[3]);
            if (LOGGER.isEnabledFor(Level.DEBUG) == true) {
                LOGGER.log(Level.DEBUG, stats.toString());
            }
        }
    }

    @Override
    public void close() {
        deleteObservers();
        statsMap.clear();
    }

}
