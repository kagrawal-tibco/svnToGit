package com.tibco.cep.bpmn.ui.graph.model.factory.ui.old;

import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.diagramming.ui.ExpandedSubprocessNodeUI;
import com.tibco.cep.diagramming.utils.ActivityTypes;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.awt.TSEColor;
import com.tomsawyer.graphicaldrawing.builder.TSNodeBuilder;
import com.tomsawyer.graphicaldrawing.complexity.TSENestingManager;

/**
 * 
 * @author ggrigore
 *
 */
public class SubProcessNodeCreator extends TSNodeBuilder {

	private static final long serialVersionUID = 1L;
	private String nodeName;
	private ActivityTypes activityType;
	
	
	public SubProcessNodeCreator(String name, ActivityTypes activityType) {
		this.nodeName = name;
		this.activityType = activityType;
	}
	
	public TSENode addNode(TSEGraph graph) {
		TSENode parentNode = super.addNode(graph);
		TSEGraph childGraph = (TSEGraph) graph.getOwnerGraphManager().addGraph();
		// childGraph.addEdge(childGraph.addNode(), childGraph.addNode());
		parentNode.setName(this.nodeName);
		parentNode.setAttribute(BpmnUIConstants.NODE_ATTR_TYPE, this.activityType);
		parentNode.setAttribute(BpmnUIConstants.NODE_ATTR_NAME, this.nodeName);
		parentNode.setChildGraph(childGraph);
		TSENestingManager.expand(parentNode);
		ExpandedSubprocessNodeUI subprocessUI = new ExpandedSubprocessNodeUI();
		// subprocessUI.setBorderColor(new TSEColor(46,0,136));
		subprocessUI.setFillColor(new TSEColor(255,220,81));
		subprocessUI.setBorderDrawn(true);
		subprocessUI.setDrawChildGraphMark(false);
		subprocessUI.setTextAntiAliasingEnabled(true);
		parentNode.setUI(subprocessUI);
		// ((DecisionGraphLayoutManager) this.getLayoutManager()).setLayoutOptionsForSubProcess(childGraph);
		
		// add a start and an end node
//		this.addStartNode(childGraph);
//		new StartNodeCreator().addNode(childGraph);
//		new EndNodeCreator().addNode(childGraph);
		
		return parentNode;
	}
}
