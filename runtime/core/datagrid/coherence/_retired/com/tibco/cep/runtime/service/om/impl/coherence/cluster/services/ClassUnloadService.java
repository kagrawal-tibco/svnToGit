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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: Nov 11, 2008
 * Time: 11:18:04 AM
 * Execute the unload external class operation on each member of the cluster
 */
public class ClassUnloadService extends CacheServiceAgent {

    /**
     * Default constructor is a must else Coherence does not invoke this
     * {@link com.tangosol.net.Invocable} on all members
     */
    public ClassUnloadService() {
        super();
    }

    /**
     *
     */
    public void run() {
        try {
            Cluster cluster = getCluster();
            if (cluster != null) {
                Thread.currentThread().setContextClassLoader(cluster.getRuleServiceProvider().getClassLoader());
                cluster.getAgentManager().deactivateExternalClass();
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
     * No state changes to be serialzed/deserialized for every member
     *
     * @param dataOutput
     * @throws IOException
     */
    public void writeExternal(DataOutput dataOutput) throws IOException {
        super.writeExternal(dataOutput);    //To change body of overridden methods use File | Settings | File Templates.
    }

    /**
     * Unload an external class loaded into each cluster member using the
     * <b>externalClasses.Path</b> property.
     *
     * @return
     * @throws Exception
     */
    //TODO Rollback operation on each node if it fails even on a single node.
    public final static boolean unloadExternalClass() throws Exception {
        boolean done = true;
        final Set<Member> failedMembers = new HashSet<Member>();
        Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();
        Logger logger = cluster.getRuleServiceProvider().getLogger(ClassUnloadService.class);
        if (cluster != null) {
            InvocationService invoker = cluster.getInvocationService();
            SingleKeyCache cache = cluster.getSingleKeyCache();
            //Invoke on all members

            Map resultSet = invoker.query(new ClassUnloadService(), null);
            java.util.Iterator<Map.Entry> iter = resultSet.entrySet().iterator();

            while (iter.hasNext()) {
                Map.Entry entry = iter.next();
                Member member = (Member) entry.getKey();
                Object ret = entry.getValue();
                boolean b = true;
                if (ret instanceof Exception) {

                    logger.log(Level.ERROR, cluster.getClusterName() + " Member=" + member + " has an exception " + ret);

                    b = false;
                    //Add this particular member to the set
                    failedMembers.add(member);
                }
                done = b && done;
            }
            if (done) {
                //Remove the solitary entry from cache

                logger.log(Level.INFO, "Class unload service - Clearing deleted entity cache ");
                cache.clear();
                logger.log(Level.INFO, "Class unload service - Deleted entity cache size " + cache.size());

            } else {
                logger.log(Level.WARN, "Unload operation could not complete succssfully on one or more nodes.");

                logger.log(Level.WARN, "Rolling back the unload transaction ");

                //Rollback on all nodes
                new UnloadServiceTxn().rollback(failedMembers);
            }
        }
        return done;
    }
}
