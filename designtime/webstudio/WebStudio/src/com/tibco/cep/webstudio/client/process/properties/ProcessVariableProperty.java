package com.tibco.cep.webstudio.client.process.properties;

import java.util.LinkedList;
import java.util.List;

/**
 * This class holds the details of process variable.
 * 
 * @author dijadhav
 * 
 */
public class ProcessVariableProperty extends Property {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6578496796309283086L;
	private List<ProcessVariable> variables = new LinkedList<ProcessVariable>();

	/**
	 * Add the process variable in the list
	 * 
	 * @param processVariable
	 */
	public void addProcessVariable(ProcessVariable processVariable) {
		if (null != processVariable) {
			variables.add(processVariable);
		}
	}

	/**
	 * @return the variables
	 */
	public List<ProcessVariable> getVariables() {
		return variables;
	}

}
