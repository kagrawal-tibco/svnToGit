package com.tibco.tea.agent.be.provider;

import com.tibco.tea.agent.api.TeaConcept;
import com.tibco.tea.agent.be.BEApplication;

/**
 * This class is the provider for BEApplication.
 * 
 * @author dijadhav
 *
 */
public class BEApplicationProvider extends ObjectProvider<BEApplication> {

	@Override
	public TeaConcept getConcept() {
		return TeaConcept.GROUP;
	}

	@Override
	public BEApplication getInstance(String key) {
		return (BEApplication) teaObjectMap.get(key);
	}

	@Override
	public String getTypeDescription() {
		return "BusinessEvents Application";
	}

	@Override
	public String getTypeName() {
		return "Application";
	}

}
