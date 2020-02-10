package com.tibco.rta;


/**
 * 
 * An asynchronous notification object that sends server notifications like exceptions and certain life cycle events
 *
 */
public interface RtaNotification extends RtaAsyncMessage {
	
	/**
	 * 
	 * An enumeration of status messages.
	 *
	 */
	public enum Status {
		SUCCESS, FAILURE, SERVER_DISCONNECTED, SERVER_CONNECTED, EXCEPTION;
	}
	
	/**
	 * Get the associated status.
	 * @return the associated status.
	 */
	Status getStatus();

	/**
	 * Get the associated exception if any.
	 * @return the associated exception if any.
	 */
    RtaException getException();

}