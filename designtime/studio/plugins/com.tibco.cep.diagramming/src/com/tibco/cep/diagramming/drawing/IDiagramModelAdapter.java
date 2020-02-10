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
public interface IDiagramModelAdapter {

	public TSENode addNode();
	public TSENode addNode(TSEGraph graph);
	public void deleteNode(TSENode node);
	public TSENodeLabel addNodeLabel(TSENode node);
	
	public TSEEdge addEdge(TSENode from, TSENode to);
	public TSEEdge addEdge(TSEGraph graph, TSENode from, TSENode to);
	public void deleteEdge(TSEEdge edge);
	public TSEEdgeLabel addEdgeLabel(TSEEdge edge);
	
	public TSEGraph addGraph();
	public void deleteGraph(TSEGraph graph);
	
	public TSEGraphManager getGraphManager();
	
	// TODO...
	
}
