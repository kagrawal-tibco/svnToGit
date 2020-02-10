package com.tibco.cep.runtime.util.scheduler.impl;

import java.util.List;

import com.tibco.cep.runtime.util.scheduler.Id;
import com.tibco.cep.runtime.util.scheduler.JobContext;

/**
 *
 * Author: Karthikeyan Subramanian / Date: Jan 27, 2010 / Time: 12:20:25 PM
 */
public class JobContextImpl implements JobContext {
    private final Id id;
    private final List<Long> timestamps;
    private long processedCount;

    public JobContextImpl(Id id, List<Long> timeStamps) {
        this.id = id;
        this.timestamps = timeStamps;
    }

    public Id getJobId() {
        return this.id;
    }

    public List<Long> getExpiredTimestamps() {
        return this.timestamps;
    }

    @Override
    public void setProcessedCount(long procCount) {
        this.processedCount = procCount;
    }

    @Override
    public long getProcessedCount() {
        return this.processedCount;
    }
}
