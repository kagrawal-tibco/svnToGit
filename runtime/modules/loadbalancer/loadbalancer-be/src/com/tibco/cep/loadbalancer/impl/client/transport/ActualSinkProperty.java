package com.tibco.cep.loadbalancer.impl.client.transport;

/*
* Author: Ashwin Jayaprakash / Date: Jul 6, 2010 / Time: 11:37:17 AM
*/

/**
 * The minimum set of parameters.
 */
public enum ActualSinkProperty {
    RuleSession,
    AbstractChannel,
    AbstractDestination,
    MessageReceiver,
    FlagLikelyDupDeliveryAfterChangeMillis(new Long(5 * 60 * 1000 /*5 mins*/)),
    ConfigProperties;

    private Object defaultValue;

    ActualSinkProperty() {
    }

    ActualSinkProperty(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }
}
