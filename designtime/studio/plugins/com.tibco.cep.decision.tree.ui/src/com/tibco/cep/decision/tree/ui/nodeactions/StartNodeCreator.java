package com.tibco.cep.decision.tree.ui.nodeactions;

import com.tibco.cep.decision.tree.common.model.FlowElement;
import com.tibco.cep.decision.tree.common.model.node.terminal.TerminalFactory;
import com.tibco.cep.decision.tree.ui.nodes.StartNodeUI;
import com.tibco.cep.decision.tree.ui.util.DecisionTreeConstants.NODE;
import com.tibco.cep.decision.tree.ui.util.DecisionTreeUiUtil;
import com.tibco.cep.diagramming.drawing.LayoutManager;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.ui.composite.TSCompositeNodeUI;

@SuppressWarnings("serial")
public class StartNodeCreator extends AbstractTerminalNodeCreator {

	public StartNodeCreator(LayoutManager layoutManager, FlowElement flowElement) {
		super(layoutManager, flowElement);
	}

	@Override
	public String getNodePrefix() {
		return DecisionTreeUiUtil.UNIQUE_START_NODE;	
	}

	@Override
	protected Object getNodeType() {
		return NODE.START;
	}

	@Override
	protected TSCompositeNodeUI getNodeUI(TSENode node) {
		return new StartNodeUI();
	}

	@Override
	protected FlowElement getFlowElement() {
		return ((TerminalFactory)getFactory()).createStart();
	}
	
}