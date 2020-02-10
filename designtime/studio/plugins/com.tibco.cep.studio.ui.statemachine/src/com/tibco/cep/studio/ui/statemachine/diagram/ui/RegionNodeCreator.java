package com.tibco.cep.studio.ui.statemachine.diagram.ui;

import com.tibco.cep.diagramming.drawing.LayoutManager;
import com.tibco.cep.diagramming.ui.FinalStateNodeUI;
import com.tibco.cep.diagramming.ui.InitialNodeUI;
import com.tibco.cep.studio.ui.statemachine.diagram.StateMachineLayoutManager;
import com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils;
import com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils.STATE;
import com.tomsawyer.drawing.TSGraphTailor;
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
/**
 * 
 * @author hitesh
 *
 */
public class RegionNodeCreator extends TSNodeBuilder {

	private TSEGraph regionGraph;
	private TSEGraphManager graphManager;
	private LayoutManager layoutManager;

	private static final long serialVersionUID = 1L;
	
	public RegionNodeCreator(LayoutManager layoutManager) {
		this.layoutManager = layoutManager;
	}
	
	public TSENode addNode(TSEGraph graph) {

		RegionGraphNodeUI regionUI = new RegionGraphNodeUI();
		
		TSENode regionNode = super.addNode(graph);
		graphManager = (TSEGraphManager) graph.getOwnerGraphManager();
		regionGraph = (TSEGraph) graphManager.addGraph();
		
		TSGraphTailor tailor = regionGraph.getTailor();
		tailor.setMargin(3.0);
		tailor.setNestedViewSpacing(3.0);
		
		String tag = StateMachineUtils.getNodeName(graph,StateMachineUtils.UNIQUE_REGION);
		regionNode.setAttribute(StateMachineUtils.STATE_TYPE, STATE.REGION);
		regionNode.setAttribute(StateMachineUtils.isExpanded, true); //isExpanded
		
		InitialNodeUI initUI = new InitialNodeUI();
		TSENode childStartNode = (TSENode) super.addNode(regionGraph);
		childStartNode.setUI((TSEObjectUI) initUI);
		childStartNode.setSize(childStartNode.getWidth() * 0.65, childStartNode.getHeight() * 0.65);
		childStartNode.setShape(TSOvalShape.getInstance());
		childStartNode.setCenter(10, 10);
		childStartNode.setResizability(TSESolidObject.RESIZABILITY_LOCKED);
		childStartNode.setName("Start");
		childStartNode.setAttribute(StateMachineUtils.STATE_TYPE, STATE.START);
		TSENodeLabel childStartNodeLabel = ((TSENodeLabel) childStartNode.addLabel());
		((TSEAnnotatedUI) childStartNodeLabel.getUI()).setTextAntiAliasingEnabled(true);
		childStartNodeLabel.setName("Start");
		childStartNodeLabel.setDefaultOffset();
		childStartNode.setUI((TSEObjectUI) initUI);

		FinalStateNodeUI finalUI = new FinalStateNodeUI();
		TSENode childEndNode = (TSENode) super.addNode(regionGraph);
		childEndNode.setShape(TSOvalShape.getInstance());
		childEndNode.setResizability(TSESolidObject.RESIZABILITY_LOCKED);
		childEndNode.setName("End");
		childEndNode.setAttribute(StateMachineUtils.STATE_TYPE, STATE.END);
		childEndNode.setSize(childEndNode.getWidth() * 0.65, childEndNode.getHeight() * 0.65);
		TSENodeLabel childEndNodeLabel = ((TSENodeLabel) childEndNode.addLabel());
		((TSEAnnotatedUI) childEndNodeLabel.getUI()).setTextAntiAliasingEnabled(true);
		childEndNodeLabel.setName("End");
		childEndNodeLabel.setDefaultOffset();
		childEndNode.setCenter(60, 10);
		childEndNode.setUI((TSEObjectUI) finalUI);

		regionNode.setChildGraph(regionGraph);
		regionNode.setResizability(TSESolidObject.RESIZABILITY_LOCKED);
		regionNode.setName(tag);
		regionNode.setSize(80, 40);
		
		if (layoutManager instanceof StateMachineLayoutManager){
			if (graph.getParent() != null) {
				((StateMachineLayoutManager) layoutManager).configureConcurrentOptions((TSENode) graph.getParent());
			}
			((StateMachineLayoutManager) layoutManager).configureCompositeRegionOptions(regionNode);
			if(childStartNodeLabel != null){
				((StateMachineLayoutManager) layoutManager).setNodeLabelOptions(childStartNodeLabel);
			}
			if(childEndNodeLabel != null){
				((StateMachineLayoutManager) layoutManager).setNodeLabelOptions(childEndNodeLabel);
			}
		}	
		
		TSENestingManager.expand(regionNode);
		regionNode.setUI((TSEObjectUI) regionUI);
		
		return regionNode;
	}
}
