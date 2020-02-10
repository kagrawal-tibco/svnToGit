/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 19/8/2010
 */

package com.tibco.cep.runtime.service.cluster.util;

import java.util.List;
import java.util.concurrent.BlockingQueue;

/*
* Author: Ashwin Jayaprakash Date: Mar 13, 2009 Time: 1:55:07 PM
*/

public interface WorkManager {
    void start();

    /**
     * @param job
     */
    void submitJob(Object job) throws InterruptedException;

    /**
     * @param jobs
     */
    void submitJobs(List<Runnable> jobs);

    /**
     * @param job
     */
    void submitJob(Runnable job) throws InterruptedException;

    /**
     * @param job
     * @throws InterruptedException
     */
    void submitJob(ParallelJob job) throws InterruptedException;

    boolean isActive();

    void shutdown();

    BlockingQueue getJobQueue();
}
