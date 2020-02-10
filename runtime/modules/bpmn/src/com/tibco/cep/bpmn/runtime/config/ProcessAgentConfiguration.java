package com.tibco.cep.bpmn.runtime.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.EList;

import com.tibco.be.util.config.CddTools;
import com.tibco.be.util.config.cdd.AgentConfig;
import com.tibco.be.util.config.cdd.DestinationConfig;
import com.tibco.be.util.config.cdd.DestinationsConfig;
import com.tibco.be.util.config.cdd.FunctionsConfig;
import com.tibco.be.util.config.cdd.ProcessAgentClassConfig;
import com.tibco.be.util.config.cdd.ProcessEngineConfig;
import com.tibco.be.util.config.cdd.ProcessesConfig;
import com.tibco.cep.repo.ArchiveInputDestinationConfig;
import com.tibco.cep.repo.impl.BEArchiveResourceImpl.InputDestinationConfigImpl;
import com.tibco.cep.runtime.service.cluster.agent.DefaultAgentConfiguration;
import com.tibco.cep.runtime.session.RuleServiceProvider;

public class ProcessAgentConfiguration extends DefaultAgentConfiguration {


	private AgentConfig agentConfig;



	public ProcessAgentConfiguration(AgentConfig agentConfig, String name, String key,
			RuleServiceProvider rsp) {
		super(name, key, rsp);
		this.agentConfig = agentConfig;
	}
	
	
	
	public Collection<String> getDeployedProcesses() {
		Set<String> processes = new HashSet<String>();
		ProcessAgentClassConfig pacc = (ProcessAgentClassConfig) agentConfig.getRef();
		// multiple process engines 
		for(ProcessEngineConfig pecc :pacc.getProcessEngine()){
			EList<String> procs = pecc.getProcess().getUri();
			processes.addAll(procs);
			EList<ProcessesConfig> ref = pecc.getProcess().getRef();
			for (ProcessesConfig processesConfig : ref) {
				 EList<String> uri = processesConfig.getUri();
				processes.addAll(uri);
			}
		}
		return processes;
	}
	
	public List<String> getStartupProcesses() {
		List<String> processes = new ArrayList<String>();
		ProcessAgentClassConfig pacc = (ProcessAgentClassConfig) agentConfig.getRef();
		
		// multiple process engines 
		for(ProcessEngineConfig pecc :pacc.getProcessEngine()){
			FunctionsConfig supProcs = pecc.getStartup();
			
			processes.addAll(supProcs.getAllUris());
			
		}
		return processes;
	}
	
	public List<String> getShutdownProcesses() {
		List<String> processes = new ArrayList<String>();
		ProcessAgentClassConfig pacc = (ProcessAgentClassConfig) agentConfig.getRef();
		
		// multiple process engines 
		for(ProcessEngineConfig pecc :pacc.getProcessEngine()){
			FunctionsConfig supProcs = pecc.getShutdown();
			
			processes.addAll(supProcs.getAllUris());
			
		}
		return processes;
	}
	
	
	Set<ArchiveInputDestinationConfig> getInputDestinations() {
		Set<ArchiveInputDestinationConfig> adestinations = new HashSet<ArchiveInputDestinationConfig>();
		ProcessAgentClassConfig pacc = (ProcessAgentClassConfig) agentConfig.getRef();
        EList<ProcessEngineConfig> engines = pacc.getProcessEngine();
        for(ProcessEngineConfig engine:engines) {
        	DestinationsConfig destinations = engine.getDestinations();
        	for(DestinationConfig dest:destinations.getDestination()) {
        		adestinations.add(this.getInputDestination(dest));
        	}
        }
        return adestinations;
	}
	
	protected ArchiveInputDestinationConfig getInputDestination(
            DestinationConfig destinationConfig) {
    	//TODO may be we should understand what type of threading model we have to override & set numWorker to -1
    	final String threadCount = CddTools.getValueFromMixed(destinationConfig.getThreadCount());
    	final String queueSize = CddTools.getValueFromMixed(destinationConfig.getQueueSize());
        return new InputDestinationConfigImpl(
                destinationConfig.getUri(),
                destinationConfig.getPreProcessor(),
                destinationConfig.getThreadingModel().getName(),
                destinationConfig.getThreadAffinityRuleFunction(),
                ((null == threadCount) || threadCount.isEmpty()) ? 0 : Integer.parseInt(threadCount),
                ((null == queueSize) || queueSize.isEmpty()) ? 0 : Integer.parseInt(queueSize),
                0);  //todo weight and thread model? 
    }
	
	public int getJobManagerThreadCount() {
		if (agentConfig.getRef() != null && agentConfig.getRef() instanceof ProcessAgentClassConfig) {
			String threadCount = CddTools.getValueFromMixed((((ProcessAgentClassConfig)agentConfig.getRef()).getProcessEngine()).get(0).getJobManager().getJobPoolThreadCount());
			return ((null == threadCount) || threadCount.isEmpty()) ? 8 : Integer.parseInt(threadCount);
		}
		return 8;
	}
	
	public int getJobManagerQueueSize() {
		if (agentConfig.getRef() != null && agentConfig.getRef() instanceof ProcessAgentClassConfig) {
		
			String queueSize = CddTools.getValueFromMixed((((ProcessAgentClassConfig)agentConfig.getRef()).getProcessEngine()).get(0).getJobManager().getJobPoolQueueSize());
			return ((null == queueSize) || queueSize.isEmpty()) ? Integer.MAX_VALUE : Integer.parseInt(queueSize);
		}
		return Integer.MAX_VALUE;
	}



	@Override
	public int getMaxActive() {
		if (agentConfig.getRef() != null && agentConfig.getRef() instanceof ProcessAgentClassConfig) {
			String maxActive = CddTools.getValueFromMixed((((ProcessAgentClassConfig) agentConfig.getRef()).getLoad().getMaxActive()));
			return ((null == maxActive) || maxActive.isEmpty()) ? 1 : Integer.parseInt(maxActive);
		} else
			return super.getMaxActive();
	}
	
	


}
