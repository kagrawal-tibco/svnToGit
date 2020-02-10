/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.events;



import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.tibco.cep.runtime.model.serializers.SerializableLite;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: May 4, 2008
 * Time: 3:42:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class EventTuple implements SerializableLite {
    private long id;
    private int agentId;

    public final static int NEWEVENT       =-2;
    public final static int RECOVEREDEVENT =-1;

    /**
     *
     * @param id
     * @param agentId
     */
    public EventTuple(long id, int agentId) {
        this.id=id;
        this.agentId=agentId;
    }

    /**
     *
     * @param id
     */
    public EventTuple(long id) {
        this.id=id;
        this.agentId=NEWEVENT;
    }

    /**
     *
     */
    public EventTuple() {
    }


    /**
     *
     * @return
     */
    public long getId() {
        return id;
    }


    /**
     *
     * @return
     */
    public boolean isAvailable() {
        return (agentId == RECOVEREDEVENT) || (agentId == NEWEVENT);
    }

    /**
     *
     * @return
     */
    public boolean isRecovered() {
        return (agentId == RECOVEREDEVENT);
    }

    /**
     *
     * @return
     */
    public boolean isNewEvent() {
        return (agentId == NEWEVENT);
    }

    /**
     *
     * @return
     */
    public boolean isOwned(int agentId) {
       return (this.agentId == agentId);
   }
    /**
     *
     * @param agentId
     */
    public boolean acquire(int agentId) {
        if (isAvailable()) {
            this.agentId=agentId;
            return true;
        } else {
            return false;
        }
    }

    /**
     * 
     */
    public void releaseAgentId() {
        this.agentId=RECOVEREDEVENT;
    }

    /**
     *
     * @param dataInput
     * @throws IOException
     */

    public void readExternal(DataInput dataInput) throws IOException {
        id=dataInput.readLong();
        agentId=dataInput.readInt();
    }

    /**
     *
     * @param dataOutput
     * @throws IOException
     */

    public void writeExternal(DataOutput dataOutput) throws IOException {
        dataOutput.writeLong(id);
        dataOutput.writeInt(agentId);
    }


    public String toString() {
        return "" + id + ","+ agentId;        
    }

    public int getAgentId() {
        return agentId;
    }
}
