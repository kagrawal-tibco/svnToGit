/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.session.impl.locks;

import java.util.Collection;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.om.api.ControlDao;
import com.tibco.cep.runtime.service.om.api.ControlDaoType;
import com.tibco.cep.runtime.service.om.api.DaoProvider;
import com.tibco.cep.runtime.session.locks.LockKeeper;
import com.tibco.cep.runtime.session.locks.LockManager;
import com.tibco.cep.util.annotation.DependsOn;

/*
* Author: Ashwin Jayaprakash Date: Jul 22, 2008 Time: 7:04:26 PM
*/

public class DefaultLockKeeper<K, V> implements LockKeeper<K, V> {
    protected ControlDao lockCache;
    protected static Logger logger = LogManagerFactory.getLogManager().getLogger(DefaultLockKeeper.class);

    @DependsOn({DaoProvider.class})
    public DefaultLockKeeper(Cluster cluster) {
        DaoProvider daoProvider = cluster.getDaoProvider();

        this.lockCache = daoProvider.createControlDao(Object.class, Collection.class, ControlDaoType.ClusterLocks);
    }

    @Override
    public void init() {
    }

    @Override
    public void batchStart() {
    }

    @Override
    public boolean lock(K key, long timeoutNanos) {
        long givenTimeToSpendMillis = timeoutNanos / (1000 * 1000);
        if (givenTimeToSpendMillis <= 0) {
            givenTimeToSpendMillis = -1;
        }
        
        long retryInterval = givenTimeToSpendMillis == -1 ? 5000 : givenTimeToSpendMillis/10;
        if(retryInterval > 5000) {
            retryInterval = 5000;
        }
        long startTimeMillis = System.currentTimeMillis();
        int retryAttempts = 0;
        boolean returnValue = false;
        while (true) {
            try {
            	returnValue = lockCache.lock(key, givenTimeToSpendMillis);
            	break;
            } catch (Exception ex) {
            	long elapsedTimeMillis = System.currentTimeMillis() - startTimeMillis;
                if(givenTimeToSpendMillis != -1 && elapsedTimeMillis >= givenTimeToSpendMillis) {
                	returnValue = false;
                	break;
                }
                try {
                    // Wait a bit before retrying
                    Thread.sleep(retryInterval);
                }
                catch (InterruptedException e) {
                }
                retryAttempts++;
                if (logger.isEnabledFor(Level.DEBUG) && retryAttempts % 10 == 0) {
                    logger.log(Level.DEBUG, ex, "lock threw exception for key=" + key + ". have already made " +
                                                retryAttempts + " retry attempts. will retry after " + retryInterval +
                                                " milliseconds.");
                }
            }
        }
        if(logger.isEnabledFor(Level.DEBUG) && retryAttempts > 0) {
            logger.log(Level.DEBUG, "took "+retryAttempts+" retries to lock for key "+key+". return value is "+returnValue+".");
        }
        return returnValue;
    }

    @Override
    public void put(LockManager.LockLevel lockLevel, LockManager.LockData<K, V> lockData,
                    Collection<Long> affectedIds) {
    }

    @Override
    public V remove(LockManager.LockLevel lockLevel, K key) {
        return null;
    }

    /**
     * @param key
     * @throws DefaultLockKeeper.FailedOperationException
     *          if unlock fails.
     */
    @Override
    public void unlock(K key) {
        int retryAttempts = 0;
        boolean b = false;
        while (true) {
            try {
                b = lockCache.unlock(key);
                break;
            } catch (Exception e) {
                try {
                    // Wait for a few seconds before retrying
                    Thread.sleep(5000);
                } catch (InterruptedException e1) {
                }
                retryAttempts++;
                if (logger.isEnabledFor(Level.DEBUG) && retryAttempts % 10 == 0) {
                    logger.log(Level.DEBUG, e, "unlock threw exception for key=" + key + ". have already made " +
                                                retryAttempts + " retry attempts.");
                }
            }
        }
        if(logger.isEnabledFor(Level.DEBUG) && retryAttempts > 0) {
            logger.log(Level.DEBUG, "took "+retryAttempts+" retries to unlock for key "+key+". return value is "+b+".");
        }

        if (b) {
            return;
        }

        throw new FailedOperationException("Unlock operation on key [" + key + "] did not succeed.");
    }

    @Override
    public void batchEnd() {
    }

    @Override
    public void discard() {
    }

    //-----------

    public static class FailedOperationException extends RuntimeException {
        public FailedOperationException(String message) {
            super(message);
        }
    }
}