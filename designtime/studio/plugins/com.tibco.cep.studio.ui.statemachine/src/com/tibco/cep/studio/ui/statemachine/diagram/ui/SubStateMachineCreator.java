package com.tibco.cep.studio.ui.statemachine.diagram.ui;

import com.tibco.cep.diagramming.drawing.LayoutManager;
import com.tibco.cep.diagramming.ui.SubStateNodeUI;
import com.tibco.cep.studio.ui.statemachine.diagram.StateMachineLayoutManager;
import com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils;
import com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils.STATE;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSENodeLabel;
import com.tomsawyer.graphicaldrawing.TSESolidObject;
import com.tomsawyer.graphicaldrawing.builder.TSNodeBuilder;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEAnnotatedUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEObjectUI;

/**
 * 
 * @author sasahoo
 *
 */
@SuppressWarnings("serial")
public class SubStateMachineCreator extends TSNodeBuilder {

	private LayoutManager layoutManager;
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.model.SimpleStateNodeCreator#addNode(com.tomsawyer.graphicaldrawing.TSEGraph)
	 */
	public SubStateMachineCreator(LayoutManager layoutManager) {
		this.layoutManager = layoutManager;
	}
	
	public TSENode addNode(TSEGraph graph) {
		TSENode node = super.addNode(graph);
		SubStateNodeUI ui = new SubStateNodeUI();
		String tag = StateMachineUtils.getNodeName(graph,StateMachineUtils.UNIQUE_SUBSTATEMACHINE);
		node.setName(tag);
		node.setTooltipText(tag);
		node.setResizability(TSESolidObject.RESIZABILITY_LOCKED);
		node.setAttribute(StateMachineUtils.STATE_TYPE, STATE.SUBSTATEMACHINE);
		node.setSize(45, 35);
		TSENodeLabel nodeLabel = (TSENodeLabel) node.addLabel();
		nodeLabel.setDefaultOffset();
		((TSEAnnotatedUI) nodeLabel.getUI()).setTextAntiAliasingEnabled(true);
		nodeLabel.setName(tag);
		node.setUI((TSEObjectUI) ui);
		if ((layoutManager instanceof StateMachineLayoutManager) && (nodeLabel != null)){
			((StateMachineLayoutManager) layoutManager).setNodeLabelOptions(nodeLabel);
		}
		return node;
	}
}

