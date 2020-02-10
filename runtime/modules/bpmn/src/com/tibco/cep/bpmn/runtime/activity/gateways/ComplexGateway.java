package com.tibco.cep.bpmn.runtime.activity.gateways;

import com.tibco.cep.bpmn.runtime.activity.Task;
import com.tibco.cep.bpmn.runtime.activity.TaskResult;
import com.tibco.cep.bpmn.runtime.agent.Job;
import com.tibco.cep.bpmn.runtime.utils.Variables;

/**
 * @author pdhar
 *
 */
public class ComplexGateway extends AbstractGateway {
	
	public ComplexGateway() {
		// TODO Auto-generated constructor stub
	}



	/* (non-Javadoc)
	 * @see com.tibco.cep.bpmn.runtime.activity.Task#execute(com.tibco.cep.bpmn.runtime.agent.Job)
	 */
	@Override
	public TaskResult execute(Job context, Variables vars, Task loopTask) {
		return null;

	}
	
	
	@Override
	protected void initForkTransforms() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void initJoinTransforms() throws Exception {
		// TODO Auto-generated method stub
		
	}



}
