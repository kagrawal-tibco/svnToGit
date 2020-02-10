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
import java.util.ArrayList;

import com.tangosol.io.ExternalizableLite;
import com.tangosol.util.Filter;
import com.tangosol.util.MapEvent;
import com.tibco.cep.runtime.service.cluster.CacheClusterProvider;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.system.MetadataCache;
import com.tibco.cep.runtime.service.cluster.txn.RtcKey;
import com.tibco.cep.util.BitMapHelper;

/**
 * User: ssubrama
 * Creation Date: May 8, 2008
 * Creation Time: 3:30:51 PM
 * <p/>
 * $LastChangedDate$
 * $Rev$
 * $LastChangedBy$
 * $URL$
 */
public class ClassFilter implements Filter, ExternalizableLite {

    int[] encodedClassIds;
    transient BitMapHelper bmHelper;


    public ClassFilter(int[] classIds) {
        this(classIds, false);

    }

    public ClassFilter(int[] classIds, boolean bNeedEncoding) {

        bmHelper = new BitMapHelper(MetadataCache.BE_TYPE_START);
        this.encodedClassIds = classIds;

        if (bNeedEncoding) {
            ArrayList<Integer> list = new ArrayList<Integer>(classIds.length);
            for (int i = 0; i < classIds.length; i++) {
                list.add(classIds[i]);
            }
            this.encodedClassIds = bmHelper.encode2BitMapVector(list);
        }

    }

    public ClassFilter() {
        bmHelper = new BitMapHelper(MetadataCache.BE_TYPE_START);
    }

    protected Cluster getCluster() throws Exception {
        return CacheClusterProvider.getInstance().getCacheCluster();
    }

    public boolean evaluate(Object o) {
        try {
            if (o instanceof MapEvent) {
                MapEvent evt = (MapEvent) o;
                RtcKey rtcKey = (RtcKey) evt.getKey();
                boolean val = bmHelper.matches(encodedClassIds, rtcKey.getBitMap());
                return val;
            } else if (o instanceof RtcKey) {
                RtcKey rtcKey = (RtcKey) o;
                boolean val = bmHelper.matches(encodedClassIds, rtcKey.getBitMap());
                return val;
            } else if (o instanceof byte[]) {
                int[] bitMap = RtcKey.extractTopic((byte[]) o);
                boolean val = bmHelper.matches(encodedClassIds, bitMap);
                return val;
            }
            return false;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    public void readExternal(DataInput dataInput) throws IOException {
        int len = dataInput.readInt();
        encodedClassIds = new int[len];
        for (int j = 0; j < len; j++) {
            encodedClassIds[j] = dataInput.readInt();
        }
    }

    public void writeExternal(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(encodedClassIds.length);
        for (int j = 0; j < encodedClassIds.length; j++) {
            dataOutput.writeInt(encodedClassIds[j]);
        }
    }
}
