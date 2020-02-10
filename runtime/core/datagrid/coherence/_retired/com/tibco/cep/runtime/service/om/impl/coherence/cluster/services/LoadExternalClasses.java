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
import com.tangosol.net.Member;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.cluster.CacheClusterProvider;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.om.impl.coherence.cluster.CacheServiceAgent;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Map;
import java.util.Set;


/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Apr 30, 2008
 * Time: 5:18:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class LoadExternalClasses extends CacheServiceAgent {

    public LoadExternalClasses() {
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
                cluster.deployExternalClasses();
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

    /**
     * @throws Exception
     */
    public final static boolean deployExternalClasses(boolean suspend, final Set<Member> members) throws Exception {
        Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();
        boolean done = true;

        if (cluster != null) {
            final Logger logger = cluster.getRuleServiceProvider().getLogger(LoadExternalClasses.class);
            final String clusterName = cluster.getClusterName();

            InvocationService invoker = cluster.getInvocationService();
            // Suspend all the nodes

            if (suspend) {
                logger.log(Level.INFO, "Cluster %s: Initiated Suspend Request to All Nodes", clusterName);

                if (!SuspendAgents.suspendAgents()) {
                    logger.log(Level.INFO, "Cluster %s: Failed to suspend all agents.", clusterName);
                    if (!ResumeAgents.resumeAgents()) {
                        logger.log(Level.INFO, "Cluster %s: Failed to resume all agents.", clusterName);
                    }
                    return false;
                }
            }

            logger.log(Level.INFO, "Cluster %s: Initiated LoadExternalClasses to all nodes.", clusterName);

            Map resultSet = invoker.query(new LoadExternalClasses(), members);
            java.util.Iterator iter = resultSet.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                com.tangosol.net.Member member = (com.tangosol.net.Member) entry.getKey();
                Object ret = entry.getValue();
                if (ret instanceof Exception) {
                    logger.log(Level.INFO, "%s: OnDeployExternalClasses: Member=%s has an exception %s",
                            clusterName, member, ret);
                    done = false;
                }
            }

            if (!ResumeAgents.resumeAgents()) {
                logger.log(Level.INFO, "Cluster %s: Failed to resume all agents.", clusterName);
            }

            return done;
        }
        return false;
    }


}


