/**
 * 
 */
package com.tibco.cep.webstudio.client.model;

/**
 * @author vpatil
 */
public class Operation extends WSDLEntity {
	private String soapAction;
	
	public Operation() {
		super();
	}

	public Operation(String soapAction) {
		super();
		this.soapAction = soapAction;
	}

	public String getSoapAction() {
		return soapAction;
	}

	public void setSoapAction(String soapAction) {
		this.soapAction = soapAction;
	}
}
