package com.tibco.cep.diagramming.model;

import com.tibco.cep.diagramming.ui.SubProcessNodeUI;
import com.tibco.cep.diagramming.utils.ActivityTypes;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSEGraphManager;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.builder.TSNodeBuilder;
import com.tomsawyer.graphicaldrawing.complexity.TSENestingManager;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEObjectUI;


public class SubProcessNodeCreator extends TSNodeBuilder {
	
	private static final long serialVersionUID = 1L;
	private TSEGraph childGraph;
	private TSEGraphManager graphManager;
	private String nodeName;
	
	public SubProcessNodeCreator(String name, ActivityTypes activityType) {
		this.nodeName = name;		
	}
	
	public TSENode addNode(TSEGraph graph) {

		SubProcessNodeUI compositeUI = new SubProcessNodeUI();
		TSENode compositeNode = super.addNode(graph);
		graphManager = (TSEGraphManager) graph.getOwnerGraphManager();
		childGraph = (TSEGraph) graphManager.addGraph();

		compositeNode.setChildGraph(childGraph);
		TSENestingManager.expand(compositeNode);
		compositeNode.setName(nodeName);
		compositeNode.setSize(60,40);
		compositeNode.setUI((TSEObjectUI) compositeUI);

		return compositeNode;
	}
}
