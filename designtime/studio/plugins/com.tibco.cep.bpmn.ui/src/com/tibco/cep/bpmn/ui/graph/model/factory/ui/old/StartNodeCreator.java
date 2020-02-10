package com.tibco.cep.bpmn.ui.graph.model.factory.ui.old;

import org.eclipse.emf.ecore.EClass;

import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.Messages;
import com.tibco.cep.bpmn.ui.editor.BpmnLayoutManager;
import com.tibco.cep.diagramming.ui.InitialNodeUI;
import com.tomsawyer.drawing.geometry.shared.TSOvalShape;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSENodeLabel;
import com.tomsawyer.graphicaldrawing.TSESolidObject;
import com.tomsawyer.graphicaldrawing.builder.TSNodeBuilder;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEAnnotatedUI;

/**
 * 
 * @author ggrigore
 *
 */
public class StartNodeCreator extends TSNodeBuilder {

	private static final long serialVersionUID = 1L;
	private EClass eventType;
	private String nodeName;	
	private BpmnLayoutManager layoutManager;	
	
	public StartNodeCreator(String name, EClass eventType, BpmnLayoutManager layoutManager) {
		this.nodeName = name;
		this.eventType = eventType;
		this.layoutManager = layoutManager;
	}

	public TSENode addNode(TSEGraph graph) {
		TSENode node = super.addNode(graph);
		decorateStartNode(node, this.nodeName, this.eventType);
		if (this.layoutManager != null && node.labels().size() > 0) {
			this.layoutManager.setNodeLabelOptions((TSENodeLabel) node.labels().get(0));
		}
		return node;
	}
	
	public static void decorateStartNode(TSENode node, String name, EClass eventType) {
		InitialNodeUI ui = new InitialNodeUI();
		TSENodeLabel nodeLabel = ((TSENodeLabel) node.addLabel());
		((TSEAnnotatedUI) nodeLabel.getUI()).setTextAntiAliasingEnabled(true);
		nodeLabel.setName(name);
		node.setTooltipText(Messages.getString("bpmnUiConstants.startEvent"));	
		node.setResizability(TSESolidObject.RESIZABILITY_LOCKED);
		node.setName(Messages.getString("bpmnUiConstants.startEvent"));
		nodeLabel.setDefaultOffset();
		node.setAttribute(BpmnUIConstants.NODE_ATTR_TYPE, eventType);		
		node.setAttribute(BpmnUIConstants.NODE_ATTR_NAME, name);
		node.setSize(60, 60);
		// node.setSize(node.getWidth() * 0.65, node.getHeight() * 0.65);
		node.setShape(TSOvalShape.getInstance());
		node.setUI(ui);
	}

}
