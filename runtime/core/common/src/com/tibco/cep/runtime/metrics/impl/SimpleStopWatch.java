package com.tibco.cep.runtime.metrics.impl;

import com.tibco.cep.runtime.metrics.StopWatch;
import com.tibco.cep.runtime.service.ServiceRegistry;
import com.tibco.cep.runtime.service.time.HeartbeatService;
import com.tibco.cep.runtime.util.FQName;

import java.lang.ref.WeakReference;

/*
* Author: Ashwin Jayaprakash Date: Jan 22, 2009 Time: 2:46:29 PM
*/
public class SimpleStopWatch<S extends Slab> implements StopWatch {
    protected static final int FLUSH_PRIVATE_COUNTER_AFTER = 5;

    protected long startTimeMillis;

    protected long currentSlabEndMillis;

    protected long privateCounter;

    private WeakReference<S> currentSlab;

    protected HeartbeatService heartbeatService;

    protected SlabProvider<S> slabProvider;

    protected FQName ownerName;

    public SimpleStopWatch(FQName ownerName, SlabProvider<S> slabProvider) {
        this.ownerName = ownerName;
        this.slabProvider = slabProvider;

        ServiceRegistry registry = ServiceRegistry.getSingletonServiceRegistry();
        this.heartbeatService = registry.getService(HeartbeatService.class);
    }

    public FQName getOwnerName() {
        return ownerName;
    }

    public SlabProvider getSlabProvider() {
        return slabProvider;
    }

    public void start() {
        startTimeMillis = captureStartTime();

        //Works for the first time too.
        if (startTimeMillis > currentSlabEndMillis) {
            moveToNextSlab();
        }

        afterStart();
    }

    S getCurrentSlab() {
        return currentSlab.get();
    }

    protected long captureStartTime() {
        HeartbeatService.Heartbeat heartbeat = heartbeatService.getLatestHeartbeat();

        return heartbeat.getTimestamp();
    }

    protected void afterStart() {
        privateCounter++;
        if (privateCounter > FLUSH_PRIVATE_COUNTER_AFTER) {
            flushPrivateCounter();
        }
    }

    protected void moveToNextSlab() {
        //Flush the old one.
        if (currentSlab != null) {
            flushPrivateCounter();
        }

        S s = slabProvider.fetchSlab(startTimeMillis);
        currentSlab = new WeakReference<S>(s);
        currentSlabEndMillis = s.getSlabEndTimeMillis();
    }

    protected void flushPrivateCounter() {
        S s = currentSlab.get();
        if (s==null) {
            return;
        }

        s.addCount(privateCounter);
        privateCounter = 0;

        if (s.isAlreadyProcessed()) {
            slabProvider.resurrectSlab(s);
        }
    }

    public void stop() {
        long timestamp = captureEndTime();

        if (timestamp > currentSlabEndMillis) {
            moveToNextSlab();
        }
    }

    protected long captureEndTime() {
        HeartbeatService.Heartbeat heartbeat = heartbeatService.getLatestHeartbeat();

        return heartbeat.getTimestamp();
    }
}
