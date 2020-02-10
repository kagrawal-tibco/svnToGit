package com.tibco.cep.bpmn.ui.graph.model.factory.ui.old;

import org.eclipse.emf.ecore.EClass;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.Messages;
import com.tibco.cep.bpmn.ui.editor.BpmnLayoutManager;
import com.tibco.cep.bpmn.ui.graph.model.factory.ui.EndEventNodeUI;
import com.tomsawyer.drawing.geometry.shared.TSOvalShape;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSENodeLabel;
import com.tomsawyer.graphicaldrawing.TSESolidObject;
import com.tomsawyer.graphicaldrawing.builder.TSNodeBuilder;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEAnnotatedUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEObjectUI;

/**
 * 
 * @author ggrigore
 *
 */
public class EndNodeCreator extends TSNodeBuilder {

	private static final long serialVersionUID = 1L;
	private EClass eventType;
	private String nodeName;	
	private BpmnLayoutManager layoutManager;	

	public EndNodeCreator(String name, EClass eventType, BpmnLayoutManager layoutManager) {
		this.nodeName = name;
		this.eventType = eventType;
		this.layoutManager = layoutManager;
	}	

	public TSENode addNode(TSEGraph graph) {
		TSENode node = super.addNode(graph);
		// TSEAddNodeCommand cmd = new TSEAddNodeCommand(graph, 0.0, 0.0);
		// this.layoutManager.getDiagramManager().executeCommand(cmd);
		decorateEndNode(node, this.nodeName, this.eventType);
		if (this.layoutManager != null && node.labels().size() > 0) {
			this.layoutManager.setNodeLabelOptions((TSENodeLabel) node.labels().get(0));
		}
		return node;
	}
	
	public static void decorateEndNode(TSENode node, String name, EClass eventType) {
		EndEventNodeUI ui = new EndEventNodeUI(BpmnModelClass.MESSAGE_EVENT_DEFINITION);
		TSENodeLabel nodeLabel = ((TSENodeLabel) node.addLabel());
		((TSEAnnotatedUI) nodeLabel.getUI()).setTextAntiAliasingEnabled(true);
		nodeLabel.setName(name);		
		nodeLabel.setDefaultOffset();
		node.setResizability(TSESolidObject.RESIZABILITY_LOCKED);		
		node.setName(Messages.getString("bpmnUiConstants.endEvent"));		
		node.setTooltipText(Messages.getString("endNodeCreater.endNode"));
		node.setAttribute(BpmnUIConstants.NODE_ATTR_TYPE, eventType);		
		node.setAttribute(BpmnUIConstants.NODE_ATTR_NAME, name);
		// node.setSize(node.getWidth() * 0.65, node.getHeight() * 0.65);
		node.setSize(60, 60);
		node.setShape(TSOvalShape.getInstance());
		node.setUI((TSEObjectUI) ui);		
	}

}
