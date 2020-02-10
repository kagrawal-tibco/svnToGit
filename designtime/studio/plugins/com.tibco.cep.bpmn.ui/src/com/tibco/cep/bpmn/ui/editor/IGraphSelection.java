package com.tibco.cep.bpmn.ui.editor;

import java.util.List;

import org.eclipse.jface.viewers.ISelection;

import com.tomsawyer.graph.TSEdge;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSEObject;

public interface IGraphSelection extends ISelection {
	
	TSEObject getGraphObject();
	
	boolean isMultiple();
	
	List<TSEObject> toList();
	
	boolean hasGraphSelection();
	
	boolean hasNodeSelection();
	
	boolean hasEdgeSelection();
	
	List<TSENode> getSelectedNodes();
	
	List<TSEdge> getSelectedEdges();
	
	List<TSEGraph> getSelectedGraphs();
}
