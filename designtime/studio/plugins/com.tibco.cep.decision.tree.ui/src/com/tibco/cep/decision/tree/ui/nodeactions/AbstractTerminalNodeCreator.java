package com.tibco.cep.decision.tree.ui.nodeactions;

import org.eclipse.emf.ecore.EFactory;

import com.tibco.cep.decision.tree.common.model.FlowElement;
import com.tibco.cep.decision.tree.common.model.node.terminal.TerminalFactory;
import com.tibco.cep.diagramming.drawing.LayoutManager;
import com.tomsawyer.drawing.geometry.shared.TSOvalShape;
import com.tomsawyer.graphicaldrawing.TSENode;

/*
@author ssailapp
@date Sep 19, 2011
 */

@SuppressWarnings("serial")
public abstract class AbstractTerminalNodeCreator extends AbstractNodeCreator {

	public AbstractTerminalNodeCreator(LayoutManager layoutManager, FlowElement flowElement) {
		super(layoutManager, flowElement);
	}
	
	protected void configureNodeUI(TSENode node) {
		node.setSize(node.getWidth() * 0.65, node.getHeight() * 0.65);
		node.setShape(TSOvalShape.getInstance());
	}
		
	protected EFactory getFactory() {
		return TerminalFactory.eINSTANCE;
	}
	
	protected boolean canShowNodeLabel() {
		return true;
	}
	
	protected boolean canShowNodeName() {
		return false;
	}
}
