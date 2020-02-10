package com.tibco.tea.agent.be.provider;

import com.tibco.tea.agent.api.TeaConcept;
import com.tibco.tea.agent.be.BEServiceInstance;

/**
 * This class is the provider for BESiteTopology
 * 
 * @author dijadhav
 *
 */
public class BEServiceInstanceProvider extends ObjectProvider<BEServiceInstance> {

	@Override
	public TeaConcept getConcept() {
		return TeaConcept.GROUP;
	}

	@Override
	public BEServiceInstance getInstance(String key) {
		return (BEServiceInstance) teaObjectMap.get(key);
	}

	@Override
	public String getTypeDescription() {
		return "Service Instance(Instance of Processing unit) of Business Events application.";
	}

	@Override
	public String getTypeName() {
		return "ServiceInstance";
	}
}
