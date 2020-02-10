package com.tibco.cep.runtime.util.scheduler;

import java.util.List;

/**
 *
 * Author: Karthikeyan Subramanian / Date: Jan 27, 2010 / Time: 12:20:25 PM
 */
public interface JobContext {

    Id getJobId();

    List<Long> getExpiredTimestamps();

    void setProcessedCount(long procCount);

    long getProcessedCount();
}
