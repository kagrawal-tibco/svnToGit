package com.tibco.cep.runtime.util.scheduler.internal;

import java.util.List;

import com.tibco.cep.runtime.util.scheduler.Id;

/**
 *
 * Author: Karthikeyan Subramanian / Date: Jan 27, 2010 / Time: 12:20:25 PM
 */
public interface Request {

    public static enum Type {
        SCHEDULED, TRIGGERED, BOTH;
    }

    Id getJobId();

    void addRequest(Request request);

    long getOldestTimestamp();

    List<Long> getRequestTimes();

    void penalize();

    Type getType();

}
