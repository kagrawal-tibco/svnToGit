/**
 * 
 */
package com.tibco.cep.driver.http.server.impl.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Wrapper to wrap base Http Request into a GZIP compatible http request.
 * 
 * @author vpatil
 */
public class GZIPRequestWrapper extends HttpServletRequestWrapper {
	
    private ServletInputStream servletInputStream;
    private BufferedReader bufferedReader;
	
	public GZIPRequestWrapper(HttpServletRequest request) throws IOException {
		super(request);
        
		servletInputStream = new GZIPRequestStream(request);
		bufferedReader = new BufferedReader(new InputStreamReader(servletInputStream));
	}
	
	@Override
	public ServletInputStream getInputStream() throws IOException {
		return servletInputStream;
	}
	
	@Override
	public BufferedReader getReader() throws IOException {
		return bufferedReader;
	}
}
