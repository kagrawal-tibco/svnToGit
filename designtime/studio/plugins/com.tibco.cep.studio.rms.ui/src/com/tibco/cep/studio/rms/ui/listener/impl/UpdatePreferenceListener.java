/**
 * 
 */
package com.tibco.cep.studio.rms.ui.listener.impl;

import com.tibco.cep.studio.rms.core.RMSCorePlugin;
import com.tibco.cep.studio.rms.preferences.util.RMSPreferenceUtils;
import com.tibco.cep.studio.rms.ui.listener.IUpdateCompletionListener;
import com.tibco.cep.studio.rms.ui.listener.UpdateCompletionEvent;

/**
 * @author aathalye
 *
 */
public class UpdatePreferenceListener implements IUpdateCompletionListener {

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.ui.listener.IUpdateCompletionListener#updateCompleted(com.tibco.cep.studio.rms.ui.listener.UpdateCompletionEvent)
	 */
	@Override
	public void updateCompleted(UpdateCompletionEvent updateCompletionEvent) {
		//Only then update preferences
		//Get repo url
		String repoURL = (String)updateCompletionEvent.getSource();
		boolean repoInfoAdded = RMSPreferenceUtils.addCheckoutURLInfo(repoURL);
		if (!repoInfoAdded) {
			RMSCorePlugin.debug("Could not add authentication server URL {0} to the repository", repoURL);
		} else {
			RMSPreferenceUtils.saveRMSPreferences();
		}
	}
}
