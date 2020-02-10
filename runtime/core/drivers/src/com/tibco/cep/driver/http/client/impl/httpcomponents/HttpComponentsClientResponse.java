package com.tibco.cep.driver.http.client.impl.httpcomponents;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import com.tibco.cep.driver.http.HttpUtils;
import com.tibco.cep.driver.http.client.HttpChannelClientResponse;

public class HttpComponentsClientResponse implements HttpChannelClientResponse {

    private HttpResponse httpResponse;

    private byte[] bytesResponse;

    private java.util.List<org.apache.http.Header> responseHeaders;

    private org.apache.http.params.HttpParams responseParams;

    /**
     * 
     * @param httpResponse
     */
    public HttpComponentsClientResponse(HttpResponse httpResponse) {
        this.httpResponse = httpResponse;
    }

    /**
     * 
     * @param bytesResponse
     * @param responseHeaders
     * @param responseParams
     */
    public HttpComponentsClientResponse(byte[] bytesResponse,
                                        java.util.List<org.apache.http.Header> responseHeaders,
                                        org.apache.http.params.HttpParams responseParams) {
        this.bytesResponse = bytesResponse;
        this.responseHeaders = responseHeaders;
        this.responseParams = responseParams;
    }

    public String getFirstHeaderValue(String headerName) {
        if (responseHeaders != null) {
            for (org.apache.http.Header header : responseHeaders) {
                if (header.getName().equals(headerName)) {
                    return header.getValue();
                }
            }
        }
        return null;
    }

    public byte[] getEntity() throws IOException {
        byte[] data = null;
        //byte conversion is done
        if (bytesResponse != null) {
            return bytesResponse;
        }
        if (httpResponse != null) {
        	HttpEntity entity = httpResponse.getEntity();
        	if (entity != null) {
        		entity = HttpUtils.decompressEntity(entity);
        		data = EntityUtils.toByteArray(entity);
        	}
        }
        return data;
    }

    public void setParameter(String paramName, Object paramValue) {
        httpResponse.getParams().setParameter(paramName, paramValue);
    }

    public Object getParameter(String paramName) {
        if (responseParams != null) {
            return responseParams.getParameter(paramName);
        }
        if (httpResponse != null) {
        	return httpResponse.getParams().getParameter(paramName);
        }
        return null;
    }
}
	
