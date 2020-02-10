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

package com.tibco.cep.runtime.service.cluster.txn;


public class RtcTransactionProperties {

    enum IsolationLevel {
        TRANSACTION_GET_COMMITTED(1),
        CONCUR_OPTIMISTIC(2);

        private int value;

        IsolationLevel(int value) {
            this.value = value;
        }

        int intValue() {
            return value;
        }
    }

    public boolean updateCache = true;
    public boolean autoCacheCommit = true;
    public int cacheTransactionConcurrency = IsolationLevel.CONCUR_OPTIMISTIC.intValue();
    public int cacheTransactionIsolation = IsolationLevel.TRANSACTION_GET_COMMITTED.intValue();

}
