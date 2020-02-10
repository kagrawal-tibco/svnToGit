package com.tibco.rta.impl;

import com.tibco.rta.Fact;
import com.tibco.rta.NotificationListenerKey;
import com.tibco.rta.RtaNotificationListener;
import com.tibco.rta.RtaNotifications;
import com.tibco.rta.queues.BatchJob;
import com.tibco.rta.queues.event.QueueEavesDropEvent;
import com.tibco.rta.queues.event.QueueEavesDropper;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 2/3/13
 * Time: 9:11 AM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultWorkQueueEavesDropper implements QueueEavesDropper {

    /**
     * The client supplied listener to notify
     */
    private RtaNotificationListener rtaNotificationListener;

    private NotificationListenerKey notificationListenerKey;

    public DefaultWorkQueueEavesDropper(RtaNotificationListener rtaNotificationListener, NotificationListenerKey notificationListenerKey) {
        this.rtaNotificationListener = rtaNotificationListener;
        this.notificationListenerKey = notificationListenerKey;
    }

    @Override
    public void notifyEavesDropper(QueueEavesDropEvent eavesDropEvent) {
        if (notificationListenerKey.isInterestedInFactDrops()) {
            BatchJob batchJob = (BatchJob) eavesDropEvent.getSource();
            Fact wrappedFact = (Fact) batchJob.getWrappedObject();
            RtaNotificationImpl notification = new RtaNotificationImpl();
            notification.setProperty(RtaNotifications.FACT_REJECT.name(), wrappedFact);
            rtaNotificationListener.onNotification(notification);
        }
    }
}
