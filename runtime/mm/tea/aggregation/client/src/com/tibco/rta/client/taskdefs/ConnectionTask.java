package com.tibco.rta.client.taskdefs;

import com.tibco.rta.client.notify.MessageReceptionNotifier;
import com.tibco.rta.client.transport.MessageTransmissionStrategy;
import com.tibco.rta.impl.DefaultRtaSession;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 17/3/13
 * Time: 4:34 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class ConnectionTask<T extends MessageTransmissionStrategy, M extends MessageReceptionNotifier> extends AbstractClientTask {

    protected DefaultRtaSession session;

    /**
     * Use this as demultiplexer
     */
    protected M messageNotifier;

    protected ConnectionTask(T transmissionStrategy) {
        super(transmissionStrategy);
    }

    public void setNotificationReactor(M notificationReactor) {
        this.messageNotifier = notificationReactor;
    }
}
