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

import java.util.concurrent.ScheduledFuture;

/*
* Author: Ashwin Jayaprakash Date: Mar 13, 2009 Time: 6:31:54 PM
*/
public interface ScheduledWorkManager extends WorkManager {
    ScheduledFuture<?> scheduleJobAtFixedDelay(Runnable job, long periodMillis);

    ScheduledFuture<?> scheduleJobAtFixedRate(Runnable job, long delayMillis);
}
