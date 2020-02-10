package com.tibco.cep.loadbalancer.impl.client.transport.tcp;

/*
* Author: Ashwin Jayaprakash / Date: Mar 25, 2010 / Time: 1:18:40 PM
*/

public class ReceivedMessageHandle {
    protected Object uniqueIdOfMessage;

    protected Object originalSenderId;

    protected String receivedBySessionId;

    protected long receivedAt;

    public ReceivedMessageHandle(Object uniqueIdOfMessage, Object originalSenderId, String receivedBySessionId,
                                 long receivedAt) {
        this.uniqueIdOfMessage = uniqueIdOfMessage;
        this.originalSenderId = originalSenderId;
        this.receivedBySessionId = receivedBySessionId;
        this.receivedAt = receivedAt;
    }

    public Object getUniqueIdOfMessage() {
        return uniqueIdOfMessage;
    }

    public Object getOriginalSenderId() {
        return originalSenderId;
    }

    public String getReceivedBySessionId() {
        return receivedBySessionId;
    }

    public long getReceivedAt() {
        return receivedAt;
    }

    //---------------

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReceivedMessageHandle)) {
            return false;
        }

        ReceivedMessageHandle that = (ReceivedMessageHandle) o;

        if (receivedAt != that.receivedAt) {
            return false;
        }
        if (!originalSenderId.equals(that.originalSenderId)) {
            return false;
        }
        if (!receivedBySessionId.equals(that.receivedBySessionId)) {
            return false;
        }
        if (!uniqueIdOfMessage.equals(that.uniqueIdOfMessage)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = uniqueIdOfMessage.hashCode();
        result = 31 * result + originalSenderId.hashCode();
        result = 31 * result + receivedBySessionId.hashCode();
        result = 31 * result + (int) (receivedAt ^ (receivedAt >>> 32));

        return result;
    }
}
