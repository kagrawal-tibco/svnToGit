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
import com.tangosol.util.ExternalizableHelper;
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
 * Date: Apr 21, 2008
 * Time: 4:46:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class InvokeRuleFunction extends CacheServiceAgent {
    String ruleFunctionURI;
    Object[] args;

    public InvokeRuleFunction(String ruleFunctionURI, Object[] args) {
        super();    //To change body of overridden methods use File | Settings | File Templates.
        this.ruleFunctionURI = ruleFunctionURI;
        this.args = args;
    }

    public InvokeRuleFunction() {
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
                try {
                    Object ret = cluster.invokeRuleFunction(ruleFunctionURI, args);
                    this.setResult(ret);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    this.setResult(ex);
                }
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
        ruleFunctionURI = dataInput.readUTF();
        args = (Object[]) ExternalizableHelper.readObject(dataInput);
    }

    /**
     * @param dataOutput
     * @throws IOException
     */
    public void writeExternal(DataOutput dataOutput) throws IOException {
        super.writeExternal(dataOutput);    //To change body of overridden methods use File | Settings | File Templates.
        dataOutput.writeUTF(ruleFunctionURI);
        ExternalizableHelper.writeObject(dataOutput, args);
    }

    /**
     * @param
     * @throws Exception
     */

    public final static Object invokeRuleFunction(String ruleFunctionURI, Object[] args) throws Exception {
        Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();
        InvocationService invoker = cluster.getInvocationService();
        Map resultSet = invoker.query(new InvokeRuleFunction(ruleFunctionURI, args), null);
        java.util.Iterator iter = resultSet.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            com.tangosol.net.Member member = (com.tangosol.net.Member) entry.getKey();
            Object ret = entry.getValue();
            if (ret instanceof Exception) {
                throw new Exception((Exception) ret);
            } else if (ret != null) {
                return ret;
            }
        }
        return null;
    }
}


