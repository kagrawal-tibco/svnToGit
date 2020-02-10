package com.tibco.cep.studio.cluster.topology.model.impl;

import java.util.List;

import com.tibco.cep.studio.cluster.topology.model.ProcessingUnitConfig;
import com.tibco.cep.studio.cluster.topology.model.ProcessingUnitsConfig;



public class ProcessingUnitsConfigImpl extends ClusterTopology{

	public ProcessingUnitsConfigImpl(ProcessingUnitsConfig processingUnitsConfig){
		this.processingUnitsConfig = processingUnitsConfig;
	}

	public List<ProcessingUnitConfig> getProcessingUnitConfig() {
		return processingUnitsConfig.getProcessingUnitConfig();
	}
	
	public void addProcessingUnitConfig(ProcessingUnitConfigImpl processingUnitConfig){
		getProcessingUnitConfig().add(processingUnitConfig.getProcessingUnitConfig());
		notifyObservers();
	}

	public void addProcessingUnitConfig(int index, ProcessingUnitConfigImpl processingUnitConfig){
		getProcessingUnitConfig().add(index, processingUnitConfig.getProcessingUnitConfig());
		notifyObservers();
	}

	public void removeProcessingUnitConfig(ProcessingUnitConfigImpl processingUnitConfig){
		getProcessingUnitConfig().remove(processingUnitConfig.getProcessingUnitConfig());
		notifyObservers();
	}

	public void removeProcessingUnitConfig(int index){
		getProcessingUnitConfig().remove(index);
		notifyObservers();
	}

	public ProcessingUnitsConfig getProcessingUnitsConfig() {
		return processingUnitsConfig;
	}
}