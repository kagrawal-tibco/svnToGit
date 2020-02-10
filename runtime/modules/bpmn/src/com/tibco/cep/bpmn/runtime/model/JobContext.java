package com.tibco.cep.bpmn.runtime.model;


import java.util.List;

import com.tibco.cep.bpmn.runtime.activity.Task;
import com.tibco.cep.bpmn.runtime.agent.ProcessStatus;
import com.tibco.cep.bpmn.runtime.templates.ProcessTemplate;
import com.tibco.cep.runtime.model.element.ProcessJob;
import com.tibco.cep.runtime.model.element.PropertyArrayString;
import com.tibco.cep.runtime.model.element.VersionedObject;

/*
* Author: Suresh Subramani / Date: 11/24/11 / Time: 4:49 PM
*/
public interface JobContext extends ProcessJob, VersionedObject {
	
    /**
     * get the ProcessTemplate attached to this Job instance
     * @return
     */
    ProcessTemplate getProcessTemplate();

    /**
     * get the processTemplateVersion  - convenience method
     * @return
     */
    short getProcessTemplateVersion();
    



    /**
     * AddChild that is being forked - TODO - do we really need.
     * @param child
     */
    void addChild(JobContext child);
    
    
    /**
     * Returns a list of job children
     * @return
     */
    List<JobContext> getJobChildren();



    /**
     * Return the process status.
     * @return
     */
    ProcessStatus getProcessStatus();

    /**
     * Set the processStatus
     * @param status
     */
    void setProcessStatus(ProcessStatus status);


    /**
     * Return the last task that the process successfully executed, checkpointed, and can be recovered from this state.
     * @return
     */
    Task getLastTaskExecuted();

    /**
     * set the last Task successfully executed.
     * @param task
     */
    void setLastTaskExecuted(Task task);

    void setNextTask(Task next);

    Task getNextTask();
    
    /**
     * returns a string array contains map <long key:encoded(eventState,event_id)>=<string value:event Task>
     * @return PropertyArrayString
     */
    PropertyArrayString getPendingEventMap();

    //TODO ProcessMetrics and Task Metrics need to be captured.




}
