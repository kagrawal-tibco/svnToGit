package com.tibco.cep.bpmn.runtime.activity.results;

import com.tibco.cep.bpmn.runtime.activity.TaskResult;
import com.tibco.cep.bpmn.runtime.agent.Job;

/*
* Author: Suresh Subramani / Date: 8/14/12 / Time: 5:49 PM
*/
public class MergeCompleteResult implements TaskResult {


    Job mergedJob;

    public MergeCompleteResult(Job mergedJob)
    {

        this.mergedJob = mergedJob;
    }
    @Override
    public Status getStatus() {
        return Status.MERGECOMPLETE;
    }

    public Object getResult() {
        return mergedJob;
    }

    public Job getMergedJob() {
        return mergedJob;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append("{status=").append(Status.MERGECOMPLETE);
        sb.append(", result=").append(mergedJob);
        sb.append('}');
        return sb.toString();
    }
}
