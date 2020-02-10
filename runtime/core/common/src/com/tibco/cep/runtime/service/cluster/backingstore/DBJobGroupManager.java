/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 11/8/2010
 */

package com.tibco.cep.runtime.service.cluster.backingstore;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

import com.tibco.cep.kernel.model.knowledgebase.DuplicateException;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.agent.InferenceAgent;
import com.tibco.cep.runtime.service.cluster.txn.RtcTransaction;
import com.tibco.cep.runtime.service.cluster.txn.RtcTransactionManager;
import com.tibco.cep.runtime.session.BEManagedThread.ShutdownJob;
import com.tibco.cep.runtime.session.JobGroupManager;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.runtime.util.DBConnectionsBusyException;
import com.tibco.cep.runtime.util.DBNotAvailableException;
import com.tibco.cep.runtime.util.SystemProperty;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Feb 25, 2009
 * Time: 5:46:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class DBJobGroupManager implements JobGroupManager {
    
    protected final static Logger logger = LogManagerFactory.getLogManager().getLogger(DBJobGroupManager.class);

    public static int DB_NOTAVAILABLE = -100;
    public static int DB_OTHER = -200;
    
    private int maxJobsInGroup;
    private int databaseRetryCount = Integer.MAX_VALUE;
    private long databaseRetrySleep = 5000;

    private Cluster cluster;
    private InferenceAgent inferenceAgent;
    private BackingStore cacheAsideStore = null;

    public DBJobGroupManager(int maxJobsInGroup, Cluster cluster, InferenceAgent inferenceAgent) {
        this.maxJobsInGroup = maxJobsInGroup;
        this.cluster = cluster;

        this.cacheAsideStore = cluster.getCacheAsideStore();
        this.inferenceAgent = inferenceAgent;

        try {
        	Properties props = RuleServiceProviderManager.getInstance().getDefaultProvider().getProperties();
        	databaseRetryCount = Integer.parseInt(props.getProperty(SystemProperty.RTC_TXN_ERROR_DATABASE_RETRY_COUNT.getPropertyName(), String.valueOf(Integer.MAX_VALUE)));
        	databaseRetrySleep = Integer.parseInt(props.getProperty(SystemProperty.RTC_TXN_ERROR_DATABASE_RETRY_SLEEP.getPropertyName(), "5000"));
        } catch (Exception e) {
        	logger.log(Level.WARN, "Invalid transaction retry parameter specified (count=%s sleep=%s)",
        			databaseRetryCount, databaseRetrySleep);
        }
    }

    public void switched(boolean toSecondary) {
        //TODO: Implement if needed.
    }

    public void execute(Collection jobs) throws InterruptedException {
        ArrayList<RtcTransaction> txns = new ArrayList<RtcTransaction>(jobs.size());
        int retryCount = 0;
        Throwable error = null;
        long pickedFromQStartTime = System.nanoTime();
        for ( ; retryCount < databaseRetryCount; retryCount++) {
            if (retryCount > 0) {
                logger.log(Level.WARN, "Attempting to write job to DB. Retry Count = " + retryCount);
            }
            try {
                Iterator allJobs = jobs.iterator();
                long totalQWaitStartTime = 0;
                while (allJobs.hasNext()) {
                    Object job = allJobs.next();

                    /*
                    Fix for 1-A2W2HO. Workaround for a bad design where a simple Thread shutdown needs a
                    special ShutdownJob to be executed which throws ClassCastExceptions.
                    */
                    if (job instanceof ShutdownJob) {
                        ((ShutdownJob) job).run();
                        continue;
                    }

                    if (job instanceof RtcTransactionManager.TxnTask) {
                        RtcTransactionManager.TxnTask task = (RtcTransactionManager.TxnTask) job;
                        txns.add(task.getTransaction());
                        totalQWaitStartTime += task.dbQueueStartTime;
                    }
                }
                long startTime = System.currentTimeMillis();
                try {
                    if (cluster.getClusterConfig().isDBBatching()) {
                        RtcTransaction combinedTxn = RtcTransaction.combineTransactions(txns, cluster);
                        cacheAsideStore.saveTransaction(combinedTxn);
                    } else {
                        cacheAsideStore.saveTransaction(txns);
                    }
                } catch (DuplicateException dpe) {
                    // No ops. Proceed to notify
                    error = dpe;
                	int errCode = DBJobGroupManager.DB_OTHER;
                    if (dpe.getCause() instanceof SQLException) {
                        errCode = ((SQLException)dpe.getCause()).getErrorCode();
                    }
                    handleDBException(txns, errCode, dpe.getMessage(), retryCount);
                }
                long totalExecuteMillis = System.currentTimeMillis() - startTime;
                int actualTxnTasks = 0;
                for (Object job : jobs) {
                    /*
                    Fix for 1-A2W2HO. Workaround for a bad design where a simple Thread shutdown needs a
                    special ShutdownJob to be executed which throws ClassCastExceptions.
                    */
                    if (job instanceof ShutdownJob) {
                        ((ShutdownJob) job).run();
                        continue;
                    }

                    if (job instanceof RtcTransactionManager.TxnTask) {
                        RtcTransactionManager.TxnTask task = (RtcTransactionManager.TxnTask) job;
                        task.notifyDone(false);
                        actualTxnTasks++;
                    }
                }

                long totalQWaitMillis = ((pickedFromQStartTime * actualTxnTasks) - (totalQWaitStartTime)) / (1000 * 1000);
                RtcTransactionManager.updateDBOpsStats(actualTxnTasks, totalExecuteMillis, totalQWaitMillis);
                if (retryCount > 0) {
                    logger.log(Level.INFO, "Attempting to write job to DB successful. Retry Count = " + retryCount);
                }
                return;
            }
            catch (DBNotAvailableException connEx) {
                // Database is disconnected. Wait until reconnected.
                error = connEx;
                logger.log(Level.WARN, "DBNotAvailableException: " + connEx.getMessage() + ", Waiting to reconnect.");
                handleDBException(txns, DBJobGroupManager.DB_NOTAVAILABLE, connEx.getMessage(), retryCount);
                try {
                    if (!cacheAsideStore.waitForReconnect(60000, -1)) {
                        cluster.getRuleServiceProvider().shutdown();
                    }
                }
                catch (Exception e) {
                    logger.log(Level.DEBUG, "Wait-for-reconnect failure: " + e.getMessage());
                }
            }
            catch (DBConnectionsBusyException busyEx) {
                // All connections are busy. Wait for sometime.
                error = busyEx;
                logger.log(Level.WARN, "DBConnectionsBusyException: " + busyEx.getMessage());
                handleDBException(txns, busyEx.getErrorCode(), busyEx.getMessage(), retryCount);
                Thread.sleep(databaseRetrySleep);
            }
            catch (RuntimeException runtEx) {
                if (runtEx instanceof java.lang.ClassCastException) {
                    // Throwing exception will exit while loop so this must be done here as well to ensure locks are released
                    for(Object job : jobs) {
                        if (job instanceof RtcTransactionManager.TxnTask) {
                            (((RtcTransactionManager.TxnTask)job)).notifyDone(true);
                        }
                    }
                    throw runtEx;
                }
                error = runtEx;
                logger.log(Level.WARN, "Failed writing to DB:" + runtEx.getMessage());
                handleDBException(txns, DBJobGroupManager.DB_OTHER, runtEx.getMessage(), retryCount);   
                Thread.sleep(databaseRetrySleep);
            }
	        catch (Throwable otherEx) {
                error = otherEx;
		        logger.log(Level.ERROR, "Failed executing DB transactions: " + otherEx.getMessage());
	            handleDBException(txns, DBJobGroupManager.DB_OTHER, otherEx.getMessage(), retryCount);   
                Thread.sleep(databaseRetrySleep);
	        }
        }

        if (retryCount > 0) {
            logger.log(Level.INFO, "Attempting to write job to DB result '%s'. Retry Count = %s", error.getMessage(), retryCount);
        }

        if (retryCount == databaseRetryCount) {
            throw new RuntimeException(
                    "DB transaction failed even after " + retryCount + " attempts. Giving up.", error);
        }
    }

    public int getMaxJobsInaGroup() {
        return maxJobsInGroup;
    }

    public int handleDBException(ArrayList<RtcTransaction> txns, int errCode, String exMsg, long retryCnt) {
        return RtcTransactionManager.handleException(txns, null, RtcTransactionManager.TXN_DB_ERROR, errCode, exMsg, retryCnt, inferenceAgent, inferenceAgent.getAgentConfig().isEnableParallelOps());
    }
    
    public static int handleDBException(ArrayList<RtcTransaction> txns, int errCode, String exMsg, long retryCnt, InferenceAgent inferenceAgent) {
		RtcTransactionManager.handleException(txns, null, RtcTransactionManager.TXN_DB_ERROR, errCode, exMsg, retryCnt, inferenceAgent, inferenceAgent.getAgentConfig().isEnableParallelOps());
    	return 0;
    }
}
