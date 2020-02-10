package com.tibco.cep.mapper.codegen;

public interface IVariableType {

	String getTypeName();

	boolean isConcept();

	boolean isEvent();
	
	boolean isTimeEvent();
	
	boolean isPrimitive();

	boolean isProcess();

	boolean isArray();
	
	boolean isTypeRequiresBox();
	
}
