package com.tibco.cep.loadbalancer.server.core.sink;

import java.util.Collection;

import com.tibco.cep.common.resource.ResourceProvider;
import com.tibco.cep.loadbalancer.message.DistributableMessage;
import com.tibco.cep.loadbalancer.message.MessageHandle;
import com.tibco.cep.util.annotation.Optional;
import com.tibco.cep.util.annotation.ReadOnly;
import com.tibco.cep.util.annotation.ThreadSafe;

/*
* Author: Ashwin Jayaprakash / Date: Mar 16, 2010 / Time: 3:41:31 PM
*/

@ThreadSafe
public interface SinkStatus {
    void setResourceProvider(ResourceProvider resourceProvider);

    SinkStateSnapshot getStateSnapshot();

    /**
     * Stored using {@link DistributableMessage#getUniqueId()}.
     *
     * @param messageHandle
     */
    void addPendingMessage(MessageHandle messageHandle);

    /**
     * Messages that have been sent but pending acknowledgement.
     *
     * @return
     */
    @ReadOnly
    @Optional
    Collection<MessageHandle> getPendingMessages();

    @Optional
    MessageHandle getPendingMessage(Object messageUniqueId);

    //-------------

    /**
     * Move from {@link #getPendingMessages()} to {@link #getAndClearSentMessages()}.
     *
     * @param messageHandle
     */
    void confirmMessageSent(MessageHandle messageHandle);

    /**
     * Messages that have been successfully sent.
     *
     * @return
     */
    @Optional
    Collection<MessageHandle> getAndClearSentMessages();

    //-------------

    /**
     * Move from {@link #getPendingMessages()} to {@link #getAndClearFailedMessages()}.
     *
     * @param messageHandle
     */
    void confirmMessageFailed(MessageHandle messageHandle);

    /**
     * Messages that could not be sent successfully.
     *
     * @return
     */
    @Optional
    Collection<MessageHandle> getAndClearFailedMessages();
}
