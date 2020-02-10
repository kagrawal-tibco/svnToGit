package com.tibco.cep.runtime.management.impl.metrics;

import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.runtime.management.MetricDef;
import com.tibco.cep.runtime.management.MetricTable;
import com.tibco.cep.runtime.metrics.Data;
import com.tibco.cep.runtime.metrics.impl.SimpleData;
import com.tibco.cep.runtime.metrics.impl.SimpleDataDef;
import com.tibco.cep.runtime.service.ServiceRegistry;
import com.tibco.cep.runtime.service.basic.AsyncWorkerServiceWatcher;
import com.tibco.cep.runtime.util.FQName;
import com.tibco.cep.runtime.util.SystemProperty;

/*
* Author: Ashwin Jayaprakash Date: Feb 18, 2009 Time: 4:04:15 PM
*/
public class AsyncWorkerDataPublisher implements Runnable {

    protected static final boolean ENABLE_METRICS = Boolean.parseBoolean(System.getProperty(
    		SystemProperty.METRIC_PUBLISH_ENABLE.getPropertyName(), Boolean.FALSE.toString()));

    protected static final long REFRESH_INTERVAL_MILLIS = Long.getLong(
    		SystemProperty.METRIC_PUBLISH_INTERVAL.getPropertyName(),10000);

    protected AsyncWorkerServiceWatcher serviceWatcher;

    protected ScheduledExecutorService executorService;

    protected MetricTable metricTable;

    protected AtomicBoolean stopFlag = new AtomicBoolean();

    protected HashMap<String, FQName> collatedWorkerNameAndFQNs;

    protected MetricDef metricDef;

    protected long jobLastErrorTS;

    public AsyncWorkerDataPublisher() {
    }

    public void init(ScheduledExecutorService executorService, MetricTable metricTable) {
        if (ENABLE_METRICS) {
            ServiceRegistry registry = ServiceRegistry.getSingletonServiceRegistry();
            this.serviceWatcher = registry.getWorkManagerWatcher();
    
            this.executorService = executorService;
            this.executorService.schedule(this, REFRESH_INTERVAL_MILLIS, TimeUnit.MILLISECONDS);
    
            this.metricTable = metricTable;
    
            SimpleDataDef.SimpleColumnDef[] columnDefs = {
                    new SimpleDataDef.SimpleColumnDef("maxThreads", Integer.class),
                    new SimpleDataDef.SimpleColumnDef("activeThreads", Integer.class),
                    new SimpleDataDef.SimpleColumnDef("queueCapacity", Integer.class),
                    new SimpleDataDef.SimpleColumnDef("queueSize", Integer.class),
                    new SimpleDataDef.SimpleColumnDef("timeMillis", Long.class),
            };
    
            SimpleDataDef dataDef = new SimpleDataDef(
                    new SimpleDataDef.SimpleColumnDef("Values", Data.class, columnDefs));
    
            FQName metricDefName = new FQName(Constants.KEY_TEMPLATE_CLUSTER_NAME,
                    Constants.KEY_TEMPLATE_PROCESS_ID,
                    Constants.KEY_TEMPLATE_AGENT_NAME,
                    Constants.KEY_TEMPLATE_AGENT_ID,
                    AsyncWorkerServiceWatcher.AsyncWorkerService.class.getSimpleName(),
                    "%asyncWorkerName%");
    
            this.metricDef = new com.tibco.cep.runtime.management.MetricDef(metricDefName, dataDef);
            metricTable.addMetricDef(metricDef);
    
            this.collatedWorkerNameAndFQNs = new HashMap<String, FQName>();
        }
    }

    public void discard() {
        stopFlag.set(true);
    }

    public com.tibco.cep.runtime.management.MetricDef getMetricDef() {
        return metricDef;
    }

    //------------

    @Override
    public void run() {
        if (stopFlag.get()) {
            return;
        }

        try {
            doWork();
        }
        catch (Throwable t) {
            long now = System.currentTimeMillis();

            if (now - jobLastErrorTS < (10 * 60 * 1000 /*10 mins*/)) {
                LogManagerFactory.getLogManager().getLogger(getClass())
                        .log(Level.DEBUG, "Metric publication failed", t);
            }
            else {
                LogManagerFactory.getLogManager().getLogger(getClass())
                        .log(Level.ERROR, "Metric publication failed", t);
            }

            jobLastErrorTS = now;
        }

        executorService.schedule(this, REFRESH_INTERVAL_MILLIS, TimeUnit.MILLISECONDS);
    }

    protected void doWork() {
        Collection<AsyncWorkerServiceWatcher.AsyncWorkerService> services =
                serviceWatcher.getRegisteredWorkerServices();

        if (services == null) {
            return;
        }

        //------------

        for (AsyncWorkerServiceWatcher.AsyncWorkerService service : services) {
            if (service.isActive() == false) {
                continue;
            }

            int maxThreads = service.getNumMaxThreads();
            int activeThreads = service.getNumActiveThreads();
            int queueCapacity = service.getJobQueueCapacity();
            int queueSize = service.getJobQueueSize();
            long timeMillis = System.currentTimeMillis();

            SimpleData data =
                    new SimpleData(maxThreads, activeThreads, queueCapacity, queueSize, timeMillis);

            FQName workerFQN = service.getName();

            metricTable.addMetricData(workerFQN, data);
        }
    }
}
