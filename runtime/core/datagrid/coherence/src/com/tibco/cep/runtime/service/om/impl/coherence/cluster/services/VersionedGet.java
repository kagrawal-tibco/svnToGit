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

import com.tangosol.util.InvocableMap;
import com.tangosol.util.processor.AbstractProcessor;
import com.tibco.cep.runtime.model.element.VersionedObject;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Apr 25, 2008
 * Time: 12:20:16 PM
 * To change this template use File | Settings | File Templates.
 */
//Bala: not used so deprecate it for now.
@Deprecated
public class VersionedGet extends AbstractProcessor {
    Integer version;

    public VersionedGet(int version) {
        this.version = version;
    }

    public VersionedGet() {
    }


    public Object process(InvocableMap.Entry entry) {
        if (entry.getValue() instanceof VersionedObject) {
            return ((VersionedObject) entry.getValue()).getVersionIndicator().compareTo(version) > 0 ? entry.getValue() : null;
        } else {
            return null;
        }
    }

    public void readExternal(DataInput dataInput) throws IOException {
        version = dataInput.readInt();
    }

    public void writeExternal(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(version);
    }
}
