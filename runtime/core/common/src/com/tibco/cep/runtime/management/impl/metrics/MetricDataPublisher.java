package com.tibco.cep.runtime.management.impl.metrics;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import com.tibco.cep.runtime.management.ManagementCentral;
import com.tibco.cep.runtime.management.MetricTable;
import com.tibco.cep.runtime.metrics.Data;
import com.tibco.cep.runtime.service.ServiceRegistry;
import com.tibco.cep.runtime.util.FQName;
import com.tibco.cep.runtime.util.SystemProperty;

/*
* Author: Ashwin Jayaprakash Date: Feb 10, 2009 Time: 5:57:24 PM
*/
public class MetricDataPublisher extends Thread {

    protected static final boolean ENABLE_METRICS = Boolean.parseBoolean(System.getProperty(
    		SystemProperty.METRIC_PUBLISH_ENABLE.getPropertyName(), Boolean.FALSE.toString()));

    protected CopyOnWriteArrayList<MetricDataJob> jobs;

    protected AtomicBoolean stopFlag;

    protected MetricTable metricTable;

    public MetricDataPublisher(String name) {
        super(name);
        setDaemon(true);
        setPriority(Thread.MIN_PRIORITY);

        this.jobs = new CopyOnWriteArrayList<MetricDataJob>();
        this.stopFlag = new AtomicBoolean();
    }

    /**
     * Also starts the thread.
     */
    public void init() {
        if (ENABLE_METRICS) {
            ServiceRegistry registry = ServiceRegistry.getSingletonServiceRegistry();
            ManagementCentral managementCentral = registry.getService(ManagementCentral.class);
            metricTable = managementCentral.getMetricTable();
    
            start();
        }
    }

    public void publishJob(MetricDataJob job) {
        jobs.add(job);
    }

    public void stopPublishing() {
        jobs.clear();

        stopFlag.set(true);

        try {
            join(3000);
        }
        catch (InterruptedException e) {
        }

        if (isAlive()) {
            interrupt();
        }
    }

    //------------

    @Override
    public void run() {
        final AtomicBoolean cachedStopFlag = stopFlag;
        final CopyOnWriteArrayList<MetricDataJob> cachedJobs = jobs;
        final MetricTable cachedMetricTable = metricTable;

        LinkedList<Data> collectedData = new LinkedList<Data>();

        while (cachedStopFlag.get() == false) {
            int counter = 0;

            for (Iterator<MetricDataJob> iterator = cachedJobs.iterator(); iterator.hasNext();) {
                MetricDataJob job = iterator.next();

                if (job.isActive() == false) {
                    //COW's Iterator does not support remove().
                    cachedJobs.remove(job);
                }

                //-----------

                FQName fqName = job.getFqn();
                int numDrained = job.drainNoWait(collectedData, 10);

                switch (numDrained) {
                    case 0:
                        break;

                    case 1: {
                        Data data = collectedData.removeFirst();
                        cachedMetricTable.addMetricData(fqName, data);
                    }
                    break;

                    default: {
                        //todo May be change this to bulk add of whole list.
                        for (Iterator<Data> iter = collectedData.iterator(); iter.hasNext();) {
                            Data data = iter.next();
                            iter.remove();

                            cachedMetricTable.addMetricData(fqName, data);
                        }
                    }
                }

                //-----------

                counter++;
                if ((counter & 15) == 0) {
                    Thread.yield();
                }
            }

            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
            }
        }
    }
}
