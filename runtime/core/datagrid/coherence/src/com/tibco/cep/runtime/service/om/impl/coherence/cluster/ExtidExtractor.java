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

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.tangosol.io.ExternalizableLite;
import com.tangosol.util.extractor.AbstractExtractor;
import com.tibco.cep.runtime.service.cluster.system.ObjectTable.Tuple;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: May 12, 2008
 * Time: 8:29:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class ExtidExtractor extends AbstractExtractor implements ExternalizableLite {
    public ExtidExtractor() {
    }

    public Object extract(Object o) {
        Tuple tuple = (Tuple) o;
        return tuple.getExtId();
    }

    public void readExternal(DataInput dataInput) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void writeExternal(DataOutput dataOutput) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
