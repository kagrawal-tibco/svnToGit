package com.tibco.rta.client.tcp;

import com.tibco.rta.NotificationListenerKey;
import com.tibco.rta.RtaNotifications;
import com.tibco.rta.impl.DefaultRtaSession;
import com.tibco.rta.impl.RtaNotificationImpl;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 28/2/13
 * Time: 8:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultTCPConnectionNotificationListener implements TCPConnectionNotificationListener {

    private DefaultRtaSession session;

    private NotificationListenerKey notificationListenerKey;

    public DefaultTCPConnectionNotificationListener(DefaultRtaSession session) {
        this.session = session;
    }

    @Override
    public void onEvent(TCPConnectionEvent connectionEvent) {
    	if (notificationListenerKey == null) {
    		return;
    	}
        if (notificationListenerKey.isInterestedInServerHealth()) {
            //Get source
            int connectionEventType = (Integer) connectionEvent.getSource();
            RtaNotificationImpl notification = new RtaNotificationImpl();
            notification.setProperty(RtaNotifications.CONNECTION_EVENT.name(), connectionEventType);
            //Inform session about remote server status.
            session.informServerStatus(connectionEventType);
            session.getNotificationListener().onNotification(notification);
        }
    }

    public void setNotificationListenerKey(NotificationListenerKey notificationListenerKey) {
        this.notificationListenerKey = notificationListenerKey;
    }
}
