package com.tibco.cep.pattern.subscriber;

import com.tibco.cep.common.exception.RecoveryException;
import com.tibco.cep.common.resource.Id;
import com.tibco.cep.common.resource.ResourceProvider;
import com.tibco.cep.pattern.subscriber.master.SubscriptionListener;
import com.tibco.cep.util.annotation.Optional;

import java.util.Collection;
import java.util.concurrent.Future;

/*
* Author: Ashwin Jayaprakash Date: Aug 17, 2009 Time: 11:09:38 AM
*/
public class SimpleSubscriptionListener implements SubscriptionListener {
    protected String subscriptionItemName;

    protected transient Id subscriptionId;

    public SimpleSubscriptionListener(String subscriptionItemName) {
        this.subscriptionItemName = subscriptionItemName;
    }

    public void start(ResourceProvider resourceProvider, Id subscriptionId) {
        this.subscriptionId = subscriptionId;

        System.out.println("Started SimpleSubscriptionListener: " + subscriptionId + ", " +
                subscriptionItemName);
    }

    public void onMessage(Object messageId, Object message) {
        System.out.println(
                "onMessage SimpleSubscriptionListener: " + messageId + " - " + message + "]");
    }

    public void onMessage(Object messageId, Object message,
                          @Optional Collection<? super Future<?>> routingJobs) throws Exception {
        onMessage(messageId, message);
    }

    public void stop() {
        System.out.println("Stopped SimpleSubscriptionListener: " + subscriptionId + ", " +
                subscriptionItemName);
    }

    public SimpleSubscriptionListener recover(ResourceProvider resourceProvider, Object... params)
            throws RecoveryException {
        return this;
    }
}
