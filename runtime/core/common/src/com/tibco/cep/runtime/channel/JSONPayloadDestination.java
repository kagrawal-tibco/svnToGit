package com.tibco.cep.runtime.channel;

/**
 * Marks that the destination supports JSON payloads
 */
public interface JSONPayloadDestination {

	/**
	 * Return whether the destination is marked as a 'JSON' payload.  This will
	 * dictate how the payload is serialized in methods such as 'toString'
	 * 
	 * @return whether this destination has been marked as a 'JSON' payload
	 */
	public boolean isJSONPayload();
	
}
