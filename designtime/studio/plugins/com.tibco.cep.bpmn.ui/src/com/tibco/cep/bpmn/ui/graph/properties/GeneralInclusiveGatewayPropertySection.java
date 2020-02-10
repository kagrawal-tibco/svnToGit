package com.tibco.cep.bpmn.ui.graph.properties;




/**
 * 
 * @author majha
 *
 */
public class GeneralInclusiveGatewayPropertySection extends GeneralExclusiveGatewayPropertySection {


	public GeneralInclusiveGatewayPropertySection() {
		super();
	}
	
	protected boolean isJoinRulefuntionApplicable(){
		return true;
	}
	
	protected boolean isForkRulefuntionApplicable(){
		return true;
	}
	
	@Override
	protected boolean isMergeFuntionApplicable() {
		// TODO Auto-generated method stub
		return false;
	}
	
	protected boolean isOutgoingSequenceOrderApplicable() {
		// TODO Auto-generated method stub
		return true;
	}

	
}