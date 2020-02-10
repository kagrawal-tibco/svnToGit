/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */
package com.tibco.cep.runtime.service.cluster.scheduler;

import com.tibco.cep.runtime.model.serializers.SerializableLite;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Sep 13, 2008
 * Time: 5:51:44 PM
 */
public interface WorkEntry extends SerializableLite {
    public final static int STATUS_NEW     = 0;
    public final static int STATUS_PENDING = 1;
    public final static int STATUS_DONE    = 2;

    long setScheduleTime(long time);
    long getScheduleTime();
    long getRepeatInterval();

    void execute(String key, Object agent) throws Exception;
}
