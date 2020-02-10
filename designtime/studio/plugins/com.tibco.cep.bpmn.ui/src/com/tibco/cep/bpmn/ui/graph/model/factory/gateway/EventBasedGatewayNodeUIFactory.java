package com.tibco.cep.bpmn.ui.graph.model.factory.gateway;

import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.EVENT_BASED_GATEWAY;

import org.eclipse.emf.ecore.EEnumLiteral;

import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.Messages;
import com.tibco.cep.bpmn.ui.editor.BpmnLayoutManager;
import com.tibco.cep.bpmn.ui.graph.model.factory.ui.EventBasedGatewayNodeUI;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEObjectUI;

public class EventBasedGatewayNodeUIFactory extends AbstractGatewayNodeUIFactory {
	
	private EEnumLiteral eventBasedGatewayType = null;

	private static final long serialVersionUID = 5559032077346841083L;

	public EventBasedGatewayNodeUIFactory(String name, String referredBEResource, String toolId,
			BpmnLayoutManager layoutManager, EEnumLiteral gatewayType) {
		super(name, referredBEResource,  toolId, layoutManager,EVENT_BASED_GATEWAY);
		this.eventBasedGatewayType = gatewayType;
		if(getNodeName() == null) {
			setNodeName(Messages.getString("title.gateway.eventbased"));//$NON-NLS-1$
		}
	}
	
	@Override
	public TSENode addNode(TSEGraph graph) {
		// XYZ
		TSENode node = super.addNode(graph);
		node.setAttribute(BpmnUIConstants.NODE_ATTR_EXT_TYPE, eventBasedGatewayType);
		return node;
	}
	
	@Override
	public TSEObjectUI initGraphUI(Object ...args) {
		return new EventBasedGatewayNodeUI();
	}	
	
	public EEnumLiteral getEventBasedGatewayType() {
		return eventBasedGatewayType;
	}
	
}
