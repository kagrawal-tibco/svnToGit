package com.tibco.cep.driver.http.server.impl.httpcomponents.handlers;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpStatus;

import com.tibco.cep.driver.http.HttpDestination;
import com.tibco.cep.driver.http.HttpUtils;
import com.tibco.cep.runtime.channel.Channel;

/**
 * Processor for Asynchronous http requests. It routes the http message to 
 * mapped destination for processing  
 *
 */
public class ASyncHttpComponentsDestinationProcessor implements AsyncHttpComponentsRequestProcessor  {

	/**
	 * A map of all the destinations on the HTTP channel 	 
	 */
    private Map<String, Channel.Destination> httpDestinations = new HashMap<String, Channel.Destination>();

    
    public Map<String, Channel.Destination> getHttpDestinations() {
		return httpDestinations;
	}
    
    /**
     * Gets the destination matching the given context path
     * @param contextPath context path
     * @return matching destination
     */
    private HttpDestination getMatchingDestination(String contextPath) {
        HttpDestination httpDestination = (HttpDestination)httpDestinations.get(contextPath);
        return httpDestination;
    }

    /** Gets the matching destination for the destination URI in the request
     *  @param request -- The Asynchronous HTTP Request
     *  @param response -- The Asynchronous HTTP Response
     */
	public void process(
            final ASyncHttpComponentsRequest request,
            final ASyncHttpComponentsResponse response) throws Exception {
				String destURI = (String)request.getAttribute(HttpUtils.DESTINATION_URI);
				if(destURI == null || destURI.trim().length() == 0) {
					destURI = request.getRequestURI();
				}
				HttpDestination httpDestination = getMatchingDestination(destURI);
				if(httpDestination == null) {
					AsyncNHttpErrorHandler.sendErrorResponse(response, AsyncNHttpErrorHandler.NO_HANDLER,HttpStatus.SC_INTERNAL_SERVER_ERROR);
				} else {
					httpDestination.processMessage(request, response, request.getMethod());
				}
    }
	
	/**
	 * Sets the HTTP destinations so that it can be used for processing
	 * @param httpDestinations
	 */
	public void setHttpDestinations(
			Map<String, Channel.Destination> httpDestinations) {
		this.httpDestinations = httpDestinations;
	}

}


