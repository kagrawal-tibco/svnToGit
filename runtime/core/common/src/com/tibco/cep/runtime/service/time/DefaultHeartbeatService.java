package com.tibco.cep.runtime.service.time;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import com.tibco.cep.runtime.config.Configuration;

/*
* Author: Ashwin Jayaprakash Date: Feb 16, 2009 Time: 3:16:21 PM
*/
public class DefaultHeartbeatService implements HeartbeatService {
    public static final long HEARTBEAT_INTERVAL_MILLIS = 50;

    protected AtomicReference<Heartbeat> latestHeartbeat;

    protected HeartbeatThread heartbeatThread;

    public String getId() {
        return getClass().getName();
    }

    public void init(Configuration configuration, Object... otherArgs) throws Exception {
        latestHeartbeat = new AtomicReference<Heartbeat>(new Heartbeat(System.currentTimeMillis()));
        heartbeatThread = new HeartbeatThread(latestHeartbeat);
    }

    public void start() throws Exception {
        heartbeatThread.start();
    }

    public void stop() throws Exception {
        heartbeatThread.requestStop();
    }

    //----------

    public long getHeartbeatIntervalMillis() {
        return HEARTBEAT_INTERVAL_MILLIS;
    }

    public Heartbeat getLatestHeartbeat() {
        return latestHeartbeat.get();
    }

    //----------

    protected static class HeartbeatThread extends Thread {
        protected AtomicBoolean stopFlag;

        protected AtomicReference<Heartbeat> latestHeartbeatContainer;

        public HeartbeatThread(AtomicReference<Heartbeat> latestHeartbeatContainer) {
            super(DefaultHeartbeatService.class.getName());

            setDaemon(true);
            int priority = (Thread.MAX_PRIORITY - Thread.NORM_PRIORITY) / 2;
            setPriority(priority);

            this.stopFlag = new AtomicBoolean();
            this.latestHeartbeatContainer = latestHeartbeatContainer;
        }

        public void requestStop() {
            stopFlag.set(true);
            interrupt();
        }

        @Override
        public void run() {
            final AtomicBoolean cachedStopFlag = stopFlag;
            final AtomicReference<Heartbeat> cachedHeartbeatContainer = latestHeartbeatContainer;

            while (cachedStopFlag.get() == false) {
                try {
                    Thread.sleep(HEARTBEAT_INTERVAL_MILLIS);
                }
                catch (InterruptedException e) {
                    //Ignore.
                }

                long after = System.currentTimeMillis();
                cachedHeartbeatContainer.set(new Heartbeat(after));
            }
        }
    }
}
