package com.tibco.cep.loadbalancer.impl.server.core.sink;

import static com.tibco.cep.util.Helper.$logger;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

import com.tibco.cep.common.resource.ResourceProvider;
import com.tibco.cep.loadbalancer.message.DistributableMessage;
import com.tibco.cep.loadbalancer.message.MessageHandle;
import com.tibco.cep.loadbalancer.server.core.sink.SinkStateSnapshot;
import com.tibco.cep.loadbalancer.server.core.sink.SinkStatus;
import com.tibco.cep.util.annotation.LogCategory;

/*
* Author: Ashwin Jayaprakash / Date: Jul 12, 2010 / Time: 1:25:45 PM
*/

@LogCategory("loadbalancer.core.server.sink.status")
public class DefaultSinkStatus implements SinkStatus {
    /**
     * {@value}
     */
    public static final int DEFAULT_MAX_PENDING_MESSAGES = 256;

    protected volatile SinkStateSnapshot stateSnapshot;

    protected ConcurrentHashMap<Object, MessageHandle> pendingMessages;

    protected LinkedBlockingQueue<MessageHandle> failedMessages;

    protected Logger logger;

    protected ResourceProvider resourceProvider;

    public DefaultSinkStatus(DefaultSinkStateSnapshot stateSnapshot) {
        this.pendingMessages = new ConcurrentHashMap<Object, MessageHandle>();
        this.failedMessages = new LinkedBlockingQueue<MessageHandle>(DEFAULT_MAX_PENDING_MESSAGES);
        this.stateSnapshot = stateSnapshot;
    }

    @Override
    public void setResourceProvider(ResourceProvider resourceProvider) {
        this.resourceProvider = resourceProvider;

        this.logger = $logger(resourceProvider, getClass());
    }

    /**
     * @param messageHandle {@link MessageHandle#setSentAt(long)} is not set here.
     */
    public void addPendingMessage(MessageHandle messageHandle) {
        pendingMessages.put(messageHandle.getDistributableMessage().getUniqueId(), messageHandle);
    }

    public MessageHandle getPendingMessage(Object messageUniqueId) {
        return pendingMessages.get(messageUniqueId);
    }

    /**
     * @param messageHandle {@link MessageHandle#setAckReceivedAt(long)} is not set here.
     */
    public void confirmMessageSent(MessageHandle messageHandle) {
        DistributableMessage message = messageHandle.getDistributableMessage();

        pendingMessages.remove(message.getUniqueId());
    }

    /**
     * @param messageHandle {@link MessageHandle#setException(Exception)} is not set here but must be done before.
     */
    public void confirmMessageFailed(MessageHandle messageHandle) {
        for (; ;) {
            boolean status = failedMessages.offer(messageHandle);
            if (status) {
                break;
            }

            //FIFO clear to make room.
            failedMessages.poll();
        }

        pendingMessages.remove(messageHandle.getDistributableMessage().getUniqueId());
    }

    @Override
    public SinkStateSnapshot getStateSnapshot() {
        return stateSnapshot;
    }

    public void setStateSnapshot(SinkStateSnapshot stateSnapshot) {
        this.stateSnapshot = stateSnapshot;
    }

    @Override
    public Collection<MessageHandle> getPendingMessages() {
        Collection<MessageHandle> c = pendingMessages.values();

        return c.isEmpty() ? null : c;
    }

    /**
     * @return <code>null</code>. No-op.
     */
    @Override
    public Collection<MessageHandle> getAndClearSentMessages() {
        return null;
    }

    /**
     * Some very old messages may be lost because of the limited storage - {@link #DEFAULT_MAX_PENDING_MESSAGES}.
     *
     * @return
     */
    @Override
    public Collection<MessageHandle> getAndClearFailedMessages() {
        LinkedBlockingQueue<MessageHandle> q = new LinkedBlockingQueue<MessageHandle>();

        failedMessages.drainTo(q);

        return q.isEmpty() ? null : q;
    }
}