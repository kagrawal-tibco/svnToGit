/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 13/8/2010
 */

package com.tibco.cep.runtime.service.cluster.system.mm;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.StandardMBean;

import com.tibco.cep.runtime.service.cluster.system.impl.ObjectTableCache;

/**
 * Created by IntelliJ IDEA. User: ssubrama Date: Jul 13, 2010 Time: 9:02:00 AM To change this template use File |
 * Settings | File Templates.
 */
public class ObjectTableCacheMBeanImpl implements ObjectTableCacheMBean {
    ObjectTableCache objectTable;

    public ObjectTableCacheMBeanImpl(ObjectTableCache objectTable) {
        this.objectTable = objectTable;
    }

    public void registerMBean() {
        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            ObjectName name = new ObjectName("com.tibco.be:service=Cache,name=" + getObjectTableCacheName());

            StandardMBean mBean = new StandardMBean(this, ObjectTableCacheMBean.class);
            mbs.registerMBean(mBean, name);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public String getObjectTableCacheName() {
        return objectTable.getObjectIdTable().getName();
    }

    @Override
    public String getObjectTableExtIdCacheName() {
        return objectTable.getObjectExtIdTable().getName();
    }

    @Override
    public int getObjectTableSize() {
        return objectTable.getObjectIdTable().size();
    }

    @Override
    public int getObjectExtIdTableSize() {
        return objectTable.getObjectExtIdTable().size();
    }

    @Override
    public String getTupleById(long id) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getTupleByExtId(String extId) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
