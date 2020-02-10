package com.tibco.cep.bpmn.runtime.activity.results;

import com.tibco.cep.bpmn.runtime.activity.TaskResult;

/*
* Author: Suresh Subramani / Date: 2/8/12 / Time: 8:09 PM
*/
public class DefaultResult implements TaskResult {

    Status status;
    Object result;

    public DefaultResult(Status status, Object result)
    {
        this.status = status;
        this.result = result;
    }
    @Override
    public Status getStatus() {
        return status;
    }

    public Object getResult() {
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append("{status=").append(status);
        sb.append(", result=").append(result);
        sb.append('}');
        return sb.toString();
    }
}
