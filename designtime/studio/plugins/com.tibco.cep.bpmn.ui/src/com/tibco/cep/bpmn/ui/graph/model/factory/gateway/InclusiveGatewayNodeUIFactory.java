package com.tibco.cep.bpmn.ui.graph.model.factory.gateway;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.ui.Messages;
import com.tibco.cep.bpmn.ui.editor.BpmnLayoutManager;
import com.tibco.cep.bpmn.ui.graph.model.factory.ui.InclusiveGatewayNodeUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEObjectUI;

public class InclusiveGatewayNodeUIFactory extends AbstractGatewayNodeUIFactory {

	private static final long serialVersionUID = -5668341728627022464L;

	public InclusiveGatewayNodeUIFactory(String name,String referredBEResource,  String toolId,
			BpmnLayoutManager layoutManager) {
		super(name,  referredBEResource,  toolId, layoutManager, BpmnModelClass.INCLUSIVE_GATEWAY);
		if(getNodeName() == null) {
			setNodeName(Messages.getString("title.inclusive"));//$NON-NLS-1$
		}
	}
	
	@Override
	public TSEObjectUI initGraphUI(Object ...args) {
		return new InclusiveGatewayNodeUI();
	}	

}
