package com.tibco.cep.bpmn.runtime.agent;


import java.util.Map;
import java.util.Set;

import com.tibco.cep.bpmn.runtime.activity.Activity;
import com.tibco.cep.bpmn.runtime.activity.Task;
import com.tibco.cep.bpmn.runtime.model.JobContext;
import com.tibco.cep.kernel.model.entity.Event;

/**
 * Created by IntelliJ IDEA.
 * User: suresh
 * Date: Nov 19, 2011
 * Time: 7:25:35 AM
 * To change this template use File | Settings | File Templates.
 */
public interface Job extends Runnable {




    enum ProcessResultMode {
        CONTINUE_SYNC,
        CONTINUE_ASYNC,
        CONTINUE_ASYNC_ANDSTOP_CURRENT,
        STOP_CURRENT

    }


    /**
     * Get the process data attached to this process context
     * @return
     */
    JobContext getJobContext();

    /**
     * Get the ProcessAgent
     * @return
     */
    ProcessAgent getProcessAgent();

    /**
     * Return the last successfully task executed by the JobImpl.
     * @return
     */

    Task getLastTask();

    /**
     * Return the current task being executed at the point of request.
     * @return
     */
    Activity getCurrentTask();

    /**
     * Return the Next task that has to be executed. If this value is set, the last task is usually null.
     * @return
     */
    Task getNextTask();

    /**
     * Return the current transition name that was taken to come to current Task that being executed.
     * @return
     */
    String getTransitionName();

    void checkpoint(boolean removeHandles) throws Exception;



    /**
     * Return the Map of PendingEvents that are not acknowledge or checkpointed till now
     * @return
     */
    Map<String, Set<PendingEvent>> getPendingEvents() ;

    /**
     * Return the Set of Events that have triggered the process.
     * @param uri
     * @return
     */
    Set<PendingEvent> getPendingEvents(String uri) ;


    void removePendingEvents(String name);


    public abstract void recordCompletedJob(Activity task, JobContext jc);


	enum PendingEventState {
        ASSERT,
        ACKNOWLEDGED,
        RELOAD,
        CONSUMED;
        
    }

    interface PendingEvent {
    	
    	long getId();

        Event getEvent();

        boolean consume();

        boolean acknowledge();

        boolean reload();

        PendingEventState getState();
        
        boolean isInitialized();


    }


}
