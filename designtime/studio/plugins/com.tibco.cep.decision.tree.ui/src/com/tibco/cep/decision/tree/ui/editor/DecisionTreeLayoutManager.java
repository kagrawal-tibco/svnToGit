package com.tibco.cep.decision.tree.ui.editor;

import java.util.ArrayList;

import com.tibco.cep.diagramming.drawing.DiagramManager;
import com.tibco.cep.diagramming.drawing.EntityLayoutManager;
import com.tomsawyer.drawing.TSEdgeLabel;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.interactive.service.TSEAllOptionsServiceInputData;
import com.tomsawyer.service.TSServiceException;
import com.tomsawyer.service.layout.TSHierarchicalLayoutInputTailor;
import com.tomsawyer.service.layout.TSLabelingInputTailor;
import com.tomsawyer.service.layout.TSLayoutConstants;
import com.tomsawyer.service.layout.TSLayoutInputTailor;
import com.tomsawyer.service.layout.TSOrthogonalLayoutInputTailor;
import com.tomsawyer.service.layout.TSRoutingInputTailor;

/*
 @author ssailapp
 @date Sep 14, 2011
 */

public class DecisionTreeLayoutManager extends EntityLayoutManager {

	public DecisionTreeLayoutManager(DiagramManager diagramManager) {
		super(diagramManager);
		initDecisionLayout();
	}

	@SuppressWarnings("unused")
	public void initDecisionLayout() {
		this.inputData = new TSEAllOptionsServiceInputData(this.graphManager);
		layoutInputTailor = new TSLayoutInputTailor(this.inputData);
		layoutInputTailor.setGraphManager(this.graphManager);
		layoutInputTailor.setAsCurrentOperation();

		TSRoutingInputTailor routingTailor = new TSRoutingInputTailor(this.inputData, this.graphManager);
		
		for (Object o : this.graphManager.buildEdges()) {
			/* // TODO - TSV 9.2
			TSEEdge edge = (TSEEdge) o;
			if (((TSENode) edge.getSourceNode()).getUI() instanceof TSSwitchNodeUI) {
				routingTailor.setSourceAttachmentSide(edge, TSLayoutConstants.ATTACHMENT_SIDE_RIGHT);
			}
			if (((TSENode) edge.getTargetNode()).getUI() instanceof TSSwitchNodeUI) {
				routingTailor.setTargetAttachmentSide(edge, TSLayoutConstants.ATTACHMENT_SIDE_RIGHT);
			}
			*/
		}

		/* //TODO - TSV 9.2
		for (Iterator graphIter = this.graphManager.graphs(false).iterator(); graphIter.hasNext();) {
			TSEGraph graph = (TSEGraph) graphIter.next();
			if (graph != this.graphManager.getMainDisplayGraph() && graph.getParent() == null) {
				System.out.println("Faulty graph " + graph.getID());
			} else if (graph != this.graphManager.getMainDisplayGraph()
					&& !(((TSENode) graph.getParent()).getUI() instanceof TSLoopNodeUI)) {
				layoutInputTailor.setFixed(graph, true);
				System.out.println("fix graph");
			} else if (graph == this.graphManager.getMainDisplayGraph()
					|| ((TSENode) graph.getParent()).getUI() instanceof TSLoopNodeUI) {
				TSDecisionLayoutInputTailor decisionTailor = new TSDecisionLayoutInputTailor(this.inputData, graph);
				if (graph.nodes().size() > 0) {
					decisionTailor.setStartNode((TSENode) graph.nodes().get(0));
				}
				for (Object o : graph.nodes()) {
					TSENode node = (TSENode) o;
					Boolean b = (Boolean) node.getAttributeValue(TSDecisionConstants.START_NODE_ATTRIBUTE);
					if (b != null && b.booleanValue() == true) {
						decisionTailor.setStartNode(node);
					}
				}
				decisionTailor.setAsCurrentLayoutStyle();
				decisionTailor.setDisconnectedNodeConstantSpacing(50);
			}
		}
		*/

		// set options for all edges
			
		/*	//TODO - TSV 9.2
		TSDecisionLayoutInputTailor decisionTailor = new TSDecisionLayoutInputTailor(this.inputData);
		for (Object oEdge : graphManager.buildEdges(TSGraphManager.INTERGRAPH_EDGES | TSGraphManager.NORMAL_EDGES)) {
			TSEEdge e = (TSEEdge) oEdge;
			Integer type = (Integer) e.getAttributeValue(TSDecisionLayout.EDGE_ATTRIBUTE_TYPE);
			if (type == null) {
				type = TSDecisionConstants.EDGE_TYPE_NOT_SPECIFIED;
			}
			decisionTailor.setEdgeType(e, type);
		}
		*/
	}

	// This method sets the layout style to Orthogonal.
	@SuppressWarnings("rawtypes")
	public void setOrthogonalLayoutOptions() {
		TSOrthogonalLayoutInputTailor layoutInputTailor = new TSOrthogonalLayoutInputTailor(this.inputData);
		layoutInputTailor.setGraph(this.graphManager.getMainDisplayGraph());
		layoutInputTailor.setAsCurrentLayoutStyle();
		TSLabelingInputTailor labTailor = new TSLabelingInputTailor(this.inputData);
		java.util.List l = this.graphManager.buildEdgeLabels();
		for (Object o : l) {
			TSEdgeLabel lab = (TSEdgeLabel) o;
			labTailor.setAssociation(lab, TSLayoutConstants.EDGE_LABEL_ASSOCIATION_SOURCE);
		}
	}

	// This method sets the layout style to Hierarchical.
	public void setHierarchicalLayoutOptions() {
		TSHierarchicalLayoutInputTailor layoutInputTailor = new TSHierarchicalLayoutInputTailor(this.inputData);
		layoutInputTailor.setGraph(this.graphManager.getMainDisplayGraph());
		layoutInputTailor.setOrthogonalRouting(true);
		layoutInputTailor.setAsCurrentLayoutStyle();
		TSLabelingInputTailor labTailor = new TSLabelingInputTailor(this.inputData);
		@SuppressWarnings("rawtypes")
		java.util.List l = this.graphManager.buildEdgeLabels();
		for (Object o : l) {
			TSEdgeLabel lab = (TSEdgeLabel) o;
			labTailor.setAssociation(lab, TSLayoutConstants.EDGE_LABEL_ASSOCIATION_SOURCE);
		}
	}

	// This method performs a Global Layout on the drawing.
	public void callGlobalLayout() {
		try {
			initDecisionLayout();
			callLayoutService();
		} catch (TSServiceException exception) {
			System.err.println("Layout failed: " + exception.getMessage());
		}
	}

	// This method performs a edge routing
	public void callGlobalRouting() {
		try {
			TSRoutingInputTailor routingTailor = new TSRoutingInputTailor(inputData, diagramManager.getGraphManager());
			routingTailor.setFixedPositions(true);

			for (Object o : diagramManager.getGraphManager().intergraphEdges()) {
				TSEEdge e = (TSEEdge) o;
				routingTailor.setSourceAttachmentSide(e, TSLayoutConstants.ATTACHMENT_SIDE_RIGHT_OR_BOTTOM);
				routingTailor.setTargetAttachmentSide(e, TSLayoutConstants.ATTACHMENT_SIDE_LEFT_OR_TOP);
			}
			invokeRouting(routingTailor);
		} catch (TSServiceException exception) {
			System.err.println("Routing failed: " + exception.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public void callGlobalRoutingAllEdges() {
		try {
			TSRoutingInputTailor routingTailor = new TSRoutingInputTailor(inputData, diagramManager.getGraphManager());
			routingTailor.setFixedPositions(true);

			ArrayList<Object> edgeList = new ArrayList<Object>();
			edgeList.addAll(diagramManager.getGraphManager().intergraphEdges());
			edgeList.addAll(diagramManager.getGraphManager().getMainDisplayGraph().edges());
			routingTailor.setEdgeList(edgeList);
			// routingTailor.setHorizontalSpacingBetweenNodes(20);
			// routingTailor.setVerticalSpacingBetweenNodes(20);
			invokeRouting(routingTailor);
		} catch (TSServiceException exception) {
			System.err.println("Routing failed: " + exception.getMessage());
		}
	}

	private void invokeRouting(TSRoutingInputTailor routingInputTailor) {
		routingInputTailor.setAsCurrentOperation();
		callLayoutService();
	}

	/*
	 * // for tree diagrams we want a different orientation so we override this
	 * method. public void setHierarchicalOptions() {
	 * super.setHierarchicalOptions(); TSEGraph graph = (TSEGraph)
	 * this.graphManager.getMainDisplayGraph();
	 * 
	 * TSHierarchicalLayoutInputTailor hierarchicalInputTailor = new
	 * TSHierarchicalLayoutInputTailor(this.inputData);
	 * 
	 * hierarchicalInputTailor.setGraph(graph);
	 * hierarchicalInputTailor.setLevelDirection
	 * (TSLayoutConstants.DIRECTION_LEFT_TO_RIGHT); // TODO: should be
	 * preference! hierarchicalInputTailor.setKeepNodeSizes(false); }
	 * 
	 * public void setIncrementalOptions() { super.setIncrementalOptions();
	 * 
	 * TSEGraph graph = (TSEGraph) this.graphManager.getMainDisplayGraph();
	 * TSHierarchicalLayoutInputTailor hierarchicalInputTailor = new
	 * TSHierarchicalLayoutInputTailor( this.inputData);
	 * 
	 * hierarchicalInputTailor.setGraph(graph);
	 * hierarchicalInputTailor.setLevelDirection
	 * (TSLayoutConstants.DIRECTION_LEFT_TO_RIGHT); }
	 * 
	 * protected void setPerCallGlobalLayoutOptions() {
	 * TSHierarchicalLayoutInputTailor hierarchicalLayoutInputTailor = new
	 * TSHierarchicalLayoutInputTailor(this.inputData); TSDGraph
	 * mainDisplayGraph = graphManager.getMainDisplayGraph();
	 * hierarchicalLayoutInputTailor.setGraph(mainDisplayGraph);
	 * 
	 * int numberOfNodes = mainDisplayGraph.numberOfNodes(); //TODO Make this a
	 * preference if (numberOfNodes > 1200) {
	 * hierarchicalLayoutInputTailor.setOrthogonalRouting(false);
	 * DecisionTreeUIPlugin.debug(DecisionTreeLayoutManager.class.getName(),
	 * "Turning off orthogonal edge routing due to large tree size. Number of Nodes {0}"
	 * , numberOfNodes); } else {
	 * hierarchicalLayoutInputTailor.setOrthogonalRouting(true); } }
	 * 
	 * protected void setPerCallIncrementalLayoutOptions() {
	 * TSHierarchicalLayoutInputTailor hierarchicalLayoutInputTailor = new
	 * TSHierarchicalLayoutInputTailor(this.inputData); TSDGraph
	 * mainDisplayGraph = graphManager.getMainDisplayGraph();
	 * hierarchicalLayoutInputTailor.setGraph(mainDisplayGraph);
	 * 
	 * int numberOfNodes = mainDisplayGraph.numberOfNodes(); if (numberOfNodes >
	 * 1200) { hierarchicalLayoutInputTailor.setOrthogonalRouting(false);
	 * DecisionTreeUIPlugin.debug(DecisionTreeLayoutManager.class.getName(),
	 * "Turning off orthogonal edge routing due to large tree size. Number of Nodes {0}"
	 * , numberOfNodes); } else {
	 * hierarchicalLayoutInputTailor.setOrthogonalRouting(true); } }
	 */

}
