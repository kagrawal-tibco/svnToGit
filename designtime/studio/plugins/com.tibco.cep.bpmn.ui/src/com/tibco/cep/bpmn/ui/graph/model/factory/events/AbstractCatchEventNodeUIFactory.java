package com.tibco.cep.bpmn.ui.graph.model.factory.events;

import org.eclipse.emf.ecore.EClass;

import com.tibco.cep.bpmn.ui.editor.BpmnLayoutManager;

public abstract class AbstractCatchEventNodeUIFactory extends AbstractEventNodeUIFactory  {

	private static final long serialVersionUID = -1227676974840107200L;
	
	public AbstractCatchEventNodeUIFactory(String name, String referredBEResource, String toolId, BpmnLayoutManager layoutManager, EClass nodeType, EClass eventDefType) {
		super(name, referredBEResource, toolId, layoutManager, nodeType, eventDefType);
	}
}
