package com.tibco.tea.agent.be.provider;

import com.tibco.tea.agent.api.TeaConcept;
import com.tibco.tea.agent.be.BEApplicationHost;
import com.tibco.tea.agent.be.BEMasterHost;

/**
 * This class is the provider for BEMasterHost.
 * 
 * @author dijadhav
 *
 */
public class BEMasterHostProvider extends ObjectProvider<BEMasterHost> {

	@Override
	public TeaConcept getConcept() {
		return TeaConcept.GROUP;
	}

	@Override
	public BEMasterHost getInstance(String key) {
		return (BEMasterHost) teaObjectMap.get(key);
	}

	@Override
	public String getTypeDescription() {
		return "BusinessEvents Master Host";
	}

	@Override
	public String getTypeName() {
		return "MasterHost";
	}

}
