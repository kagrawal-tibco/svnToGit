package com.tibco.rta.common.taskdefs;

import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 13/6/13
 * Time: 3:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class IdempotentRetryTask implements Task {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_COMMON.getCategory());

    /**
     * The task to retry
     */
    protected Task wrappedTask;

    protected int retryCount;

    protected long waitTime;

    public IdempotentRetryTask(int retryCount,
                               long waitTime,
                               Task wrappedTask) {
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
                return wrappedTask.perform();
            } catch (Exception e) {
                if (LOGGER.isEnabledFor(Level.ERROR)) {
                    LOGGER.log(Level.ERROR, e.getMessage());
                }

                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "", e);
                }

                if (loop >= retryCount) {
                    //Retries exceeded
                    throw new Exception(String.format("Retries exceeded for task [%s]", wrappedTask.getTaskName()), e);
                }
                if (LOGGER.isEnabledFor(Level.INFO)) {
                    LOGGER.log(Level.INFO, "Attempting retry for operation [%s] : Attempt [%d] after waiting for [%d] milliseconds", getTaskName(), loop+1, waitTime);
                }
                Thread.sleep(waitTime);
            }
        }
        return null;
    }

    @Override
    public String getTaskName() {
        return wrappedTask.getTaskName();
    }
}
