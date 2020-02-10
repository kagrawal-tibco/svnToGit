package com.tibco.cep.studio.dashboard.core.variables;

import java.text.ParseException;
import java.util.Collections;
import java.util.List;

public class GVARVariableProvider implements VariableProvider {

	@Override
	public String getIdentifier() {
		return "GVAR";
	}
	
	@Override
	public boolean isEditable() {
		return false;
	}
	
	@Override
	public List<VariableArgument> getSupportedArguments() {
		return Collections.emptyList();
	}

	@Override
	public Variable parse(String expression) throws ParseException {
		if (expression == null) {
			throw new ParseException("No argument specified for "+getIdentifier(),-1);
		}
		if (expression.trim().length() == 0) {
			throw new ParseException("Invalid argument specified for "+getIdentifier(), -1);
		}	
		//TODO validate the global variable against the project 
		return new Variable(getIdentifier(),expression);
	}

}