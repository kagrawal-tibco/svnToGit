package com.tibco.cep.driver.http.server.impl.httpcomponents.handlers;

import org.apache.http.nio.entity.NStringEntity;

/**
 * Utility class to send back asynchronous error response back to client
 * @author vjavere
 *
 */
public class AsyncNHttpErrorHandler {
	
	public static String RESOURCE_NOT_AVAILABLE = "The requested resource is not available ";

	public static String ACCESS_DENIED = "Access is denied for the requested resource ";

	public static String NO_HANDLER = "No handler for the given resource ";

	/**
	 * Sends the response back with the given HTTP Error Code
	 * @param response
	 * @param errorCode
	 */
	public static void sendErrorResponse(ASyncHttpComponentsResponse response, int errorCode) {
	       try {
		        response.setStatus(errorCode);
		        response.sendResponse();
	        } catch(Exception ex) {
	        	//if there is any error on sending response print it and ignore
	        	ex.printStackTrace();
	        }
		
	}
	
	/**
	 * Sends the response back with the given HTTP Error Code and Error Message
	 * @param response
	 * @param errorMessage
	 * @param errorCode
	 */
	public static void sendErrorResponse(ASyncHttpComponentsResponse response, String errorMessage, int errorCode) {
	       try {
		        response.setStatus(errorCode);
		    	NStringEntity entity = new NStringEntity("<html><body><h1>" +
		    			"Server Failed with Error: <br>" + errorMessage + "</br></h1></body></html>",
		                "UTF-8");
		        entity.setContentType("text/html; charset=UTF-8");
		        response.setEntity(entity);
		        response.sendResponse();
	        } catch(Exception ex) {
	        	//if there is any error on sending response print it and ignore
	        	ex.printStackTrace();
	        }
	}
	
	

}
