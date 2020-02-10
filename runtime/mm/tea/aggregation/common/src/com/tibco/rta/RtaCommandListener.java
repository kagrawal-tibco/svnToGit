package com.tibco.rta;

/**
 * 
 * A client application can implement this listener and get asynchronous notifications from the server
 * 
 */
public interface RtaCommandListener {
	
	/**
	 * Callback gets invoked when there is an asynchronous command. Users to provide an implementation.
	 * @param command command that was received asynchronously.
	 */
	void onCommand(RtaCommand command);
}