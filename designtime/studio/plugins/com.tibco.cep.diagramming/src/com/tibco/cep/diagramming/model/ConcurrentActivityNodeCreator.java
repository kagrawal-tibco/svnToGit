package com.tibco.cep.diagramming.model;

import com.tibco.cep.diagramming.ui.ChildGraphNodeUI;
import com.tibco.cep.diagramming.ui.ConcurrentNodeUI;
import com.tibco.cep.diagramming.ui.FinalStateNodeUI;
import com.tibco.cep.diagramming.ui.InitialNodeUI;
import com.tomsawyer.drawing.TSGraphTailor;
import com.tomsawyer.drawing.geometry.shared.TSOvalShape;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSEGraphManager;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSENodeLabel;
import com.tomsawyer.graphicaldrawing.builder.TSNodeBuilder;
import com.tomsawyer.graphicaldrawing.complexity.TSENestingManager;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEObjectUI;

public class ConcurrentActivityNodeCreator extends TSNodeBuilder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TSEGraphManager graphManager;
	private TSEGraph concurrentGraph;

	public TSENode addNode(TSEGraph graph) {
		ConcurrentNodeUI concurentUI = new ConcurrentNodeUI();
		TSENode concurrentNode = super.addNode(graph);
		graphManager = (TSEGraphManager) graph.getOwnerGraphManager();
		concurrentGraph = (TSEGraph) graphManager.addGraph();

		TSENode childCompositeNode1 = (TSENode) concurrentGraph.addNode();
		TSENode childCompositeNode2 = (TSENode) concurrentGraph.addNode();
		createCompositeStateForConcurrent(childCompositeNode1, 1);
		createCompositeStateForConcurrent(childCompositeNode2, 2);
		childCompositeNode1.setCenter(10, 10);
		childCompositeNode2.setCenter(10, -100);
		concurrentNode.setChildGraph(concurrentGraph);
		TSENestingManager.expand(concurrentNode);
		concurrentNode.setName("Concurrent");
		concurrentNode.setSize(60, 40);
		concurrentNode.setUI((TSEObjectUI) concurentUI);

		return concurrentNode;
	}

	private TSEGraph createCompositeStateForConcurrent(TSENode parent, int i) {

		TSEGraph childCompositeGraph = (TSEGraph) graphManager.addGraph();

		TSGraphTailor tailor = childCompositeGraph.getTailor();
		tailor.setMargin(3.0);
		tailor.setNestedViewSpacing(3.0);

		InitialNodeUI initUI = new InitialNodeUI();
		FinalStateNodeUI finalUI = new FinalStateNodeUI();
		ChildGraphNodeUI compositeUI = new ChildGraphNodeUI();

		TSENode childStartNode = (TSENode) childCompositeGraph.addNode();
		childStartNode.setUI((TSEObjectUI) initUI);
		childStartNode.setSize(childStartNode.getWidth() * 0.65, childStartNode
				.getHeight() * 0.65);
		childStartNode.setShape(TSOvalShape.getInstance());
		childStartNode.setCenter(10, 10);
		childStartNode.setName("Start");
		((TSENodeLabel) childStartNode.addLabel()).setName("Start");
		childStartNode.setUI((TSEObjectUI) initUI);

		TSENode childEndNode = (TSENode) childCompositeGraph.addNode();
		childEndNode.setShape(TSOvalShape.getInstance());
		childEndNode.setName("End");
		((TSENodeLabel) childEndNode.addLabel()).setName("End");
		childEndNode.setCenter(150, 10);
		childEndNode.setUI((TSEObjectUI) finalUI);

		parent.setChildGraph(childCompositeGraph);
		if (i == 1) {
			parent.setName("Region");
		} else {
			parent.setName("Region_" + (i - 1));
		}
		TSENestingManager.expand(parent);
		parent.setUI((TSEObjectUI) compositeUI);

		return childCompositeGraph;

	}
}
