package com.tibco.cep.bpmn.runtime.activity.gateways;

import com.tibco.cep.bpmn.runtime.activity.Task;
import com.tibco.cep.bpmn.runtime.activity.TaskResult;
import com.tibco.cep.bpmn.runtime.activity.Transition;
import com.tibco.cep.bpmn.runtime.activity.results.DefaultResult;
import com.tibco.cep.bpmn.runtime.activity.results.ExceptionResult;
import com.tibco.cep.bpmn.runtime.agent.Job;
import com.tibco.cep.bpmn.runtime.utils.Variables;

/**
 * @author pdhar
 *
 */
public class ExclusiveGateway extends AbstractGateway {
	

	public ExclusiveGateway() {

	}


	@Override
	protected void initForkTransforms() throws Exception {
	}

	@Override
	protected void initJoinTransforms() throws Exception {
	}



	/* (non-Javadoc)
	 * @see com.tibco.cep.bpmn.runtime.activity.Task#execute(com.tibco.cep.bpmn.runtime.agent.Job)
	 */
	@Override
	public TaskResult execute(Job context, Variables vars, Task loopTask) {
		TaskResult result = null;
		if(isConverging() && !isDiverging()){
			for (Transition t : this.outgoingTranisitions)
	        {
				// converging gateway have only one outgoing
	             result = new DefaultResult(TaskResult.Status.EXCLUSIVE,  t.toTask());
	             break;
	        }
		}else{
			for (Transition t : this.outgoingTranisitions)
	        {
	            if(!t.isDefault() && t.eval(context) ) {
	                result = new DefaultResult(TaskResult.Status.EXCLUSIVE,  t.toTask());
	                break;
	            }
	        }
			if(result == null) {
				//Check for any default transition
		        for (Transition t : this.outgoingTranisitions)
		        {
		            if(t.isDefault()) {
		                result = new DefaultResult(TaskResult.Status.EXCLUSIVE,  t.toTask());
		                break;
		            }
		        }
			}
	        
		}

        if(result == null) {
        	result = new ExceptionResult("Exclusive Gateway doesnot have a Default");
        }

		return result;  // needed for debugger exit task notification

	}


}
