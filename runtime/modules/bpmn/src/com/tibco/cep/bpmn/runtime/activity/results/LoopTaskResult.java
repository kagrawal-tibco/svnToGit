package com.tibco.cep.bpmn.runtime.activity.results;

import com.tibco.cep.bpmn.runtime.activity.TaskResult;

public class LoopTaskResult extends DefaultResult implements TaskResult {
	public LoopTaskResult(Status status, Object result) {
		super(status, result);
	}


}
