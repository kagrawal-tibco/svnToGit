package com.tibco.cep.studio.ui.statemachine.diagram.ui;

import com.tibco.cep.designtime.core.model.states.StateComposite;
import com.tibco.cep.diagramming.ui.InitialNodeUI;
import com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils;
import com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils.STATE;
import com.tomsawyer.drawing.geometry.shared.TSOvalShape;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSENodeLabel;
import com.tomsawyer.graphicaldrawing.builder.TSNodeBuilder;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEAnnotatedUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEObjectUI;
/**
 * 
 * @author sasahoo
 *
 */
@SuppressWarnings("serial")
public class StartStateNodeCreator extends TSNodeBuilder {
	
	/* (non-Javadoc)
	 * @see com.tomsawyer.graphicaldrawing.builder.TSNodeBuilder#addNode(com.tomsawyer.graphicaldrawing.TSEGraph)
	 */
	public TSENode addNode(TSEGraph graph) {
		if(StateMachineUtils.isStateStartPresent(((StateComposite)graph.getUserObject()).getStateEntities(), "Start")){
		   return null;	
		}
		InitialNodeUI ui = new InitialNodeUI();
		TSENode node = super.addNode(graph);
		node.setName("Start");
		node.setAttribute(StateMachineUtils.STATE_TYPE, STATE.START);
		TSENodeLabel nodeLabel = (TSENodeLabel) node.addLabel();
		((TSEAnnotatedUI) nodeLabel.getUI()).setTextAntiAliasingEnabled(true);
		nodeLabel.setDefaultOffset();
		nodeLabel.setName("Start");
		node.setSize(node.getWidth() * 0.65, node.getHeight() * 0.65);
		node.setShape(TSOvalShape.getInstance());
		node.setUI((TSEObjectUI) ui);
		return node;
	}

}
