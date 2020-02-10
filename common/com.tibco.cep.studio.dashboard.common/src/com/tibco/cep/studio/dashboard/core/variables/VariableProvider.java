package com.tibco.cep.studio.dashboard.core.variables;

import java.text.ParseException;
import java.util.List;

public interface VariableProvider {
	
	public String getIdentifier();
	
	public boolean isEditable();
	
	public List<VariableArgument> getSupportedArguments();
	
	public Variable parse(String expression) throws ParseException;
	
}