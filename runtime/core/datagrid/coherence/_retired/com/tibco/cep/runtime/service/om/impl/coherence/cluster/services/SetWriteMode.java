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
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.om.impl.coherence.cluster.CacheServiceAgent;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Nov 12, 2007
 * Time: 3:23:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class SetWriteMode extends CacheServiceAgent {
    boolean writeMode = false;

    public SetWriteMode(boolean writeMode) {
        super();    //To change body of overridden methods use File | Settings | File Templates.
        this.writeMode = writeMode;
    }

    public SetWriteMode() {
    }

    /**
     *
     */
    public void run() {
        try {
            Cluster cluster = getCluster();
            if (cluster != null) {
                cluster.setWriteMode(writeMode);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @param dataInput
     * @throws IOException
     */
    public void readExternal(DataInput dataInput) throws IOException {
        super.readExternal(dataInput);    //To change body of overridden methods use File | Settings | File Templates.
        writeMode = dataInput.readBoolean();
    }

    /**
     * @param dataOutput
     * @throws IOException
     */
    public void writeExternal(DataOutput dataOutput) throws IOException {
        super.writeExternal(dataOutput);    //To change body of overridden methods use File | Settings | File Templates.
        dataOutput.writeBoolean(writeMode);
    }

    /**
     * @param cluster
     * @param writeMode
     * @throws Exception
     */
    public final static void setWriteMode(Cluster cluster, boolean writeMode) throws Exception {
        InvocationService invoker = cluster.getInvocationService();
        invoker.execute(new SetWriteMode(writeMode), null, null);
    }

}
