package com.tibco.cep.runtime.service.basic;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryNotificationInfo;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryType;
import java.lang.management.MemoryUsage;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

import javax.management.ListenerNotFoundException;
import javax.management.Notification;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;

/*
* Author: Ashwin Jayaprakash Date: Oct 17, 2008 Time: 1:34:51 PM
*/
public class JVMHeapWatcher {
    public static final double DEFAULT_THRESHOLD = 0.6;

    protected MemCollectionThresholdListener thresholdListener;

    //Needed to add this because of large datasets which are being handled for which
    //60% is on the lower side.
    protected double configuredThreshold;

    /*
     * Used as a Set. Dummy values. Only keys are relevant.
     */
    protected ConcurrentHashMap<JVMHeapWarningListener, Object> internalListeners;

    public JVMHeapWatcher() {
    }

    public void init() {
        install();

        this.internalListeners = new ConcurrentHashMap<JVMHeapWarningListener, Object>();

    }

    //Call this a hack way of setting an instance variable from an unrelated class
    public void setThreshold(double threshold) {
        if (threshold < 0.0) {
            configuredThreshold = DEFAULT_THRESHOLD;
        } else {
            configuredThreshold = threshold;
        }
    }

    private void install() {
        Collection<MemoryPoolMXBean> pools = fetchUsageThresholdSupportedPools();

        for (MemoryPoolMXBean pool : pools) {
            MemoryUsage poolUsage = pool.getUsage();
            long maxMemoryBytes = poolUsage.getMax();
            long warningThresholdBytes = (long) (maxMemoryBytes * configuredThreshold);

            pool.setUsageThreshold(warningThresholdBytes);
        }

        MemoryMXBean mbean = fetchMemoryMXBean();
        NotificationEmitter emitter = (NotificationEmitter) mbean;

        String handback = System.identityHashCode(pools) + ":" + System.nanoTime();
        thresholdListener = new MemCollectionThresholdListener(handback);

        emitter.addNotificationListener(thresholdListener, null, null);
    }

    protected MemoryMXBean fetchMemoryMXBean() {
        return ManagementFactory.getMemoryMXBean();
    }

    protected Collection<MemoryPoolMXBean> fetchUsageThresholdSupportedPools() {
        LinkedList<MemoryPoolMXBean> pools = new LinkedList<MemoryPoolMXBean>();

        for (MemoryPoolMXBean pool : ManagementFactory.getMemoryPoolMXBeans()) {
            if (pool.getType() == MemoryType.HEAP && pool.isUsageThresholdSupported()) {
                pools.add(pool);
            }
        }

        return pools;
    }

    protected Collection<MemoryPoolMXBean> fetchAllPools() {
        LinkedList<MemoryPoolMXBean> pools = new LinkedList<MemoryPoolMXBean>();

        for (MemoryPoolMXBean pool : ManagementFactory.getMemoryPoolMXBeans()) {
            if (pool.getType() == MemoryType.HEAP) {
                pools.add(pool);
            }
        }

        return pools;
    }

    public void addListener(JVMHeapWarningListener listener) {
        internalListeners.put(listener, listener);
    }

    public void removeListener(JVMHeapWarningListener listener) {
        internalListeners.remove(listener);
    }

    public void stop() {
        MemoryMXBean mbean = fetchMemoryMXBean();
        NotificationEmitter emitter = (NotificationEmitter) mbean;

        if (thresholdListener != null) {
            try {
                emitter.removeNotificationListener(thresholdListener);
            }
            catch (ListenerNotFoundException e) {
                //Ignore
            }

            thresholdListener = null;
        }

        internalListeners.clear();
        internalListeners = null;
    }

    //-------------

    private void onNotify(Notification notification, MemoryNotificationInfo info) {
        printAllStats(notification, info);

        String warningPoolName = info.getPoolName();
        MemoryUsage warningPoolsUsage = info.getUsage();
        long used = warningPoolsUsage.getUsed();
        long max = warningPoolsUsage.getMax();

        for (JVMHeapWarningListener warningListener : internalListeners.keySet()) {
            warningListener.onWarning(notification, warningPoolName, max, used);
        }
    }

    protected void printAllStats(Notification notification, MemoryNotificationInfo info) {
        StringBuilder s = new StringBuilder();

        String message = notification.getMessage();
        String warningPoolName = info.getPoolName();
        MemoryUsage warningPoolsUsage = info.getUsage();

        s.append("\n=========================== JVM Heap warning ===========================");
        s.append("\nMessage: ").append(message);
        s.append("\nPool for which warning was issued: [").append(warningPoolName)
                .append("]. Stats follow:");
        s.append("\nOriginal threshold warning set at: ").append(configuredThreshold * 100)
                .append(" %");
        extractUsage(s, warningPoolsUsage);

        for (MemoryPoolMXBean poolBean : fetchAllPools()) {
            String poolName = poolBean.getName();

            if (poolName.equals(warningPoolName) == false) {
                MemoryUsage poolUsage = poolBean.getCollectionUsage();

                if (poolUsage != null) {
                    s.append("\n\nPool [").append(poolName).append("] stats follow:");
                    extractUsage(s, poolUsage);
                }
            }
        }

        s.append("\n~--------~--------~--------~--------~--------~--------~--------~--------");

        System.err.println(s);
    }

    private static void extractUsage(StringBuilder s, MemoryUsage memoryUsage) {
        s.append("\n    Initial           : ").append(convertToKB(memoryUsage.getInit()))
                .append(" KB");

        s.append("\n    Committed         : ").append(convertToKB(memoryUsage.getCommitted()))
                .append(" KB");

        double d = 100 * (memoryUsage.getUsed() / (double) memoryUsage.getMax());
        s.append("\n    Used              : ").append(convertToKB(memoryUsage.getUsed()))
                .append(" KB (").append(d).append(" %)");

        s.append("\n    Max               : ").append(convertToKB(memoryUsage.getMax()))
                .append(" KB");
    }

    private static long convertToKB(long bytes) {
        return bytes / 1024;
    }

    //------------

    protected class MemCollectionThresholdListener implements NotificationListener {
        protected static final long DEFAULT_SUPPRESS_TIME_MILLIS = 3 * 60 * 1000;

        protected Object handback;

        protected long lastWarningAtMillis;

        public MemCollectionThresholdListener(Object handback) {
            this.handback = handback;
        }

        public void handleNotification(Notification notification, Object handback) {
            long currentTime = System.currentTimeMillis();

            //todo Make this smarter. Suppress only messages per pool. 

            if (lastWarningAtMillis == 0 ||
                    (currentTime - lastWarningAtMillis) > DEFAULT_SUPPRESS_TIME_MILLIS) {
                String notifType = notification.getType();

                if (notifType.equals(MemoryNotificationInfo.MEMORY_THRESHOLD_EXCEEDED)) {
                    CompositeData cd = (CompositeData) notification.getUserData();

                    MemoryNotificationInfo info = MemoryNotificationInfo.from(cd);
                    JVMHeapWatcher.this.onNotify(notification, info);

                    lastWarningAtMillis = currentTime;
                }
            }
        }
    }

    public static interface JVMHeapWarningListener {
        void onWarning(Notification notification, String poolName, long maxBytes, long usedBytes);
    }
}
