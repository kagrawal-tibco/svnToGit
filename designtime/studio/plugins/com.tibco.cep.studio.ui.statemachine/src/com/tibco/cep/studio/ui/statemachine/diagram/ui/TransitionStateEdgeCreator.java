package com.tibco.cep.studio.ui.statemachine.diagram.ui;

import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSEGraphManager;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.builder.TSEdgeBuilder;

/**
 * 
 * @author sasahoo
 *
 */
@SuppressWarnings("serial")
public class TransitionStateEdgeCreator extends TSEdgeBuilder{


	/* (non-Javadoc)
	 * @see com.tomsawyer.graphicaldrawing.builder.TSEdgeBuilder#addEdge(com.tomsawyer.graphicaldrawing.TSEGraphManager, com.tomsawyer.graphicaldrawing.TSENode, com.tomsawyer.graphicaldrawing.TSENode)
	 */
	@Override
	public TSEEdge addEdge(TSEGraphManager arg0, TSENode arg1, TSENode arg2) {
		TSEEdge edge  = super.addEdge(arg0, arg1, arg2);
//		TSEEdgeUI edgeUI = new TSECurvedEdgeUI();
		StateMachine machine = (StateMachine)arg0.getAnchorGraph().getUserObject();
		//TODO Unique identifier needs to be changed..
		String tag  = StateMachineUtils.getEdgeName(machine, StateMachineUtils.UNIQUE_TRANSITION);
		edge.setName(tag);
		
		// we don't need to create the edge label now, we do that in StateMachineDiagramChangeListner
//		TSEEdgeLabel label = (TSEEdgeLabel) edge.addLabel();
//		label.setText(tag);
//		((TSEAnnotatedUI) label.getUI()).setTextAntiAliasingEnabled(true);
//		edge.setUI((TSEObjectUI) edgeUI);

		return edge;
	}
}
