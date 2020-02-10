package com.tibco.cep.pattern.util.callback;

import com.tibco.cep.common.exception.RecoveryException;
import com.tibco.cep.common.resource.ResourceProvider;
import com.tibco.cep.pattern.matcher.master.DriverView;
import com.tibco.cep.pattern.matcher.master.Input;
import com.tibco.cep.pattern.matcher.master.Source;
import com.tibco.cep.pattern.matcher.master.TimeInput;
import com.tibco.cep.pattern.matcher.response.Failure;
import com.tibco.cep.pattern.matcher.response.Response;
import com.tibco.cep.pattern.matcher.response.Success;

/*
* Author: Ashwin Jayaprakash Date: Sep 1, 2009 Time: 11:34:03 AM
*/
public class TrackableDriverCallback extends AbstractDriverCallback {
    protected transient DriverTracker driverTracker;

    public TrackableDriverCallback(DriverTracker driverTracker) {
        this.driverTracker = driverTracker;
    }

    //--------------

    @Override
    public void start(ResourceProvider resourceProvider, DriverView driverView) {
        super.start(resourceProvider, driverView);

        driverTracker.onStart(driverId);
    }

    @Override
    public void afterInput(Source source, Input input, Response response) {
        super.afterInput(source, input, response);

        if (response instanceof Success) {
            driverTracker.onSuccess(driverId, (Success) response, input, source);
        }
        else {
            driverTracker.onFailure(driverId, (Failure) response, input, source);
        }
    }

    @Override
    public void afterTimeOut(TimeInput input, Response response) {
        super.afterTimeOut(input, response);

        if (response instanceof Success) {
            driverTracker.onSuccess(driverId, (Success) response, input, null);
        }
        else {
            driverTracker.onFailure(driverId, (Failure) response, input, null);
        }
    }

    @Override
    public void stop() {
        super.stop();

        driverTracker.onStop(driverId);
    }

    //--------------

    public TrackableDriverCallback recover(ResourceProvider resourceProvider, Object... params)
            throws RecoveryException {
        super.recover(resourceProvider, params);

        return this;
    }
}
