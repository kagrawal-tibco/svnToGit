package com.tibco.cep.bpmn.runtime.activity;

public class MoveToTransition extends DefaultTransition {

	Task prevTask;
	Task nextTask;

	public MoveToTransition(Task prevTask, Task nextTask) {
		this.prevTask = prevTask;
		this.nextTask = nextTask;
	}

	@Override
	public Task fromTask() {
		return prevTask;
	}

	@Override
	public Task toTask() {
		return nextTask;
	}

}
