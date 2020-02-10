/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.scheduler.impl;


import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.scheduler.Poller;
import com.tibco.cep.runtime.service.cluster.scheduler.WorkManagerEntry;
import com.tibco.cep.runtime.service.cluster.system.mm.TaskSchedulerMBean;

/*
 * A Task Scheduler per WorkManagerEntry(scheduler)
 */
public class DefaultTaskScheduler implements TaskSchedulerMBean {
    WorkManagerEntry entry;
    Object schedulerAgent;
    Poller poller;
    Cluster cluster;
    DefaultSchedulerCache schedulerCache;

    public DefaultTaskScheduler(DefaultSchedulerCache schedulerCache, WorkManagerEntry entry, Object schedulerAgent) throws Exception {
        this.schedulerCache = schedulerCache;
        this.cluster = schedulerCache.getCluster();
        this.entry = entry;
        this.schedulerAgent = schedulerAgent;

        if (cluster.getClusterConfig().isHasBackingStore()) {
            poller = new DefaultDBPoller(this);
        }
        else {
            poller = new DefaultCachePoller(this);
        }
        //registerMBean();
    }

    protected final WorkManagerEntry getEntry() {
        return this.entry;
    }

    protected final DefaultSchedulerCache getSchedulerCache() {
        return this.schedulerCache;
    }

    public Poller getPoller() {
        return poller;
    }

    public String getName() {
        return entry.getId();
    }

    public void flush() throws Exception {
        poller.flush();
    }

    public int getPollingInterval() {
        return (int) entry.getPollingInterval();
    }

    public int getRefreshAhead() {
        return (int) entry.getRefreshAhead();
    }

    public String getType() {
        return poller.getType();
    }

    public int getPendingCount() {
        return poller.getPendingCount();

    }

    private void registerMBean() {
        try {
        	MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        	ObjectName name = new ObjectName("com.tibco.be:service=Schedulers,name=" + entry.getId());

            mbs.registerMBean(this, name);
            //Suresh TODO : cluster.startRMIServer needs to be added
            //cluster.startRMIServer(mbs);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected void shutdown() {
        poller.flush();
        poller.shutdown();

    }

    protected void start() throws Exception {
        poller.start();
    }
}
