/**
 * 
 */
package com.tibco.cep.liveview.agent;

import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.util.CustomThreadPoolExecutor;
import com.tibco.cep.runtime.util.FQName;

/**
 * @author vpatil
 *
 */
public class PublisherExecutor {
	
	private CustomThreadPoolExecutor threadPoolExecutor;
    private RuleServiceProvider rsp;
    private LiveViewAgentConfiguration lvAgentConf;
    
    public PublisherExecutor(RuleServiceProvider rsp, LiveViewAgentConfiguration lvAgentConf) {
		this.rsp = rsp;
		this.lvAgentConf = lvAgentConf;
	}
    
    public void init() throws Exception {
    	int qs = lvAgentConf.getPublisherQueueSize();
    	int tc = lvAgentConf.getPublisherThreadCount();
    	
        threadPoolExecutor = CustomThreadPoolExecutor.create("be.liveview.publisher", tc, qs, this.rsp); 
        threadPoolExecutor.setName(new FQName("be.liveview.publisher.executor"));
    }
    
    public void start() throws Exception {
    	threadPoolExecutor.resumeResource();
    }

    public void submitTask(PublisherTask task) {
    	threadPoolExecutor.execute(task);
    }
    
    public void shutdown() throws Exception {
    	threadPoolExecutor.shutdown();
    }
}
