/**
 * 
 */
package com.tibco.cep.liveview.agent;

import java.util.Map;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.liveview.as.FilteredListenerConfigurator;
import com.tibco.cep.liveview.as.TupleContent;
import com.tibco.cep.liveview.client.LVClient;
import com.tibco.cep.runtime.service.cluster.agent.AgentConfiguration;
import com.tibco.cep.runtime.service.cluster.agent.NewInferenceAgent;
import com.tibco.cep.runtime.session.RuleServiceProvider;

/**
 * @author vpatil
 *
 */
public class LiveViewAgent extends NewInferenceAgent {
	
	private PublisherExecutor publisherExecutor;
	private LVClient lvClient;
	
	public LiveViewAgent(AgentConfiguration agentConfig, RuleServiceProvider rsp, Type type) throws Exception {
		super(agentConfig, rsp, type);
		
		publisherExecutor = new PublisherExecutor(rsp, (LiveViewAgentConfiguration) agentConfig);
		lvClient = new LVClient((LiveViewAgentConfiguration) agentConfig, rsp);
	}
	
	@Override
	protected void onInit() throws Exception {
		super.onInit();
		
		logger.log(Level.INFO, String.format("Initializing LiveViewAgent:%s", this.name));

		publisherExecutor.init();
		
		lvClient.intialize();
		
		logger.log(Level.INFO, String.format("Initialized LiveViewAgent:%s", this.name));
	}
	
	@Override
	protected void onActivate(boolean reactivate) throws Exception {
		super.onActivate(reactivate);
		
		publisherExecutor.start();
		
		Map<String, String> entityToFilterMap = ((LiveViewAgentConfiguration) agentConfig).getEntitySet();
		FilteredListenerConfigurator.configureSpaceListener(entityToFilterMap, cluster.getDaoProvider(), this);
		
		this.logger.log(Level.INFO, "Agent %s - %s: Activated", this.getAgentName(), this.getAgentId());
	}
	
	@Override
	protected void onShutdown() throws Exception {
		super.onShutdown();
		
		lvClient.shutdown();
		publisherExecutor.shutdown();
		
		this.logger.log(Level.INFO, "Agent %s - %s: Shutdown", this.getAgentName(), this.getAgentId());
	}
	
	
	public void submitPublisherTask(TupleContent tupleContent) {
		publisherExecutor.submitTask(createPublisherTask(tupleContent));
	}
	
	public LVClient getLVClient() {
		return lvClient;
	}
	
	private PublisherTask createPublisherTask(TupleContent tupleContent) {
		PublisherTask pTask = new PublisherTask(tupleContent);
		pTask.setLiveViewAgent(this);
		return pTask;
	}
}
