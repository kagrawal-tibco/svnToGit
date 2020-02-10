package com.tibco.tea.agent.be.provider;

import com.tibco.tea.agent.api.TeaConcept;
import com.tibco.tea.agent.be.BEProcessingUnitAgent;

/**
 * This class is the provider for BEProcessingUnitAgent
 * 
 * @author dijadhav
 *
 */
public class BEProcessingUnitAgentProvider extends ObjectProvider<BEProcessingUnitAgent> {
	@Override
	public TeaConcept getConcept() {
		return TeaConcept.GROUP;
	}

	@Override
	public BEProcessingUnitAgent getInstance(String key) {
		return (BEProcessingUnitAgent) teaObjectMap.get(key);
	}

	@Override
	public String getTypeDescription() {
		return "Agent class in proicessing unit.";
	}

	@Override
	public String getTypeName() {
		return "ProcessingUnitAgent";
	}

}
