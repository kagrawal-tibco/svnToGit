package com.tibco.cep.decision.tree.ui.nodeactions;

import org.eclipse.emf.ecore.EFactory;

import com.tibco.cep.decision.tree.common.model.FlowElement;
import com.tibco.cep.decision.tree.common.model.node.NodeFactory;
import com.tibco.cep.decision.tree.ui.nodes.DescriptionNodeUI;
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
public class DescriptionNodeCreator extends AbstractNodeCreator {

	protected NodeFactory factory;
	
	public DescriptionNodeCreator(LayoutManager layoutManager, FlowElement flowElement) {
		super(layoutManager, flowElement);
		factory = NodeFactory.eINSTANCE;
	}
	
	protected void configureNodeUI(TSENode node) {
		node.setSize(40, 20);
	}
	
	protected EFactory getFactory() {
		return NodeFactory.eINSTANCE;
	}

	@Override
	protected String getNodePrefix() {
		return DecisionTreeUiUtil.UNIQUE_DESCRIPTION_NODE;
	}

	@Override
	protected Object getNodeType() {
		return NODE.DESCRIPTION;
	}

	@Override
	protected FlowElement getFlowElement() {
		return ((NodeFactory)getFactory()).createDescriptionElement();
	}

	@Override
	protected TSCompositeNodeUI getNodeUI(TSENode node) {
		return new DescriptionNodeUI();
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



