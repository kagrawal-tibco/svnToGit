package com.tibco.rta.common.service;

import java.util.concurrent.Callable;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 23/11/12
 * Time: 12:23 PM
 * An abstraction of any workitem that can be queued on the engine
 * and executed by the workitem service.
 * @see WorkItemService
 */
public interface WorkItem<T> extends Callable<T> {

    /**
     * A non-blocking get call as opposed to the blocking one from {@link java.util.concurrent.Future}
     * <p>
     *     Implementations should ensure that this call does not result in
     *     a wait on the actual workitem implementation.
     * </p>
     * @return
     */
    T get();
}
