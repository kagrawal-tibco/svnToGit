/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 19/8/2010
 */

package com.tibco.cep.runtime.service.cluster.system.mm;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Iterator;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.StandardMBean;

import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.scheduler.WorkManagerEntry;
import com.tibco.cep.runtime.service.cluster.scheduler.impl.DefaultSchedulerCache;

public class ScheduleCacheMBeanImpl implements SchedulerCacheMBean {

    DefaultSchedulerCache schedulerCache;
    Cluster cluster;

    public ScheduleCacheMBeanImpl(DefaultSchedulerCache schedulerCache) {
        this.schedulerCache = schedulerCache;
        cluster = schedulerCache.getCluster();
        registerMBean();
    }

    private void registerMBean() {
        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            //ObjectName name = new ObjectName("com.tibco.cep.runtime.service.om.impl.coherence.cluster:type=CacheClusterMBean");
            //ObjectName name = new ObjectName("BusinessEvents.Cluster:type=CacheClusterMBean");
            ObjectName name = new ObjectName("com.tibco.be:service=Cluster,name=" + cluster.getClusterName() + "$schedulers");

            StandardMBean mBean = new StandardMBean(this, SchedulerCacheMBean.class);
            mbs.registerMBean(mBean, name);

            //Check if already started

            //Suresh TODO : Check for RMI - M&M case.
            // int RMI_STATE = cluster.getRMIStartupState();
            // if (RMI_STATE != 0) {
            //     cluster.startRMIServer(mbs);
            // }
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public String[] getInfo() {
        Iterator<String> allSchedulers = this.schedulerCache.getWorkManagerDao().keySet().iterator();
        ArrayList<String> buf = new ArrayList<String>();
        while (allSchedulers.hasNext()) {
            StringBuffer ret = new StringBuffer();
            String schedulerId = (String) allSchedulers.next();
            ret.append("{Id=" + schedulerId);
            WorkManagerEntry entry = (WorkManagerEntry) this.schedulerCache.getWorkManagerDao().get(schedulerId);
            if (entry != null) {
                ret.append(",assignedTo=" + entry.getNodeId());
                ret.append(",pollInterval=" + entry.getPollingInterval());
                ret.append(",refreshAhead=" + entry.getRefreshAhead());
                ret.append("}");
            } else {
                ret.append(", No Entry Found}");
            }
            buf.add(ret.toString());
        }
        return buf.toArray(new String[]{});
    }
}
