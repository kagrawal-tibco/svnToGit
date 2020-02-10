package com.tibco.cep.diagramming.model;


import com.tomsawyer.drawing.geometry.shared.TSPolygonShape;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.builder.TSNodeBuilder;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEObjectUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEShapeNodeUI;

public class InSignalNodeCreator extends TSNodeBuilder {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TSENode addNode(TSEGraph graph)
	{
		TSEShapeNodeUI ui = new TSEShapeNodeUI();
		TSENode node = super.addNode(graph);
		node.setSize(50, 30);
		node.setShape(TSPolygonShape.fromString(
			"[ 5 (0, 0) (100, 0) (100, 100) (0, 100) (25, 50) ]"));
		node.setName("Input\nSignal");
		// node.setResizability(TSESolidObject.RESIZABILITY_TIGHT_FIT);
		node.setUI((TSEObjectUI) ui);			

		return node;
	}	

}
