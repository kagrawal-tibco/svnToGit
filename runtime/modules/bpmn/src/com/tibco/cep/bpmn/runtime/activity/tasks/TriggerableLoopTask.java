package com.tibco.cep.bpmn.runtime.activity.tasks;

import com.tibco.cep.bpmn.runtime.activity.Triggerable;
import com.tibco.cep.bpmn.runtime.agent.Job;
import com.tibco.cep.bpmn.runtime.model.JobContext;

public interface TriggerableLoopTask extends Triggerable{

	boolean lock(Job currentJob);

	void unlock(Job currentJob);
	
	void setCheckPointedJobContext(JobContext checkPointedJob);
	
	JobContext getCheckPointedJobContext();
}
