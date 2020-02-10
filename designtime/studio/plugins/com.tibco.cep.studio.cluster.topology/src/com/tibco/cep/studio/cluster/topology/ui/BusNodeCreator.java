package com.tibco.cep.studio.cluster.topology.ui;

import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSENodeLabel;
import com.tomsawyer.graphicaldrawing.builder.TSNodeBuilder;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEAnnotatedUI;

public class BusNodeCreator extends TSNodeBuilder {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see com.tomsawyer.graphicaldrawing.builder.TSNodeBuilder#addNode(com.tomsawyer.graphicaldrawing.TSEGraph)
	 */
	public TSENode addNode(TSEGraph graph) {
	
		BusNodeUI ui = new BusNodeUI();
		TSENode node = super.addNode(graph);
		TSENodeLabel nodeLabel = (TSENodeLabel) node.addLabel();
		((TSEAnnotatedUI)nodeLabel.getUI()).setTextAntiAliasingEnabled(true);
		nodeLabel.setName("Cluster");
		node.setUI(ui);
		node.setWidth(300.0);
		return node;
	}
	
}
