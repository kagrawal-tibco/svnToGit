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
import java.util.StringTokenizer;

import com.tibco.cep.runtime.service.cluster.agent.InferenceAgent;
import com.tibco.cep.runtime.service.cluster.scheduler.WorkEntry;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Nov 19, 2008
 * Time: 2:50:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class SMTimeoutTask implements WorkEntry {
    long scheduledTime;

    public SMTimeoutTask(long scheduledTime) {
        this.scheduledTime=scheduledTime;
    }

    public SMTimeoutTask() {
    }

    public void execute(String key, Object cacheAgent) throws Exception {
        StringTokenizer st= new StringTokenizer(key, ".");
        long sm_id=Long.valueOf(st.nextToken());
        String property_name=st.nextToken();
        int count=Integer.valueOf(st.nextToken());

        if (cacheAgent instanceof InferenceAgent) {
            InferenceAgent ia= (InferenceAgent) cacheAgent;
            ia.assertCacheSMTimeout(sm_id, property_name, count, scheduledTime);
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
    }

    public void writeExternal(DataOutput dataOutput) throws IOException {
        dataOutput.writeLong(scheduledTime);
    }
}
