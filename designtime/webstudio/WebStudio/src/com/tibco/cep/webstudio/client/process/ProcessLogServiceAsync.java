package com.tibco.cep.webstudio.client.process;


import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * 
 * @author sasahoo
 *
 */

public interface ProcessLogServiceAsync {
	public void setLogLevel(String s, AsyncCallback<String> callback);
}
