/**
 * 
 */
package com.tibco.cep.studio.rms.ui.listener;

/**
 * @author aathalye
 *
 */
public interface IAuthCompletionListener {
	
	/**
	 * Get notification that authentication process hs completed.
	 * @param authCompletionEvent
	 */
	void authCompleted(AuthCompletionEvent authCompletionEvent);
}
