package com.tibco.tea.agent.be.provider;

import com.tibco.tea.agent.api.TeaConcept;
import com.tibco.tea.agent.be.BEApplicationHost;

/**
 * This class is the provider for BEApplicationHost.
 * 
 * @author dijadhav
 *
 */
public class BEApplicationHostProvider extends ObjectProvider<BEApplicationHost> {

	@Override
	public TeaConcept getConcept() {
		return TeaConcept.GROUP;
	}

	@Override
	public BEApplicationHost getInstance(String key) {
		return (BEApplicationHost) teaObjectMap.get(key);
	}

	@Override
	public String getTypeDescription() {
		return "BusinessEvents Application Host";
	}

	@Override
	public String getTypeName() {
		return "Host";
	}

}
