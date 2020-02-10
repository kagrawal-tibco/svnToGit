package com.tibco.cep.diagramming.model;

import com.tibco.cep.diagramming.ui.ChildGraphNodeUI;
import com.tibco.cep.diagramming.ui.FinalStateNodeUI;
import com.tibco.cep.diagramming.ui.InitialNodeUI;
import com.tomsawyer.drawing.geometry.shared.TSOvalShape;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSEGraphManager;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSENodeLabel;
import com.tomsawyer.graphicaldrawing.builder.TSNodeBuilder;
import com.tomsawyer.graphicaldrawing.complexity.TSENestingManager;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEObjectUI;

public class CompositeNodeCreator extends TSNodeBuilder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TSEGraph compositeGraph;
	private TSEGraphManager graphManager;

	public TSENode addNode(TSEGraph graph) {

		ChildGraphNodeUI compositeUI = new ChildGraphNodeUI();
		TSENode compositeNode = super.addNode(graph);
		graphManager = (TSEGraphManager) graph.getOwnerGraphManager();
		compositeGraph = (TSEGraph) graphManager.addGraph();

		InitialNodeUI initUI = new InitialNodeUI();
		TSENode childStartNode = (TSENode) compositeGraph.addNode();
		childStartNode.setUI((TSEObjectUI) initUI);
		childStartNode.setSize(childStartNode.getWidth() * 0.65, childStartNode
				.getHeight() * 0.65);
		childStartNode.setShape(TSOvalShape.getInstance());
		childStartNode.setCenter(10, 10);
		childStartNode.setName("Start");
		((TSENodeLabel) childStartNode.addLabel()).setName("Start");
		childStartNode.setUI((TSEObjectUI) initUI);

		FinalStateNodeUI finalUI = new FinalStateNodeUI();
		TSENode childEndNode = (TSENode) compositeGraph.addNode();
		childEndNode.setShape(TSOvalShape.getInstance());
		childEndNode.setName("End");
		((TSENodeLabel) childEndNode.addLabel()).setName("End");
		childEndNode.setCenter(10, -40);
		childEndNode.setUI((TSEObjectUI) finalUI);

		compositeNode.setChildGraph(compositeGraph);
		TSENestingManager.expand(compositeNode);
		compositeNode.setName("Composite");
		compositeNode.setSize(60, 30);
		compositeNode.setUI((TSEObjectUI) compositeUI);

		return compositeNode;
	}
}
