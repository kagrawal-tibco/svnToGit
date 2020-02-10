package com.tibco.cep.dashboard.psvr.variables;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.studio.dashboard.core.variables.Variable;

class ENVVariableValueProvider implements VariableValueProvider {

	@Override
	public String getIdentifier() {
		return "ENV";
	}

	@Override
	public String getValue(VariableContext context, Variable variable) {
		String key = variable.getArgument();
		String value = System.getProperty(key);
		if (value == null) {
			context.getLogger().log(Level.WARN, "No value found for " + key + " in " + variable);
			value = "";
		}
		return value;
	}

}