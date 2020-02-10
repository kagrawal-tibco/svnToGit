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

package com.tibco.cep.runtime.service.cluster.txn;



import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.IOException;

import com.tibco.cep.runtime.model.serializers.SerializableLite;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: May 10, 2008
 * Time: 12:35:58 PM
 * To change this template use File | Settings | File Templates.
 */

public class RtcKey implements SerializableLite {
    long txnId;
    int[] bitMap;

    public RtcKey() {
    }

    /**
     * @param txnId
     * @param bitMap
     */
    public RtcKey(long txnId, int[] bitMap) {
        this.txnId = txnId;
        this.bitMap = bitMap;
    }

    public long getTxnId() {
        return txnId;
    }

    public int[] getBitMap() {
        return bitMap;
    }

    public void readExternal(DataInput dataInput) throws IOException {
        txnId = dataInput.readLong();
        int len = dataInput.readInt();
        bitMap = new int[len];
        for (int i = 0; i < len; i++) {
            bitMap[i] = dataInput.readInt();
        }
    }

    public void writeExternal(DataOutput dataOutput) throws IOException {
        dataOutput.writeLong(txnId);
        if (bitMap != null) {
            dataOutput.writeInt(bitMap.length);
            for (int i = 0; i < bitMap.length; i++) {
                dataOutput.writeInt(bitMap[i]);
            }
        } else {
            dataOutput.writeInt(0);
        }
    }

    public final static int[] extractTopic(byte[] buf) throws IOException {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(buf));
        dis.readLong();
        int len = dis.readInt();
        int[] ret = new int[len];
        for (int i = 0; i < len; i++) {
            ret[i] = dis.readInt();
        }
        return ret;
    }
}