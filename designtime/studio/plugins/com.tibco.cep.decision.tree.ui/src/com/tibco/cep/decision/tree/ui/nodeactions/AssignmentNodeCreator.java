package com.tibco.cep.decision.tree.ui.nodeactions;

import com.tibco.cep.decision.tree.common.model.FlowElement;
import com.tibco.cep.decision.tree.common.model.node.action.ActionFactory;
import com.tibco.cep.decision.tree.ui.nodes.AssignmentNodeUI;
import com.tibco.cep.decision.tree.ui.util.DecisionTreeConstants.NODE;
import com.tibco.cep.decision.tree.ui.util.DecisionTreeUiUtil;
import com.tibco.cep.diagramming.drawing.LayoutManager;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.ui.composite.TSCompositeNodeUI;

/*
@author ssailapp
@date Oct 7, 2011
 */

@SuppressWarnings("serial")
public class AssignmentNodeCreator extends AbstractActionNodeCreator {

	public AssignmentNodeCreator(LayoutManager layoutManager, FlowElement flowElement) {
		super(layoutManager, flowElement);
	}
	
	@Override
	protected String getNodePrefix() {
		return DecisionTreeUiUtil.UNIQUE_ASSIGNMENT_NODE;
	}

	@Override
	protected Object getNodeType() {
		return NODE.ASSIGNMENT;
	}

	@Override
	protected FlowElement getFlowElement() {
		return ((ActionFactory)getFactory()).createAssignment();
	}

	@Override
	protected TSCompositeNodeUI getNodeUI(TSENode node) {
		return new AssignmentNodeUI();
	}
	
	@Override
	protected boolean canShowNodeLabel() {
		return false;
	}
	
	@Override
	protected boolean canShowNodeName() {
		return true;
	}
} 
