package com.tibco.cep.loadbalancer.message;

import com.tibco.cep.util.annotation.Optional;
import com.tibco.cep.util.annotation.ThreadSafe;

/*
* Author: Ashwin Jayaprakash / Date: Jul 9, 2010 / Time: 5:47:46 PM
*/

@ThreadSafe
public interface MessageHandle {
    /**
     * {@value}.
     */
    int NOT_APPLICABLE = -1;

    DistributableMessage getDistributableMessage();

    /**
     * @return {@link #NOT_APPLICABLE} if this operation has not been started or an error was encountered during
     *         sending.
     * @see #getPostSendException()
     */
    long getSentAt();

    /**
     * @return Error occurred after the message was sent and before ack was received.
     */
    @Optional
    Exception getPostSendException();

    /**
     * @return {@link #NOT_APPLICABLE} if ack has not been received yet or if the message was never sent in the first
     *         place.
     *         <p/>
     *         If an ack is received then {@link #getPostSendException()} must return <code>null</code>.
     */
    long getAckReceivedAt();

    void setDistributableMessage(DistributableMessage distributableMessage);

    void setSentAt(long sentAt);

    void setAckReceivedAt(long ackReceivedAt);

    void setException(Exception exception);
}