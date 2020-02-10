package com.tibco.cep.bpmn.ui.graph.properties;


/**
 * 
 * @author majha
 *
 */
public class GeneralScriptTaskPropertySection extends
		GeneralTaskPropertySection {

	public GeneralScriptTaskPropertySection() {
		super();
	}

	@Override
	protected boolean isScriptTask() {
		return true;
	}
	
	@Override
	protected boolean isResourcePropertyVisible() {
		// TODO Auto-generated method stub
		return false;
	}
}