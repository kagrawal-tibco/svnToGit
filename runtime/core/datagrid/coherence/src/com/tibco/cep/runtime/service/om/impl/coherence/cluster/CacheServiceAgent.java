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

package com.tibco.cep.runtime.service.om.impl.coherence.cluster;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Mar 17, 2008
 * Time: 10:26:52 AM
 * To change this template use File | Settings | File Templates.
 */

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.tangosol.io.ExternalizableLite;
import com.tangosol.net.AbstractInvocable;
import com.tibco.cep.runtime.service.cluster.CacheClusterProvider;
import com.tibco.cep.runtime.service.cluster.Cluster;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Nov 12, 2007
 * Time: 3:19:33 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class CacheServiceAgent extends AbstractInvocable implements ExternalizableLite {

    /**
     * @param
     */
    public CacheServiceAgent() {
    }

    protected Cluster getCluster() throws Exception {
        return CacheClusterProvider.getInstance().getCacheCluster();
    }

    public void readExternal(DataInput dataInput) throws IOException {
    }

    public void writeExternal(DataOutput dataOutput) throws IOException {
    }

}
