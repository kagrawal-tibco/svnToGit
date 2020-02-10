/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.agent.tasks;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.tibco.cep.runtime.model.event.TimeEvent;
import com.tibco.cep.runtime.service.cluster.agent.InferenceAgent;
import com.tibco.cep.runtime.service.cluster.scheduler.WorkEntry;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Jan 27, 2009
 * Time: 3:03:23 PM
 * To change this template use File | Settings | File Templates.
 */
@Deprecated
public class SendTimeEventTask implements WorkEntry {
    long scheduledTime;
    String extId;
    String closure;
    int typeId;
    String agentName;

    public SendTimeEventTask(String agentName, TimeEvent te, int typeId) {
        this.scheduledTime=te.getScheduledTimeMillis();
        this.extId=te.getExtId();
        this.closure=te.getClosure();
        this.typeId=typeId;
        this.agentName=agentName;
    }

    public SendTimeEventTask() {
    }

    public void execute(String key, Object cacheAgent) throws Exception {
//        StringTokenizer st= new StringTokenizer(key, ".");
//        String agentName=st.nextToken();
//        st.nextToken(); // Skip the id
//        int typeId= Integer.valueOf(st.nextToken());
//        String extId=st.nextToken();
//        String closure=st.nextToken();

        if (cacheAgent instanceof InferenceAgent) {
            InferenceAgent ia= (InferenceAgent) cacheAgent;
            //ia.sendTimeEventCommand(agentName, typeId, extId, closure);
            // send to an agent group
        } else {
            // TODO: Puneet
        }
    }

    public long getScheduleTime() {
        return scheduledTime;
    }

    public long setScheduleTime(long newScheduledTime) {
        long oldScheduledTime = scheduledTime;
        scheduledTime = newScheduledTime;
        return oldScheduledTime;
    }
    
    public long getRepeatInterval() {
        return 0;
    }
    
    public void readExternal(DataInput dataInput) throws IOException {
        scheduledTime=dataInput.readLong();
        if (dataInput.readBoolean()) {
            extId= dataInput.readUTF();
        }
        if (dataInput.readBoolean()) {
            closure= dataInput.readUTF();
        }

        agentName= dataInput.readUTF();
        typeId= dataInput.readInt();


    }

    public void writeExternal(DataOutput dataOutput) throws IOException {
        dataOutput.writeLong(scheduledTime);
        if ((extId != null) && (extId.length() > 0)) {
            dataOutput.writeBoolean(true);
            dataOutput.writeUTF(extId);
        } else {
            dataOutput.writeBoolean(false);
        }

        if ((closure != null) && (closure.length() > 0)) {
            dataOutput.writeBoolean(true);
            dataOutput.writeUTF(closure);
        } else {
            dataOutput.writeBoolean(false);
        }

        dataOutput.writeUTF(agentName);
        dataOutput.writeInt(typeId);
    }
}
