package com.tibco.cep.driver.http.server.impl.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletResponse;

import com.tibco.cep.driver.http.server.HttpChannelServerResponse;


/**
 * This class reads a HTTP Servlet response and adapts to a generic http
 * response  
 * @author vjavere
 *
 */
public class HttpServerResponse implements HttpChannelServerResponse {

	private HttpServletResponse response;

    private AsyncContext asyncContext;

	public HttpServerResponse(HttpServletResponse response, AsyncContext asyncContext) {
		this.response = response;
        this.asyncContext = asyncContext;
	}

    public HttpServerResponse(HttpServletResponse response) {
		this(response, null);
	}
	

	public void setStatus(int statusCode) {
		response.setStatus(statusCode);
	}
	

	public void setStatus(int statusCode, String statusPhrase) {
		response.setStatus(statusCode, statusPhrase);
	}


	public void addHeader(String key, String value) {
		response.addHeader(key, value);
	}
	

	public void setHeader(String key, String value) {
		response.setHeader(key, value);
	}
	

	public void setContentType(String contentType) {
		response.setContentType(contentType);
	}
	

	public void setContentLength(int length) {
		response.setContentLength(length);
	}
	

	public Writer getWriter() throws IOException {
		return response.getWriter();
	}


	public OutputStream getOutputStream() throws IOException {
		return response.getOutputStream();
	}
	

	public void sendResponse() {
        //Call complete since the response is sent asynchronously.
        asyncContext.complete();
	}
}