package com.tibco.cep.driver.http.client;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;


public interface HttpChannelClientRequest {

    public void setParameter(String paramName, String paramValue);

    public Object getRequestMethod();
    
    public String getHost() ;
    
    public int getPort();
    
    public String getPath();

    public String getMethodType();

    public void writeContent(byte[] bytes);

    public void writeContent(HashMap<String, String> params);

    public void writeContent(String content) throws UnsupportedEncodingException;
}
