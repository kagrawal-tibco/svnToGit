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
import com.tibco.cep.runtime.session.RuleServiceProvider;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Apr 22, 2008
 * Time: 2:15:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class GetAgentProperty extends CacheServiceAgent {
    private String property;

    public GetAgentProperty() {

    }

    public GetAgentProperty(String prop) {
        super();
        this.property = prop;
    }

    /**
     *
     */
    public void run() {
        try {
            RuleServiceProvider rsp = getCluster().getRuleServiceProvider();
            String val = rsp.getProperties().getProperty(property);
            this.setResult(val);
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
        this.property = dataInput.readUTF();
    }

    /**
     * @param dataOutput
     * @throws java.io.IOException
     */
    public void writeExternal(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(this.property);
    }

    public final static Object getProperty(String prop, Member member) {
        boolean done = true;

        Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();
        if (cluster != null) {
            InvocationService invoker = cluster.getInvocationService();
            Set memberSet = new HashSet();
            memberSet.add(member);
            Map resultSet = invoker.query(new GetAgentProperty(prop), memberSet);
            final Logger logger = cluster.getRuleServiceProvider().getLogger(GetAgentProperty.class);
            java.util.Iterator iter = resultSet.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                com.tangosol.net.Member mbr = (com.tangosol.net.Member) entry.getKey();
                Object ret = entry.getValue();
                if (ret instanceof Exception) {
                    logger.log(Level.INFO, "%s: getAgentProperty: member=%s has an exception %s",
                            cluster.getClusterName(), member, ret);
                    return null;
                } else {
                    return ret;
                }
            }
        }
        return null;
    }

}