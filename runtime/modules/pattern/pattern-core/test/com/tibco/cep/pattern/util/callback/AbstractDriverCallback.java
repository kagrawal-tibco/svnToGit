package com.tibco.cep.pattern.util.callback;

import com.tibco.cep.common.exception.RecoveryException;
import com.tibco.cep.common.resource.Id;
import com.tibco.cep.common.resource.ResourceProvider;
import com.tibco.cep.pattern.matcher.impl.util.SequenceFormatter;
import com.tibco.cep.pattern.matcher.master.*;
import com.tibco.cep.pattern.matcher.response.Response;
import com.tibco.cep.pattern.matcher.trace.SequenceView;

/*
* Author: Ashwin Jayaprakash Date: Sep 1, 2009 Time: 11:35:13 AM
*/

/**
 * Simple class to help get started.
 */
public abstract class AbstractDriverCallback implements DriverCallback {
    protected transient DriverView driverView;

    protected transient Id driverId;

    protected AbstractDriverCallback() {
    }

    //----------

    public void start(ResourceProvider resourceProvider, DriverView driverView) {
        this.driverView = driverView;

        driverId = driverView.getDriverCorrelationId();

        System.out.println("Driver [" + driverId + "] started.");
    }

    public void afterInput(Source source, Input input, Response response) {
        System.out.println("Response for driver [" + driverId + "] due to Input [" + input +
                "] from Source [" + source + "] is: " + response);
    }

    public void afterTimeOut(TimeInput input, Response response) {
        System.out.println("Response for driver [" + driverId + "] due to TimeInput [" + input +
                "] is: " + response);
    }

    public void stop() {
        System.out.println("Driver [" + driverId + "] stopped.");

        //-------------

        SequenceView sequence = driverView.getSequence();
        StringBuilder sequenceAsSB = SequenceFormatter.print(driverId, sequence);
        System.out.println(sequenceAsSB);
    }

    @SuppressWarnings({"unchecked"})
    public AbstractDriverCallback recover(ResourceProvider resourceProvider, Object... params)
            throws RecoveryException {
        return this;
    }
}
