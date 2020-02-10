package com.tibco.rta.client.taskdefs;

import com.tibco.rta.impl.DefaultRtaSession;

import java.util.concurrent.Callable;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 4/2/13
 * Time: 3:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class IdempotentRunnableRetryTask extends IdempotentRetryTask implements Callable<Object> {

    public IdempotentRunnableRetryTask(DefaultRtaSession session,
                                       int retryCount,
                                       long waitTime,
                                       AbstractClientTask wrappedTask) {
        super(session, retryCount, waitTime, wrappedTask);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object call() {
        try {
            return perform();
        } catch (Throwable e) {
            //Retries exceeded
            throw new RuntimeException(String.format("Exception in task %s", wrappedTask.getTaskName()), e);
        }
    }
}
