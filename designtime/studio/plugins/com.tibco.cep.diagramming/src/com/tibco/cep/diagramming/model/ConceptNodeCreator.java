package com.tibco.cep.diagramming.model;

import com.tibco.cep.diagramming.ui.SimpleConceptNodeUI;
import com.tomsawyer.drawing.geometry.shared.TSPolygonShape;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSEGraphManager;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.builder.TSNodeBuilder;

public class ConceptNodeCreator extends TSNodeBuilder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TSENode addNode(TSEGraph graph) {
		
		SimpleConceptNodeUI ui = new SimpleConceptNodeUI ();
		TSENode node = super.addNode(graph);
		TSEGraphManager graphManager = (TSEGraphManager) graph.getOwnerGraphManager();
		graphManager.getNodeBuilder().setNodeUI(ui);
		node.setName("Concept");
		node.setSize(60, 30);
//		graphManager.getNodeBuilder().setShape(TSPolygonShape.getInstance(TSPolygonShape.ROUNDED_RECTANGLE));
		node.setShape(TSPolygonShape.getInstance(TSPolygonShape.ROUNDED_RECTANGLE));

		return node;
	}
}
