package com.tibco.cep.webstudio.client.process;


import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * 
 * @author sasahoo
 *
 */
@RemoteServiceRelativePath("logService")
public interface ProcessLogService extends RemoteService {
	public String setLogLevel(String s);
}
