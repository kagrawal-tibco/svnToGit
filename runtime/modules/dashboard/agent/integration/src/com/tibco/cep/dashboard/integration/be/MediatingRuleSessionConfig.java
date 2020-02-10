package com.tibco.cep.dashboard.integration.be;

import java.util.Properties;
import java.util.Set;

import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.kernel.model.rule.RuleFunction;
import com.tibco.cep.repo.ArchiveInputDestinationConfig;
import com.tibco.cep.runtime.session.RuleSessionConfig;

class MediatingRuleSessionConfig implements RuleSessionConfig {

	private RuleSessionConfig originalRuleSessionConfig;

	private RuleSessionConfig.InputDestinationConfig[] modifiedInputDestinationConfigs;

	MediatingRuleSessionConfig(RuleSessionConfig originalRuleSessionConfig, ArchiveInputDestinationConfig[] destinationConfigs) {
		this.originalRuleSessionConfig = originalRuleSessionConfig;
		RuleSessionConfig.InputDestinationConfig[] inputDestinations = this.originalRuleSessionConfig.getInputDestinations();
		modifiedInputDestinationConfigs = new RuleSessionConfig.InputDestinationConfig[inputDestinations.length + destinationConfigs.length];
		System.arraycopy(inputDestinations, 0, modifiedInputDestinationConfigs, 0, inputDestinations.length);
		int i = inputDestinations.length;
		for (ArchiveInputDestinationConfig destinationConfig : destinationConfigs) {
			String destinationURI = destinationConfig.getDestinationURI();
			ThreadingModel threadingModel = ThreadingModel.getByLiteral(destinationConfig.getThreadingModel());
			int workerThreadCnt = destinationConfig.getNumWorker();
			int queueSize = destinationConfig.getQueueSize();
			int weight = destinationConfig.getWeight();
			RuleSessionConfig.InputDestinationConfig inputDestinationConfig = new InputDestinationConfigImpl(destinationURI, threadingModel, workerThreadCnt, queueSize, weight);
			modifiedInputDestinationConfigs[i] = inputDestinationConfig;
			i++;
		}
	}

	@Override
	public boolean deleteStateTimeoutOnStateExit() {
		return originalRuleSessionConfig.deleteStateTimeoutOnStateExit();
	}

	@Override
	public Properties getCacheConfig() {
		return originalRuleSessionConfig.getCacheConfig();
	}

	@Override
	public Set<String> getDeployedRuleUris() {
		return originalRuleSessionConfig.getDeployedRuleUris();
	}

	@Override
	public InputDestinationConfig[] getInputDestinations() {
		return modifiedInputDestinationConfigs;
	}

	@Override
	public String getName() {
		return originalRuleSessionConfig.getName();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class getShutdownClass() {
		return originalRuleSessionConfig.getShutdownClass();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class getStartupClass() {
		return originalRuleSessionConfig.getStartupClass();
	}

	@Override
	public boolean removeRefOnDeleteNonRepeatingTimeEvent() {
		return originalRuleSessionConfig.removeRefOnDeleteNonRepeatingTimeEvent();
	}

	@Override
	public boolean allowEventModificationInRTC() {
		return originalRuleSessionConfig.allowEventModificationInRTC();
	}

	
	private class InputDestinationConfigImpl implements RuleSessionConfig.InputDestinationConfig {

		String destURI;
//		Event filterEvent;
//		RuleFunction preProcessor;
		int numWorker;
		int queueSize;
		int weight;

		RuleSessionConfig.ThreadingModel threadingModel;

		public InputDestinationConfigImpl(String destURI, RuleSessionConfig.ThreadingModel threadingModel, int numWorker, int queueSize, int weight) {
			this.destURI = destURI;
			this.threadingModel = threadingModel;
			this.numWorker = numWorker;
			this.queueSize = queueSize;
			this.weight = weight;
		}

		public String getURI() {
			return destURI;
		}

		public Event getFilter() {
			return null;
		}

		public RuleFunction getPreprocessor() {
			return null;
		}

		public int getNumWorker() {
			return numWorker;
		}

		public int getQueueSize() {
			return queueSize;
		}

		public int getWeight() {
			return weight;
		}

		public RuleSessionConfig.ThreadingModel getThreadingModel() {
			return threadingModel;
		}
		
		public RuleFunction getThreadAffinityRuleFunction() {
			return null;
		}

	}
}