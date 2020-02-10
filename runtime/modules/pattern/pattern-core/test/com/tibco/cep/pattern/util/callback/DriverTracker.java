package com.tibco.cep.pattern.util.callback;

import com.tibco.cep.common.resource.Id;
import com.tibco.cep.pattern.matcher.master.Input;
import com.tibco.cep.pattern.matcher.master.Source;
import com.tibco.cep.pattern.matcher.response.Complete;
import com.tibco.cep.pattern.matcher.response.Failure;
import com.tibco.cep.pattern.matcher.response.Success;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/*
* Author: Ashwin Jayaprakash Date: Sep 1, 2009 Time: 11:49:10 AM
*/
public interface DriverTracker {
    Set<Id> getDriverCorrelationIds();

    /**
     * Last trace for the given driver-id.
     *
     * @param driverCorrelationId
     * @return
     */
    DriverTrace getDriverTrace(Id driverCorrelationId);

    /**
     * All traces for the given driver-id (more than 1, if any).
     *
     * @param driverCorrelationId
     * @return
     */
    List<DriverTrace> getDriverTraces(Id driverCorrelationId);

    /**
     * All traces.
     *
     * @return
     */
    Collection<DriverTrace> getDriverTraces();

    //--------------

    void onStart(Id driverCorrelationId);

    /**
     * @param driverCorrelationId
     * @param success
     * @param input
     * @param optionalSource      Can be <code>null</code>.
     */
    void onSuccess(Id driverCorrelationId, Success success, Input input, Source optionalSource);

    /**
     * @param driverCorrelationId
     * @param failure
     * @param input
     * @param optionalSource      Can be <code>null</code>.
     */
    void onFailure(Id driverCorrelationId, Failure failure, Input input, Source optionalSource);

    /**
     * @param driverCorrelationId
     * @param complete
     * @param input
     * @param optionalSource      Can be <code>null</code>.
     */
    void onCompletion(Id driverCorrelationId, Complete complete, Input input,
                      Source optionalSource);

    void onStop(Id driverCorrelationId);
}
