/**
 * 
 */
package com.tibco.cep.studio.rms.client;

import org.apache.http.HttpRequest;
import org.apache.http.entity.StringEntity;

import com.tibco.cep.studio.rms.core.utils.RequestHeaders;

/**
 * @author aathalye
 *
 */
public interface MethodBuilder<T extends HttpRequest> {
	
	T buildMethod(String url,
			      String eventName,
			      String eventNS,
			      RequestHeaders reqHeaders,
			      StringEntity requestEntity);	
	
}
