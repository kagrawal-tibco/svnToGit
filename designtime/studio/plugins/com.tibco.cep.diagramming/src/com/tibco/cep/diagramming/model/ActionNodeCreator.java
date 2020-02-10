package com.tibco.cep.diagramming.model;

import com.tibco.cep.diagramming.ui.ActionNodeUI;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.builder.TSNodeBuilder;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEObjectUI;

public class ActionNodeCreator extends TSNodeBuilder {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6116211446265844019L;

	public TSENode addNode(TSEGraph graph)
	{
		ActionNodeUI ui = new ActionNodeUI();
		TSENode node = super.addNode(graph);
		node.setName("\nAction");
		node.setSize(60, 30);		
		node.setUI((TSEObjectUI) ui);			

		return node;
	}	

}
