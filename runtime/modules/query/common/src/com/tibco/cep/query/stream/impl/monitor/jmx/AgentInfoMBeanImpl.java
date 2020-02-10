package com.tibco.cep.query.stream.impl.monitor.jmx;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.StandardMBean;

import com.tibco.cep.query.service.QueryProperty;
import com.tibco.cep.query.stream.impl.monitor.QueryMonitor;
import com.tibco.cep.query.stream.impl.monitor.model.AgentInfoMBean;

/*
* Author: Ashwin Jayaprakash Date: Apr 9, 2009 Time: 10:56:25 AM
*/
public class AgentInfoMBeanImpl
        implements AgentInfoMBean, com.tibco.cep.query.stream.impl.monitor.model.Info {
    protected String regionName;

    protected QueryMonitor monitor;

    protected ObjectName objectName;

    public void init(ObjectName parentName, QueryMonitor monitor, String regionName)
            throws MalformedObjectNameException {
        this.monitor = monitor;
        this.regionName = regionName;
        this.objectName = new ObjectName(parentName + ",service=AgentInfo");
    }

    public void register() throws Exception {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

        StandardMBean mBean = new StandardMBean(this, AgentInfoMBean.class);
        mBeanServer.registerMBean(mBean, objectName);
    }

    public void unregister() throws Exception {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        mBeanServer.unregisterMBean(objectName);
    }

    public void refresh() throws Exception {
    }

    //------------

    public long getLocalCacheEvictSeconds() {
        return ((Number) monitor.getValue(QueryProperty.LOCALCACHE_EVICTSECONDS, regionName))
                .longValue();
    }

    public long getLocalCacheMaxElements() {
        return ((Number) monitor.getValue(QueryProperty.LOCALCACHE_MAXELEMENTS, regionName))
                .longValue();
    }

    public long getLocalCacheCurrentElements() {
        return ((Number) monitor
                .getOtherValue(QueryMonitor.OtherProperty.LOCALCACHE_SIZE, regionName)).longValue();
    }

    public boolean getLocalCachePrefetchAggressive() {
        return (Boolean) monitor.getValue(QueryProperty.LOCALCACHE_PREFETCHAGGRESSIVE, regionName);
    }
}
