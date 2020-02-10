package com.tibco.rta.impl.util;

import com.tibco.rta.NotificationListenerKey;
import com.tibco.rta.RtaNotificationListener;
import com.tibco.rta.RtaNotifications;
import com.tibco.rta.impl.RtaNotificationImpl;

import java.util.concurrent.Future;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 28/2/13
 * Time: 12:28 PM
 * Listener which gets notified when async task completes.
 * <p>
 *     This listener expresses interest in fact publish async notifications.
 * </p>
 * @see com.tibco.rta.client.taskdefs.ConnectionAwareTaskManager
 * @see NotificationListenerKey
 */
public class DefaultQueueEventNotificationListener implements QueueEventNotificationListener {

    private RtaNotificationListener rtaNotificationListener;

    private NotificationListenerKey notificationListenerKey;

    public DefaultQueueEventNotificationListener(RtaNotificationListener rtaNotificationListener, NotificationListenerKey notificationListenerKey) {
        this.rtaNotificationListener = rtaNotificationListener;
        this.notificationListenerKey = notificationListenerKey;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void notifyQueueEvent(QueueEvent queueEvent) throws Exception {
        if (notificationListenerKey.isInterestedInFactPublish()) {
            //Get source
            Object source = queueEvent.getSource();
            if (source instanceof Future) {
                //This is no longer blocking call because it has completed here.
                String futureResult = ((Future<String>)source).get();
                RtaNotificationImpl notification = new RtaNotificationImpl();
                notification.setProperty(RtaNotifications.FUTURE_RESULT.name(), futureResult);
                rtaNotificationListener.onNotification(notification);
            }
        }
    }
}
