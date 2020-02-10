package com.tibco.cep.bpmn.ui.graph.model.factory.events;

import org.eclipse.emf.ecore.EClass;

import com.tibco.cep.bpmn.ui.editor.BpmnLayoutManager;

public abstract class AbstractThrowEventNodeUIFactory extends AbstractEventNodeUIFactory {

	private static final long serialVersionUID = -2589517160708848234L;
	
	public AbstractThrowEventNodeUIFactory(String name, String referredBEResource, String toolId,BpmnLayoutManager layoutManager, EClass nodeType, EClass eventDefType) {
		super(name,referredBEResource, toolId, layoutManager, nodeType,eventDefType);
	}
}
