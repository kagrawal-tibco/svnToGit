package com.tibco.cep.bpmn.ui.graph.model.factory.gateway;

import org.eclipse.emf.ecore.EClass;

import com.tibco.cep.bpmn.ui.editor.BpmnLayoutManager;
import com.tibco.cep.bpmn.ui.graph.model.AbstractNodeUIFactory;
import com.tomsawyer.drawing.geometry.shared.TSPolygonShape;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSESolidObject;

public abstract class AbstractGatewayNodeUIFactory extends AbstractNodeUIFactory {

	private static final long serialVersionUID = -3295656949387570999L;
	
	public AbstractGatewayNodeUIFactory(String name,  String referredBEResource, String toolId,
			BpmnLayoutManager layoutManager, EClass nodeType) {
		super(name, referredBEResource, toolId, layoutManager, nodeType);
	}
	

	@Override
	public void decorateNode(TSENode node) {
		node.setShape(TSPolygonShape.fromString(
		"[ 4 (50, 0) (100, 50) (50, 100) (0, 50) ]"));
//		 node.setTag(getNodeName());
		node.setResizability(TSESolidObject.RESIZABILITY_LOCKED);
		node.setSize(50, 50);	
		node.setUI(getNodeUI());
		String name = getNodeName(); 
		name = (name == null? "": name);
//		node.setName(name);
	}

	@Override
	public void layoutNode(TSENode node) {
		// TODO Auto-generated method stub

	}
	
}
