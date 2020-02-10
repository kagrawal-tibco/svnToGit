package com.tibco.cep.bpmn.model.designtime.utils;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;


public interface WrappedObjectVisitor <C extends EClass,O extends EObject> {
	
	 boolean visit(EObjectWrapper<C, O> eObjWrapper);
	
}
