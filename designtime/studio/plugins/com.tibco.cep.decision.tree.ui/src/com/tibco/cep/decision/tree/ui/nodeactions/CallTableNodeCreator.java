package com.tibco.cep.decision.tree.ui.nodeactions;

import com.tibco.cep.decision.tree.common.model.FlowElement;
import com.tibco.cep.decision.tree.common.model.node.action.ActionFactory;
import com.tibco.cep.decision.tree.ui.nodes.CallTableNodeUI;
import com.tibco.cep.decision.tree.ui.util.DecisionTreeConstants.NODE;
import com.tibco.cep.decision.tree.ui.util.DecisionTreeUiUtil;
import com.tibco.cep.diagramming.drawing.LayoutManager;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.ui.composite.TSCompositeNodeUI;

/*
@author ssailapp
@date Sep 16, 2011
 */

@SuppressWarnings("serial")
public class CallTableNodeCreator extends AbstractActionNodeCreator {

	public CallTableNodeCreator(LayoutManager layoutManager, FlowElement flowElement) {
		super(layoutManager, flowElement);
	}
	
	@Override
	protected String getNodePrefix() {
		return DecisionTreeUiUtil.UNIQUE_CALL_TABLE_NODE;
	}

	@Override
	protected Object getNodeType() {
		return NODE.CALL_DECISION_TABLE;
	}

	@Override
	protected FlowElement getFlowElement() {
		return ((ActionFactory)getFactory()).createCallDecisionTableAction();
	}

	@Override
	protected TSCompositeNodeUI getNodeUI(TSENode node) {
		return new CallTableNodeUI();
	}
	
	protected boolean canShowNodeLabel() {
		return false;
	}
	
	protected boolean canShowNodeName() {
		return true;
	}
}

