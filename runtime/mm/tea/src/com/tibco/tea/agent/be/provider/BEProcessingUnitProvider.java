package com.tibco.tea.agent.be.provider;

import com.tibco.tea.agent.api.TeaConcept;
import com.tibco.tea.agent.be.BEProcessingUnit;

/**
 * This class is the provider for BEProcessingUnit
 * 
 * @author dijadhav
 *
 */
public class BEProcessingUnitProvider extends ObjectProvider<BEProcessingUnit> {
	@Override
	public TeaConcept getConcept() {
		return TeaConcept.GROUP;
	}

	@Override
	public BEProcessingUnit getInstance(String key) {
		return (BEProcessingUnit) teaObjectMap.get(key);
	}

	@Override
	public String getTypeDescription() {
		return "Agent class in proicessing unit.";
	}

	@Override
	public String getTypeName() {
		return "ProcessingUnit";
	}

}
