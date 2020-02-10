package com.tibco.cep.driver.http.server;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * Interface to represent a generic HTTP response
 * @author vjavere
 *
 */
public interface HttpChannelServerResponse {
	
	/**
	 * sets the status code on the response 
	 * @param statusCode
	 */
	public void setStatus(int statusCode);

	/**
	 * sets the status code and the description phrase on the response 
	 * @param statusCode
	 * @param statusPhrase
	 */
	public void setStatus(int statusCode, String statusPhrase);

	/**
	 * Adds a header key/value pair to the HTTP response, This method allows response headers to have multiple values
	 * @param key
	 * @param value
	 */
	public void addHeader(String key, String value);
	
	/**
	 * Sets a header key/value pair to the HTTP response, if the header already exists it will
	 * be overwritten by this value
	 * @param key
	 * @param value
	 */
	public void setHeader(String key, String value);
	
	/**
	 * Sets the content type for the response. If obtaining a {@link Writer}, this method should be called first
	 * @param contentType a {@link String} specifying the MIME type of the content 
	 */
	//Modified by Anand on 03/04/2011 to fix BE-8262 , we need to set the content type 
	public void setContentType(String contentType);
	
	/**
	 * Sets the length of the content
	 * @param length an integer specifying the length of the content 
	 */
	//Modified by Anand on 03/04/2011 to fix BE-8262 , we need to set the content type 
	public void setContentLength(int length);

	/**
	 * Gets the response writer 
	 * @return Writer -- response writer 
	 * @throws IOException
	 */
	public Writer getWriter() throws IOException;

	/**
	 * Gets the response outputStream 
	 * @return OutputStream -- response outputStream 
	 * @throws IOException
	 */
	public OutputStream getOutputStream() throws IOException;	

	/**
	 * Sends the response back, this method applies only in case of asynchronous HTTP response wherein the
	 * response needs to be triggered explicitly 
	 */
	public void sendResponse();
	
}
