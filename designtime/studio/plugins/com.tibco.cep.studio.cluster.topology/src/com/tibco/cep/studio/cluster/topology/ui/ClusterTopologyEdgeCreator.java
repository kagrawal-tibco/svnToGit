package com.tibco.cep.studio.cluster.topology.ui;

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
public class ClusterTopologyEdgeCreator extends TSEdgeBuilder {

	private static final long serialVersionUID = 1L;

	public TSEEdge addEdge(TSEGraphManager arg0, TSENode arg1, TSENode arg2) {
		TSEEdge edge = super.addEdge(arg0, arg1, arg2);
//		((TSEEdgeLabel) edge.addLabel()).setTag("Transition");
		TSECurvedEdgeUI edgeUI = new TSECurvedEdgeUI();
		edgeUI.setAntiAliasingEnabled(true);
		edgeUI.setCurvature(100);
		edge.setUI(edgeUI);
		return edge;
	}
}
