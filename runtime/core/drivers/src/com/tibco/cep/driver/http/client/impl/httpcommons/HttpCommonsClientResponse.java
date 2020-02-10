package com.tibco.cep.driver.http.client.impl.httpcommons;

import java.io.IOException;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpMethodBase;

import com.tibco.cep.driver.http.client.HttpChannelClientResponse;

public class HttpCommonsClientResponse implements HttpChannelClientResponse {

	private HttpMethodBase httpResponse;
	
	public HttpCommonsClientResponse(HttpMethodBase httpResponse) {
		this.httpResponse = httpResponse;
	}
	
	public String getFirstHeaderValue(String headerName) {
		Header header = httpResponse.getResponseHeader(headerName); 
		if(header != null)
			return header.getValue();
		else
			return null;
	}
	
	public byte[] getEntity() throws IOException {
    	return httpResponse.getResponseBody();
	}

	public void setParameter(String paramName, Object paramValue) {
		httpResponse.getParams().setParameter(paramName, paramValue);
	}

	public Object getParameter(String paramName) {
		return httpResponse.getParams().getParameter(paramName); 
	}
}
	
