package com.tibco.cep.bpmn.ui.graph.model.factory.gateway;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.ui.Messages;
import com.tibco.cep.bpmn.ui.editor.BpmnLayoutManager;
import com.tibco.cep.bpmn.ui.graph.model.factory.ui.ExclusiveGatewayNodeUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEObjectUI;

public class ExclusiveGatewayNodeUIFactory extends AbstractGatewayNodeUIFactory {

	private static final long serialVersionUID = -5512407804662078720L;

	public ExclusiveGatewayNodeUIFactory(String name,String referredBEResource, String toolId,
			BpmnLayoutManager layoutManager) {
		super(name, referredBEResource, toolId, layoutManager, BpmnModelClass.EXCLUSIVE_GATEWAY);
		if(getNodeName() == null) {
			setNodeName(Messages.getString("title.exclusive")); //$NON-NLS-1$
		}
	}
	
	@Override
	public TSEObjectUI initGraphUI(Object ...args) {
		return new ExclusiveGatewayNodeUI();
	}	

}
