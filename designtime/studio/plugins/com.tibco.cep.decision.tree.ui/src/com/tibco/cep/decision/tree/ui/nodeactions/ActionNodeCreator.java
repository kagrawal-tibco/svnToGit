package com.tibco.cep.decision.tree.ui.nodeactions;

import com.tibco.cep.decision.tree.common.model.FlowElement;
import com.tibco.cep.decision.tree.common.model.node.action.ActionFactory;
import com.tibco.cep.decision.tree.ui.nodes.ActionNodeUI;
import com.tibco.cep.decision.tree.ui.util.DecisionTreeConstants.NODE;
import com.tibco.cep.decision.tree.ui.util.DecisionTreeUiUtil;
import com.tibco.cep.diagramming.drawing.LayoutManager;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.ui.composite.TSCompositeNodeUI;

/*
@author ssailapp
@date Nov 21, 2011
 */

@SuppressWarnings("serial")
public class ActionNodeCreator extends AbstractActionNodeCreator {

	public ActionNodeCreator(LayoutManager layoutManager, FlowElement flowElement) {
		super(layoutManager, flowElement);
	}
	
	@Override
	protected String getNodePrefix() {
		return DecisionTreeUiUtil.UNIQUE_ACTION_NODE;
	}

	@Override
	protected Object getNodeType() {
		return NODE.ACTION;
	}

	@Override
	protected FlowElement getFlowElement() {
		return ((ActionFactory)getFactory()).createAction();
	}

	@Override
	protected TSCompositeNodeUI getNodeUI(TSENode node) {
		return new ActionNodeUI();
	}
	
	protected boolean canShowNodeLabel() {
		return false;
	}
	
	protected boolean canShowNodeName() {
		return true;
	}
}
