package com.tibco.cep.diagramming.model;


import com.tibco.cep.diagramming.ui.DecisionNodeUI;
import com.tibco.cep.diagramming.utils.GatewayType;
import com.tibco.cep.diagramming.utils.TSConstants;
import com.tomsawyer.drawing.geometry.shared.TSPolygonShape;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.builder.TSNodeBuilder;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEObjectUI;

public class DecisionNodeCreator extends TSNodeBuilder {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String nodeName;
	
	private GatewayType gatewayType;
	
	public DecisionNodeCreator(String name,
			                   final GatewayType gatewayType) {
		this.nodeName = name;
		this.gatewayType = gatewayType;
	}

	public TSENode addNode(TSEGraph graph)
	{
		DecisionNodeUI ui = new DecisionNodeUI();
		TSENode node = super.addNode(graph);
		node.setShape(TSPolygonShape.fromString(
			"[ 4 (50, 0) (100, 50) (50, 100) (0, 50) ]"));
		node.setName(nodeName);
		node.setAttribute(TSConstants.DECISION_NODE_TYPE_ATTR, gatewayType);
		node.setAttribute(TSConstants.NODE_TYPE_ATTR, gatewayType.getName());
		node.setSize(60, 50);	
		node.setUI((TSEObjectUI) ui);			

		return node;
	}	

}
