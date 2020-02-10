package com.tibco.cep.bpmn.runtime.activity;

import com.tibco.cep.bpmn.runtime.activity.tasks.AbstractTask;
import com.tibco.cep.bpmn.runtime.activity.tasks.DefaultLoopTask;
import com.tibco.cep.bpmn.runtime.activity.tasks.DefaultTriggerableLoopTask;

/*
* Author: Suresh Subramani / Date: 11/24/11 / Time: 5:42 PM
*/
public class TaskFactory {

    private  static TaskFactory singleTon = new TaskFactory();


    public  static TaskFactory getInstance() {
        return singleTon;
    }

    private TaskFactory() {
    }


    public Task newTask( String taskType, String taskName,boolean isLoopActivity) throws Exception
    {
        Class<? extends Task> taskClass = TaskRegistry.getInstance().getTaskType(taskType);
        if (taskClass == null) 
        	throw new Exception(String.format("Task %s is of invalid task type :%s", taskName, taskType));

        Task task = taskClass.newInstance();
        
        if(isLoopActivity) {
        	if(task instanceof Triggerable){
        		task = new DefaultTriggerableLoopTask((AbstractTask) task);
        	} else {
        		task = new DefaultLoopTask((AbstractTask) task);
        	}
        }

        //TaskRegistry.getInstance().addTask(taskName, task);

       return task;
    }

    public Transition newTransition(String transitionId) throws Exception
    {
        Transition transition = new DefaultTransition();
        TaskRegistry.getInstance().addTransition(transitionId, transition);
        return transition;
    }



    
//    public Transition newTransition(InitContext context, String transitionName) throws Exception
//    {
//    	final String type = BpmnMetaModel.INSTANCE.getExpandedName(BpmnModelClass.SEQUENCE_FLOW).toString();
//        Class<? extends Transition> transitionClass = TaskRegistry.getInstance().getTransitionType(type);
//        if (transitionClass == null) throw new Exception(String.format("Invalid tra type name :%s", type));
//
//        Transition transition = transitionClass.newInstance();
//
//        transition.init(context, new Object[] {transitionName});
//
//       return transition;
//    }


}
