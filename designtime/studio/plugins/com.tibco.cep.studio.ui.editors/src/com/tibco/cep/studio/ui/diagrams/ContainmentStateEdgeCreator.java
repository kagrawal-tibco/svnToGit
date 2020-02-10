package com.tibco.cep.studio.ui.diagrams;

import com.tibco.cep.diagramming.ui.UMLCurvedEdgeUI;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSEGraphManager;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.builder.TSEdgeBuilder;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEEdgeUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEObjectUI;
/**
 * 
 * @author hitesh
 * 
 */
public class ContainmentStateEdgeCreator extends TSEdgeBuilder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see com.tomsawyer.graphicaldrawing.builder.TSEdgeBuilder#addEdge(com.tomsawyer.graphicaldrawing.TSEGraphManager, com.tomsawyer.graphicaldrawing.TSENode, com.tomsawyer.graphicaldrawing.TSENode)
	 */
	@Override
	public TSEEdge addEdge(TSEGraphManager arg0, TSENode arg1, TSENode arg2) {
		TSEEdge edge  = super.addEdge(arg0, arg1, arg2);
		TSEEdgeUI edgeUI = new UMLCurvedEdgeUI(UMLCurvedEdgeUI.CONTAINMENT);
		edge.setName("Containment");
		edge.addLabel().setText("Containment");
		edge.setUI((TSEObjectUI) edgeUI);
		return edge;
	}
	
}
