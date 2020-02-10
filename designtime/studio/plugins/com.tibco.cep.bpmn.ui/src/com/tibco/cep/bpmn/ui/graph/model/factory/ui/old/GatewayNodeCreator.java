package com.tibco.cep.bpmn.ui.graph.model.factory.ui.old;


import com.tibco.cep.bpmn.ui.graph.model.factory.ui.InclusiveGatewayNodeUI;
import com.tibco.cep.diagramming.utils.GatewayType;
import com.tibco.cep.diagramming.utils.TSConstants;
import com.tomsawyer.drawing.geometry.shared.TSPolygonShape;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSESolidObject;
import com.tomsawyer.graphicaldrawing.builder.TSNodeBuilder;

/**
 * 
 * @author ggrigore
 *
 */
public class GatewayNodeCreator extends TSNodeBuilder {
	
	private static final long serialVersionUID = 1L;
	private String nodeName;
	private GatewayType gatewayType;
	
	public GatewayNodeCreator(String name, final GatewayType gatewayType) {
		this.nodeName = name;
		this.gatewayType = gatewayType;
	}

	public TSENode addNode(TSEGraph graph) {
		TSENode node = super.addNode(graph);
		decorateGatewayNode(node, this.nodeName, this.gatewayType);
		return node;
	}	
	
	public static void decorateGatewayNode(TSENode node, String name, GatewayType gatewayType) {
		InclusiveGatewayNodeUI ui = new InclusiveGatewayNodeUI();
		node.setShape(TSPolygonShape.fromString(
			"[ 4 (50, 0) (100, 50) (50, 100) (0, 50) ]"));
		node.setName(name);
		node.setAttribute(TSConstants.DECISION_NODE_TYPE_ATTR, gatewayType);
		node.setResizability(TSESolidObject.RESIZABILITY_LOCKED);
		node.setSize(60, 50);	
		node.setUI(ui);			
	}	
}
