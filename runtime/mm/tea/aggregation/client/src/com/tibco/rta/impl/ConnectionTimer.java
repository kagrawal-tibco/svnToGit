package com.tibco.rta.impl;

import com.tibco.rta.ConfigProperty;
import com.tibco.rta.client.taskdefs.ConnectionTask;
import com.tibco.rta.client.tcp.TCPConnectionEvent;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 5/3/13
 * Time: 3:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class ConnectionTimer<C extends ConnectionTask<?, ?>> {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_CLIENT.getCategory());

    /**
     * Connection up constant
     */
    public static final int CONNECTION_UP = 1 << 1;

    /**
     * Connection down constant
     */
    public static final int CONNECTION_DOWN = 1 << 2;

    private DefaultRtaSession session;

    private Timer retryTimer;

    /**
     * A member tpo indicate whether server connection is up or down.
     */
    private volatile int connectionState;

    /**
     * The async connection task
     */
    private C taskToRetry;

    /**
     * Generic synchronizer
     */
    private final ReentrantLock mainLock = new ReentrantLock();

    /**
     * Timer needs to wait on this
     */
    private final Condition taskCondition = mainLock.newCondition();

    ConnectionTimer(DefaultRtaSession session) {
        this.session = session;
    }

    void setConnectionState(int connectionState) {
        this.connectionState = connectionState;
        if ((connectionState & CONNECTION_DOWN) == 0) {
            if (LOGGER.isEnabledFor(Level.INFO)) {
                LOGGER.log(Level.INFO, "Connection established");
            }
            //Stop it
            if (retryTimer != null) {
                retryTimer.cancel();
                retryTimer.purge();
                retryTimer = null;
            }
        } else if ((connectionState & CONNECTION_UP) == 0) {
            beginRetry();
        }
    }

    void setRetryTask(C connectionTask) {
        //If already set not required to proceed
        //If we renter without this check it can cause deadlock
        //between timer thread and async connection thread.
        if (taskToRetry != null) {
            return;
        }
        ReentrantLock lock = mainLock;
        lock.lock();

        try {
            taskToRetry = connectionTask;
            //Signal waiter that task is set
            taskCondition.signal();
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    void beginRetry() {
        long waitTime = (Long) ConfigProperty.RETRY_WAIT_INTERVAL.getValue(session.getConfiguration());
        if (retryTimer == null) {
            retryTimer = new Timer("Retry-Timer");
        }
        retryTimer.schedule(new ConnectionRetryTask(), 0L, waitTime);
    }

    private class ConnectionRetryTask extends TimerTask {

        @Override
        public void run() {
            if ((connectionState & CONNECTION_UP) == 0) {
                if (LOGGER.isEnabledFor(Level.INFO)) {
                    LOGGER.log(Level.INFO, "Retry timer attempting connection");
                }

                ReentrantLock lock = mainLock;
                lock.lock();

                try {
                    while (taskToRetry == null) {
                        //Wait for task to be set
                        taskCondition.await();
                    }
                    //TODO
                    session.establishConnectionPipeline(taskToRetry);
                    //Get object exchanged
                    Object exchanged = session.getExchanged();
                    if (exchanged instanceof TCPConnectionEvent) {
                        setConnectionState(ConnectionTimer.CONNECTION_UP);
                    }
                } catch (InterruptedException ie) {
                    LOGGER.log(Level.ERROR, "", ie);
                } finally {
                    if (lock.isHeldByCurrentThread()) {
                        lock.unlock();
                    }
                }
            }
        }
    }
}