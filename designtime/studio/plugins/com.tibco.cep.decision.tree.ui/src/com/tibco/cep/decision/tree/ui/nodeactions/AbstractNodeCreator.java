package com.tibco.cep.decision.tree.ui.nodeactions;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EFactory;

import com.tibco.cep.decision.tree.common.model.FlowElement;
import com.tibco.cep.decision.tree.ui.editor.DecisionTreeDiagramManager;
import com.tibco.cep.decision.tree.ui.editor.DecisionTreeLayoutManager;
import com.tibco.cep.decision.tree.ui.util.DecisionTreeUiUtil;
import com.tibco.cep.diagramming.drawing.LayoutManager;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSENodeLabel;
import com.tomsawyer.graphicaldrawing.TSESolidObject;
import com.tomsawyer.graphicaldrawing.builder.TSNodeBuilder;
import com.tomsawyer.graphicaldrawing.ui.composite.TSCompositeNodeUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEAnnotatedUI;
import com.tomsawyer.service.layout.TSLayoutConstants;

/*
@author ssailapp
@date Sep 23, 2011
 */

@SuppressWarnings("serial")
public abstract class AbstractNodeCreator extends TSNodeBuilder {

	protected LayoutManager layoutManager;
	protected FlowElement flowElement;
	
	private final String DEFAULT_NODE_NAME = "__default_node_name__";
	
	/* (non-Javadoc)
	 * @see com.tomsawyer.graphicaldrawing.builder.TSNodeBuilder#addNode(com.tomsawyer.graphicaldrawing.TSEGraph)
	 */
	public AbstractNodeCreator(LayoutManager layoutManager, FlowElement flowElement) {
		this.layoutManager = layoutManager;
		if (flowElement == null) {
			this.flowElement = getFlowElement();
			this.flowElement.setName(DEFAULT_NODE_NAME);
		} else {
			this.flowElement = flowElement;
		}
		
		registerNodeUI();
	}


	private void registerNodeUI() { 
		/* // TODO - TSV 9.2
		TSEEnumerationTable.getTable().addUIName(TSDecisionXMLAttributeConstants.SWITCH_NODE_UI, SwitchConditionNodeUI.class);
		TSEEnumerationTable.getTable().addUIName(TSDecisionXMLAttributeConstants.LOOP_NODE_UI, LoopNodeUI.class);
		TSEEnumerationTable.getTable().addUIName(TSDecisionXMLAttributeConstants.ACTION_NODE_UI, ActionNodeUI.class);
		TSEEnumerationTable.getTable().addUIName(TSDecisionXMLAttributeConstants.CONDITION_NODE_UI, BooleanConditionNodeUI.class);
		TSEEnumerationTable.getTable().addUIName(TSDecisionXMLAttributeConstants.DECISION_CASE_NODE_UI, TSDecisionCaseNodeUI.class);
		*/
	}
	
	public TSENode addNode(TSEGraph graph) {
		TSENode node = (TSENode) super.addNode(graph);
		if (flowElement.getName().equals(DEFAULT_NODE_NAME)) {
			flowElement.setName(DecisionTreeUiUtil.getNodeName(graph, getNodePrefix()));
		}
		try {
			node.setUserObject(flowElement);
			TSCompositeNodeUI ui = getNodeUI(node);
			node.setUI(ui);
			String nodeName = flowElement.getName();
			if (canShowNodeName()) {
				node.setName(DecisionTreeUiUtil.getTrimmedText(nodeName));
			} else {
				node.setName("");
			}
			node.setTooltipText(nodeName);
			node.setID(flowElement.getId());
			node.setAttribute(DecisionTreeUiUtil.NODE_TYPE, getNodeType());
			configureNodeUI(node);
			node.setResizability(TSESolidObject.RESIZABILITY_LOCKED);
			//ui.setTextAntiAliasingEnabled(false);
			//ui.setBorderDrawn(true);
		
			if (canShowNodeLabel()){
				addNodeLabel(graph, node, nodeName);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return node;
	}
	
	private void addNodeLabel(TSEGraph graph, TSENode node, String nodeName) {
		TSENodeLabel nodeLabel = (TSENodeLabel) node.addLabel();
		if (nodeLabel != null) {
			nodeLabel.setDefaultOffset();
			((TSEAnnotatedUI) nodeLabel.getUI()).setTextAntiAliasingEnabled(true);
			nodeLabel.setName(DecisionTreeUiUtil.getTrimmedText(nodeName));
			if (layoutManager instanceof DecisionTreeLayoutManager){
				TSENode startNode = ((DecisionTreeDiagramManager)
						((DecisionTreeLayoutManager) layoutManager).getDiagramManager()).getStartNode(graph);
				List<TSENode> list = new LinkedList<TSENode>();
				list.add(startNode);
				list.add(node);
				((DecisionTreeLayoutManager) layoutManager).addSequenceConstraint(
						list, TSLayoutConstants.DIRECTION_LEFT_TO_RIGHT);
				((DecisionTreeLayoutManager) layoutManager).setNodeLabelOptions(nodeLabel);
			}
		}	
	}
	
	protected abstract String getNodePrefix();

	protected abstract Object getNodeType();
	
	protected abstract FlowElement getFlowElement();
	
	protected abstract void configureNodeUI(TSENode node);
	
	protected abstract TSCompositeNodeUI getNodeUI(TSENode node);
	
	protected abstract boolean canShowNodeLabel();
	
	protected abstract boolean canShowNodeName();
	
	protected abstract EFactory getFactory();

}
