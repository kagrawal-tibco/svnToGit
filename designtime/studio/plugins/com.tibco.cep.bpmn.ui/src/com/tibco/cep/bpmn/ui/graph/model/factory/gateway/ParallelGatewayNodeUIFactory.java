package com.tibco.cep.bpmn.ui.graph.model.factory.gateway;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.ui.Messages;
import com.tibco.cep.bpmn.ui.editor.BpmnLayoutManager;
import com.tibco.cep.bpmn.ui.graph.model.factory.ui.ParallelGatewayNodeUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEObjectUI;

public class ParallelGatewayNodeUIFactory extends AbstractGatewayNodeUIFactory {

	private static final long serialVersionUID = -7214929656659742212L;

	public ParallelGatewayNodeUIFactory(String name, String referredBEResource, String toolId,
			BpmnLayoutManager layoutManager) {
		super(name, referredBEResource, toolId, layoutManager, BpmnModelClass.PARALLEL_GATEWAY);
		if(getNodeName() == null) {
			setNodeName(Messages.getString("title.parallel")); //$NON-NLS-1$
		}
	}

	@Override
	public TSEObjectUI initGraphUI(Object ...args) {
		return new ParallelGatewayNodeUI();
	}
}
