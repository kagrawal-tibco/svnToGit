package com.tibco.cep.studio.ui.statemachine.diagram.ui;

import com.tibco.cep.diagramming.drawing.LayoutManager;
import com.tibco.cep.studio.ui.statemachine.diagram.StateMachineDiagramManager;
import com.tibco.cep.studio.ui.statemachine.diagram.StateMachineLayoutManager;
import com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils;
import com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils.STATE;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSENodeLabel;
import com.tomsawyer.graphicaldrawing.TSESolidObject;
import com.tomsawyer.graphicaldrawing.builder.TSNodeBuilder;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEAnnotatedUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEObjectUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEShapeNodeUI;
/**
 * 
 * @author sasahoo
 *
 */
@SuppressWarnings("serial")
public class SimpleStateNodeCreator extends TSNodeBuilder{

	private LayoutManager layoutManager;
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.model.SimpleStateNodeCreator#addNode(com.tomsawyer.graphicaldrawing.TSEGraph)
	 */
	public SimpleStateNodeCreator(LayoutManager layoutManager) {
		this.layoutManager = layoutManager;
	}
	
	public TSENode addNode(TSEGraph graph) {
		// SimpleStateNodeUI ui = new SimpleStateNodeUI();
		TSEShapeNodeUI ui = StateMachineDiagramManager.getStateNodeUI();
		
		TSENode node = super.addNode(graph);
		try {
			String tag = StateMachineUtils.getNodeName(graph,StateMachineUtils.UNIQUE_SIMPLE_STATE);
			node.setName(tag);
			node.setAttribute(StateMachineUtils.STATE_TYPE, STATE.SIMPLE);
			node.setSize(40, 20);
			node.setResizability(TSESolidObject.RESIZABILITY_LOCKED);
			TSENodeLabel nodeLabel = (TSENodeLabel) node.addLabel();
			nodeLabel.setDefaultOffset();
			((TSEAnnotatedUI) nodeLabel.getUI()).setTextAntiAliasingEnabled(true);
			nodeLabel.setName(tag);
			if ((layoutManager instanceof StateMachineLayoutManager) && (nodeLabel != null)){
				((StateMachineLayoutManager) layoutManager).setNodeLabelOptions(nodeLabel);
			}
			node.setUI((TSEObjectUI) ui);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return node;
	}
}
