package com.tibco.cep.bpmn.core.codegen.temp;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.designtime.core.model.rule.Symbol;

public interface BaseGenerator<C extends EClass, O extends EObject> {

	Symbol[] getScopeSymbols();

	BaseGenerator<C,O> getParent();
	
	boolean visit(EObjectWrapper<C, O> eObjWrapper);

}
