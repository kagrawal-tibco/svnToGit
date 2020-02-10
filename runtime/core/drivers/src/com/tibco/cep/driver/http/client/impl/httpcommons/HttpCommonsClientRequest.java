package com.tibco.cep.driver.http.client.impl.httpcommons;


import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import com.tibco.cep.driver.http.HttpMethods;
import com.tibco.cep.driver.http.client.HttpChannelClientRequest;

public class HttpCommonsClientRequest implements HttpChannelClientRequest {

	private HttpMethodBase clientRequest;

	public HttpCommonsClientRequest(HttpMethodBase clientRequest) {
		this.clientRequest = clientRequest;
	}
	
	public void setParameter(String paramName,String paramValue) {
		clientRequest.getParams().setParameter(paramName, paramValue);
	}
	
	public void setBooleanParameter(String paramName,Boolean paramValue) {
		clientRequest.getParams().setBooleanParameter(paramName, paramValue);
	}

	public void setIntParameter(String paramName,Integer paramValue) {
		clientRequest.getParams().setIntParameter(paramName, paramValue);
	}

	public void setDoubleParameter(String paramName,Double paramValue) {
		clientRequest.getParams().setDoubleParameter(paramName, paramValue);
	}
	
	public void setLongParameter(String paramName,Long paramValue) {
		clientRequest.getParams().setLongParameter(paramName, paramValue);
	}
	
	public Object getRequestMethod() {
		return clientRequest;
	}
	
    public void writeContent(HashMap<String, String> params) {
    	Iterator<String> iter = params.keySet().iterator();
    	while (iter.hasNext()) {
    		String key = iter.next();
    		clientRequest.getParams().setParameter(key, params.get(key));
    	}
    }

    public void writeContent(byte[] bytes) {
   	
    	//bytes is zero then , nothing is there to write so return
		if (bytes != null && bytes.length == 0) {
			return;
		}
    	
        if (clientRequest instanceof GetMethod) {
            GetMethod gm = (GetMethod) clientRequest;
            throw new RuntimeException("HTTP GET method cannot be used to send byte array");
        } else if (clientRequest instanceof PostMethod) {
            PostMethod pm = (PostMethod) clientRequest;
            pm.setRequestEntity(new ByteArrayRequestEntity(bytes, HttpMethods.DEFAULT_CONTENT_TYPE));
        }
    }
    
    public void writeContent(String str) {
        if (clientRequest instanceof GetMethod) {
            throw new RuntimeException("HTTP GET method cannot be used to send post data");
        } else if (clientRequest instanceof PostMethod) {
            PostMethod pm = (PostMethod) clientRequest;
            pm.setRequestEntity(new StringRequestEntity(str));
        }
    }
    
    public String getMethodType() {
    	return clientRequest.getName();
    }


	public String getHost() {
		String host = "localhost";
		try {
			URI uri = clientRequest.getURI();
			host = uri.getHost();
		} catch (URIException e) {
			throw new RuntimeException(e);
		}
		return host;
	}

	public String getPath() {
		String path="";
		try {
			URI uri = clientRequest.getURI();
			path = uri.getPath();
		} catch (URIException e) {
			throw new RuntimeException(e);
		}
		return path;
	}

	public int getPort()  {
		int port = 80;
		try {
			URI uri = clientRequest.getURI();
			port = uri.getPort();
		} catch (URIException e) {
			throw new RuntimeException(e);
		}
		return port;
	}
}
