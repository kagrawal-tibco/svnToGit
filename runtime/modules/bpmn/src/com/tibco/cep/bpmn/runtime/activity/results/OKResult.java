package com.tibco.cep.bpmn.runtime.activity.results;

import com.tibco.cep.bpmn.runtime.activity.TaskResult;

/*
* Author: Suresh Subramani / Date: 2/8/12 / Time: 5:06 PM
*/
public class OKResult implements TaskResult {

    Object result;
    public OKResult(Object result) {
        this.result = result;
    }
    @Override
    public Status getStatus() {
        return Status.OK;
    }

    public Object getResult() {
        return  result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append("{result=").append(result);
        sb.append('}');
        return sb.toString();
    }
}
