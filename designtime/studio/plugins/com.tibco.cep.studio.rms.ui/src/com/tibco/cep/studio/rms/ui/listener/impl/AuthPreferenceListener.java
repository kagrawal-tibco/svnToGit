/**
 * 
 */
package com.tibco.cep.studio.rms.ui.listener.impl;

import com.tibco.cep.studio.rms.core.RMSCorePlugin;
import com.tibco.cep.studio.rms.preferences.util.RMSPreferenceUtils;
import com.tibco.cep.studio.rms.ui.listener.AuthCompletionEvent;
import com.tibco.cep.studio.rms.ui.listener.IAuthCompletionListener;

/**
 * @author aathalye
 *
 */
public class AuthPreferenceListener implements IAuthCompletionListener {

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.ui.listener.IAuthCompletionListener#authCompleted(com.tibco.cep.studio.rms.ui.listener.AuthCompletionEvent)
	 */
	@Override
	public void authCompleted(AuthCompletionEvent authCompletionEvent) {
		if (authCompletionEvent.getAuthEventType() == AuthCompletionEvent.AUTH_SUCCESS_EVENT) {
			//Only then update preferences
			//Get repo url
			String repoURL = (String)authCompletionEvent.getSource();
			boolean repoInfoAdded = RMSPreferenceUtils.addAuthURLInfo(repoURL);
			if (!repoInfoAdded) {
				RMSCorePlugin.debug("Could not add authentication server URL {0} to the repository", repoURL);
			} else {
				RMSPreferenceUtils.saveRMSPreferences();
			}
		}
	}
}
