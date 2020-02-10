package com.tibco.cep.dashboard.psvr.variables;

import com.tibco.cep.studio.dashboard.core.variables.Variable;

public interface VariableValueProvider {
	
	public String getIdentifier();
	
	public String getValue(VariableContext context, Variable variable);

}
