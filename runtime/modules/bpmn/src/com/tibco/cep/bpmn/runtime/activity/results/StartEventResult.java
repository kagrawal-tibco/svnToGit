package com.tibco.cep.bpmn.runtime.activity.results;

import com.tibco.cep.bpmn.runtime.activity.TaskResult;
import com.tibco.cep.bpmn.runtime.agent.Job;
import com.tibco.cep.kernel.model.entity.Event;

/*
* Author: Suresh Subramani / Date: 7/19/12 / Time: 6:32 PM
*/
public class StartEventResult implements TaskResult{

    Job createdJob;
    Event causal;

    public Job getCreatedJob() {
        return createdJob;
    }

    public Event getCausal() {
        return causal;
    }

    public StartEventResult(Job createdJob, Event causal) {
        this.createdJob = createdJob;
        this.causal = causal;

    }

    @Override
    public Status getStatus() {
        return Status.STARTEVENT;
    }

    @Override
    public Object getResult() {
        return getCreatedJob();
    }
}
