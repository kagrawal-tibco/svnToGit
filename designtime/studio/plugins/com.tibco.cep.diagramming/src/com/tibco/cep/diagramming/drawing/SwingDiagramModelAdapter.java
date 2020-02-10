package com.tibco.cep.diagramming.drawing;

import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSEEdgeLabel;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSEGraphManager;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSENodeLabel;

/**
 * 
 * @author ggrigore
 *
 */
public class SwingDiagramModelAdapter implements IDiagramModelAdapter {

	private TSEGraphManager graphManager;
	
	public SwingDiagramModelAdapter(TSEGraphManager graphManager) {
		this.graphManager = graphManager;
	}
	
	public TSENode addNode() {
		return (TSENode) this.graphManager.getMainDisplayGraph().addNode();
	}

	public TSENode addNode(TSEGraph graph) {
		return (TSENode) graph.addNode();
	}
	
	public TSENodeLabel addNodeLabel(TSENode node) {
		return (TSENodeLabel) node.addLabel();
	}

	public void deleteNode(TSENode node) {
		this.graphManager.getMainDisplayGraph().discard(node);
	}

	public TSEEdge addEdge(TSENode from, TSENode to) {
		return (TSEEdge) this.graphManager.addEdge(from, to);
	}
	
	public TSEEdge addEdge(TSEGraph graph, TSENode from, TSENode to) {
		return (TSEEdge) graph.addEdge(from, to);
	}

	public void deleteEdge(TSEEdge edge) {
		this.graphManager.discard(edge);
	}
	
	public TSEEdgeLabel addEdgeLabel(TSEEdge edge) {
		return (TSEEdgeLabel) edge.addLabel();
	}

	public TSEGraph addGraph() {
		return (TSEGraph) this.graphManager.addGraph();
	}

	public void deleteGraph(TSEGraph graph) {
		this.graphManager.discard(graph);
	}

	public TSEGraphManager getGraphManager() {
		return this.graphManager;
	}
}
