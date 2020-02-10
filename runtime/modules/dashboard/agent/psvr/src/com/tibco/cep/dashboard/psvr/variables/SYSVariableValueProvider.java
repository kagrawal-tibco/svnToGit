package com.tibco.cep.dashboard.psvr.variables;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.studio.dashboard.core.query.BEViewsQueryDateTypeInterpreter;
import com.tibco.cep.studio.dashboard.core.variables.Variable;

public class SYSVariableValueProvider implements VariableValueProvider {
	
	@Override
	public String getIdentifier() {
		return "SYS";
	}

	@Override
	public String getValue(VariableContext context, Variable variable) {
		String value = "#"+variable.getArgument();
		if (BEViewsQueryDateTypeInterpreter.getPredefinedBind(value) != null) {
			return BEViewsQueryDateTypeInterpreter.convertBindValueToString(value);
		}
		context.getLogger().log(Level.WARN, "Invalid argument in "+variable);
		return "";
	}
	
}
