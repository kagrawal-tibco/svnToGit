package com.tibco.cep.bpmn.runtime.activity;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.utils.ROEObjectWrapper;
import com.tibco.cep.bpmn.runtime.activity.mapper.Mapper;
import com.tibco.cep.bpmn.runtime.agent.Job;
import com.tibco.cep.bpmn.runtime.utils.Variables;

/**
 * Created by IntelliJ IDEA.
 * User: suresh
 * Date: Nov 19, 2011
 * Time: 7:26:50 AM
 * To change this template use File | Settings | File Templates.
 */
public interface Task extends Activity {



    Mapper getMapper();



    /**
     * Execute the Task with this Job
     *
     *
     * @param context
     * @param vars process variables
     * @param loopTask TODO
     * @throws com.tibco.cep.bpmn.runtime.agent.ProcessException
     */
    TaskResult exec(Job context, Variables vars, Task loopTask);
    

    /**
     * Get all the outgoingTransitions
     * @return
     */
    Transition[] getOutgoingTransitions();

    /**
     * The incoming Transition
     * @return
     */
    Transition[] getIncomingTransitions();

    public void initTransitions() throws Exception;

    public String getInputMapperString();

    public String getOutputMapperString();

    boolean isCheckpointEnabled();

    boolean isAsyncExec();

    short getIndex();
    
    public ROEObjectWrapper<EClass,EObject> getTaskModel();


}
