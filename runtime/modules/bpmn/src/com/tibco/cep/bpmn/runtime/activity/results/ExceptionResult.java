package com.tibco.cep.bpmn.runtime.activity.results;

import com.tibco.cep.bpmn.runtime.activity.TaskResult;

/*
* Author: Suresh Subramani / Date: 2/8/12 / Time: 5:11 PM
*/
public class ExceptionResult implements TaskResult {

    private Throwable throwable;

    public ExceptionResult(Throwable ex) {
        this.throwable = ex;
    }

    public ExceptionResult(String message) {
        this.throwable = new Exception(message);
    }
    @Override
    public Status getStatus() {
        return Status.ERROREXCEPTION;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    @Override
    public Object getResult() {
        return throwable;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append("{throwable=").append(throwable);
        sb.append('}');
        return sb.toString();
    }
}
