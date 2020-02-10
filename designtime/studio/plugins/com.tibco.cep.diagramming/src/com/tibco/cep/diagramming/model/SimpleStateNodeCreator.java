package com.tibco.cep.diagramming.model;

import com.tibco.cep.diagramming.ui.RoundRectNodeUI;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.builder.TSNodeBuilder;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEObjectUI;

public class SimpleStateNodeCreator extends TSNodeBuilder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see com.tomsawyer.graphicaldrawing.builder.TSNodeBuilder#addNode(com.tomsawyer.graphicaldrawing.TSEGraph)
	 */
	public TSENode addNode(TSEGraph graph) {
		RoundRectNodeUI ui = new RoundRectNodeUI();
		TSENode node = super.addNode(graph);
		node.setName("SimpleState");
		node.setTooltipText("Simple State");
		node.setSize(60, 30);
		node.setUI((TSEObjectUI) ui);

		return node;
	}

}