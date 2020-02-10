package com.tibco.cep.studio.dashboard.core.variables;

public class Variable {
	
	private String identifier;
	
	private String argument;

	public Variable(String identifier, String identifierArgument) {
		super();
		this.identifier = identifier;
		this.argument = identifierArgument;
	}
	
	public String getIdentifier() {
		return identifier;
	}
	
	public String getArgument() {
		return argument;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((identifier == null) ? 0 : identifier.hashCode());
		result = prime * result + ((argument == null) ? 0 : argument.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Variable other = (Variable) obj;
		if (identifier == null) {
			if (other.identifier != null) {
				return false;
			}
		} else if (!identifier.equals(other.identifier)) {
			return false;
		}
		if (argument == null) {
			if (other.argument != null) {
				return false;
			}
		} else if (!argument.equals(other.argument)) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(Variable.class.getSimpleName());
		sb.append("[identifier=");
		sb.append(identifier);
		sb.append(",argument=");
		sb.append(argument);
		sb.append("]");
		return sb.toString();
	}
	
}
