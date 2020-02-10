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
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.cluster.CacheClusterProvider;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.om.impl.coherence.cluster.CacheServiceAgent;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Apr 22, 2008
 * Time: 2:12:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class SuspendAgents extends CacheServiceAgent {

    public SuspendAgents() {
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
                cluster.getAgentManager().suspendAgents();
                this.setResult(Boolean.TRUE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            this.setResult(ex);
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

    public final static boolean suspendAgents() throws Exception {
        boolean done = true;
        CacheCluster cluster = CacheClusterProvider.getInstance().getCacheCluster();
        if (cluster != null) {
            InvocationService invoker = cluster.getInvocationService();
            Map resultSet = invoker.query(new SuspendAgents(), null);
            final Logger logger = cluster.getRuleServiceProvider().getLogger(ResumeAgents.class);
            final boolean canLogInfo = logger.isEnabledFor(Level.INFO);
            java.util.Iterator iter = resultSet.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                com.tangosol.net.Member member = (com.tangosol.net.Member) entry.getKey();
                Object ret = entry.getValue();
                if (ret instanceof Exception) {
                    if (canLogInfo) {
                        logger.log(Level.INFO, "%s: OnSuspend: Member=%s has an exception: %s",
                                cluster.getClusterName(), member, ret);
                    }
                    done = false;
                }
            }
        }
        return done;
    }

}


