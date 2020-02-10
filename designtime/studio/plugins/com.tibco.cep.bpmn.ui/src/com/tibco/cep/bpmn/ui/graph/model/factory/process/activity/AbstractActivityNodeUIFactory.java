package com.tibco.cep.bpmn.ui.graph.model.factory.process.activity;

import org.eclipse.emf.ecore.EClass;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.ui.editor.BpmnLayoutManager;
import com.tibco.cep.bpmn.ui.graph.model.AbstractNodeUIFactory;

/**
 * @author pdhar
 *
 */
public abstract class AbstractActivityNodeUIFactory extends AbstractNodeUIFactory {


	/**
	 * 
	 */
	private static final long serialVersionUID = -5990275412922795305L;

	public AbstractActivityNodeUIFactory(String name, String referredBEResource, String toolId,
			BpmnLayoutManager layoutManager, EClass nodeType,Object ...args) {
		super(name,  referredBEResource,toolId, layoutManager, nodeType,args);
	}

	
	public AbstractActivityNodeUIFactory(String name, String referredBEResource, String toolId,BpmnLayoutManager layoutManager,Object ...args) {
		this(name, referredBEResource,  toolId, layoutManager, BpmnModelClass.ACTIVITY,args);
	}
	
	public void removeBoundaryEvent(){
		
	}

}
