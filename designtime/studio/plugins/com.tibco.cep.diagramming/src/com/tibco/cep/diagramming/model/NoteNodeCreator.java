package com.tibco.cep.diagramming.model;


import com.tibco.cep.diagramming.ui.NoteNodeUI;
import com.tomsawyer.drawing.geometry.shared.TSPolygonShape;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSEGraphManager;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.builder.TSNodeBuilder;

public class NoteNodeCreator extends TSNodeBuilder {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TSENode addNode(TSEGraph graph)
	{
		NoteNodeUI ui = new NoteNodeUI();
		TSENode noteNode = super.addNode(graph);
		TSEGraphManager graphManager = (TSEGraphManager) graph.getOwnerGraphManager();
		noteNode.setName("Note");
		noteNode.setSize(40, 25);
		noteNode.setShape(TSPolygonShape.fromString("[ 6 (0, 0) (100, 0) (100, 75) (75, 75) (75, 100) (0, 100) ]"));
//      noteNode.setResizability(TSESolidObject.RESIZABILITY_TIGHT_FIT);
//		noteNode.setUI((TSEObjectUI) ui);			
		graphManager.getNodeBuilder().setNodeUI(ui);

		return noteNode;
	}	

}
