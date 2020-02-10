package com.tibco.cep.bpmn.ui.graph.model.factory.ui.old;

import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSEGraphManager;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.builder.TSEdgeBuilder;
import com.tomsawyer.graphicaldrawing.ui.simple.TSECurvedEdgeUI;

/**
 * 
 * @author ggrigore
 *
 */
public class EdgeCreator extends TSEdgeBuilder {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6766325673456547928L;

	public TSEEdge addEdge(TSEGraphManager arg0, TSENode arg1, TSENode arg2) {
		TSEEdge edge = super.addEdge(arg0, arg1, arg2);
		return edge;
	}
	
	static public void decorateEdge(TSEEdge edge) {
		TSECurvedEdgeUI edgeUI = new TSECurvedEdgeUI();
		edgeUI.setAntiAliasingEnabled(true);
		edgeUI.setCurvature(100);
		edge.setUI(edgeUI);
	}
}
