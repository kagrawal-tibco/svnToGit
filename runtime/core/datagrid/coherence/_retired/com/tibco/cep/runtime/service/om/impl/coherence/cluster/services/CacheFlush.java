/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 27/8/2010
 */

package com.tibco.cep.runtime.service.om.impl.coherence.cluster.services;

import com.tangosol.net.InvocationService;
import com.tibco.cep.runtime.service.cluster.CacheClusterProvider;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.om.impl.coherence.cluster.CacheServiceAgent;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Nov 12, 2007
 * Time: 6:43:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class CacheFlush extends CacheServiceAgent {

    public CacheFlush() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }


    /**
     *
     */
    public void run() {
        try {
            Cluster cluster = getCluster();
            if (cluster != null) {
                Thread.currentThread().setContextClassLoader(cluster.getRuleServiceProvider().getClassLoader());
                cluster.flushCache();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @param dataInput
     * @throws java.io.IOException
     */
    public void readExternal(DataInput dataInput) throws IOException {
        super.readExternal(dataInput);    //To change body of overridden methods use File | Settings | File Templates.
    }

    /**
     * @param dataOutput
     * @throws IOException
     */
    public void writeExternal(DataOutput dataOutput) throws IOException {
        super.writeExternal(dataOutput);    //To change body of overridden methods use File | Settings | File Templates.
    }

    /**
     * @param cluster
     * @throws Exception
     */
    public final static void flushCache(Cluster cluster) throws Exception {
        InvocationService invoker = cluster.getInvocationService();
        invoker.query(new CacheFlush(), null);
    }

    public final static void flushCache() throws Exception {
        Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();
        if (cluster != null) {
            InvocationService invoker = cluster.getInvocationService();
            invoker.query(new CacheFlush(), cluster.getGroupMembershipService().getStorageEnabledMembers());
        }
    }

}

