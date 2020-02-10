package com.tibco.cep.diagramming.model;

import com.tibco.cep.diagramming.ui.DecisionNodeUI;
import com.tibco.cep.diagramming.utils.GatewayType;
import com.tibco.cep.diagramming.utils.TSConstants;
import com.tomsawyer.drawing.geometry.shared.TSPolygonShape;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.builder.TSNodeBuilder;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEObjectUI;

/**
 * 
 * @author hitesh
 *
 */

public class GroupNodeCreator extends TSNodeBuilder {

	private static final long serialVersionUID = 1L;
	private String nodeName;
	private GatewayType gatewayType;
	
	public GroupNodeCreator(String name, GatewayType gateWayType) {
		this.nodeName = name;
		this.gatewayType = gateWayType;
	}

	public TSENode addNode(TSEGraph graph)
	{
		TSENode tsNode = super.addNode(graph);
		tsNode.setName(nodeName);
		tsNode.setSize(60, 60);
		tsNode.setAttribute(TSConstants.TYPE_ATTR, gatewayType);
		tsNode.setAttribute(TSConstants.NODE_TYPE_ATTR, gatewayType.getName());
		tsNode.setShape(TSPolygonShape.fromString("[ 8 (25,0) (75,0) (100,25) (100,75) (75,100) (25,100) (0,75) (0,25)]"));
		tsNode.setUI((TSEObjectUI) new DecisionNodeUI());
		return tsNode;
	}
	
}
