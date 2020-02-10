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
 * @author sasahoo
 *
 */
@SuppressWarnings("serial")
public class ConcurrentStateNodeCreator extends TSNodeBuilder {

	private TSEGraphManager graphManager;
	private TSEGraph concurrentGraph;
	private LayoutManager layoutManager;

	/* (non-Javadoc)
	 * @see com.tomsawyer.graphicaldrawing.builder.TSNodeBuilder#addNode(com.tomsawyer.graphicaldrawing.TSEGraph)
	 */
	
	public ConcurrentStateNodeCreator(LayoutManager layoutManager) {
		this.layoutManager = layoutManager;
	}
	
	public TSENode addNode(TSEGraph graph) {
		
		ConcurrentStateNodeUI concurentUI = new ConcurrentStateNodeUI();
		TSENode concurrentNode = super.addNode(graph);
		
		concurrentNode.setAttribute(StateMachineUtils.STATE_TYPE, STATE.CONCURRENT);
		
		concurrentNode.setAttribute(StateMachineUtils.isExpanded, true); //isExpanded
		
		graphManager = (TSEGraphManager) graph.getOwnerGraphManager();
		concurrentGraph = (TSEGraph) graphManager.addGraph();
		String tag = StateMachineUtils.getNodeName(graph,StateMachineUtils.UNIQUE_CONCURRENT_STATE);

		TSENode childRegionCompositeNode_0 = super.addNode(concurrentGraph);

		String tag_region_0="Region_0";

		childRegionCompositeNode_0.setAttribute(StateMachineUtils.STATE_TYPE, STATE.REGION);
		childRegionCompositeNode_0.setAttribute(StateMachineUtils.isExpanded, true); //isExpanded

		TSENode childRegionCompositeNode_1 = super.addNode(concurrentGraph);

		String tag_region_1="Region_1";

		childRegionCompositeNode_1.setAttribute(StateMachineUtils.STATE_TYPE, STATE.REGION);
		childRegionCompositeNode_1.setAttribute(StateMachineUtils.isExpanded, true); //isExpanded
		
		createCompositeStateForConcurrent(childRegionCompositeNode_0,tag_region_0);
		createCompositeStateForConcurrent(childRegionCompositeNode_1,tag_region_1);

		childRegionCompositeNode_0.setCenter(0, 10);
		childRegionCompositeNode_1.setCenter(0, -65);

		concurrentNode.setChildGraph(concurrentGraph);
		concurrentNode.setResizability(TSESolidObject.RESIZABILITY_LOCKED);
		
		TSENestingManager.expand(concurrentNode);

		concurrentNode.setName(tag);
		concurrentNode.setSize(300, 200);
		concurrentNode.setUI((TSEObjectUI) concurentUI);

		if (layoutManager instanceof StateMachineLayoutManager){
			((StateMachineLayoutManager) layoutManager).configureConcurrentOptions(concurrentNode);
		}
		
		return concurrentNode;
	} 

	/**
	 * @param parent
	 * @param region
	 * @param tag
	 * @return
	 */
	private TSEGraph createCompositeStateForConcurrent(TSENode parent,/*StateComposite region,*/String tag) {

		TSEGraph childCompositeGraph = (TSEGraph) parent.getOwnerGraphManager().addGraph();

		TSGraphTailor tailor = childCompositeGraph.getTailor();
		tailor.setMargin(3.0);
		tailor.setNestedViewSpacing(3.0);

		InitialNodeUI initUI = new InitialNodeUI();
		FinalStateNodeUI finalUI = new FinalStateNodeUI();
		RegionGraphNodeUI compositeUI = new RegionGraphNodeUI();

		TSENode childStartNode = super.addNode(childCompositeGraph);
		childStartNode.setSize(childStartNode.getWidth() * 0.65, childStartNode.getHeight() * 0.65);
		childStartNode.setShape(TSOvalShape.getInstance());
		childStartNode.setCenter(10, 10);
		childStartNode.setResizability(TSESolidObject.RESIZABILITY_LOCKED);
		childStartNode.setName("Start");
		childStartNode.setAttribute(StateMachineUtils.STATE_TYPE, STATE.START);
		TSENodeLabel childStartNodeLabel = ((TSENodeLabel) childStartNode.addLabel());
		((TSEAnnotatedUI) childStartNodeLabel.getUI()).setTextAntiAliasingEnabled(true);
		childStartNodeLabel.setDefaultOffset();
		childStartNodeLabel.setName("Start");
		childStartNode.setUI((TSEObjectUI) initUI);

		TSENode childEndNode = super.addNode(childCompositeGraph);
		childEndNode.setSize(childEndNode.getWidth() * 0.65, childEndNode.getHeight() * 0.65);
		childEndNode.setShape(TSOvalShape.getInstance());
		childEndNode.setResizability(TSESolidObject.RESIZABILITY_LOCKED);
		childEndNode.setName("End");
		childEndNode.setAttribute(StateMachineUtils.STATE_TYPE, STATE.END);
		TSENodeLabel childEndNodeLabel = ((TSENodeLabel) childEndNode.addLabel());
		((TSEAnnotatedUI) childEndNodeLabel.getUI()).setTextAntiAliasingEnabled(true);
		childEndNodeLabel.setDefaultOffset();
		childEndNodeLabel.setName("End");
		childEndNode.setCenter(60, 10);
		childEndNode.setUI((TSEObjectUI) finalUI);
		
		parent.setChildGraph(childCompositeGraph);
		parent.setName(tag);
		parent.setResizability(TSESolidObject.RESIZABILITY_LOCKED);
		parent.setSize(120, 70);
		
		if (layoutManager instanceof StateMachineLayoutManager){
			((StateMachineLayoutManager) layoutManager).configureCompositeRegionOptions(parent);
			if(childStartNodeLabel != null){
				((StateMachineLayoutManager) layoutManager).setNodeLabelOptions(childStartNodeLabel);
			}
			if(childEndNodeLabel != null){
				((StateMachineLayoutManager) layoutManager).setNodeLabelOptions(childEndNodeLabel);
			}
		}
		
		TSENestingManager.expand(parent);
		parent.setUI((TSEObjectUI) compositeUI);
		
		return childCompositeGraph;
	}
}
