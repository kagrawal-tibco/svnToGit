package com.tibco.cep.decision.tree.ui.nodeactions;

import org.eclipse.emf.ecore.EFactory;

import com.tibco.cep.decision.tree.common.model.FlowElement;
import com.tibco.cep.decision.tree.common.model.node.action.ActionFactory;
import com.tibco.cep.diagramming.drawing.LayoutManager;
import com.tomsawyer.drawing.geometry.shared.TSRectShape;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSESolidObject;

/*
@author ssailapp
@date Sep 26, 2011
 */

@SuppressWarnings("serial")
public abstract class AbstractActionNodeCreator extends AbstractNodeCreator {

	protected ActionFactory factory;
	
	public AbstractActionNodeCreator(LayoutManager layoutManager, FlowElement flowElement) {
		super(layoutManager, flowElement);
		factory = ActionFactory.eINSTANCE;
	}
	
	protected void configureNodeUI(TSENode node) {
		//node.setSize(40, 20);
		node.setShape(new TSRectShape());
		node.setResizability(TSESolidObject.RESIZABILITY_TIGHT_WIDTH);
	}
	
	protected EFactory getFactory() {
		return ActionFactory.eINSTANCE;
	}
}

