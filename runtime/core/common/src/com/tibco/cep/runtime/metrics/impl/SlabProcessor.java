package com.tibco.cep.runtime.metrics.impl;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.management.MetricDef;
import com.tibco.cep.runtime.metrics.Data;
import com.tibco.cep.runtime.metrics.DataDef;
import com.tibco.cep.runtime.metrics.DataPipe;
import com.tibco.cep.runtime.metrics.StopWatch;
import com.tibco.cep.runtime.metrics.StopWatchManager;
import com.tibco.cep.runtime.metrics.StopWatchOwnerOption;
import com.tibco.cep.runtime.service.ServiceRegistry;
import com.tibco.cep.runtime.service.time.HeartbeatService;
import com.tibco.cep.runtime.util.FQName;
import com.tibco.cep.runtime.util.SystemProperty;

/*
* Author: Ashwin Jayaprakash Date: Jan 23, 2009 Time: 3:53:31 PM
*/

public class SlabProcessor<W extends StopWatch, S extends Slab>
        extends AbstractStopWatchOwner<W, S> implements Runnable {
    static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(SlabProcessor.class);

    protected static final boolean ENABLE_METRICS = Boolean.parseBoolean(System.getProperty(
    		SystemProperty.METRIC_PUBLISH_ENABLE.getPropertyName(), Boolean.FALSE.toString()));
    
    protected static final int SLAB_PROCESS_COUNT = Integer.getInteger(SystemProperty.METRIC_SLAB_COUNT.getPropertyName(), Integer.MAX_VALUE); //original value 3
    
    protected static final int DELAYED_SLAB_PROCESS_MULTIPLE = 3;

    protected static long METRICS_PUBLISHED = 0L;

    protected final AtomicBoolean stopFlag;

    protected PriorityBlockingQueue<S> sortedSlabs;

    protected long processLagMillis;

    protected HeartbeatService heartbeatService;

    protected InternalDataPipe dataPipe;

    protected ScheduledExecutorService executorService;
    
    public SlabProcessor(FQName metricDefName, FQName metricName) {
        super(metricDefName, metricName);

        this.stopFlag = new AtomicBoolean();
    }

    protected int getDelayedSlabProcessMultiple() {
        return DELAYED_SLAB_PROCESS_MULTIPLE;
    }

    @Override
    public void init(StopWatchCreator<W> stopWatchCreator, SlabWorker<S> slabWorker,
                     StopWatchOwnerOption... options) {
        super.init(stopWatchCreator, slabWorker, options);

        sortedSlabs = new PriorityBlockingQueue<S>(48, new SlabComparator());
        stopFlag.set(false);
        processLagMillis = getSlabResolutionMillis() * getDelayedSlabProcessMultiple();

        DataDef dataDef = slabWorker.getSlabDataDef();
        MetricDef metricDef = new MetricDef(metricDefName, dataDef);
        dataPipe = new SimpleDataPipe(metricName, metricDef);

        ServiceRegistry registry = ServiceRegistry.getSingletonServiceRegistry();

        heartbeatService = registry.getService(HeartbeatService.class);

        if (ENABLE_METRICS) {
            InternalStopWatchManager watchManager = (InternalStopWatchManager) registry.getService(
                    StopWatchManager.class);
            executorService = watchManager.getScheduledExecutorService();

            executorService.schedule(this, getSlabResolutionMillis(), TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public void discard() {
        requestStop();

        super.discard();
    }

    @Override
    protected void onNewSlab(S slab) {
        sortedSlabs.offer(slab);
    }

    public void resurrectSlab(S slab) {
        sortedSlabs.offer(slab);
    }

    public DataPipe getDataPipe() {
        return dataPipe;
    }

    //-----------

    public void requestStop() {
        stopFlag.set(true);
    }

    public void run() {
        if (stopFlag.get()) {
            return;
        }

        try {
            long scheduleAt = doWork();
            if (scheduleAt >= 0) {
                executorService.schedule(this, scheduleAt, TimeUnit.MILLISECONDS);
            }

            if (LOGGER.isEnabledFor(Level.TRACE) && ((METRICS_PUBLISHED++ % 1000) == 0)) {
                LOGGER.log(Level.TRACE, "Metric processing scheduled at " + scheduleAt + " ms intervals");
            }
        }
        catch (Throwable t) {
            LOGGER.log(Level.WARN, "Error occurred while processing slabs", t);
        }
    }

    /**
     * @return The delay in milliseconds (from current time) to schedule the next job at. <code>-1</code> or less to stop
     *         scheduling for ever.
     */
    protected long doWork() {
        //Process at least 3 slabs at each schedule.
        for (int tries = 0; tries < SLAB_PROCESS_COUNT; tries++) {
            long slabResolutionMillis = getSlabResolutionMillis();

            S slab = sortedSlabs.poll();
            if (slab == null) {
                return slabResolutionMillis;
            }

            //------------

            long slabEndMillis = slab.getSlabEndTimeMillis();
            long timeInMillisToProcess = slabEndMillis + processLagMillis;
            long currentMillis = heartbeatService.getLatestHeartbeat().getTimestamp();

            //Slab is ready to be processed.
            if (currentMillis > timeInMillisToProcess) {
                process(slab);

                Slab nextSlab = sortedSlabs.peek();
                //Hmm.. there is another slab. Is it time to process it? Let's check;
                if (nextSlab != null) {
                    continue;
                }

                return slabResolutionMillis;
            }

            //------------

            //Slab is not ready yet. Put it back in.
            sortedSlabs.offer(slab);

            long waitUntil = (timeInMillisToProcess - currentMillis);
            if (waitUntil < slabResolutionMillis) {
                waitUntil = slabResolutionMillis;
            }

            return waitUntil;
        }

        return -1;
    }

    protected void process(S slab) {
        Long start = slab.getSlabStartTimeMillis();
        //Cleanup.
        startTimeMillisAndSlabs.remove(start);

        Data data = slabWorker.extractSlabData(slab);
        dataPipe.offer(data);
    }

    //-----------

    protected static class SlabComparator implements Comparator<Slab> {
        public int compare(Slab s1, Slab s2) {
            long v1 = s1.getSlabStartTimeMillis();
            long v2 = s2.getSlabStartTimeMillis();

            return (v1 < v2 ? -1 : (v1 == v2 ? 0 : 1));
        }
    }
}