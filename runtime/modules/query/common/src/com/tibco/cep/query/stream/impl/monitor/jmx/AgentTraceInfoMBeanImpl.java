package com.tibco.cep.query.stream.impl.monitor.jmx;

import java.lang.management.ManagementFactory;
import java.sql.Timestamp;
import java.util.Collection;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.StandardMBean;

import com.tibco.cep.query.stream.impl.monitor.QueryMonitor;
import com.tibco.cep.query.stream.impl.monitor.model.AgentTraceInfoMBean;
import com.tibco.cep.query.stream.impl.rete.service.ReteEntityDispatcher;

/*
* Author: Ashwin Jayaprakash Date: Apr 8, 2009 Time: 4:42:31 PM
*/
public class AgentTraceInfoMBeanImpl
        implements com.tibco.cep.query.stream.impl.monitor.model.AgentTraceInfoMBean,
        com.tibco.cep.query.stream.impl.monitor.model.Info {
    protected String regionName;

    protected QueryMonitor monitor;

    protected ObjectName objectName;

    protected ReteEntityDispatcher.Run mostRecentRun;

    protected long lastRefreshedAtMillis;

    public void init(ObjectName parentName, QueryMonitor monitor, String regionName)
            throws MalformedObjectNameException {
        this.monitor = monitor;
        this.regionName = regionName;
        this.objectName = new ObjectName(parentName + ",service=AgentTraceInfo");
    }

    public void register() throws Exception {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

        StandardMBean mBean = new StandardMBean(this, AgentTraceInfoMBean.class);
        mBeanServer.registerMBean(mBean, objectName);
    }

    public void unregister() throws Exception {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        mBeanServer.unregisterMBean(objectName);
    }

    public void refresh() throws Exception {
        Collection<ReteEntityDispatcher.Run> recentRuns =
                monitor.fetchCacheProcessorTrace(regionName);

        for (ReteEntityDispatcher.Run recentRun : recentRuns) {
            if (recentRun != null) {
                mostRecentRun = recentRun;

                lastRefreshedAtMillis = System.currentTimeMillis();
            }
        }
    }

    protected void refreshIfNeeded() {
        if (mostRecentRun == null || (System.currentTimeMillis() - lastRefreshedAtMillis) >= 1000) {
            try {
                refresh();
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    //------------

    public Timestamp getMeasurementStartTime() {
        refreshIfNeeded();

        return (mostRecentRun == null) ? null : new Timestamp(mostRecentRun.getStartTimeMillis());
    }

    public Timestamp getMeasurementEndTime() {
        refreshIfNeeded();

        return (mostRecentRun == null) ? null : new Timestamp(mostRecentRun.getEndTimeMillis());
    }

    public int getNewEntities() {
        refreshIfNeeded();

        return (mostRecentRun == null) ? 0 : mostRecentRun.getNewEntities();
    }

    public int getModifiedEntities() {
        refreshIfNeeded();

        return (mostRecentRun == null) ? 0 : mostRecentRun.getModifiedEntities();
    }

    public int getDeletedEntities() {
        refreshIfNeeded();

        return (mostRecentRun == null) ? 0 : mostRecentRun.getDeletedEntities();
    }

    public Throwable getError() {
        refreshIfNeeded();

        return (mostRecentRun == null) ? null : mostRecentRun.getError();
    }

    public double getTps() {
        refreshIfNeeded();

        return (mostRecentRun == null) ? 0.0 : mostRecentRun.getTps();
    }
}
