package com.tibco.cep.pattern.util.callback;

import com.tibco.cep.common.resource.Id;
import com.tibco.cep.pattern.matcher.master.Input;
import com.tibco.cep.pattern.matcher.master.Source;
import com.tibco.cep.pattern.matcher.response.Response;

import java.util.LinkedList;

/*
* Author: Ashwin Jayaprakash Date: Sep 1, 2009 Time: 11:48:05 AM
*/
public class DriverTrace {
    protected Id driverId;

    protected LinkedList<CallTrace> callTraces;

    protected volatile boolean stopped;

    public DriverTrace(Id driverId) {
        this.driverId = driverId;
        this.callTraces = new LinkedList<CallTrace>();
    }

    public Id getDriverId() {
        return driverId;
    }

    public synchronized LinkedList<CallTrace> getCallTraces() {
        return (LinkedList<CallTrace>) callTraces.clone();
    }

    public synchronized void record(Source source, Input input, Response response) {
        CallTrace callTrace = new CallTrace(source, input, response);

        callTraces.add(callTrace);
    }

    public synchronized void record(Input input, Response response) {
        CallTrace callTrace = new CallTrace(input, response);

        callTraces.add(callTrace);
    }

    public boolean isStopped() {
        return stopped;
    }

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }
}
