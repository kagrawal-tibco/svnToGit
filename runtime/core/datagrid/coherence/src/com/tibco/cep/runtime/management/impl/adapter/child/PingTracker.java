package com.tibco.cep.runtime.management.impl.adapter.child;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import com.tibco.cep.runtime.management.impl.adapter.RemoteProcessManager;

/*
* Author: Ashwin Jayaprakash Date: Mar 18, 2009 Time: 3:59:15 PM
*/
public class PingTracker extends Thread {
    protected final AtomicLong lastReceivedTimestamp;

    protected final AtomicBoolean stopFlag;

    protected final LinkedBlockingQueue<Long> pingQueue;

    protected final MissedPingListener pingMissedListener;

    public PingTracker(MissedPingListener pingMissedListener) {
        super(PingTracker.class.getSimpleName() + ".Thread");
        setDaemon(true);
        setPriority(Thread.MIN_PRIORITY);

        this.lastReceivedTimestamp = new AtomicLong(System.currentTimeMillis());
        this.stopFlag = new AtomicBoolean();
        this.pingQueue = new LinkedBlockingQueue<Long>();
        this.pingMissedListener = pingMissedListener;
    }

    public void ping() {
        pingQueue.offer(System.currentTimeMillis());
    }

    public void stopTrackerThread() {
        stopFlag.set(true);
        interrupt();

        try {
            join(10 * 1000);
        }
        catch (InterruptedException e) {
            //Ignore.
        }
    }

    public void run() {
        for (; stopFlag.get() == false;) {
            try {
                Long pingTimestampMillis = pingQueue
                        .poll(RemoteProcessManager.PING_DELAY_MILLIS, TimeUnit.MILLISECONDS);

                if (pingTimestampMillis == null) {
                    long lastReceivedTimeMillis = lastReceivedTimestamp.get();
                    long delay = System.currentTimeMillis() - lastReceivedTimeMillis;

                    if (delay > RemoteProcessManager.PING_DELAY_MILLIS) {
                        pingMissedListener.onMissedPing(lastReceivedTimeMillis);
                    }
                }
                else {
                    lastReceivedTimestamp.set(pingTimestampMillis);
                }
            }
            catch (Exception e) {
                //Ignore.
            }
        }
    }

    //------------

    public static interface MissedPingListener {
        void onMissedPing(long lastReceivedTimeMillis);
    }
}
