/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.backingstore;

import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledFuture;

import com.tibco.cep.common.resource.Id;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.util.ScheduledWorkManager;
import com.tibco.cep.runtime.service.cluster.util.WorkManagerFactory;
import com.tibco.cep.runtime.service.om.api.ControlDao;
import com.tibco.cep.runtime.service.om.api.ControlDaoType;
import com.tibco.cep.runtime.session.RuleServiceProvider;

public class ClusterBackingStoreManager {
    Cluster cluster;

    ControlDao taskTableDao;

    ScheduledWorkManager scheduledWorkManager;

    ConcurrentLinkedQueue<ScheduledFuture<?>> allScheduledJobs;

    public ClusterBackingStoreManager(Cluster cluster) throws Exception {
        this.cluster = cluster;
        //taskTableDao = cluster.getCache(EntityCacheName.getCacheName(EntityCacheName.REPLICATED_CACHE_UNLIMITED,
        //        cluster.getClusterName(), "", "$backingStoreTasks"));

        taskTableDao = cluster.getDaoProvider().createControlDao(String.class, String.class, ControlDaoType.BackingStoreTasks);

        RuleServiceProvider rsp = cluster.getRuleServiceProvider();
        scheduledWorkManager = WorkManagerFactory.fetchCommonScheduledWorkManager(cluster.getClusterName(), rsp);

        allScheduledJobs = new ConcurrentLinkedQueue<ScheduledFuture<?>>();
    }


    protected void startJob(String key) throws Exception {
        StringTokenizer tk = new StringTokenizer(key, ":");
        String clz = tk.nextToken();
        long sleep = Long.valueOf(tk.nextToken());
        Runnable run = (Runnable) Class.forName(key).newInstance();
        startJob(run, sleep);
    }

    protected void startJob(Runnable run, long sleep) throws Exception {
        ScheduledFuture<?> future = scheduledWorkManager.scheduleJobAtFixedDelay(run, sleep);
        allScheduledJobs.add(future);
    }

    synchronized void addTask(Runnable task, long sleep) {
        String key = task.getClass().getName() + ":" + sleep;
        taskTableDao.lock(key, -1);
        try {
            Integer state = (Integer) taskTableDao.get(key);
            if (state == null) {
                taskTableDao.put(key, cluster.getGroupMembershipService().getLocalMember().getMemberId());
                startJob(task, sleep);
            }
            return;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            taskTableDao.unlock(key);
        }
    }

    synchronized boolean acquire(String key) {
        taskTableDao.lock(key, -1);
        try {
            Id node = (Id) taskTableDao.get(key);
            if (node != null) {
                taskTableDao.put(key, cluster.getGroupMembershipService().getLocalMember().getMemberId());
                return true;
            }
            return false;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            taskTableDao.unlock(key);
        }
    }

    protected void memberLeft(Id member) {
        Iterator allTasks = taskTableDao.keySet().iterator();
        while (allTasks.hasNext()) {
            String task = (String) allTasks.next();
            try {
                Id node = (Id) taskTableDao.get(task);
                taskTableDao.lock(task, -1);
                if (node.equals(member)) {
                    if (acquire(task)) {
                        startJob(task);
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            } finally {
                taskTableDao.unlock(task);
            }
        }
    }

    public void shutdown() {
        for (ScheduledFuture<?> scheduledJob : allScheduledJobs) {
            scheduledJob.cancel(false);
        }

        allScheduledJobs.clear();
    }

    class AcquireTask implements Runnable {
        String key;

        AcquireTask(String key) {
            this.key = key;
        }

        public void run() {
            try {
                if (acquire(key)) {
                    startJob(key);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
