/**
 * 
 */
package com.tibco.cep.studio.rms.client;

/**
 * @author aathalye
 *
 */
public interface RequestBuilder {
	
	public Object buildRequest(final Object input) throws Exception;
}
