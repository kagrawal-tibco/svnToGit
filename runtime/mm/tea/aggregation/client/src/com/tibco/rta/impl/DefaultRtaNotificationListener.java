package com.tibco.rta.impl;

import com.tibco.rta.Fact;
import com.tibco.rta.RtaNotification;
import com.tibco.rta.RtaNotificationListener;
import com.tibco.rta.RtaNotifications;
import com.tibco.rta.client.taskdefs.IdempotentRetryTask;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;

/**
 * Created by aathalye on 11/11/14.
 *
 * <p>
 *     Default implementation which API clients can use and customize if needed.
 * </p>
 */
public class DefaultRtaNotificationListener implements RtaNotificationListener {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_CLIENT_PROBE.getCategory());

    @Override
    public void onNotification(RtaNotification notification) {
        Integer connectionEvent = (Integer) notification.getValue(RtaNotifications.CONNECTION_EVENT.name());
        if (connectionEvent != null) {
            if (LOGGER.isEnabledFor(Level.INFO)) {
                LOGGER.log(Level.INFO, "Connection Event [%s]", connectionEvent);
            }
        }
        Fact rejectedFact = (Fact) notification.getValue(RtaNotifications.FACT_REJECT.name());
        if (rejectedFact != null) {
            if (LOGGER.isEnabledFor(Level.INFO)) {
                LOGGER.log(Level.INFO, "Fact Rejected [%s]", rejectedFact.getKey());
            }
        }
        IdempotentRetryTask retryTask = (IdempotentRetryTask) notification.getValue(RtaNotifications.TASK_REJECT.name());
        if (retryTask != null) {
            if (LOGGER.isEnabledFor(Level.INFO)) {
                LOGGER.log(Level.INFO, "Task Rejected [%s]", retryTask.getWrappedTask());
            }
        }
    }
}
