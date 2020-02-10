package com.tibco.cep.bpmn.runtime.activity.results;

import com.tibco.cep.bpmn.runtime.activity.TaskResult;
import com.tibco.cep.bpmn.runtime.agent.Job;

/*
* Author: Suresh Subramani / Date: 5/5/12 / Time: 11:55 AM
*/
public class CallActivityResult implements TaskResult {

    Job calledActivityContext;

    public CallActivityResult(Job childContext) {
        this.calledActivityContext = childContext;
    }

    @Override
    public Status getStatus() {
        return Status.CALLACTIVITY;
    }

    @Override
    public Object getResult() {
        return calledActivityContext;
    }


    public Job getCalledActivityContext() {
        return calledActivityContext;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append("{calledActivityContext=").append(calledActivityContext);
        sb.append('}');
        return sb.toString();
    }
}
