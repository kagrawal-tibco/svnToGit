package com.tibco.cep.webstudio.client.handler;

import com.google.gwt.event.shared.EventHandler;

/**
 * Change handler to cater to editor changes.
 * 
 * @author Vikram Patil
 */
public interface IWebStudioPartChangeHandler extends EventHandler {

	/**
	 * @param event
	 * @param propId
	 */
	public void fireChange(WebStudioEditorPartChangeEvent event);

}
