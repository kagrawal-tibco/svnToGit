/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.scheduler;

import java.util.Set;

import com.tibco.cep.runtime.service.cluster.Cluster;

public interface SchedulerCache {
    byte MODE_INFERENCEAGENT = 0x01;
    byte MODE_QUERYAGENT = 0x02;
    byte MODE_CACHEAGENT = 0x04;
    byte MODE_ALL = MODE_INFERENCEAGENT | MODE_QUERYAGENT | MODE_CACHEAGENT;

    public void init(Cluster cluster) throws Exception;

    void createScheduler(String id, long pollInterval,
                            long refreshAhead,
                            boolean isCacheLimited, byte mode);

    void schedule(String schedulerId, String workKey, WorkEntry entry) throws Exception;

	/** BE-22006 
    Object[] listEntries(String schedulerId, long refreshAhead, int limit) throws Exception;
	 */
    WorkEntry getEntry(String schedulerId, String workKey) throws Exception;
	
    
    WorkEntry remove(String schedulerId, String workKey) throws Exception;

    void remove(String schedulerId, Set<String> workKeys) throws Exception;

    void removeScheduler(String id, long pollInterval, long refreshAhead);

    void start();

    boolean isNodeConfiguredAsScheduler();

    boolean schedulerExists(String schedulerName);
}
