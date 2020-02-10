package com.tibco.rta.impl;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.tibco.rta.ConfigProperty;
import com.tibco.rta.RtaNotificationListener;
import com.tibco.rta.RtaNotifications;
import com.tibco.rta.client.taskdefs.impl.HeartbeatTask;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.property.PropertyAtom;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 29/3/13
 * Time: 4:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class HeartbeatSender {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_CLIENT.getCategory());

    /**
     * Constant indicating server is up.
     */
    private static final int SERVER_UP_STATE = 1 << 1;

    /**
     * Constant indicating server down.
     */
    private static final int SERVER_DOWN_STATE = 1 << 2;

    /**
     * Current state of server. See above.
     */
    private int serverState = SERVER_DOWN_STATE;

    /**
     * The timer thread.
     */
    private Timer heartbeatTimer;

    /**
     * The session to send heartbeat.
     */
    private DefaultRtaSession session;

    /**
     * Number of successive failures for receiving heartbeats.
     */
    private int failedAttemptCount;

    HeartbeatSender(DefaultRtaSession session) {
        this.heartbeatTimer = new Timer("Heartbeat-Sender-Thread");

        this.session = session;
    }

    void start() {
        //Get configuration
        Map<ConfigProperty, PropertyAtom<?>> sessionConfiguration = session.getConfiguration();
        //Get heartbeat interval
        long hbInterval = (Long) ConfigProperty.HEARTBEAT_INTERVAL.getValue(sessionConfiguration);

        HeartbeatTask heartbeatTask = new HeartbeatTask(session.getTransmissionStrategy());

        heartbeatTask.setSession(session);
        //Start timer
        HeartbeatTimerTask heartbeatTimerTask = new HeartbeatTimerTask(heartbeatTask);

        heartbeatTimer.schedule(heartbeatTimerTask, 0L, hbInterval);
    }

    void stop() {
        heartbeatTimer.purge();
        heartbeatTimer.cancel();
    }

    private class HeartbeatTimerTask extends TimerTask {

        private HeartbeatTask heartbeatTask;

        HeartbeatTimerTask(HeartbeatTask heartbeatTask) {
            this.heartbeatTask = heartbeatTask;
        }

        @Override
        public void run() {
            try {
                heartbeatTask.perform();
                if ((serverState & SERVER_DOWN_STATE) != 0) {
                    serverState = SERVER_UP_STATE;
                    //Only then notify that server is up
                    notifyServerState(serverState);
                    //reset count
                    failedAttemptCount = 0;
                }
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, "Error sending heartbeat", e);
                failedAttemptCount++;
                //Inform session of possible server outage
                //TODO make this configurable
                if (failedAttemptCount == 3) {
                    if (LOGGER.isEnabledFor(Level.WARN)) {
                        LOGGER.log(Level.WARN, "Number of successive heartbeat failures [%d]. SPM server suspected to be down.", failedAttemptCount);
                    }
                    //Set state to down
                    if ((serverState & SERVER_DOWN_STATE) == 0) {
                        serverState = SERVER_DOWN_STATE;
                        //Notify only if not already notified about down.
                        notifyServerState(serverState);
                    }
                    //reset count
                    failedAttemptCount = 0;
                }
            }
        }

        private void notifyServerState(int serverState) {
            RtaNotificationImpl notification = new RtaNotificationImpl();
            notification.setProperty(RtaNotifications.CONNECTION_EVENT.name(), serverState);
            RtaNotificationListener notificationListener = session.getNotificationListener();
            if (notificationListener != null) {
                notificationListener.onNotification(notification);
            }
        }
    }
}
