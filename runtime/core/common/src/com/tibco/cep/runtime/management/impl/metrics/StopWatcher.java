package com.tibco.cep.runtime.management.impl.metrics;

import java.util.concurrent.ConcurrentHashMap;

import com.tibco.cep.runtime.management.MetricDef;
import com.tibco.cep.runtime.management.MetricTable;
import com.tibco.cep.runtime.metrics.DataPipe;
import com.tibco.cep.runtime.metrics.StopWatchOwner;
import com.tibco.cep.runtime.metrics.StopWatchOwnerLifecycleListener;

/*
* Author: Ashwin Jayaprakash Date: Feb 9, 2009 Time: 2:39:09 PM
*/
public class StopWatcher implements StopWatchOwnerLifecycleListener {
    protected ConcurrentHashMap<StopWatchOwner, MetricDataJob> watchesAndPublishers;

    protected MetricDataPublisher dataPublisher;

    protected MetricTable metricTable;
    
    public StopWatcher() {
    	watchesAndPublishers = new ConcurrentHashMap<StopWatchOwner, MetricDataJob>();
    	dataPublisher = new MetricDataPublisher(getClass().getName());
	}

    public void init(MetricTable metrictable) {

        dataPublisher.init();

        this.metricTable = metrictable;
    }

    public void onNew(StopWatchOwner watchOwner) {
        DataPipe dataPipe = watchOwner.getDataPipe();
        if (dataPipe != null) {
            MetricDef metricDef = dataPipe.getMetricDef();
            metricTable.addMetricDef(metricDef);
    
            MetricDataJob job = new MetricDataJob(watchOwner);
            watchesAndPublishers.put(watchOwner, job);
            dataPublisher.publishJob(job);
        }
    }

    public void onDiscard(StopWatchOwner watchOwner) {
        MetricDataJob job = watchesAndPublishers.remove(watchOwner);
        if (job != null) {
            job.deactivate();
        }
    }

    public void discard() {
        for (StopWatchOwner watchOwner : watchesAndPublishers.keySet()) {
            onDiscard(watchOwner);
        }
        watchesAndPublishers.clear();

        dataPublisher.stopPublishing();
    }
}