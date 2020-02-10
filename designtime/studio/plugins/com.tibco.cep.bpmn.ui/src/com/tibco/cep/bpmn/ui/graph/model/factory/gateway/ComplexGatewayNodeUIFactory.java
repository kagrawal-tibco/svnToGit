package com.tibco.cep.bpmn.ui.graph.model.factory.gateway;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.ui.Messages;
import com.tibco.cep.bpmn.ui.editor.BpmnLayoutManager;
import com.tibco.cep.bpmn.ui.graph.model.factory.ui.ComplexGatewayNodeUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEObjectUI;

public class ComplexGatewayNodeUIFactory extends AbstractGatewayNodeUIFactory {

	private static final long serialVersionUID = 402989662099279093L;

	public ComplexGatewayNodeUIFactory(String name, String referredBEResource, String toolId,
			BpmnLayoutManager layoutManager) {
		super(name, referredBEResource, toolId, layoutManager,BpmnModelClass.COMPLEX_GATEWAY);
		if(getNodeName() == null) {
			setNodeName(Messages.getString("title.complex"));//$NON-NLS-1$
		}
	}
	
	@Override
	public TSEObjectUI initGraphUI(Object ...args) {
		return new ComplexGatewayNodeUI();
	}	

}
