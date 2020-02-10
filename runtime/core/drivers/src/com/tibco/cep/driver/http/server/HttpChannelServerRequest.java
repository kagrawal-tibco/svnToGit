package com.tibco.cep.driver.http.server;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * Interface to represent a generic HTTP request
 * @author vjavere
 *
 */
public interface HttpChannelServerRequest {
	
	/**
	 * Gets request Attribute with the given name
	 * @param attributeName
	 * @return Object - Attribute Value
	 */
	public Object getAttribute(String attributeName);

	/**
	 * Gets the character encoding format for the request
	 * @return String - Character encoding format
	 */
	public String getCharacterEncoding();
	
	/**
	 * Returns the length of request body content
	 * @return
	 */
	public int getContentLength() ;

	/**
	 * Returns the type of the request content
	 * @return String - Content Type
	 */
	public String getContentType() ;

	/**
	 * Gets the Header with the given name 
	 * @param headerName
	 * @return String -- header value
	 */
	public String getHeader(String headerName);

	/**
	 * Gets all the request header names 
	 * @return
	 */
	public Iterator getHeaderNames();

	/**
	 * Gets the request input stream
	 * @return InputStream request input stream
	 * @throws IOException
	 */
	public InputStream getInputStream() throws IOException;

	/**
	 * Gets the HTTP method for the request
	 * @return
	 */
	public String getMethod() ;
	
	/**
	 * Gets all the values for the given parameter name
	 * @param parameterName
	 * @return String [] -- values
	 */
	public String[] getParameterValues(String parameterName);

	/**
	 * Gets the request path info
	 * @return the request path info
	 */
	public String getPathInfo();

	/**
	 * Gets the request protocol
	 * @return String - Protocol
	 */
	public String getProtocol();
	
	/**
	 * Gets the request query string
	 * @return
	 */
	public String getQueryString();

	/**
	 * Gets the request URI from the requestLine
	 * @return String -- request URI
	 */
	public String getRequestURI() ;

	/**
	 * Gets the server name
	 * @return String - server name
	 */
	public String getServerName();

	/**
	 * Gets the server port from a HTTP request
	 * @return - server port
	 */
	public int getServerPort();
	
	/**
	 * Returns a boolean indicating whether this request was made using a secure channel, 
	 * such as HTTPS.
	 * @return a boolean indicating if the request was made using a secure channel
	 */
	public boolean isSecure();

    /**
     * Remove header from request with this name.
     * @param headerName
     */
    public void removeHeader(String headerName);
}
