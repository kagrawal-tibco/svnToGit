/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.scheduler;


import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.tibco.cep.common.resource.Id;
import com.tibco.cep.impl.common.resource.UID;
import com.tibco.cep.runtime.model.serializers.SerializableLite;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Sep 12, 2008
 * Time: 8:37:23 AM
 * To change this template use File | Settings | File Templates.
 */
public class WorkManagerEntry implements SerializableLite {
    private String id;
    private long pollingInterval;
    private long refreshAhead;
    private boolean isCacheLimited=true;
    private Id nodeId;
    private byte mode;

    public WorkManagerEntry() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getPollingInterval() {
        return pollingInterval;
    }

    public void setPollingInterval(long pollingInterval) {
        this.pollingInterval = pollingInterval;
    }

    public long getRefreshAhead() {
        return refreshAhead;
    }

    public void setRefreshAhead(long refreshAhead) {
        this.refreshAhead = refreshAhead;
    }

    public void readExternal(DataInput dataInput) throws IOException {
        id=dataInput.readUTF();
        pollingInterval=dataInput.readLong();
        refreshAhead=dataInput.readLong();
        isCacheLimited=dataInput.readBoolean();
        mode=dataInput.readByte();
        if (dataInput.readBoolean()) {
            nodeId=new UID(dataInput.readUTF());
        }

    }

    public void writeExternal(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(id);
        dataOutput.writeLong(pollingInterval);
        dataOutput.writeLong(refreshAhead);
        dataOutput.writeBoolean(isCacheLimited);
        dataOutput.writeByte(mode);
        if (nodeId != null) {
            dataOutput.writeBoolean(true);
            dataOutput.writeUTF(nodeId.getAsString());
        } else {
            dataOutput.writeBoolean(false);
        }
    }


    public Id getNodeId() {
        return nodeId;
    }

    public void setNodeId(Id nodeId) {
        this.nodeId = nodeId;
    }

    public boolean isCacheLimited() {
        return isCacheLimited;
    }

    public void setCacheLimited(boolean cacheLimited) {
        isCacheLimited = cacheLimited;
    }

    public byte getMode() {
        return mode;
    }

    public void setMode(byte mode) {
        this.mode = mode;
    }
    public String toString() {
    	return "WorkManager: Id=" + id + ", poll=" + pollingInterval + ", refrAhd=" + refreshAhead + ", nodeId=" + nodeId + ", mode=" + mode;
    }
}
