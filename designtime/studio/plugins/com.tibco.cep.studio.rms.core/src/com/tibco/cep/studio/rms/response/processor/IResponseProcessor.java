/**
 * 
 */
package com.tibco.cep.studio.rms.response.processor;

import java.io.InputStream;

import org.apache.http.HttpResponse;

import com.tibco.cep.studio.rms.core.utils.OperationType;
import com.tibco.cep.studio.rms.response.IResponse;
import com.tibco.cep.studio.rms.response.ResponseProcessingException;

/**
 * @author aathalye
 *
 */
public interface IResponseProcessor<O extends Object, R extends IResponse> {
	
	
	/**
	 * Process incoming response, and return an instance of {@code IResponse}
	 * @param responseType: The type of operation
	 * @param method : POST/GET etc.
	 * @param response
	 * @return an instance of <tt>IResponse</tt> based on the type of the operation
	 * @see  OperationType
	 * @throws ResponseProcessingException
	 */
	IResponse processResponse(OperationType responseType, HttpResponse httpResponse, InputStream response) throws ResponseProcessingException;
	
	/**
	 * Adds an instance of {@code IResponseProcessor} to the chain
	 * @param <T>
	 * @param processor
	 */
	<T extends IResponseProcessor<O, R>> void addProcessor(T processor);
	
	/**
	 * @param <T>
	 * @return the next processor in the chain
	 */
	IResponseProcessor<O, R> nextProcessor();
}
