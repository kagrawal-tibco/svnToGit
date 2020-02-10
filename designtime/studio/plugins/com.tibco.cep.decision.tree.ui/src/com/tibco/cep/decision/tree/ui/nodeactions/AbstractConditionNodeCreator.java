package com.tibco.cep.decision.tree.ui.nodeactions;

import org.eclipse.emf.ecore.EFactory;

import com.tibco.cep.decision.tree.common.model.FlowElement;
import com.tibco.cep.decision.tree.common.model.node.condition.ConditionFactory;
import com.tibco.cep.diagramming.drawing.LayoutManager;
import com.tomsawyer.graphicaldrawing.TSENode;

/*
@author ssailapp
@date Sep 26, 2011
 */

@SuppressWarnings("serial")
public abstract class AbstractConditionNodeCreator extends AbstractNodeCreator {

	public AbstractConditionNodeCreator(LayoutManager layoutManager, FlowElement flowElement) {
		super(layoutManager, flowElement);
	}
	
	protected void configureNodeUI(TSENode node) {
		//node.setSize(40, 20);
	}
	
	protected EFactory getFactory() {
		return ConditionFactory.eINSTANCE;
	}
}
