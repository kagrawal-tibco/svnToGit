/**
 * 
 */
package com.tibco.cep.webstudio.client.http;

/**
 * HTTP method types supported for server communication
 * 
 * @author Vikram Patil
 */
public enum HttpMethod {
	GET, POST, PUT, DELETE;

	public String getValue() {
		return this.toString();
	}
}
