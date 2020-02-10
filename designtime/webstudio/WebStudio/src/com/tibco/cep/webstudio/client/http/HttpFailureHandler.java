/**
 * 
 */
package com.tibco.cep.webstudio.client.http;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handler for server API's unsuccessful execution
 * 
 * @author Vikram Patil
 */
public interface HttpFailureHandler extends EventHandler {
	public void onFailure(HttpFailureEvent event);

}
