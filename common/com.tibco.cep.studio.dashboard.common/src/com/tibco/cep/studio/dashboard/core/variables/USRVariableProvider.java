package com.tibco.cep.studio.dashboard.core.variables;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

public class USRVariableProvider implements VariableProvider {

	private static enum ARGUMENT { TOKEN, NAME, ROLE };

	private List<VariableArgument> arguments;

	public USRVariableProvider(){
		arguments = new LinkedList<VariableArgument>();
		for (ARGUMENT argument : ARGUMENT.values()) {
			arguments.add(new VariableArgument("${"+getIdentifier()+":"+argument+"}",argument.toString()));
		}
	}

	@Override
	public String getIdentifier() {
		return "USR";
	}

	@Override
	public boolean isEditable() {
		return false;
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
		try {
			ARGUMENT.valueOf(expression.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new ParseException("Invalid argument specified for "+getIdentifier(), -1);
		}
		return new Variable(getIdentifier(),expression);
	}

}