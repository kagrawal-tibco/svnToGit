package com.tibco.cep.bpmn.runtime.templates;

import java.util.Collection;
import java.util.List;

import com.tibco.cep.bpmn.runtime.activity.Task;
import com.tibco.cep.bpmn.runtime.agent.ProcessException;
import com.tibco.cep.bpmn.runtime.model.JobContext;
import com.tibco.cep.bpmn.runtime.model.VersionInfo;
import com.tibco.cep.designtime.model.process.ProcessModel;

/*
* Author: Suresh Subramani / Date: 11/24/11 / Time: 4:49 PM
*/

/**
 * A modeled process using BPMN notation is represented as a JobImpl Template at runtime.
 * An instance of the ProcessTemplate is JobImpl represented by the interface JobImpl. (AbstractProcess implements JobImpl)
 *
 * A ProcessTemplate is created by traversing the modeled graph, and constructing the appropriate nodes and edges
 */
public interface ProcessTemplate  extends CodegenTemplate {


    /**
     * Return the version information as modeled.
     * @return
     */
    VersionInfo getVersionInfo();
    

    
    ProcessModel getProcessModel();

    //Return tha Task that represents the fully qualified Name
    Task getTask(String name);

 
    JobContext newProcessData() throws ProcessException;

    Task getTask(short index);

    Collection<Task> allTasks();




    /**
     * List of Task that are Starter task.
     * @return
     */
    List<Task> getStarterTask();

    /**
     * List of Task that are Triggerable. This includes ReceiveTask, Intermediate also.
     * @return
     */
    List<Task> getTriggerableTask();


    /**
     * Return the Process's distinguished Name.
     * @return
     */
    ProcessName getProcessName();
    
    /**
     * Returns the Exception Result Handler Process or Function URI
     * @return
     */
    String getExceptionHandler();
    
    /**
     * Returns true if the exception handling is done asynchronously, else false
     * @return
     */
    boolean isAsyncExceptionHandler();


}
