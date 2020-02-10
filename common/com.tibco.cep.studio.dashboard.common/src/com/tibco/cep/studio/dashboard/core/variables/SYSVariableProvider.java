package com.tibco.cep.studio.dashboard.core.variables;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

import com.tibco.cep.studio.dashboard.core.query.BEViewsQueryDateTypeInterpreter;
import com.tibco.cep.studio.dashboard.core.query.BEViewsQueryDateTypeInterpreter.PRE_DEFINED_BINDS;

public class SYSVariableProvider implements VariableProvider {
	
	private List<VariableArgument> arguments;
	
	public SYSVariableProvider(){
		arguments = new LinkedList<VariableArgument>();
		for (BEViewsQueryDateTypeInterpreter.PRE_DEFINED_BINDS bind : BEViewsQueryDateTypeInterpreter.PRE_DEFINED_BINDS.values()) {
			arguments.add(new VariableArgument("${"+getIdentifier()+":"+bind+"}",bind.toString()));
		}
	}
	
	@Override
	public boolean isEditable() {
		return true;
	}

	@Override
	public String getIdentifier() {
		return "SYS";
	}
	
	@Override
	public List<VariableArgument> getSupportedArguments() {
		return arguments;
	}

	@Override
	public Variable parse(String expression) throws ParseException {
		if (expression == null) {
			throw new ParseException("No argument specified for "+getIdentifier(),-1);
		}
		if (expression.trim().length() == 0) {
			throw new ParseException("Invalid argument specified for "+getIdentifier(), -1);
		}		
		PRE_DEFINED_BINDS predefinedBind = BEViewsQueryDateTypeInterpreter.getPredefinedBind("#"+expression);
		if (predefinedBind == null) {
			throw new ParseException("Invalid argument specified for "+getIdentifier(), -1);
		}
		return new Variable(getIdentifier(),expression);
	}

}