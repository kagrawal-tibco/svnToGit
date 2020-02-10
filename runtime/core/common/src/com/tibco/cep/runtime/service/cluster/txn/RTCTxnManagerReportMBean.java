/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.txn;

/*
* Author: Ashwin Jayaprakash Date: Mar 10, 2009 Time: 3:29:37 PM
*/
public interface RTCTxnManagerReportMBean {
    long getTotalSuccessfulTxns();

    float getAvgSuccessfulTxnTimeMillis();

    long getTotalErrors();

    void printRecentErrors();

    long getPendingCacheWrites();

    long getPendingDBWrites();

    long getTotalDBTxnsCompleted();

    float getAvgDBTxnMillis();
    
    float getAvgDBOpsBatchSize();

    float getAvgDBQueueWaitTimeMillis();

    int getLastDBBatchSize();

    float getAvgCacheTxnMillis();

    float getAvgCacheQueueWaitTimeMillis();

    float getAvgActionTxnMillis();

    int getPendingActions();

    int getPendingLocksToRelease();

    int getPendingEventsToAck();

    void resetStats();
}
