package com.tibco.cep.studio.core.rules;

public interface IRulesProblem {

	public int getLine();
	public int getOffset();
	public int getLength();
	public int getSeverity();
	public int getProblemCode();
	public String getErrorMessage();

}
