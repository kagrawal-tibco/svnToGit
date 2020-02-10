package com.tibco.rta.client.taskdefs;

import com.tibco.rta.ConnectionRefusedException;
import com.tibco.rta.client.ConnectionException;
import com.tibco.rta.client.SessionInitFailedException;
import com.tibco.rta.impl.DefaultRtaSession;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 4/2/13
 * Time: 3:43 PM
 * Implementation of idempotent retry pattern.
 */
public class IdempotentRetryTask extends AbstractClientTask {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_CLIENT.getCategory());

    /**
     * The task to retry
     */
    protected AbstractClientTask wrappedTask;

    protected DefaultRtaSession session;

    protected int retryCount;

    protected long waitTime;

    public IdempotentRetryTask(DefaultRtaSession session,
                               int retryCount,
                               long waitTime, AbstractClientTask wrappedTask) {
        super(session.getTransmissionStrategy());
        this.session = session;
        this.retryCount = retryCount;
        this.waitTime = waitTime;
        this.wrappedTask = wrappedTask;
    }

    public IdempotentRetryTask(int retryCount,
                               long waitTime,
                               AbstractClientTask wrappedTask) {
        super(null);
        this.retryCount = retryCount;
        this.waitTime = waitTime;
        this.wrappedTask = wrappedTask;
    }

    @Override
    public Object perform() throws Throwable {
        if (LOGGER.isEnabledFor(Level.TRACE)) {
            LOGGER.log(Level.TRACE, "Retry count for operation [%s] set to [%d]", getTaskName(), retryCount);
        }
        for (int loop = 0; loop <= retryCount; loop++) {
            try {
                if (session != null) {
                    ConnectionAwareTaskManager taskManager = session.getTaskManager();
                    //For synchronous tasks if connection is down sleep
                    if (wrappedTask.isSync() && taskManager.isConnectionDown()) {
                        //Sleep
                        sleep();
                    }
                }
                return wrappedTask.perform();
            } catch (Exception e) {
                if (e instanceof SessionInitFailedException) {
                    //Rethrow
                    throw e;
                }
                if (LOGGER.isEnabledFor(Level.ERROR)) {
                    LOGGER.log(Level.ERROR, "", e);
                }

                //Perform salvage
                wrappedTask.salvage();

                if (e instanceof ConnectionException) {
                    if (LOGGER.isEnabledFor(Level.DEBUG)) {
                        LOGGER.log(Level.DEBUG, "Connection failed. Aborted retry of task [%s]", getTaskName());
                    }
                    //Stop retry. No point retrying
                    if (wrappedTask.requeue) {
                        if (session != null) {
                            session.getTaskManager().requeue(this);
                        }
                    }
                    break;
                }
                if (loop >= retryCount) {
                    //Retries exceeded
                    if (e instanceof ConnectionRefusedException) {
                        //Throw as is
                        throw e;
                    }
                    throw new Exception(String.format("Retries exceeded for task [%s]", wrappedTask.getTaskName()), e);
                }
                if (LOGGER.isEnabledFor(Level.INFO)) {
                    LOGGER.log(Level.INFO, "Attempting retry for operation [%s] : Attempt [%d] after waiting for [%d] milliseconds", getTaskName(), loop+1, waitTime);
                }
                sleep();
            }
        }
        return null;
    }

    private void sleep() {
        //Make current thread sleep
        try {
            Thread.sleep(waitTime);
        } catch (InterruptedException ie) {
            if (LOGGER.isEnabledFor(Level.INFO)) {
                LOGGER.log(Level.INFO, "Thread [%s] performing operation [%s] was interrupted.", Thread.currentThread(), getTaskName());
            }
        }
    }

    public DefaultRtaSession getSession() {
        return session;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public long getWaitTime() {
        return waitTime;
    }

    public AbstractClientTask getWrappedTask() {
        return wrappedTask;
    }

    @Override
    public String getTaskName() {
        return wrappedTask.getTaskName();
    }
}
