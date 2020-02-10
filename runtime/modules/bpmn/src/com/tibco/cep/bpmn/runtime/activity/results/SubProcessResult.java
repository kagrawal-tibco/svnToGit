package com.tibco.cep.bpmn.runtime.activity.results;

import com.tibco.cep.bpmn.runtime.activity.TaskResult;
import com.tibco.cep.bpmn.runtime.agent.Job;

/*
* Author: Suresh Subramani / Date: 5/5/12 / Time: 11:55 AM
*/
public class SubProcessResult implements TaskResult {

    Job subJob;

    public SubProcessResult(Job childContext) {
        this.subJob = childContext;
    }

    @Override
    public Status getStatus() {
        return Status.SUBPROCESS;
    }

    @Override
    public Object getResult() {
        return subJob;
    }


    public Job getSubProcessResult() {
        return subJob;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append("{subJob=").append(subJob);
        sb.append('}');
        return sb.toString();
    }
}
