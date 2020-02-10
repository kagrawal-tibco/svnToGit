/**
 * 
 */
package com.tibco.cep.webstudio.client.http;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handler for server API's successful execution
 * 
 * @author Vikram Patil
 */
public interface HttpSuccessHandler extends EventHandler {
	public void onSuccess(HttpSuccessEvent event);

}
