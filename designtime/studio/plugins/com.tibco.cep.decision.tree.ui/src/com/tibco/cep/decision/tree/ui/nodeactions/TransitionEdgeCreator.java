package com.tibco.cep.decision.tree.ui.nodeactions;

import com.tibco.cep.decision.tree.ui.util.DecisionTreeUiUtil;
import com.tibco.cep.diagramming.ui.PolylineEdgeUI;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSEGraphManager;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.builder.TSEdgeBuilder;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEObjectUI;

@SuppressWarnings("serial")
public class TransitionEdgeCreator extends TSEdgeBuilder {

	@Override
	public TSEEdge addEdge(TSEGraphManager graphMgr, TSENode srcNode, TSENode tgtNode) {
		TSEEdge edge  = super.addEdge(graphMgr, srcNode, tgtNode);
		PolylineEdgeUI edgeUI = new PolylineEdgeUI();

		String tag =  DecisionTreeUiUtil.getEdgeName(graphMgr.selectedGraph(), DecisionTreeUiUtil.UNIQUE_TRANSITION);
		edge.setName(tag);
		
		// we don't need to create the edge label now, we do that in DecisionTreeDiagramChangeListner
//		TSEEdgeLabel label = (TSEEdgeLabel) edge.addLabel();
//		label.setText(tag);
//		((TSEAnnotatedUI) label.getUI()).setTextAntiAliasingEnabled(true);

		edge.setUI((TSEObjectUI) edgeUI);

		return edge;
	}
}
