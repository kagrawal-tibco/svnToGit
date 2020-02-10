package com.tibco.cep.dashboard.psvr.variables;

import com.tibco.cep.studio.dashboard.core.variables.Variable;

public class CONSTANTVariableValueProvider implements VariableValueProvider {

	@Override
	public String getIdentifier() {
		return "CONSTANT";
	}

	@Override
	public String getValue(VariableContext context, Variable variable) {
		return variable.getArgument();
	}
	
}
