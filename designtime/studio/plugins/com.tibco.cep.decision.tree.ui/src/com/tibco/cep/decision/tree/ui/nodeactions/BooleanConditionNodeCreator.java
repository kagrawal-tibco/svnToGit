package com.tibco.cep.decision.tree.ui.nodeactions;

import com.tibco.cep.decision.tree.common.model.FlowElement;
import com.tibco.cep.decision.tree.common.model.node.condition.ConditionFactory;
import com.tibco.cep.decision.tree.ui.nodes.BooleanConditionNodeUI;
import com.tibco.cep.decision.tree.ui.util.DecisionTreeConstants.NODE;
import com.tibco.cep.decision.tree.ui.util.DecisionTreeUiUtil;
import com.tibco.cep.diagramming.drawing.LayoutManager;
import com.tomsawyer.drawing.geometry.shared.TSPolygonShape;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSESolidObject;
import com.tomsawyer.graphicaldrawing.ui.composite.TSCompositeNodeUI;

/*
@author ssailapp
@date Sep 16, 2011
 */

@SuppressWarnings("serial")
public class BooleanConditionNodeCreator extends AbstractConditionNodeCreator {

	public BooleanConditionNodeCreator(LayoutManager layoutManager, FlowElement flowElement) {
		super(layoutManager, flowElement);
	}
	
	@Override
	protected String getNodePrefix() {
		return DecisionTreeUiUtil.UNIQUE_BOOLEAN_CONDITION_NODE;
	}

	@Override
	protected Object getNodeType() {
		return NODE.BOOLEAN_CONDITION;
	}

	@Override
	protected FlowElement getFlowElement() {
		return ((ConditionFactory)getFactory()).createBoolCond();
	}
	
	@Override
	protected TSCompositeNodeUI getNodeUI(TSENode node) {
		return new BooleanConditionNodeUI();
	}
	
	@Override
	protected boolean canShowNodeLabel() {
		return false;
	}
	
	@Override
	protected boolean canShowNodeName() {
		return true;
	}
	
	@Override
	protected void configureNodeUI(TSENode node) {
		super.configureNodeUI(node);
		node.setShape(TSPolygonShape.getInstance(TSPolygonShape.DIAMOND));
		node.setResizability(TSESolidObject.RESIZABILITY_TIGHT_WIDTH);
	}
}
