package com.tibco.cep.decision.tree.ui.nodeactions;

import com.tibco.cep.decision.tree.common.model.FlowElement;
import com.tibco.cep.decision.tree.common.model.node.terminal.TerminalFactory;
import com.tibco.cep.decision.tree.ui.nodes.EndNodeUI;
import com.tibco.cep.decision.tree.ui.util.DecisionTreeConstants.NODE;
import com.tibco.cep.decision.tree.ui.util.DecisionTreeUiUtil;
import com.tibco.cep.diagramming.drawing.LayoutManager;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.ui.composite.TSCompositeNodeUI;

@SuppressWarnings("serial")
public class EndNodeCreator extends AbstractTerminalNodeCreator {

	public EndNodeCreator(LayoutManager layoutManager, FlowElement flowElement) {
		super(layoutManager, flowElement);
	}

	@Override
	protected String getNodePrefix() {
		return DecisionTreeUiUtil.UNIQUE_END_NODE;
	}

	@Override
	protected Object getNodeType() {
		return NODE.END;
	}

	@Override
	protected TSCompositeNodeUI getNodeUI(TSENode node) {
		return new EndNodeUI();
	}

	@Override
	protected FlowElement getFlowElement() {
		return ((TerminalFactory)getFactory()).createEnd();
	}
}
