package com.tibco.cep.diagramming.model;

import com.tibco.cep.diagramming.ui.SubStateNodeUI;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.builder.TSNodeBuilder;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEObjectUI;

public class SubStateNodeCreator extends TSNodeBuilder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TSENode addNode(TSEGraph graph) {

		TSENode node = super.addNode(graph);
		SubStateNodeUI ui = new SubStateNodeUI();
		node.setSize(40, 40);
		node.setUI((TSEObjectUI) ui);

		return node;
	}
}

