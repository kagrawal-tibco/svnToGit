package com.tibco.rta;

/**
 * 
 * A client application can implement this listener and get asynchronous notfications from the server
 * 
 */
public interface RtaNotificationListener {
	
	/**
	 * Callback gets invoked when there is an asynchronous notification. Users to provide an implementation.
	 * @param notification notification that was received asynchronously.
	 */
	void onNotification(RtaNotification notification);

}