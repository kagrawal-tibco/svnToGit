package com.tibco.cep.pattern.util.callback;

import com.tibco.cep.common.exception.RecoveryException;
import com.tibco.cep.common.resource.Id;
import com.tibco.cep.common.resource.ResourceProvider;
import com.tibco.cep.pattern.matcher.master.AdvancedDriverOwner;
import com.tibco.cep.pattern.matcher.master.DriverCallbackCreator;
import com.tibco.cep.pattern.matcher.master.Input;
import com.tibco.cep.pattern.matcher.master.Source;
import com.tibco.cep.pattern.matcher.response.Complete;
import com.tibco.cep.pattern.matcher.response.Failure;
import com.tibco.cep.pattern.matcher.response.Success;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/*
* Author: Ashwin Jayaprakash Date: Sep 1, 2009 Time: 11:48:05 AM
*/
public class TrackableDriverCallbackCreator implements DriverCallbackCreator, DriverTracker {
    protected ConcurrentHashMap<Id, CopyOnWriteArrayList<DriverTrace>> driverTraces;

    public TrackableDriverCallbackCreator() {
        this.driverTraces = new ConcurrentHashMap<Id, CopyOnWriteArrayList<DriverTrace>>();
    }

    public TrackableDriverCallback create(ResourceProvider resourceProvider,
                                          AdvancedDriverOwner driverOwner) {
        return new TrackableDriverCallback(this);
    }

    public TrackableDriverCallbackCreator recover(ResourceProvider resourceProvider,
                                                  Object... params)
            throws RecoveryException {
        return this;
    }

    public Set<Id> getDriverCorrelationIds() {
        return driverTraces.keySet();
    }

    public List<DriverTrace> getDriverTraces(Id driverCorrelationId) {
        return driverTraces.get(driverCorrelationId);
    }

    public DriverTrace getDriverTrace(Id driverCorrelationId) {
        CopyOnWriteArrayList<DriverTrace> list = driverTraces.get(driverCorrelationId);
        if (list == null) {
            return null;
        }

        return list.get(list.size() - 1);
    }

    public Collection<DriverTrace> getDriverTraces() {
        LinkedList<DriverTrace> list = new LinkedList<DriverTrace>();

        for (CopyOnWriteArrayList<DriverTrace> traces : driverTraces.values()) {
            list.addAll(traces);
        }

        return list;
    }

    //-------------

    public void onStart(Id driverCorrelationId) {
        CopyOnWriteArrayList<DriverTrace> list = getDriverTraceList(driverCorrelationId);

        list.add(new DriverTrace(driverCorrelationId));
    }

    private CopyOnWriteArrayList<DriverTrace> getDriverTraceList(Id driverCorrelationId) {
        CopyOnWriteArrayList<DriverTrace> list = driverTraces.get(driverCorrelationId);
        if (list != null) {
            return list;
        }

        list = new CopyOnWriteArrayList<DriverTrace>();

        CopyOnWriteArrayList<DriverTrace> existingList =
                driverTraces.putIfAbsent(driverCorrelationId, list);
        if (existingList != null) {
            list = existingList;
        }

        return list;
    }

    private DriverTrace getLastDriverTrace(Id driverCorrelationId) {
        CopyOnWriteArrayList<DriverTrace> list = driverTraces.get(driverCorrelationId);

        DriverTrace driverTrace = list.get(list.size() - 1);

        return driverTrace;
    }

    public void onSuccess(Id driverCorrelationId, Success success, Input input,
                          Source optionalSource) {
        DriverTrace driverTrace = getLastDriverTrace(driverCorrelationId);

        if (optionalSource == null) {
            driverTrace.record(input, success);
        }
        else {
            driverTrace.record(optionalSource, input, success);
        }
    }

    public void onFailure(Id driverCorrelationId, Failure failure, Input input,
                          Source optionalSource) {
        DriverTrace driverTrace = getLastDriverTrace(driverCorrelationId);

        if (optionalSource == null) {
            driverTrace.record(input, failure);
        }
        else {
            driverTrace.record(optionalSource, input, failure);
        }
    }

    public void onCompletion(Id driverCorrelationId, Complete complete, Input input,
                             Source optionalSource) {
        DriverTrace driverTrace = getLastDriverTrace(driverCorrelationId);

        if (optionalSource == null) {
            driverTrace.record(input, complete);
        }
        else {
            driverTrace.record(optionalSource, input, complete);
        }
    }

    public void onStop(Id driverCorrelationId) {
        DriverTrace driverTrace = getLastDriverTrace(driverCorrelationId);

        driverTrace.setStopped(true);
    }
}
