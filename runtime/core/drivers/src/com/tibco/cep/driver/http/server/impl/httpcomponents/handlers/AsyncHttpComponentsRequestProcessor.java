package com.tibco.cep.driver.http.server.impl.httpcomponents.handlers;

/**
 * Interface to process Asynchronous HTTP Requests
 * It acts as a command for the AsyncRequestHandler which calls the specific processor implementation
 *  mapped to the request URIs 
 * @author vjavere
 *
 */
public interface AsyncHttpComponentsRequestProcessor {

   /**
    * Process the request and send back the response asynchronously
    * @param request
    * @param response
    * @throws Exception
    */	
   public void process(
            final ASyncHttpComponentsRequest request,
            final ASyncHttpComponentsResponse response) throws Exception;
}
