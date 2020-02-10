package com.tibco.cep.bpmn.ui.graph.properties;


/**
 * 
 * @author majha
 *
 */
public class GeneralRuleFunctionTaskPropertySection extends
		GeneralTaskPropertySection {

	public GeneralRuleFunctionTaskPropertySection() {
		super();
	}

	@Override
	protected boolean isScriptTask() {
		return false;
	}
}