package com.tibco.cep.bpmn.core.codegen;


public interface BaseGenerator {
	

	BaseGenerator getParent();
	
	void generate() throws Exception ;

}
