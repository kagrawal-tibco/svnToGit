package com.tibco.cep.dashboard.psvr.variables;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.studio.dashboard.core.variables.Variable;

public class VARVariableValueProvider implements VariableValueProvider {
	
	@Override
	public String getIdentifier() {
		return "VAR";
	}

	@Override
	public String getValue(VariableContext context, Variable variable) {
		String argument = variable.getArgument();
		if(context.getTuple() == null) {
			context.getLogger().log(Level.WARN, "No tuple found, defaulting "+argument+" to ''");
			return "";
		}
		return context.getTuple().getFieldValueByName(argument).toString();
	}


}
