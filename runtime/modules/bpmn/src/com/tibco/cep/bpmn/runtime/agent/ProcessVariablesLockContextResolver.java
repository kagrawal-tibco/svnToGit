package com.tibco.cep.bpmn.runtime.agent;

import java.lang.Thread.State;

import com.tibco.cep.bpmn.runtime.activity.Activity;
import com.tibco.cep.bpmn.runtime.activity.Task;
import com.tibco.cep.bpmn.runtime.activity.tasks.InferenceTask;
import com.tibco.cep.bpmn.runtime.activity.tasks.LoopTask;
import com.tibco.cep.bpmn.runtime.activity.tasks.ReceiveTask;
import com.tibco.cep.bpmn.runtime.model.JobContext;
import com.tibco.cep.runtime.session.impl.locks.LockContextResolver;

public class ProcessVariablesLockContextResolver extends LockContextResolver<Object>{

	@Override
	public Object getContext() {
		Job ctx = JobImpl.getCurrentJob();
		
        JobContext pv = ctx != null ? ctx.getJobContext() : null;
        if(pv != null){
        	Activity currentTask = ctx.getCurrentTask();
    		if(currentTask != null && (currentTask instanceof Task)){
    			Task temp = (Task)currentTask;
    			if(currentTask instanceof LoopTask){
    				LoopTask t = (LoopTask)currentTask;
    				temp = t.getLoopTask();
    			}
    			if(temp instanceof InferenceTask){
    				Object lockContext = temp.getLockContext(pv);
    				if(lockContext != null) {
    					System.err.println(String.format("Thread:[%s] GetContext:[%s]",Thread.currentThread().getName(),lockContext));
    					return lockContext;
    				}
    			} else if(temp instanceof ReceiveTask) {
    				return Thread.currentThread();
    			}
    			
    		}
        }
        
		while(pv != null) {
			JobContext x = (JobContext) pv.getParent();
            if(x != null){
                pv = x;
            }else{
                return pv;
            }
		}

		return Thread.currentThread();
	}

	@Override
	public String getName(Object context) {
		return (context instanceof JobContext)
                ? ((JobContext)context).getProcessTemplate().getProcessName().getSimpleName()
                : context.toString();
	}

	@Override
	public StackTraceElement[] getStackTrace(Object context) {
		return null;
	}

	@Override
	public State getThreadState(Object context) {
		return null;
	}
}
