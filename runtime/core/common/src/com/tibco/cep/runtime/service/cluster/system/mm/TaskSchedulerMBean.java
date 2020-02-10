/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.system.mm;

public interface TaskSchedulerMBean {

    public String getName();

    public String getType();

    public int getPollingInterval();

    public int getRefreshAhead();

    public int getPendingCount();

    public void flush() throws Exception;
}
