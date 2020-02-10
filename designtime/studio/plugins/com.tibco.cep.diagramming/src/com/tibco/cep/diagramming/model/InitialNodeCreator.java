package com.tibco.cep.diagramming.model;

import com.tibco.cep.diagramming.ui.InitialNodeUI;
import com.tibco.cep.diagramming.utils.EVENT_TYPE;
import com.tibco.cep.diagramming.utils.TSConstants;
import com.tomsawyer.drawing.geometry.shared.TSOvalShape;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSENodeLabel;
import com.tomsawyer.graphicaldrawing.builder.TSNodeBuilder;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEObjectUI;

/**
 * 
 * @author hitesh
 *
 */

public class InitialNodeCreator extends TSNodeBuilder {

	private static final long serialVersionUID = 1L;

	public TSENode addNode(TSEGraph graph) {

		InitialNodeUI ui = new InitialNodeUI();
		
		TSENode node = super.addNode(graph);
		node.setName("Start");
		node.setTooltipText("Start Node");
		((TSENodeLabel) node.addLabel()).setName("Start");
		node.setAttribute(TSConstants.TYPE_ATTR, EVENT_TYPE.START);
		node.setSize(node.getWidth() * 0.65, node.getHeight() * 0.65);
		node.setShape(TSOvalShape.getInstance());
		node.setUI((TSEObjectUI) ui);

		return node;
	}

}
