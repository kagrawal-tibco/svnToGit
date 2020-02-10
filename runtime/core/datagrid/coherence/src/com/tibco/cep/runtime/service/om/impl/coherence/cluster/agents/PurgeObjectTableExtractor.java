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

package com.tibco.cep.runtime.service.om.impl.coherence.cluster.agents;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.tangosol.io.ExternalizableLite;
import com.tangosol.util.extractor.AbstractExtractor;
import com.tibco.cep.runtime.service.cluster.system.impl.ObjectTupleImpl;

/**
 * Created by IntelliJ IDEA.
 * User: amgupta
 * Date: Aug 7, 2008
 * Time: 3:55:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class PurgeObjectTableExtractor extends AbstractExtractor implements ExternalizableLite {
    public final static PurgeObjectTableExtractor INSTANCE = new PurgeObjectTableExtractor();

    public PurgeObjectTableExtractor() {
    }

    public Object extract(Object o) {
        if (o instanceof byte[]) {
            try {
                ObjectTupleImpl e = ObjectTupleImpl.deserialize((byte[]) o);
                return e.isDeleted();
            } catch (IOException e1) {
                e1.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public void readExternal(DataInput dataInput) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void writeExternal(DataOutput dataOutput) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}

