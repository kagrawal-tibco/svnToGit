package com.tibco.cep.driver.http.client;

import java.net.MalformedURLException;

import com.tibco.cep.driver.http.HttpDestination;
import com.tibco.cep.driver.http.HttpDestination.HttpClientSerializationContext;

public interface HttpChannelClient {
	
    public String getHost();

    public int getPort();

    public void init();
    
    /*public void start();
    
    public void stop();*/
    
    public void setDestinationURL(HttpDestination destination) throws MalformedURLException;

    public HttpChannelClientRequest getClientRequest();

    public void execute(HttpClientSerializationContext clientContext);


}
