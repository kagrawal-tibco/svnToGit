/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.session.impl.locks;

import java.util.Collection;

import com.tibco.cep.runtime.management.impl.metrics.Constants;
import com.tibco.cep.runtime.metrics.StopWatch;
import com.tibco.cep.runtime.metrics.StopWatchManager;
import com.tibco.cep.runtime.metrics.StopWatchOwner;
import com.tibco.cep.runtime.metrics.util.StopWatchDispenser;
import com.tibco.cep.runtime.service.ServiceRegistry;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.txn.RtcTransactionManager;
import com.tibco.cep.runtime.session.locks.LockKeeper;
import com.tibco.cep.runtime.session.locks.LockManager;
import com.tibco.cep.runtime.util.FQName;
import com.tibco.cep.runtime.util.ProcessInfo;
import com.tibco.cep.util.annotation.DependsOn;

/*
* Author: Ashwin Jayaprakash Date: Jul 7, 2008 Time: 12:57:56 PM
*/
public class DefaultConcurrentLockManager<K> extends AbstractConcurrentLockManager<K, Collection<Long>> {
    protected RtcTransactionManager.RTCTxnManagerReport managerReport;

    protected StopWatchDispenser<StopWatch> lockRequests;

    public DefaultConcurrentLockManager() {
        super();
    }

    public DefaultConcurrentLockManager(LockKeeper<K, Collection<Long>> lockKeeper) {
        super(lockKeeper);
    }

    public DefaultConcurrentLockManager(boolean enableLockRecording,
                                        LockKeeper<K, Collection<Long>> lockKeeper) {
        super(enableLockRecording, lockKeeper);
    }

    @DependsOn({StopWatchManager.class})
    @Override
    public void init(Cluster cluster) throws Exception {
        super.init(cluster);

        //-----------

        String clusterName = cluster.getClusterName();

        ServiceRegistry registry = ServiceRegistry.getSingletonServiceRegistry();
        StopWatchManager stopWatchManager = registry.getService(StopWatchManager.class);

        FQName metricDefName = new FQName(Constants.KEY_TEMPLATE_CLUSTER_NAME,
                Constants.KEY_TEMPLATE_PROCESS_ID, LockManager.class.getSimpleName());

        FQName metricName = new FQName(clusterName, ProcessInfo.getProcessIdentifier(),
                LockManager.class.getSimpleName());

        StopWatchOwner<StopWatch> watchOwner =
                stopWatchManager.fetchOwner(metricDefName, metricName, StopWatch.class);

        lockRequests = new StopWatchDispenser<StopWatch>(watchOwner);
    }

    @Override
    protected LockKeeper<K, Collection<Long>> createLockForInit(Cluster cluster)
            throws Exception {
        managerReport = RtcTransactionManager.getReport();

        if (cluster == null) {
            return new DummyLockKeeper<K, Collection<Long>>();
        }

        return new DefaultLockKeeper<K, Collection<Long>>(cluster);
    }

    @Override
    public boolean lock(K key, long timeToSpendMillis, LockLevel level) {
        StopWatch stopWatch = lockRequests.createOrGetStopWatchForThread();
        boolean b = false;

        stopWatch.start();
        try {
            b = super.lock(key, timeToSpendMillis, level);
        }
        finally {
            stopWatch.stop();
        }

        if (b) {
            managerReport.increasePendingLocksToRelease(1);
        }

        return b;
    }

    @Override
    public void unlock(K key) {
        super.unlock(key);

        managerReport.decreasePendingLocksToRelease(1);
    }

    @Override
    public void unlock(K key, Collection<Long> affectedIds) {
        super.unlock(key, affectedIds);

        managerReport.decreasePendingLocksToRelease(1);
    }

    @Override
    public void unlock(Collection<LockData<K, Collection<Long>>> allLockData,
                       Collection<Long> affectedIds) {
        super.unlock(allLockData, affectedIds);

        managerReport.decreasePendingLocksToRelease(allLockData.size());
    }

    @Override
    public void unlockAllLocksHeldByThread(Collection<Long> affectedIds) {
        int numLocks = 0;
        Collection<LockData<K, Collection<Long>>> locksStuck = super.getLockDataStuckToThread();
        if (locksStuck != null) {
            numLocks = locksStuck.size();
        }

        super.unlockAllLocksHeldByThread(affectedIds);

        if (locksStuck != null) {
            managerReport.decreasePendingLocksToRelease(numLocks);
        }
    }
}
