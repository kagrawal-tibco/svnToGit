package com.tibco.cep.bpmn.runtime.activity.events;

import com.tibco.cep.bpmn.runtime.activity.Task;
import com.tibco.cep.bpmn.runtime.activity.TaskResult;
import com.tibco.cep.bpmn.runtime.activity.tasks.AbstractTask;
import com.tibco.cep.bpmn.runtime.agent.Job;
import com.tibco.cep.bpmn.runtime.utils.Variables;

/**
 * @author pdhar
 *
 */
public class BoundaryEvent extends AbstractTask {
	
	public BoundaryEvent() {
		// TODO Auto-generated constructor stub
	}




	/* (non-Javadoc)
	 * @see com.tibco.cep.bpmn.runtime.activity.Task#execute(com.tibco.cep.bpmn.runtime.agent.Job)
	 */
	@Override
	public TaskResult execute(Job context, Variables vars, Task loopTask) {
		// TODO Auto-generated method stub
        return null;

	}


}
