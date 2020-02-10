package com.tibco.cep.webstudio.client;


import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * 
 * @author sasahoo
 *
 */
@RemoteServiceRelativePath("pushService")
public interface PushService extends RemoteService
{
	public String listenService(String s);

}
