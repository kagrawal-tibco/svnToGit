package com.tibco.cep.bpmn.runtime.activity.tasks;

import com.tibco.cep.bpmn.runtime.activity.MoveToTransition;
import com.tibco.cep.bpmn.runtime.activity.Task;
import com.tibco.cep.bpmn.runtime.activity.TaskResult;
import com.tibco.cep.bpmn.runtime.activity.Transition;
import com.tibco.cep.bpmn.runtime.agent.Job;
import com.tibco.cep.bpmn.runtime.utils.Variables;

public class MoveToTask extends AbstractTask {

	Task nextTask;
	Task prevTask;
	Transition outgoingTransition;

	public MoveToTask() {
		super();
		nextTask = null;
	}

	public void init(Task prevTask, Task nextTask) throws Exception {
		this.prevTask = prevTask;
		this.nextTask = nextTask;
		outgoingTransition = new MoveToTransition(prevTask, nextTask);
	}

	@Override
	public Transition[] getOutgoingTransitions() {

		return new Transition[] { outgoingTransition };
	}

	@Override
	public TaskResult execute(Job context, Variables vars, Task loopTask) {
		return null;
	}

}
