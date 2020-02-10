package com.tibco.cep.bpmn.ui.graph.tool;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.List;

import org.eclipse.emf.ecore.EClass;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.editor.BpmnDiagramManager;
import com.tibco.cep.bpmn.ui.editor.BpmnEditor;
import com.tibco.cep.bpmn.ui.graph.BpmnImages;
import com.tibco.cep.bpmn.ui.graph.model.command.AbstractEdgeCommandFactory;
import com.tibco.cep.bpmn.ui.graph.model.command.IGraphCommand;
import com.tibco.cep.bpmn.ui.graph.rule.AssociationRule;
import com.tibco.cep.bpmn.ui.graph.rule.DiagramRuleSet;
import com.tibco.cep.bpmn.ui.graph.rule.FlowNodeRule;
import com.tibco.cep.bpmn.ui.graph.rule.MultipleSequenceflowRule;
import com.tibco.cep.diagramming.tool.ReconnectEdgeTool;
import com.tibco.cep.diagramming.utils.DiagramUtils;
import com.tibco.cep.studio.ui.overview.OverviewUtils;
import com.tomsawyer.graphicaldrawing.TSEConnector;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.interactive.command.editing.TSEReconnectEdgeCommand;

public class BpmnReconnectEdgeTool extends ReconnectEdgeTool {
	private DiagramRuleSet ruleSet;
	private BpmnDiagramManager bpmnGraphDiagramManager;
	private MultipleSequenceflowRule multipleSequenceflowRule;

	/**
	 * @param bpmnGraphDiagramManager
	 */
	public BpmnReconnectEdgeTool(
			BpmnDiagramManager bpmnGraphDiagramManager) {
		this.bpmnGraphDiagramManager = bpmnGraphDiagramManager;
		this.ruleSet = new DiagramRuleSet();
		addAllRules();
		multipleSequenceflowRule = new MultipleSequenceflowRule(this.ruleSet, true);
	}
	
	private void addAllRules() {
		getRuleSet().addRule(new FlowNodeRule(this.ruleSet));
		getRuleSet().addRule(new AssociationRule(this.ruleSet));
	}
	
	public DiagramRuleSet getRuleSet() {
		return ruleSet;
	}

	/**
	 * @return
	 */
	public BpmnDiagramManager getBpmnGraphDiagramManager() {
		return bpmnGraphDiagramManager;
	}
	
	@Override
	public boolean isActionAllowed() {
		if(!((BpmnEditor)getBpmnGraphDiagramManager().getEditor()).isEnabled()){
			return false;
		}
		TSENode srcTSNode, tgtTSNode;
		TSENode newNode2 = getNewNode();
		TSEEdge edge = this.getEdge();
		TSENode sourceNode = (TSENode)edge.getSourceNode();
		TSENode targetNode = (TSENode)edge.getTargetNode();
		if(targetNode == getSourceNode())   
		{
			srcTSNode = newNode2;
			tgtTSNode = targetNode;
		}else {
			srcTSNode = sourceNode; 
			tgtTSNode = getTargetNode();
			
		}

		boolean isAllowed = getRuleSet().isAllowed(srcTSNode, tgtTSNode, edge);
		if(isAllowed){
			isAllowed = multipleSequenceflowRule.isAllowed(new Object[]{srcTSNode, tgtTSNode, edge});
		}
		if (!isAllowed) {
			try {
				Toolkit tk = Toolkit.getDefaultToolkit();
				Image image = BpmnImages.createIcon("icons/Invalid10x10.png").getImage();
				Cursor c = tk.createCustomCursor(image, new Point(0,0),"invalid");
				this.setActionCursor(c);
				this.setCursor(c);
				this.cancelAction();
			} catch (Exception e) {
				BpmnUIPlugin.log(e);
			}
		}
		DiagramUtils.refreshDiagram(getBpmnGraphDiagramManager());
		OverviewUtils.refreshOverview(getBpmnGraphDiagramManager().getEditor().getEditorSite(), true, true);
		return isAllowed;
	}
	
	 @SuppressWarnings("rawtypes")
	protected TSEReconnectEdgeCommand newReconnectEdgeCommand(TSEEdge paramTSEEdge, TSENode paramTSENode, TSEConnector paramTSEConnector, boolean paramBoolean, List paramList1, List paramList2)
	  {
		 EClass sourceNodeType = (EClass) getSourceNode().getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
			EClass targetNodeType = (EClass) getTargetNode().getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
			AbstractEdgeCommandFactory cf = null;
			if(BpmnModelClass.ARTIFACT.isSuperTypeOf(sourceNodeType) ||
					BpmnModelClass.ARTIFACT.isSuperTypeOf(targetNodeType)) {
				// at least one needs to be an artifact
				cf = getBpmnGraphDiagramManager()
				.getModelGraphFactory().getEdgeCommandFactory(
						BpmnModelClass.ASSOCIATION);
			} else if(BpmnModelClass.FLOW_NODE.isSuperTypeOf(sourceNodeType) &&
					BpmnModelClass.FLOW_NODE.isSuperTypeOf(targetNodeType)) {
				// both nodes need to be a flow node
				cf = getBpmnGraphDiagramManager()
				.getModelGraphFactory().getEdgeCommandFactory(
						BpmnModelClass.SEQUENCE_FLOW);
			}
		 
		 IGraphCommand<TSEEdge> cmd = cf.getCommand(IGraphCommand.COMMAND_RECONNECT,
			paramTSEEdge, paramTSENode, paramTSEConnector, paramBoolean, paramList1, paramList1);
	    return (TSEReconnectEdgeCommand)cmd.getAdapter(TSEReconnectEdgeCommand.class);
	  }

}
