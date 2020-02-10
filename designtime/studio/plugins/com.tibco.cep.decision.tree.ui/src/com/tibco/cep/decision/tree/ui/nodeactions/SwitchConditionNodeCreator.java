package com.tibco.cep.decision.tree.ui.nodeactions;

import com.tibco.cep.decision.tree.common.model.FlowElement;
import com.tibco.cep.decision.tree.common.model.node.condition.ConditionFactory;
import com.tibco.cep.decision.tree.ui.nodes.SwitchConditionNodeUI;
import com.tibco.cep.decision.tree.ui.util.DecisionTreeConstants.NODE;
import com.tibco.cep.decision.tree.ui.util.DecisionTreeUiUtil;
import com.tibco.cep.diagramming.drawing.LayoutManager;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSESolidObject;
import com.tomsawyer.graphicaldrawing.ui.composite.TSCompositeNodeUI;

/*
@author ssailapp
@date Oct 6, 2011
 */

@SuppressWarnings("serial")
public class SwitchConditionNodeCreator extends AbstractConditionNodeCreator {

	public SwitchConditionNodeCreator(LayoutManager layoutManager, FlowElement flowElement) {
		//super();
		super(layoutManager, flowElement);
	}
	
	/*
	@Override
	public TSENode addNode(TSEGraph graph) {
		TSENode node = super.addNode(graph, TSDecisionNodeBuilder.SWITCH_NODE);
		node.setAttribute(DecisionTreeUtils.NODE_TYPE, getNodeType());
		return node;
	}
	*/
	
	@Override
	protected String getNodePrefix() {
		return DecisionTreeUiUtil.UNIQUE_SWITCH_CONDITION_NODE;
	}

	@Override
	protected Object getNodeType() {
		return NODE.SWITCH_CONDITION;
	}

	@Override
	protected FlowElement getFlowElement() {
		return ConditionFactory.eINSTANCE.createSwitchCond();
	}
	
	@SuppressWarnings("unused")
	@Override
	protected TSCompositeNodeUI getNodeUI(TSENode node) {
		SwitchConditionNodeUI ui = new SwitchConditionNodeUI();
		/* // TODO - TSV 9.2
		  ui.initAllOwnerProperties();
		return ui;
		 */
		return null;
	}
	
	@Override
	protected boolean canShowNodeLabel() {
		return false;
	}

	@Override
	protected boolean canShowNodeName() {
		return false;
	}
	
	@Override
	protected void configureNodeUI(TSENode node) {
		/* // TODO - TSV 9.2
		((SwitchConditionNodeUI) node.getUI()).initAllOwnerProperties();
		*/
		node.setExpandedResizability(TSESolidObject.RESIZABILITY_NO_FIT);
	}
	
}
