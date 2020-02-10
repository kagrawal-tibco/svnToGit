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
import com.tibco.cep.runtime.service.om.api.ComparisonResult;

/**
 * Created by IntelliJ IDEA. User: apuneet Date: Apr 25, 2008 Time: 12:20:16 PM To change this
 * template use File | Settings | File Templates.
 */
//Bala: this is deprecated. use the common one.
@Deprecated
public class VersionedGetV2 extends AbstractProcessor {
    int currentVersion;

    public VersionedGetV2(int currentVersion) {
        this.currentVersion = currentVersion;
    }

    public VersionedGetV2() {
    }

    /**
     * @param entry
     * @return The value if the version in the Cache is greater than the current version or one of
     *         {@link ComparisonResult}.
     */
    public Object process(InvocableMap.Entry entry) {
        final Object value = entry.getValue();

        if (value == null) {
            return ComparisonResult.VALUE_NOT_PRESENT;
        } else if (value instanceof VersionedObject) {
            final int cacheVersion = ((VersionedObject) value).getVersion();

            if (cacheVersion > currentVersion) {
                return value;
            }

            return ComparisonResult.SAME_VERSION;
        }

        return ComparisonResult.NOT_VERSIONED;
    }

    public void readExternal(DataInput dataInput) throws IOException {
        currentVersion = dataInput.readInt();
    }

    public void writeExternal(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(currentVersion);
    }
}