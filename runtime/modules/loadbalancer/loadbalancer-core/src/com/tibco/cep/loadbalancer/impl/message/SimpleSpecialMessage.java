package com.tibco.cep.loadbalancer.impl.message;

import java.io.Serializable;

import com.tibco.cep.loadbalancer.message.SpecialMessage;

/*
* Author: Ashwin Jayaprakash / Date: Aug 3, 2010 / Time: 1:59:52 PM
*/

//todo Get rid of these Serializable classes
public class SimpleSpecialMessage implements SpecialMessage, Serializable {
    protected Object uniqueId;

    protected Object senderId;

    public SimpleSpecialMessage() {
    }

    public SimpleSpecialMessage(Object uniqueId, Object senderId) {
        this.uniqueId = uniqueId;
        this.senderId = senderId;
    }

    public Object getUniqueId() {
        return uniqueId;
    }

    @Override
    public Object getSenderId() {
        return senderId;
    }

    //-----------------

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{uniqueId=" + uniqueId + ", senderId=" + senderId + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SimpleSpecialMessage)) {
            return false;
        }

        SimpleSpecialMessage that = (SimpleSpecialMessage) o;

        if (!senderId.equals(that.senderId)) {
            return false;
        }
        if (!uniqueId.equals(that.uniqueId)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = uniqueId.hashCode();
        result = 31 * result + senderId.hashCode();
        return result;
    }
}
