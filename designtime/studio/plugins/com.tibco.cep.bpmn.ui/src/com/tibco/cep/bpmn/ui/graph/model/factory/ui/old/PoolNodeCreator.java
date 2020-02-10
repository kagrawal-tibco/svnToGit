package com.tibco.cep.bpmn.ui.graph.model.factory.ui.old;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.Messages;
import com.tibco.cep.bpmn.ui.editor.BpmnLayoutManager;
import com.tibco.cep.bpmn.ui.graph.model.factory.ui.PoolNodeUI;
import com.tibco.cep.diagramming.utils.ActivityTypes;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.builder.TSNodeBuilder;
import com.tomsawyer.graphicaldrawing.complexity.TSENestingManager;

/**
 * 
 * @author ggrigore
 *
 */
public class PoolNodeCreator extends TSNodeBuilder {

	private static final long serialVersionUID = 1L;
	private String nodeName;
	private ActivityTypes activityType;
	private BpmnLayoutManager layoutManager;
	
	
	public PoolNodeCreator(String name, ActivityTypes activityType, BpmnLayoutManager layoutManager) {
		this.nodeName = name;
		this.activityType = activityType;
		this.layoutManager = layoutManager;
	}
	
	public TSENode addNode(TSEGraph graph) {
		TSENode parentNode = super.addNode(graph);
		TSEGraph childGraph = (TSEGraph) graph.getOwnerGraphManager().addGraph();
		TSENode startNode = super.addNode(childGraph);
		StartNodeCreator.decorateStartNode(startNode, 
				Messages.getString("title.start.event"), //$NON-NLS-1$ 
				BpmnModelClass.START_EVENT);
		TSENode endNode = super.addNode(childGraph);
		//I18n
		EndNodeCreator.decorateEndNode(endNode, 
									Messages.getString("bpmnUiConstants.endEvent"), 
									BpmnModelClass.END_EVENT);
		
		parentNode.setName(this.nodeName);
		parentNode.setAttribute(BpmnUIConstants.NODE_ATTR_TYPE, this.activityType);
		parentNode.setAttribute(BpmnUIConstants.NODE_ATTR_NAME, this.nodeName);
		parentNode.setChildGraph(childGraph);
		TSENestingManager.expand(parentNode);
		
		PoolNodeUI ui = new PoolNodeUI();
		ui.setOuterRoundRect(true);
		ui.setFillColor(BpmnUIConstants.LANE_FILL_COLOR);
		ui.setBorderDrawn(true);
		ui.setDrawChildGraphMark(false);
		ui.setTextAntiAliasingEnabled(true);
		parentNode.setUI(ui);
		if (layoutManager != null) {
			layoutManager.setLayoutOptionsForSubProcess(childGraph);
		}
		
		// System.out.println("child graph nodes: " + parentNode.getChildGraph().numberOfNodes());
		
		// add a start and an end node
//		this.addStartNode(childGraph);
//		new StartNodeCreator().addNode(childGraph);
//		new EndNodeCreator().addNode(childGraph);
		
		return parentNode;
	}

	/*
	public TSENode addNode2(TSEGraph graph) {
		CompositeStateNodeGraphUI compositeUI = new CompositeStateNodeGraphUI();
		TSENode compositeNode = super.addNode(graph);
		graphManager = (TSEGraphManager) graph.getOwnerGraphManager();
		compositeGraph = (TSEGraph) graphManager.addGraph();
		
		// add start and end nodes
		// ...
		
		compositeNode.setChildGraph(compositeGraph);
		compositeNode.setResizability(TSESolidObject.RESIZABILITY_LOCKED);
		
		// set layout options
		// ...
		
		TSENestingManager.expand(compositeNode);
		
		compositeNode.setName(tag);
		compositeNode.setSize(60, 30);
		compositeNode.setUI((TSEObjectUI) compositeUI);		
	}
	*/
}
