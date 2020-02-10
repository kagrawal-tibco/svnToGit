package com.tibco.cep.studio.ui.statemachine.diagram.ui;

import java.util.LinkedList;

import com.tibco.cep.diagramming.drawing.LayoutManager;
import com.tibco.cep.diagramming.ui.FinalStateNodeUI;
import com.tibco.cep.diagramming.ui.InitialNodeUI;
import com.tibco.cep.studio.ui.statemachine.diagram.StateMachineLayoutManager;
import com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils;
import com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils.STATE;
import com.tomsawyer.drawing.geometry.shared.TSOvalShape;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSEGraphManager;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSENodeLabel;
import com.tomsawyer.graphicaldrawing.TSESolidObject;
import com.tomsawyer.graphicaldrawing.builder.TSNodeBuilder;
import com.tomsawyer.graphicaldrawing.complexity.TSENestingManager;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEAnnotatedUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEObjectUI;
import com.tomsawyer.service.layout.TSLayoutConstants;
/**
 * 
 * @author sasahoo
 *
 */
@SuppressWarnings("serial")
public class CompositeStateNodeCreator extends TSNodeBuilder {

	private TSEGraph compositeGraph;
	private TSEGraphManager graphManager;
	private LayoutManager layoutManager;
	
	/* (non-Javadoc)
	 * @see com.tomsawyer.graphicaldrawing.builder.TSNodeBuilder#addNode(com.tomsawyer.graphicaldrawing.TSEGraph)
	 */
	public CompositeStateNodeCreator(LayoutManager layoutManager) {
		this.layoutManager = layoutManager;
	}
	
	public TSENode addNode(TSEGraph graph) {
		
		CompositeStateNodeGraphUI compositeUI = new CompositeStateNodeGraphUI();
		TSENode compositeNode = super.addNode(graph);
		graphManager = (TSEGraphManager) graph.getOwnerGraphManager();
		compositeGraph = (TSEGraph) graphManager.addGraph();
		
		String tag = StateMachineUtils.getNodeName(graph,StateMachineUtils.UNIQUE_COMPOSITE_STATE);
		
		compositeNode.setAttribute(StateMachineUtils.STATE_TYPE, STATE.COMPOSITE);
		
		compositeNode.setAttribute(StateMachineUtils.isExpanded, true); //isExpanded
		
		InitialNodeUI initUI = new InitialNodeUI();
		TSENode childStartNode = (TSENode) super.addNode(compositeGraph);
		childStartNode.setSize(childStartNode.getWidth() * 0.65, childStartNode.getHeight() * 0.65);
		childStartNode.setShape(TSOvalShape.getInstance());
		childStartNode.setCenter(10, 10);
		childStartNode.setName("Start");
		childStartNode.setResizability(TSESolidObject.RESIZABILITY_LOCKED);
		childStartNode.setAttribute(StateMachineUtils.STATE_TYPE, STATE.START);
		TSENodeLabel childStartNodeLabel = ((TSENodeLabel) childStartNode.addLabel());
		((TSEAnnotatedUI) childStartNodeLabel.getUI()).setTextAntiAliasingEnabled(true);
		childStartNodeLabel.setDefaultOffset();
		childStartNodeLabel.setName("Start");
		childStartNode.setUI((TSEObjectUI) initUI);
		
		FinalStateNodeUI finalUI = new FinalStateNodeUI();
		TSENode childEndNode = (TSENode) super.addNode(compositeGraph);
		childEndNode.setSize(childEndNode.getWidth() * 0.65, childEndNode.getHeight() * 0.65);
		childEndNode.setShape(TSOvalShape.getInstance());
		childEndNode.setCenter(70, 10);
		childEndNode.setName("End");
		childEndNode.setResizability(TSESolidObject.RESIZABILITY_LOCKED);
		childEndNode.setAttribute(StateMachineUtils.STATE_TYPE, STATE.END);
		TSENodeLabel childEndNodeLabel = ((TSENodeLabel) childEndNode.addLabel());
		((TSEAnnotatedUI) childEndNodeLabel.getUI()).setTextAntiAliasingEnabled(true);
		childEndNodeLabel.setDefaultOffset();
		childEndNodeLabel.setName("End");
		childEndNode.setUI((TSEObjectUI) finalUI);
		
		compositeNode.setChildGraph(compositeGraph);
		compositeNode.setResizability(TSESolidObject.RESIZABILITY_LOCKED);
		
		if (layoutManager instanceof StateMachineLayoutManager){
			((StateMachineLayoutManager) layoutManager).configureCompositeRegionOptions(compositeNode);
			
			LinkedList<TSENode> list = new LinkedList<TSENode>();
			list.add(childStartNode);
			list.add(childEndNode);
			((StateMachineLayoutManager) layoutManager).addSequenceConstraint(
				list, TSLayoutConstants.DIRECTION_LEFT_TO_RIGHT);
			
			if(childStartNodeLabel != null){
				((StateMachineLayoutManager) layoutManager).setNodeLabelOptions(childStartNodeLabel);
			}
			if(childEndNodeLabel != null){
				((StateMachineLayoutManager) layoutManager).setNodeLabelOptions(childEndNodeLabel);
			}
		}	
		
		TSENestingManager.expand(compositeNode);
		
		compositeNode.setName(tag);
		compositeNode.setSize(60, 30);
		compositeNode.setUI((TSEObjectUI) compositeUI);
		
		return compositeNode;
	}
}
