package com.tibco.cep.driver.http.client.impl.httpcommons;

import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.HeadMethod;
import org.apache.commons.httpclient.methods.OptionsMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.TraceMethod;

import com.tibco.be.util.GUIDGenerator;
import com.tibco.cep.driver.http.HttpDestination;
import com.tibco.cep.driver.http.HttpMethods;
import com.tibco.cep.driver.http.HttpUtils;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;

public class HttpCommonsClientService {
 
	/**
	 * 
	 * @param url
	 * @param requestEvent
	 * @param correlationId
	 * @param callbackRuleFunctionURL
	 * @param methodType
	 * @param optionalHeaders
	 * @return
	 * @throws Exception
	 */
	public final static String sendAsyncRequest(String url,
									            SimpleEvent requestEvent,
									            String correlationId,
									            String callbackRuleFunctionURL,
									            String methodType,
									            String[] optionalHeaders)
        throws Exception {
    	
        HttpMethodBase req = createRequest(url,methodType,optionalHeaders);
        HttpCommonsClientRequest clientReq = new HttpCommonsClientRequest(req);
        if(correlationId == null || correlationId.trim().length() == 0) {
        	correlationId = GUIDGenerator.getGUID();
        }
        clientReq.setParameter(HttpUtils.HTTP_REQ_CORRELATION_ID, correlationId);
        clientReq.setParameter(HttpUtils.HTTP_RESPONSE_SUCCESS_CALLBACK_URI, callbackRuleFunctionURL);
        RuleServiceProvider rsp = RuleServiceProviderManager.getInstance().getDefaultProvider();
        
        HttpDestination httpDestination = (HttpDestination)rsp.getChannelManager().getDestination(requestEvent.getDestinationURI());
        httpDestination.sendMessage(clientReq, requestEvent);
        return correlationId;
    } // main



    /**
     * Creates a request to execute
     *
     * @return  a request without an entity
     */
    public final static HttpMethodBase createRequest(String url, String methodType,String [] optionalHeaders) {
    	HttpMethodBase req;
    	if(methodType.equals(HttpMethods.METHOD_GET)) {
    		req = new GetMethod(url);
    	} else if(methodType.equals(HttpMethods.METHOD_POST)) {
    		req = new PostMethod(url);
    	} else if(methodType.equals(HttpMethods.METHOD_DELETE)) {
    		req = new DeleteMethod(url);
    	} else if(methodType.equals(HttpMethods.METHOD_PUT)) {
    		req = new PutMethod(url);
    	} else if(methodType.equals(HttpMethods.METHOD_HEAD)) {
    		req = new HeadMethod(url);
    	} else if(methodType.equals(HttpMethods.METHOD_OPTIONS)) {
    		req = new OptionsMethod(url);
    	} else if(methodType.equals(HttpMethods.METHOD_TRACE)) {
    		req = new TraceMethod(url);
    	} else {
    		throw new RuntimeException("Invalid Method Type");
    	}
    	if(optionalHeaders != null) {
	    	for(int i=0; i<optionalHeaders.length; i++) {
	    		int separator = optionalHeaders[i].indexOf('=');
	    		if(separator != -1) {
	    			String optionalHeaderName = optionalHeaders[i].substring(0,separator);
	    			if(separator < optionalHeaders[i].length() ){
		    			String optionalHeaderValue = optionalHeaders[i].substring(separator+1,optionalHeaders[i].length());
		    			req.setRequestHeader(optionalHeaderName,optionalHeaderValue);
	    			}
	    		}
	    	}
    	}
        return req;
    }



}
