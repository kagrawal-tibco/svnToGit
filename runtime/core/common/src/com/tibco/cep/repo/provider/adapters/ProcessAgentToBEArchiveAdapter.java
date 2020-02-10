package com.tibco.cep.repo.provider.adapters;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.util.EList;

import com.tibco.be.util.config.cdd.AgentClassConfig;
import com.tibco.be.util.config.cdd.ClusterConfig;
import com.tibco.be.util.config.cdd.DestinationConfig;
import com.tibco.be.util.config.cdd.DestinationsConfig;
import com.tibco.be.util.config.cdd.ProcessAgentClassConfig;
import com.tibco.be.util.config.cdd.ProcessEngineConfig;
import com.tibco.be.util.packaging.Constants;
import com.tibco.cep.repo.ArchiveInputDestinationConfig;

public class ProcessAgentToBEArchiveAdapter extends
		AbstractAgentToBEArchiveAdapter  {

	public ProcessAgentToBEArchiveAdapter(ClusterConfig config,
			AgentClassConfig agentClassConfig, String agentKey) {
		super(config, agentClassConfig, agentKey);
	}
	
	
	public Set<ArchiveInputDestinationConfig> getInputDestinations() {
		Set<ArchiveInputDestinationConfig> adestinations = new HashSet<ArchiveInputDestinationConfig>();
		EList<ProcessEngineConfig> engines = this.getProcessAgentGroupConfig().getProcessEngine();

		for (ProcessEngineConfig engine:engines) {
			DestinationsConfig destinations = engine.getDestinations();

			for (DestinationConfig dest:destinations.getAllDestinations()) {
				adestinations.add(this.getInputDestination(dest));
			}
		}
		return adestinations;
	}
	 
	 protected ProcessAgentClassConfig getProcessAgentGroupConfig() {
		 return (ProcessAgentClassConfig) this.agentClassConfig;
	 }

    public Constants.ArchiveType getType() {
        return Constants.ArchiveType.PROCESSGRAPH;
    }

}
