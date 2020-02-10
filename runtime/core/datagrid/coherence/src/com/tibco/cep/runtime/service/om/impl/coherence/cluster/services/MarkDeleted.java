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

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.tangosol.io.ExternalizableLite;
import com.tangosol.util.InvocableMap;
import com.tangosol.util.processor.AbstractProcessor;
import com.tibco.cep.runtime.service.cluster.system.impl.ObjectTupleImpl;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: May 12, 2008
 * Time: 9:36:00 AM
 * To change this template use File | Settings | File Templates.
 */
public class MarkDeleted extends AbstractProcessor implements ExternalizableLite {

    public MarkDeleted() {
    }

    public Object process(InvocableMap.Entry entry) {
        if (entry.getValue() instanceof byte[]) {
            ObjectTupleImpl tuple = null;
            try {
                tuple = ObjectTupleImpl.deserialize((byte[]) entry.getValue());
                if (entry.isPresent()) {
                    tuple.markDeleted();
                    entry.setValue(ObjectTupleImpl.serialize(tuple));
                    return true;
                } else {
                    return false;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    public void readExternal(DataInput dataInput) throws IOException {

    }

    public void writeExternal(DataOutput dataOutput) throws IOException {
    }
}


