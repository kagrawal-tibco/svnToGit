package com.tibco.cep.webstudio.client;


import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * 
 * @author sasahoo
 *
 */
public interface PushServiceAsync
{
	public void listenService(String s, AsyncCallback<String> callback);

}
