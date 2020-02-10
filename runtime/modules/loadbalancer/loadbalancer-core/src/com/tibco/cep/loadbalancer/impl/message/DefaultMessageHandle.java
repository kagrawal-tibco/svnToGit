package com.tibco.cep.loadbalancer.impl.message;

import com.tibco.cep.loadbalancer.message.DistributableMessage;
import com.tibco.cep.loadbalancer.message.MessageHandle;
import com.tibco.cep.util.annotation.ThreadSafe;

/*
* Author: Ashwin Jayaprakash / Date: Jul 12, 2010 / Time: 3:44:11 PM
*/
@ThreadSafe
public class DefaultMessageHandle implements MessageHandle {
    protected volatile long sentAt;

    protected volatile long ackReceivedAt;

    protected volatile Exception exception;

    protected DistributableMessage distributableMessage;

    public DefaultMessageHandle() {
        this.sentAt = NOT_APPLICABLE;
        this.ackReceivedAt = NOT_APPLICABLE;
    }

    @Override
    public void setDistributableMessage(DistributableMessage distributableMessage) {
        this.distributableMessage = distributableMessage;
    }

    @Override
    public DistributableMessage getDistributableMessage() {
        return distributableMessage;
    }

    @Override
    public void setSentAt(long sentAt) {
        this.sentAt = sentAt;
    }

    @Override
    public long getSentAt() {
        return sentAt;
    }

    @Override
    public void setAckReceivedAt(long ackReceivedAt) {
        this.ackReceivedAt = ackReceivedAt;
    }

    @Override
    public long getAckReceivedAt() {
        return ackReceivedAt;
    }

    @Override
    public void setException(Exception exception) {
        this.exception = exception;
    }

    @Override
    public Exception getPostSendException() {
        return exception;
    }
}
