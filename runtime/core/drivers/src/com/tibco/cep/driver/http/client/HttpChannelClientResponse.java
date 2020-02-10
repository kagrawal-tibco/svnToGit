package com.tibco.cep.driver.http.client;

import java.io.IOException;

public interface HttpChannelClientResponse {
	
	public String getFirstHeaderValue(String headerName);
	public byte[] getEntity() throws IOException;
	public void setParameter(String paramName, Object paramValue);
	public Object getParameter(String paramName);
	
}
