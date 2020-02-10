package com.tibco.cep.studio.dashboard.core.variables;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public final class VariableExpression {
	
	private String expression;
	
	private String parsedExpression;
	
	private List<Variable> variables;

	VariableExpression(String expression) {
		super();
		this.expression = expression;
		this.variables = new LinkedList<Variable>();
	}
	
	public String getExpression() {
		return expression;
	}
	
	void setParsedExpression(String parsedExpression) {
		this.parsedExpression = parsedExpression;
	}

	public String getParsedExpression() {
		return parsedExpression;
	}
	
	void setVariables(List<Variable> variables) {
		this.variables = new LinkedList<Variable>(variables);
	}
	
	public List<Variable> getVariables() {
		return Collections.unmodifiableList(variables);
	}
	
	public boolean hasVariables(){
		return variables.isEmpty() == false;
	}
	
	public Variable getFirstVariable(){
		if (variables.isEmpty() == false) {
			return variables.get(0);
		}
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((expression == null) ? 0 : expression.hashCode());
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
		VariableExpression other = (VariableExpression) obj;
		if (expression == null) {
			if (other.expression != null) {
				return false;
			}
		} else if (!expression.equals(other.expression)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(VariableExpression.class.getSimpleName());
		sb.append("[expression=");
		sb.append(expression);
		sb.append(",parsedexpression=");
		sb.append(parsedExpression);
		sb.append(",variables=");
		sb.append(variables);
		sb.append("]");
		return sb.toString();
	}
}
