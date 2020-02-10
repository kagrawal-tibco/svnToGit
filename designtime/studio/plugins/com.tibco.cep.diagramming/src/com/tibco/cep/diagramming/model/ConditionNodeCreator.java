package com.tibco.cep.diagramming.model;

import com.tibco.cep.diagramming.ui.ConditionNodeUI;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.builder.TSNodeBuilder;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEObjectUI;

public class ConditionNodeCreator extends TSNodeBuilder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TSENode addNode(TSEGraph graph)
	{
		ConditionNodeUI ui = new ConditionNodeUI();
		TSENode node = super.addNode(graph);
		node.setName("\nCondition");
		node.setSize(60, 30);		
		node.setUI((TSEObjectUI) ui);			

		return node;
	}	
}
