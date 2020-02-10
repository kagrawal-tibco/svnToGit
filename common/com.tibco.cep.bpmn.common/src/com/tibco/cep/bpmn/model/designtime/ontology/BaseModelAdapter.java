package com.tibco.cep.bpmn.model.designtime.ontology;

import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.model.process.ProcessModel;

public abstract class BaseModelAdapter {
	
	protected ProcessModel processModel;
	protected EObject element;
	protected Object[] params;
	public BaseModelAdapter(ProcessModel processModel2, EObject element2,
			Object[] params) {
		this.processModel = processModel2;
		this.element = element2;
		this.params = params;
	}
	

}
