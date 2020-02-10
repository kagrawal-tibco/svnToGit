package com.tibco.cep.bpmn.runtime.activity.results;


import java.util.List;

import com.tibco.cep.bpmn.runtime.activity.TaskResult;
import com.tibco.cep.bpmn.runtime.agent.Job;

/*
* Author: Suresh Subramani / Date: 2/8/12 / Time: 8:06 PM
*/
public class ForkResult implements TaskResult {


    private List<Job> processes;

    public ForkResult(List<Job> processes) {
        this.processes = processes;
    }
    @Override
    public Status getStatus() {
        return Status.FORK;
    }

    @Override
    public Object getResult() {
        return processes;
    }

    public List<Job> getForkedProcess() {
        return processes;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append("{processes=").append(processes);
        sb.append('}');
        return sb.toString();
    }
}
