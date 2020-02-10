package com.tibco.cep.bpmn.ui.graph.properties;


/**
 * 
 * @author majha
 *
 */
public class GeneralSendTaskPropertySection extends
		GeneralTaskPropertySection {

	public GeneralSendTaskPropertySection() {
		super();
	}

	@Override
	protected boolean isDestinationPropertyVisible() {
		return false;
	}
}