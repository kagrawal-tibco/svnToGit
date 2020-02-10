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

package com.tibco.cep.runtime.service.cluster.om;

import com.tibco.cep.kernel.core.base.BaseHandle;
import com.tibco.cep.kernel.core.base.RtcOperationList;
import com.tibco.cep.kernel.core.base.tuple.TupleRow;
import com.tibco.cep.kernel.model.knowledgebase.TypeInfo;
import com.tibco.cep.kernel.model.knowledgebase.WorkingMemory;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: May 8, 2008
 * Time: 3:54:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class RtcChangeStatus extends BaseHandle {
    protected int[] dirtyBitArray;

    public RtcChangeStatus(RtcOperationList.RtcListEntry rtcEntry) {
        super(null);
        rtcEntry.handle.copyStatus(this);
        if (null != rtcEntry.mutableDirtyBitArray) {
            dirtyBitArray = new int[rtcEntry.mutableDirtyBitArray.length];
            System.arraycopy(rtcEntry.mutableDirtyBitArray, 0, this.dirtyBitArray, 0, rtcEntry.mutableDirtyBitArray.length);
        } else {
            dirtyBitArray = new int[0];
        }
    }

    public RtcChangeStatus(byte status, int[] dirtyBitArray) {
        super(null);
        this.dirtyBitArray = dirtyBitArray;
        this.rtcStatus = status;
    }

    public boolean hasRef() {
        throw new UnsupportedOperationException("This method() should not be called on RtcChangeStatus");
    }

    public int[] getDirtyBitArray() {
        return dirtyBitArray;
    }

    public byte getRtcStatus() {
        return rtcStatus;
    }

    public Object getObject() {
        throw new UnsupportedOperationException("This method() should not be called on RtcChangeStatus");
    }

    @Override
    public void clearAsserted() {
        throw new RuntimeException("This method() should not be called on RtcChangeStatus");
    }

    @Override
    public void clearDirty() {
        throw new RuntimeException("This method() should not be called on RtcChangeStatus");
    }

    @Override
    public void clearInRete() {
        throw new RuntimeException("This method() should not be called on RtcChangeStatus");
    }

    @Override
    public void evict() {
        throw new RuntimeException("This method() should not be called on RtcChangeStatus");
    }

    @Override
    public TypeInfo getTypeInfo() {
        throw new RuntimeException("This method() should not be called on RtcChangeStatus");
    }

    @Override
    public WorkingMemory getWorkingMemory() {
        throw new RuntimeException("This method() should not be called on RtcChangeStatus");
    }////////////////////////////////////  non RTC operation /////////////////////////////////////////////////////////////

    @Override
    public boolean okMarkAssert() {
        throw new RuntimeException("This method() should not be called on RtcChangeStatus");
    }

    @Override
    public boolean okMarkDelete() {
        throw new RuntimeException("This method() should not be called on RtcChangeStatus");
    }

    @Override
    public boolean okMarkDirty() {
        throw new RuntimeException("This method() should not be called on RtcChangeStatus");
    }

    @Override
    public String printInfo() {
        throw new RuntimeException("This method() should not be called on RtcChangeStatus");
    }

    @Override
    public void registerTupleRow(short tableId, TupleRow row) {
        throw new RuntimeException("This method() should not be called on RtcChangeStatus");
    }

    @Override
    public void removeFromTable(short tableId) {
        throw new RuntimeException("This method() should not be called on RtcChangeStatus");
    }

    @Override
    public void removeFromTables() {
        throw new RuntimeException("This method() should not be called on RtcChangeStatus");
    }

    @Override
    public boolean rtcAlreadyIncluded() {
        throw new RuntimeException("This method() should not be called on RtcChangeStatus");
    }

    @Override
    public void rtcClearAll() {
        throw new RuntimeException("This method() should not be called on RtcChangeStatus");
    }

    @Override
    public void setAsserted() {
        throw new RuntimeException("This method() should not be called on RtcChangeStatus");
    }

    @Override
    public void setInRete() {
        throw new RuntimeException("This method() should not be called on RtcChangeStatus");
    }

//    @Override
//    public Object setRef(Object obj) {
//        throw new RuntimeException("This method() should not be called on RtcChangeStatus");
//    }

    @Override
    public void setRetracted() {
        throw new RuntimeException("This method() should not be called on RtcChangeStatus");
    }

    @Override
    protected void setRtcAsserted() {
        throw new RuntimeException("This method() should not be called on RtcChangeStatus");
    }

    @Override
    protected void setRtcContainerRef() {
        throw new RuntimeException("This method() should not be called on RtcChangeStatus");
    }

    @Override
    protected void setRtcDeleted() {
        throw new RuntimeException("This method() should not be called on RtcChangeStatus");
    }

    @Override
    protected void setRtcModified() {
        throw new RuntimeException("This method() should not be called on RtcChangeStatus");
    }

    @Override
    protected void setRtcReverseRef() {
        throw new RuntimeException("This method() should not be called on RtcChangeStatus");
    }

    @Override
    protected void setRtcScheduled() {
        throw new RuntimeException("This method() should not be called on RtcChangeStatus");
    }

    @Override
    protected void setRtcStateChanged() {
        throw new RuntimeException("This method() should not be called on RtcChangeStatus");
    }

    @Override
    public void setTypeInfo(TypeInfo info) {
        throw new RuntimeException("This method() should not be called on RtcChangeStatus");
    }

    @Override
    protected void touch() {
        throw new RuntimeException("This method() should not be called on RtcChangeStatus");
    }

    @Override
    public void unregisterTupleRow(short tableId, TupleRow row) {
        throw new RuntimeException("This method() should not be called on RtcChangeStatus");
    }

    @Override
    public void unregisterTupleRows(short tableId) {
        throw new RuntimeException("This method() should not be called on RtcChangeStatus");
    }
}
