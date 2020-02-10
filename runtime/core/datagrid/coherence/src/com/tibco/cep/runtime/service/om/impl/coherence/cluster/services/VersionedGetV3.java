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

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.tangosol.util.InvocableMap;
import com.tibco.cep.runtime.model.element.VersionedObject;
import com.tibco.cep.runtime.service.om.api.ComparisonResult;

/*
* Author: Ashwin Jayaprakash Date: Jan 7, 2009 Time: 2:08:20 PM
*/
//Bala: Not used, so deprecate
@Deprecated
public class VersionedGetV3 implements InvocableMap.EntryProcessor, Externalizable {
    protected int currentVersion;

    protected int versionStartBytePosition;

    /**
     * @param currentVersion
     * @param versionStartBytePosition <code>-1</code> if this is not to be used.
     */
    public VersionedGetV3(int currentVersion, int versionStartBytePosition) {
        this.currentVersion = currentVersion;
        this.versionStartBytePosition = versionStartBytePosition;
    }

    public VersionedGetV3() {
        this.versionStartBytePosition = -1;
    }

    public Object process(InvocableMap.Entry entry) {
        Object value = entry.getValue();

        return doCompare(value);
    }

    public Map processAll(Set set) {
        Set<InvocableMap.Entry> input = set;
        Map<Object, Object> output = new HashMap<Object, Object>(set.size());

        for (InvocableMap.Entry entry : input) {
            Object key = entry.getKey();
            Object value = entry.getValue();
            Object result = doCompare(value);

            output.put(key, result);
        }

        return output;
    }

    /**
     * @param value
     * @return The value if the version in the Cache is greater than the current version or one of
     *         {@link ComparisonResult}.
     */
    protected Object doCompare(Object value) {
        if (value == null) {
            return ComparisonResult.VALUE_NOT_PRESENT;
        } else if (value instanceof byte[]) {
            byte[] bytes = (byte[]) value;

            if (versionStartBytePosition >= 0) {
                int cacheVersion = (((bytes[versionStartBytePosition] & 0xff) << 24) |
                        ((bytes[versionStartBytePosition + 1] & 0xff) << 16) |
                        ((bytes[versionStartBytePosition + 2] & 0xff) << 8) |
                        ((bytes[versionStartBytePosition + 3] & 0xff) << 0));

                if (cacheVersion > currentVersion) {
                    return value;
                }

                return ComparisonResult.SAME_VERSION;
            }

            return value;
        } else if (value instanceof VersionedObject) {
            int cacheVersion = ((VersionedObject) value).getVersion();

            if (cacheVersion > currentVersion) {
                return value;
            }

            return ComparisonResult.SAME_VERSION;
        }

        return ComparisonResult.NOT_VERSIONED;
    }

    //-------------

    public void readExternal(ObjectInput in) throws IOException {
        currentVersion = in.readInt();
        versionStartBytePosition = in.readInt();
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(currentVersion);
        out.writeInt(versionStartBytePosition);
    }
}