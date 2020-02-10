package com.tibco.cep.decision.tree.ui.nodeactions;

import org.eclipse.emf.ecore.EFactory;

import com.tibco.cep.decision.tree.common.model.FlowElement;
import com.tibco.cep.decision.tree.common.model.node.NodeFactory;
import com.tibco.cep.decision.tree.ui.util.DecisionTreeConstants.NODE;
import com.tibco.cep.decision.tree.ui.util.DecisionTreeUiUtil;
import com.tibco.cep.diagramming.drawing.LayoutManager;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.ui.composite.TSCompositeNodeUI;

/*
@author ssailapp
@date Oct 6, 2011
 */

@SuppressWarnings("serial")
public class LoopNodeCreator extends AbstractNodeCreator {

	public LoopNodeCreator(LayoutManager layoutManager, FlowElement flowElement) {
		super(layoutManager, flowElement);
	}
	
	@Override
	public TSENode addNode(TSEGraph graph) {
		/* // TODO - TSV 9.2
		TSENode node = super.addNode(graph, TSDecisionNodeBuilder.LOOP_NODE);
		*/
		TSENode node = super.addNode(graph);
		node.setAttribute(DecisionTreeUiUtil.NODE_TYPE, getNodeType());
		return node;
	}
	
	@Override
	protected EFactory getFactory() {
		return NodeFactory.eINSTANCE;
	}
	
	@Override
	protected String getNodePrefix() {
		return DecisionTreeUiUtil.UNIQUE_LOOP_NODE;
	}

	@Override
	protected Object getNodeType() {
		return NODE.LOOP;
	}

	@Override
	protected FlowElement getFlowElement() {
		return ((NodeFactory)getFactory()).createLoop();
	}
	
	@Override
	protected TSCompositeNodeUI getNodeUI(TSENode node) {
		return null;
		/* // TODO - TSV 9.2
		return new LoopNodeUI();
		*/
	}
	
	protected boolean canShowNodeLabel() {
		return true;
	}

	@Override
	protected void configureNodeUI(TSENode node) {
		/* // TODO - TSV 9.2
		((TSLoopNodeUI) node.getUI()).initAllOwnerProperties();
		*/
	}

	@Override
	protected boolean canShowNodeName() {
		return false;
	}
}
