package com.tibco.cep.bpmn.runtime.agent;

import com.tibco.cep.bpmn.runtime.config.ProcessAgentConfiguration;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.util.CustomThreadPoolExecutor;
import com.tibco.cep.runtime.util.FQName;

/*
* Author: Suresh Subramani / Date: 12/14/11 / Time: 6:34 PM
*/
public class DefaultProcessExecutor implements ProcessAgent.ProcessExecutor {

    CustomThreadPoolExecutor threadPoolExecutor;
    RuleServiceProvider rsp;
	private ProcessAgentConfiguration config;
	


    public DefaultProcessExecutor(RuleServiceProvider rsp, ProcessAgentConfiguration config)
    {
    	this.rsp = rsp;
    	this.config = config;
    }

    public void init() throws Exception {
        //TODO - nos of threads and Queue
    	int qs = config.getJobManagerQueueSize();
    	int tc = config.getJobManagerThreadCount();
    	
    	
        threadPoolExecutor = CustomThreadPoolExecutor.create("be.bpmn.process", tc, qs, this.rsp); //This is infinite Queue size
        threadPoolExecutor.setName(new FQName("be.bpmn.process.executor"));
    }

    public void start() throws Exception {
          threadPoolExecutor.resumeResource();
    }

    @Override
    public void submitJob(Job job) {
        threadPoolExecutor.execute(job);
    }
}
