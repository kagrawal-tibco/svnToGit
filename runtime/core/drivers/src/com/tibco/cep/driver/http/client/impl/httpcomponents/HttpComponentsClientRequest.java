package com.tibco.cep.driver.http.client.impl.httpcomponents;


import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.http.Header;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;

import com.tibco.cep.driver.http.client.HttpChannelClientRequest;

public class HttpComponentsClientRequest implements HttpChannelClientRequest {

    static final private String DEFAULT_ENCODING = "UTF-8";

    private HttpUriRequest clientRequest;

    public HttpComponentsClientRequest(HttpUriRequest clientRequest) {
        this.clientRequest = clientRequest;
    }

    public void setParameter(String paramName, String paramValue) {
        clientRequest.getParams().setParameter(paramName, paramValue);
    }

    public void setBooleanParameter(String paramName, Boolean paramValue) {
        clientRequest.getParams().setBooleanParameter(paramName, paramValue);
    }

    public void setIntParameter(String paramName, Integer paramValue) {
        clientRequest.getParams().setIntParameter(paramName, paramValue);
    }

    public void setDoubleParameter(String paramName, Double paramValue) {
        clientRequest.getParams().setDoubleParameter(paramName, paramValue);
    }

    public void setLongParameter(String paramName, Long paramValue) {
        clientRequest.getParams().setLongParameter(paramName, paramValue);
    }

    public Object getRequestMethod() {
        return clientRequest;
    }

    public void writeContent(HashMap<String, String> params) {
        Iterator<String> iter = params.keySet().iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            clientRequest.setHeader(key, params.get(key));
        }
    }

    public void writeContent(String str) throws UnsupportedEncodingException {
        if (clientRequest instanceof HttpGet) {
            throw new RuntimeException("HTTP GET method cannot be used to send post data");
        } else if (clientRequest instanceof HttpPost) {
            ((HttpPost) clientRequest).setEntity(new StringEntity(str, DEFAULT_ENCODING));
        }
    }

    public void writeContent(byte[] bytes) {
        if (clientRequest instanceof HttpGet) {
            throw new RuntimeException(
                    "HTTP GET method cannot be used to send byte array");
        } else if (clientRequest instanceof HttpPost) {
            ByteArrayEntity byteArrayEntity = new ByteArrayEntity(bytes);

            Header header = clientRequest.getFirstHeader(HTTP.CONTENT_TYPE);
            String contentType = null;
            if (header != null)
                contentType = header.getValue();
            if (contentType == null)
                byteArrayEntity.setContentType(HTTP.PLAIN_TEXT_TYPE + HTTP.CHARSET_PARAM + DEFAULT_ENCODING);
            else
                byteArrayEntity.setContentType(contentType);

            ((HttpPost) clientRequest).setEntity(byteArrayEntity);
        }
    }

    public String getMethodType() {
        return clientRequest.getMethod();
    }

    public String getHost() {
        URI uri = clientRequest.getURI();
        return uri.getHost();
    }

    public String getPath() {
        URI uri = clientRequest.getURI();
        return uri.getPath();
    }

    public int getPort() {
        URI uri = clientRequest.getURI();
        return uri.getPort();
    }
}
