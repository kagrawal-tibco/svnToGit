package com.tibco.cep.loadbalancer.impl.message;

import java.io.Serializable;

import com.tibco.cep.loadbalancer.message.DistributionResponse;

/*
* Author: Ashwin Jayaprakash / Date: Jul 27, 2010 / Time: 3:03:19 PM
*/

//todo Get rid of these Serializable classes
public class SimpleDistributionResponse implements DistributionResponse, Serializable {
    protected Object uniqueIdOfMessage;

    protected boolean positiveAck;

    public SimpleDistributionResponse() {
    }

    public SimpleDistributionResponse(Object uniqueIdOfMessage, boolean positiveAck) {
        this.uniqueIdOfMessage = uniqueIdOfMessage;
        this.positiveAck = positiveAck;
    }

    @Override
    public Object getUniqueIdOfMessage() {
        return uniqueIdOfMessage;
    }

    public boolean isPositiveAck() {
        return positiveAck;
    }

    //---------------

    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "{uniqueIdOfMessage=" + uniqueIdOfMessage + ", positiveAck=" + positiveAck + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SimpleDistributionResponse)) {
            return false;
        }

        SimpleDistributionResponse response = (SimpleDistributionResponse) o;

        if (positiveAck != response.positiveAck) {
            return false;
        }
        if (!uniqueIdOfMessage.equals(response.uniqueIdOfMessage)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = uniqueIdOfMessage.hashCode();
        result = 31 * result + (positiveAck ? 1 : 0);

        return result;
    }
}
