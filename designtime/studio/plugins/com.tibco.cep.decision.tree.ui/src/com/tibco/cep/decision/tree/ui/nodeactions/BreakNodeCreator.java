package com.tibco.cep.decision.tree.ui.nodeactions;

import com.tibco.cep.decision.tree.common.model.FlowElement;
import com.tibco.cep.decision.tree.common.model.node.terminal.TerminalFactory;
import com.tibco.cep.decision.tree.ui.nodes.BreakNodeUI;
import com.tibco.cep.decision.tree.ui.util.DecisionTreeConstants.NODE;
import com.tibco.cep.decision.tree.ui.util.DecisionTreeUiUtil;
import com.tibco.cep.diagramming.drawing.LayoutManager;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.ui.composite.TSCompositeNodeUI;

/*
@author ssailapp
@date Sep 19, 2011
 */

@SuppressWarnings("serial")
public class BreakNodeCreator extends AbstractTerminalNodeCreator {

	public BreakNodeCreator(LayoutManager layoutManager, FlowElement flowElement) {
		super(layoutManager, flowElement);
	}

	@Override
	protected String getNodePrefix() {
		return DecisionTreeUiUtil.UNIQUE_BREAK_NODE;
	}

	@Override
	protected Object getNodeType() {
		return NODE.BREAK;
	}

	@Override
	protected TSCompositeNodeUI getNodeUI(TSENode node) {
		return new BreakNodeUI();
	}

	@Override
	protected FlowElement getFlowElement() {
		return ((TerminalFactory)getFactory()).createBreak();
	}

}

