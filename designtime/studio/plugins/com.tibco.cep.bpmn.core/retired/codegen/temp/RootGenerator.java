package com.tibco.cep.bpmn.core.codegen.temp;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.designtime.core.model.rule.Symbol;

public interface RootGenerator<C extends EClass, O extends EObject> extends BaseGenerator<C,O> {
	
	EObjectWrapper<C, O> getProcessWrapper();
	
	Symbol getJobSymbol();
	
	Symbol getProcessSymbol();

}
