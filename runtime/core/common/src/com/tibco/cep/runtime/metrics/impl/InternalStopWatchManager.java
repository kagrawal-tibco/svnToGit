package com.tibco.cep.runtime.metrics.impl;

import java.util.concurrent.ScheduledExecutorService;

import com.tibco.cep.runtime.metrics.StopWatchManager;

/*
* Author: Ashwin Jayaprakash Date: Jan 26, 2009 Time: 8:04:31 PM
*/

/**
 * Internal interface.
 */
public interface InternalStopWatchManager extends StopWatchManager {
    ScheduledExecutorService getScheduledExecutorService();
}
