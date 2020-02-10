package com.tibco.cep.driver.http.server.impl.httpcomponents.handlers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.protocol.HttpContext;

import com.tibco.cep.driver.http.HttpDestination;
import com.tibco.cep.driver.http.server.HttpChannelServerRequest;
import com.tibco.cep.runtime.channel.Channel;

/**
 * Synchronous handler to handle the http requests. This handler uses HTTP Core 
 * entities for reading http requests synchronously and routes the http message to 
 * proper destination for processing  
 *
 */

public class HttpChannelSyncRequestHandler implements org.apache.http.protocol.HttpRequestHandler  {
	
    private Map<String, Channel.Destination> httpDestinations =
                            new HashMap<String, Channel.Destination>();
    
		/**
		 * A map of all the destinations on the HTTP channel 	 
		 */
        public Map<String, Channel.Destination> getHttpDestinations() {
			return httpDestinations;
		}
        
        private HttpDestination getMatchingDestination(final HttpChannelServerRequest request) {
            String contextPath = request.getRequestURI();
            HttpDestination httpDestination = (HttpDestination)httpDestinations.get(contextPath);
            return httpDestination;
        }

        /** Handle Synchronous HTTP Request/Response handling
         *  @param request -- The Synchronous HTTP Request
         *  @param response -- The Synchronous HTTP Response
         *  @param trigger -- The Asynchronous HTTP Response Trigger, this is used to 
         *  submit the response asynchronously when the request processing is done and
         *  response needs to be sent back   
         *  @param context - HTTP Context associated with the request 
         */        
		public void handle(
                HttpRequest request,
                HttpResponse response,
                HttpContext context) throws HttpException, IOException {
            String method = request.getRequestLine().getMethod().toUpperCase(Locale.ENGLISH);
            HttpChannelServerRequest httpcorereq = new SyncHttpComponentsRequest(request,context,new RequestUriEncoding());
            SyncHttpComponentsResponse httpcoreresp = new SyncHttpComponentsResponse(response);
            HttpDestination httpDestination = getMatchingDestination(httpcorereq);
            try {
            	httpDestination.processMessage(httpcorereq, httpcoreresp, method);
            } catch (Exception ex) {
            	ex.printStackTrace();
            	throw new RuntimeException(ex);
            }
            
        }

		public void setHttpDestinations(
				Map<String, Channel.Destination> httpDestinations) {
			this.httpDestinations = httpDestinations;
		}

}


