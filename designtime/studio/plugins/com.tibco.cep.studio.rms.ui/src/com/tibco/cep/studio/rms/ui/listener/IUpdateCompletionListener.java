/**
 * 
 */
package com.tibco.cep.studio.rms.ui.listener;

/**
 * Update from RMS. Applies to checkout and update.
 * @author aathalye
 *
 */
public interface IUpdateCompletionListener {
	
	void updateCompleted(UpdateCompletionEvent updateCompletionEvent);
}
