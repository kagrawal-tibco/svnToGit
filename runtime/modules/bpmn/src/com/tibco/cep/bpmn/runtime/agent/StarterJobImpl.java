package com.tibco.cep.bpmn.runtime.agent;

import java.util.ArrayList;
import java.util.List;

import com.tibco.cep.bpmn.runtime.activity.Task;
import com.tibco.cep.bpmn.runtime.activity.TaskResult;
import com.tibco.cep.bpmn.runtime.activity.Triggerable;
import com.tibco.cep.bpmn.runtime.templates.ProcessTemplate;
import com.tibco.cep.bpmn.runtime.utils.Variables;
import com.tibco.cep.kernel.model.entity.Event;

/*
* Author: Suresh Subramani / Date: 7/19/12 / Time: 5:51 PM
*/
public class StarterJobImpl extends JobImpl implements StarterJob  {

    public StarterJobImpl(ProcessAgent processAgent, ProcessTemplate template) {
        super(new StarterJobContext(template,processAgent.getRuleServiceProvider().getIdGenerator().nextEntityId()), processAgent);

    }

    @Override
    public void run() {
        throw new RuntimeException("Attempting to start a StarterJobImpl");
    }



    @Override
    public void assertEvent(Task task, Event event)  {

        if (!(task instanceof Triggerable))
            return; //DO NOTHING.
        
		try {
			JobImpl.setCurrentJob(this);
			Triggerable t = (Triggerable) task;

			Variables vars = new Variables();
			vars.setVariable(t.getInputEvent().getName(), event);
			TaskResult result = TaskResult.EMPTY;

			if (t.eval(vars)) {
				setCurrentTask(t);
				result = t.exec(this, vars, null);
				List<Job> jobs = new ArrayList<Job>();
				ProcessResultMode resultMode = super.processTaskResult(task, result, jobs);
				interpretProcessResultMode(resultMode, jobs);

			}
		} finally {
			JobImpl.setCurrentJob(null);
		}

    }
}
