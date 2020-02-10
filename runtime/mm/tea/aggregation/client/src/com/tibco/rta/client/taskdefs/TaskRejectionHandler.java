package com.tibco.rta.client.taskdefs;

import com.tibco.rta.NotificationListenerKey;
import com.tibco.rta.RtaNotificationListener;
import com.tibco.rta.RtaNotifications;
import com.tibco.rta.impl.RtaNotificationImpl;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 4/3/13
 * Time: 7:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class TaskRejectionHandler implements RejectedExecutionHandler {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_CLIENT.getCategory());

    private RtaNotificationListener notificationListener;

    private NotificationListenerKey notificationListenerKey;

    /**
     *
     * @param notificationListener
     * @param notificationListenerKey
     */
    public TaskRejectionHandler(RtaNotificationListener notificationListener, NotificationListenerKey notificationListenerKey) {
        this.notificationListener = notificationListener;
        this.notificationListenerKey = notificationListenerKey;
    }

    /**
     *
     * @param runnable
     * @param executor
     */
    @Override
    public void rejectedExecution(Runnable runnable, ThreadPoolExecutor executor) {
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "Rejecting task [%s]", runnable);
        }
        if (notificationListener != null && notificationListenerKey.isInterestedInTaskRejections()) {
            notificationListener.onNotification(null);
        }
    }

    /**
     *
     * @param retryTask
     * @param executor
     * @param <I>
     */
    public <I extends IdempotentRetryTask> void rejectedExecution(I retryTask, ThreadPoolExecutor executor) {
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "Rejecting task [%s]", retryTask.getTaskName());
        }
        if (notificationListener != null && notificationListenerKey.isInterestedInTaskRejections()) {
            RtaNotificationImpl notification = new RtaNotificationImpl();
            notification.setProperty(RtaNotifications.TASK_REJECT.name(), retryTask);
            notificationListener.onNotification(notification);
        }
    }
}
