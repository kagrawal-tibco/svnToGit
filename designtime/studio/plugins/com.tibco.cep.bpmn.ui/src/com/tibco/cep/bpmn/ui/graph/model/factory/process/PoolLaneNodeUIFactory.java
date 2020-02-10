package com.tibco.cep.bpmn.ui.graph.model.factory.process;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.Messages;
import com.tibco.cep.bpmn.ui.editor.BpmnLayoutManager;
import com.tibco.cep.bpmn.ui.graph.model.AbstractNodeUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.factory.ui.LaneNodeUI;
import com.tibco.cep.bpmn.ui.graph.model.factory.ui.PoolLaneAbstractNodeUI;
import com.tibco.cep.bpmn.ui.graph.model.factory.ui.PoolNodeUI;
import com.tomsawyer.graph.TSGraph;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSESolidObject;
import com.tomsawyer.graphicaldrawing.complexity.TSENestingManager;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEObjectUI;

/**
 * @author pdhar
 *
 */
public class PoolLaneNodeUIFactory extends AbstractNodeUIFactory {

	private static final long serialVersionUID = -7538090704847300230L;
	private boolean isPool = false;

	public PoolLaneNodeUIFactory(String name,String referredBEResource, String toolId,
							BpmnLayoutManager layoutManager,
							boolean isPool) {
		super(name, referredBEResource, toolId, layoutManager, BpmnModelClass.LANE,isPool);
		this.isPool = isPool;
		if(getNodeName() == null) {
			if(!isPool){
				setNodeName(Messages.getString("title.lane"));//$NON-NLS-1$
			} else {
				setNodeName(Messages.getString("title.pool"));//$NON-NLS-1$
			}
		}
		
	}
	
	public TSENode addNode(TSEGraph graph) {
		// XYZ
		TSENode node = super.addNode(graph);
		if (isPool)
			node.setAttribute(BpmnUIConstants.NODE_ATTR_EXT_TYPE, BpmnModelClass.PROCESS);
		return node;
	}
	
	@Override
	public TSEObjectUI initGraphUI(Object ...args) {
		PoolLaneAbstractNodeUI ui = null;
		if(args.length > 0) {
			if(args[0] instanceof Boolean) {
				isPool = ((Boolean) args[0]);
			}
		}
		if (!isPool) {
			ui = new LaneNodeUI();
		} else {
			ui = new PoolNodeUI();
			ui.isFirst = isPool;
		}
		ui.setOuterRoundRect(true);
		ui.setFillColor(BpmnUIConstants.LANE_FILL_COLOR);
		ui.setBorderDrawn(true);
		ui.setDrawChildGraphMark(false);
		ui.setTextAntiAliasingEnabled(true);
		return ui;
	}
	
//	public PoolLaneAbstractNodeUI getNodeUI(boolean isPool) {
//		this.isPool = isPool;
//		PoolLaneAbstractNodeUI ui = (PoolLaneAbstractNodeUI) initGraphUI();
//		return ui;
//	}
	
	

	
	@Override
	public void decorateNode(TSENode node) {
		node.setResizability(TSESolidObject.RESIZABILITY_NO_FIT);
		TSGraph graph = node.getOwnerGraph();
		TSEGraph childGraph = (TSEGraph) node.getChildGraph();
		if(childGraph == null) {
			// XYZ
			childGraph = (TSEGraph) graph.getOwnerGraphManager().addGraph();
			node.setChildGraph(childGraph);
			childGraph.setUserObject(node.getUserObject());
		}
			
		TSENestingManager.expand(node);
		node.setUI(getNodeUI());		
	}
	
	@Override
	public void layoutNode(TSENode node) {
		if (getLayoutManager() != null) {
//			if(isPool){
				getLayoutManager().setLayoutOptionsForPool(node);
//			}else{
//				getLayoutManager().setLayoutOptionsForLane(node);
//				getLayoutManager().setLayoutOptionsForSubProcess((TSEGraph) node.getChildGraph());
//			}
			
		}
		
	}
	

}
