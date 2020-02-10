package com.tibco.tea.agent.be.provider;

import com.tibco.tea.agent.api.TeaConcept;
import com.tibco.tea.agent.be.BEAgent;

/**
 * This class is the provider for BEAgent
 * 
 * @author dijadhav
 *
 */
public class BEAgentProvider extends ObjectProvider<BEAgent> {
	@Override
	public TeaConcept getConcept() {
		return TeaConcept.GROUP;
	}

	@Override
	public BEAgent getInstance(String key) {
		return (BEAgent) teaObjectMap.get(key);
	}

	@Override
	public String getTypeDescription() {
		return "Agent class in service instance unit.";
	}

	@Override
	public String getTypeName() {
		return "Agent";
	}

}
