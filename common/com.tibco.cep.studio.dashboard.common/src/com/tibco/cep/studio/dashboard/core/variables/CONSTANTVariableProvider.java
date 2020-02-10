package com.tibco.cep.studio.dashboard.core.variables;

import java.text.ParseException;
import java.util.Collections;
import java.util.List;

public class CONSTANTVariableProvider implements VariableProvider {

	@Override
	public String getIdentifier() {
		return "CONSTANT";
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
		return new Variable(getIdentifier(),expression);
	}



}