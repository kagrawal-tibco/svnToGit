/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.scheduler;

import com.tibco.cep.runtime.service.om.api.ControlDao;

public interface Poller {

    void start () throws Exception;
    void shutdown();

    void flush();

    String getType();

    int getPendingCount();

    ControlDao getWorkListDao();
}
