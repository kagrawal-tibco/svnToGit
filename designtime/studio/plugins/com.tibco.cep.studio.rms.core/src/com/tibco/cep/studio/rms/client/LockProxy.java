/**
 * 
 */
package com.tibco.cep.studio.rms.client;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.DOMException;

import com.tibco.cep.studio.rms.client.builder.impl.GetMethodBuilder;
import com.tibco.cep.studio.rms.core.utils.OperationType;
import com.tibco.cep.studio.rms.core.utils.RMSConstants;
import com.tibco.cep.studio.rms.core.utils.RMSUtil;
import com.tibco.cep.studio.rms.core.utils.RequestHeaders;
import com.tibco.cep.studio.rms.response.IResponse;
import com.tibco.cep.studio.rms.response.processor.IResponseProcessor;
import com.tibco.cep.studio.rms.response.processor.impl.ArtifactLockResponseProcessor;

/**
 * @author aathalye
 * @date July 29, 2008
 *
 */
public class LockProxy {
	
		
	private static String baseURL;
	
	private static IResponseProcessor<Object, IResponse> start;
	
	private static enum LockOperationType {
		LOCK, UNLOCK;
	}
	
	static {
		baseURL = RMSUtil.buildRMSURL();
		if (baseURL == null || baseURL.length() == 0) {
			baseURL = "http://localhost:5000/Transports/Channels/HTTPChannel/";
		}
		start = new ArtifactLockResponseProcessor<Object, IResponse>();
	}
	
	/**
	 * @param username
	 * @param requestedResource
	 */
	public static Map<Integer, String> lockRequest(final String username, 
			                                       final String requestedResource) throws Exception {
		String requestURL = RMSUtil.createRequestURL(baseURL,
				RMSConstants.LCK_REQUEST_DEST_PROPERTY, "LockRequestDestination");
		
		return lockUnlockUtility(username, 
				                 requestedResource, 
				                 RMSConstants.LCK_REQUEST_EVENT, 
				                 requestURL,
				                 LockOperationType.LOCK);
	}
	
	@SuppressWarnings("unchecked")
	private static Map<Integer, String> lockUnlockUtility(final String username, 
                                                          final String requestedResource,
                                                          final String eventName,
                                                          final String requestURL,
                                                          final LockOperationType lckOperationType) throws Exception {
		if (username == null || requestedResource == null) {
			throw new IllegalArgumentException("Invalid parameter specification");
		}
		
		HttpClient client = new DefaultHttpClient();
		RequestHeaders requestHeaders = new RequestHeaders();
		
		// Create a method instance.
		MethodBuilder<HttpGet> builder = new GetMethodBuilder();
		requestHeaders.setLockResourceRequested(requestedResource);
		requestHeaders.setLockResourceRequestor(username);
		HttpGet method = builder.buildMethod(requestURL,
				eventName, "", requestHeaders, null);
		try {
			// Execute the method.
			HttpResponse httpResponse = client.execute(method);
//			if (statusCode != HttpStatus.SC_OK) {
//				// This should not come for get
//			}
			InputStream responseBody = httpResponse.getEntity().getContent();
			IResponse response = start.processResponse(OperationType.LCK_REQUEST, httpResponse,
					responseBody);
			return (Map<Integer, String>)response.getResponseObject();
		} 
//		catch (HttpException e) {
//			TRACE.logError(CLASS, e.getMessage());
//			throw e;
//		} 
	catch (DOMException e) {
//			TRACE.logError(CLASS, e.getMessage());
			throw e;
		} catch (IOException e) {
//			TRACE.logError(CLASS, e.getMessage());
			throw e;
		} catch (Exception e) {
//			TRACE.logError(CLASS, e.getMessage());
			throw e;
		}
	}
	
	/**
	 * @param username
	 * @param requestedResource
	 */
	public static Map<Integer, String> unlockRequest(final String username, 
			                                         final String requestedResource) throws Exception {
		String requestURL = RMSUtil.createRequestURL(baseURL,
				RMSConstants.UNLCK_REQUEST_DEST_PROPERTY, "UnlockRequestDestination");
		return lockUnlockUtility(username, 
				                 requestedResource, 
				                 RMSConstants.UNLCK_REQUEST_EVENT, 
				                 requestURL,
				                 LockOperationType.UNLOCK);
	}
}
