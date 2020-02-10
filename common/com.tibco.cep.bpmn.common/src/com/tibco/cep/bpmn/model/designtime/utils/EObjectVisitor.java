package com.tibco.cep.bpmn.model.designtime.utils;

import org.eclipse.emf.ecore.EObject;

public interface EObjectVisitor {
	
	boolean visit(EObject eObj);
	
}
