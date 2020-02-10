package com.tibco.cep.studio.ui.statemachine.diagram.ui;

import java.util.LinkedList;
import java.util.List;

import com.tibco.cep.diagramming.drawing.LayoutManager;
import com.tibco.cep.diagramming.ui.FinalStateNodeUI;
import com.tibco.cep.studio.ui.statemachine.diagram.StateMachineDiagramManager;
import com.tibco.cep.studio.ui.statemachine.diagram.StateMachineLayoutManager;
import com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils;
import com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils.STATE;
import com.tomsawyer.drawing.geometry.shared.TSOvalShape;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSENodeLabel;
import com.tomsawyer.graphicaldrawing.TSESolidObject;
import com.tomsawyer.graphicaldrawing.builder.TSNodeBuilder;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEAnnotatedUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEObjectUI;
import com.tomsawyer.service.layout.TSLayoutConstants;

/**
 * 
 * @author sasahoo
 *
 */
@SuppressWarnings("serial")
public class FinalStateNodeCreator extends TSNodeBuilder {
	
	private LayoutManager layoutManager;
	
	/* (non-Javadoc)
	 * @see com.tomsawyer.graphicaldrawing.builder.TSNodeBuilder#addNode(com.tomsawyer.graphicaldrawing.TSEGraph)
	 */
	public FinalStateNodeCreator(LayoutManager layoutManager) {
		this.layoutManager = layoutManager;
	}
	
	public TSENode addNode(TSEGraph graph) {
	
		FinalStateNodeUI ui = new FinalStateNodeUI();
		TSENode node = super.addNode(graph);
		String tag = StateMachineUtils.getNodeName(graph, StateMachineUtils.UNIQUE_END_STATE);
		node.setName(tag);
		node.setAttribute(StateMachineUtils.STATE_TYPE, STATE.END);
		TSENodeLabel nodeLabel = ((TSENodeLabel) node.addLabel());
		((TSEAnnotatedUI) nodeLabel.getUI()).setTextAntiAliasingEnabled(true);
		nodeLabel.setDefaultOffset();
		nodeLabel.setName(tag);
		node.setSize(node.getWidth() * 0.65, node.getHeight() * 0.65);
		node.setResizability(TSESolidObject.RESIZABILITY_LOCKED);
		node.setShape(TSOvalShape.getInstance());
		node.setUI((TSEObjectUI) ui);
		if ((layoutManager instanceof StateMachineLayoutManager) && (nodeLabel != null)){
			TSENode startNode = ((StateMachineDiagramManager)
				((StateMachineLayoutManager) layoutManager).getDiagramManager()).getStartNode(graph);
			List<TSENode> list = new LinkedList<TSENode>();
			list.add(startNode);
			list.add(node);
			((StateMachineLayoutManager) layoutManager).addSequenceConstraint(
				list, TSLayoutConstants.DIRECTION_LEFT_TO_RIGHT);
			
			((StateMachineLayoutManager) layoutManager).setNodeLabelOptions(nodeLabel);
		}
		
		// todo: we should add left to right constraint between the single start node in the SM
		// and also this new end node
		
		return node;
	}

}
