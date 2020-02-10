package com.tibco.cep.loadbalancer.impl.jmx;

import java.lang.management.ManagementFactory;
import java.util.concurrent.atomic.AtomicInteger;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.StandardMBean;

import com.tibco.cep.loadbalancer.util.Helper;

/*
* Author: Ashwin Jayaprakash / Date: 10/4/11 / Time: 1:35 PM
*/
public class RouterDestinationMbeanImpl implements RouterDestinationMbean {
    protected String parentName;

    protected String name;

    protected AtomicInteger totalMessagesSent;

    protected AtomicInteger totalPendingAcks;

    public RouterDestinationMbeanImpl() {
        this.totalMessagesSent = new AtomicInteger();
        this.totalPendingAcks = new AtomicInteger();
    }

    public void register() throws Exception {
        MBeanServer platform = ManagementFactory.getPlatformMBeanServer();
        StandardMBean standardMBean = new StandardMBean(this, RouterDestinationMbean.class);
        ObjectName on = new ObjectName(Helper.JMX_ROOT_NAME + ",router=" + parentName + ",destination=" + name);
        if (platform.isRegistered(on) == false) {
            platform.registerMBean(standardMBean, on);
        }
    }

    @Override
    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int incrementTotalMessagesSent() {
        return totalMessagesSent.incrementAndGet();
    }

    @Override
    public int getTotalMessagesSent() {
        return totalMessagesSent.get();
    }

    public int incrementTotalPendingAcks() {
        return totalPendingAcks.incrementAndGet();
    }

    public int decrementTotalPendingAcks() {
        return totalPendingAcks.decrementAndGet();
    }

    @Override
    public int getTotalPendingAcks() {
        return totalPendingAcks.get();
    }

    @Override
    public void clearStats() {
        totalMessagesSent.set(0);
        totalPendingAcks.set(0);
    }
}
