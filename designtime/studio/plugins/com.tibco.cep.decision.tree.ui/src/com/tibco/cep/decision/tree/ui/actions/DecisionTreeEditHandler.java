package com.tibco.cep.decision.tree.ui.actions;

import java.util.Iterator;

import com.tibco.cep.decision.tree.common.model.edge.Edge;
import com.tibco.cep.decision.tree.common.model.edge.EdgeFactory;
import com.tibco.cep.decision.tree.common.model.node.NodeElement;
import com.tibco.cep.decision.tree.common.model.node.action.ActionFactory;
import com.tibco.cep.decision.tree.common.model.node.condition.BoolCond;
import com.tibco.cep.decision.tree.common.model.node.condition.ConditionFactory;
import com.tibco.cep.decision.tree.common.model.node.terminal.TerminalFactory;
import com.tibco.cep.decision.tree.ui.DecisionTreeUIPlugin;
import com.tibco.cep.decision.tree.ui.editor.DecisionTreeDiagramChangeListener;
import com.tibco.cep.decision.tree.ui.editor.DecisionTreeDiagramManager;
import com.tibco.cep.decision.tree.ui.editor.DecisionTreeEditor;
import com.tibco.cep.decision.tree.ui.util.DecisionTreeConstants.NODE;
import com.tibco.cep.decision.tree.ui.util.DecisionTreeUiUtil;
import com.tibco.cep.studio.ui.util.StudioUIUtils;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSEEdgeLabel;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEAnnotatedUI;

public class DecisionTreeEditHandler {

	public static void handleNodeAdd(TSENode tsNode, DecisionTreeDiagramManager manager, DecisionTreeDiagramChangeListener listener) {
		Object nodeType = tsNode.getAttributeValue(DecisionTreeUiUtil.NODE_TYPE);
		if (nodeType == null) {
			DecisionTreeUIPlugin.logErrorMessage("Tree model invalid.");
			return;
		}
		NodeElement element = null;
		if (nodeType.equals(NODE.BOOLEAN_CONDITION)) {
			element = ConditionFactory.eINSTANCE.createBoolCond();
		} else if (nodeType.equals(NODE.SWITCH_CONDITION)) {
			element = ConditionFactory.eINSTANCE.createSwitchCond();
		} else if (nodeType.equals(NODE.ASSIGNMENT)) {
			element = ActionFactory.eINSTANCE.createAssignment();
		} else if (nodeType.equals(NODE.CALL_RF)) {
			element = ActionFactory.eINSTANCE.createCallRF();
		} else if (nodeType.equals(NODE.CALL_DECISION_TABLE)) {
			element = ActionFactory.eINSTANCE.createCallDecisionTableAction();
		} else if (nodeType.equals(NODE.CALL_DECISION_TREE)) {
			element = ActionFactory.eINSTANCE.createCallDecisionTreeAction();
		} else if (nodeType.equals(NODE.START)) {
			element = TerminalFactory.eINSTANCE.createStart();
		} else if (nodeType.equals(NODE.BREAK)) {
			element = TerminalFactory.eINSTANCE.createBreak();
		} else if (nodeType.equals(NODE.END)) {
			element = TerminalFactory.eINSTANCE.createEnd();
		}
		if (element != null) {
			addNewNode(manager, tsNode, element);
			markEditorDirty(manager);
		}
	}

	public static void handleEdgeAdd(TSEEdge tsEdge, DecisionTreeDiagramManager manager, DecisionTreeDiagramChangeListener listener) {
		addNewEdge(manager, tsEdge);
		markEditorDirty(manager);
	}
	
	public static void addEdgeLabel(TSEEdge tsEdge, String tag, DecisionTreeDiagramManager manager) {
		TSEEdgeLabel label = (TSEEdgeLabel) tsEdge.addLabel();
		label.setName(tag);
		((TSEAnnotatedUI) label.getUI()).setTextAntiAliasingEnabled(true);
		//((DecisionTreeLayoutManager) manager.getLayoutManager()).callBatchLabeling();
	}

    public static void handleNodeDelete(TSENode tsNode, DecisionTreeDiagramManager manager, DecisionTreeDiagramChangeListener listener) {
    	if (tsNode == null) 
    		return;
		removeNode(manager, tsNode);
    	markEditorDirty(manager);
    }
    
    public static void handleEdgeDelete(TSEEdge tsEdge, DecisionTreeDiagramManager manager, DecisionTreeDiagramChangeListener listener) {
    	if (tsEdge == null)
    		return;
    	removeEdge(manager, tsEdge);
    	markEditorDirty(manager);
    }
		
	private static void markEditorDirty(final DecisionTreeDiagramManager manager) {
		StudioUIUtils.invokeOnDisplayThread(new Runnable() {
			public void run() {
				((DecisionTreeEditor) manager.getEditor()).modified();
			}
		}, false);
	}
	
	private static void addNewNode(DecisionTreeDiagramManager manager, TSENode tsNode, NodeElement nodeElement) {
		String nodeName = (String) tsNode.getName();
		nodeElement.setName(nodeName);
		nodeElement.setId(DecisionTreeUiUtil.getUniqueId(manager.getDecisionTree()));
		manager.getDecisionTree().getNodes().add(nodeElement);
		tsNode.setUserObject(nodeElement);
	}
	
	private static void removeNode(DecisionTreeDiagramManager manager, TSENode tsNode) {
		NodeElement nodeElement = (NodeElement) tsNode.getUserObject();
		
		// Update the incoming transitions
		for (Edge inEdge: nodeElement.getInEdges()) {
			inEdge.setTgt(null);
			//manager.getDecisionTree().getEdges().remove(inEdge);
		}
			
		// Update the outgoing transitions
		Edge outEdge = nodeElement.getOutEdge();
		if (outEdge != null) {
			outEdge.setSrc(null);
			//manager.getDecisionTree().getEdges().remove(outEdge);
		}
		if (nodeElement instanceof BoolCond) {
			Edge falseEdge = ((BoolCond)nodeElement).getFalseEdge();
			if (falseEdge != null) {
				falseEdge.setSrc(null);
				//manager.getDecisionTree().getEdges().remove(falseEdge);
			}
		}
		
		manager.getDecisionTree().getNodes().remove(nodeElement);
	}
	
	
	private static void addNewEdge(DecisionTreeDiagramManager manager, TSEEdge tsEdge) {
		NodeElement srcElement = (NodeElement) tsEdge.getSourceNode().getUserObject();
		NodeElement tgtElement = (NodeElement) tsEdge.getTargetNode().getUserObject();
		Edge edgeElement = EdgeFactory.eINSTANCE.createEdge();
		edgeElement.setName("");
		edgeElement.setSrc(srcElement);
		edgeElement.setTgt(tgtElement);
		edgeElement.setId(DecisionTreeUiUtil.getUniqueId(manager.getDecisionTree()));
		tsEdge.setUserObject(edgeElement);
		manager.getDecisionTree().getEdges().add(edgeElement);
		tgtElement.getInEdges().add(edgeElement);
		if (srcElement instanceof BoolCond && srcElement.getOutEdge() != null) {
			edgeElement.setName("no");
			((BoolCond)srcElement).setFalseEdge(edgeElement);
		} else {
			srcElement.setOutEdge(edgeElement);
		}
	}

	private static void removeEdge(DecisionTreeDiagramManager manager, TSEEdge tsEdge) {
		Edge edgeElement = (Edge) tsEdge.getUserObject();	
		
		if (edgeElement == null)
			return;
		
		// Update source node
		NodeElement srcElement = edgeElement.getSrc();
		if (srcElement != null) {
			if (edgeElement.equals(srcElement.getOutEdge())) {
				srcElement.setOutEdge(null);
			} else if (srcElement instanceof BoolCond && edgeElement.equals(((BoolCond)srcElement).getFalseEdge())) {
				((BoolCond)srcElement).setFalseEdge(null);
			}
		}

		// Update target node
		if (edgeElement.getTgt() != null) {
			edgeElement.getTgt().getInEdges().remove(edgeElement);
		}
		
		manager.getDecisionTree().getEdges().remove(edgeElement);
	}

	
	/**
	 * @param tgtElement Node for which the incoming transitions should be updated
	 * @param currentElement Node whose reference must be removed in the tgtNode's incomingTransitions list
	 */
	@SuppressWarnings("unused")
	private static void removeIncomingReference(NodeElement currentElement, NodeElement tgtElement) {
		if (tgtElement == null)
			return;
		for (Iterator<Edge> itr=tgtElement.getInEdges().iterator(); itr.hasNext();) {
			NodeElement nodeElement = (NodeElement) itr.next();
			if (nodeElement.equals(currentElement)) {
				itr.remove();
			}
		}
	}
}
