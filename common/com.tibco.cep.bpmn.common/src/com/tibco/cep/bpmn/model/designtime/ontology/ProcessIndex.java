package com.tibco.cep.bpmn.model.designtime.ontology;

import java.util.Collection;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;


public interface ProcessIndex extends BaseIndex {
	
	public Collection<EObject> getFlowNodes(boolean hasIncoming, boolean hasOutgoing,
			EClass  type);
	
	

}
