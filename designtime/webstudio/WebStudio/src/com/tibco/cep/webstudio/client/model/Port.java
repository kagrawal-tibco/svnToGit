/**
 * 
 */
package com.tibco.cep.webstudio.client.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vpatil
 */
public class Port extends WSDLEntity {
	
	private List<Operation> operations;

	public Port() {
		super();
		operations = new ArrayList<Operation>();
	}
	
	public void addOperations(Operation operation) {
		operations.add(operation);
	}
	
	public List<Operation> getOperations() {
		return this.operations;
	}
	
	public Operation getOperationByName(String name) {
		for (Operation operation : operations) {
			if (operation.getName().equals(name)) {
				return operation;
			}
		}
		return null;
	}
	
	public Operation getOperationBySoapAction(String soapAction) {
		for (Operation operation : operations) {
			if (operation.getSoapAction().equals(soapAction)) {
				return operation;
			}
		}
		return null;
	}
}
