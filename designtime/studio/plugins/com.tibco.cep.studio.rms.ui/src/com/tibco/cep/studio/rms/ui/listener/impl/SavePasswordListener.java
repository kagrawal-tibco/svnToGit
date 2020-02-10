package com.tibco.cep.studio.rms.ui.listener.impl;

import com.tibco.cep.studio.rms.core.RMSCorePlugin;
import com.tibco.cep.studio.rms.preferences.util.RMSPreferenceUtils;
import com.tibco.cep.studio.rms.ui.listener.AuthCompletionEvent;
import com.tibco.cep.studio.rms.ui.listener.IAuthCompletionListener;

public class SavePasswordListener implements IAuthCompletionListener {

	@Override
	public void authCompleted(AuthCompletionEvent authCompletionEvent) {
		if (authCompletionEvent.getAuthEventType() == AuthCompletionEvent.AUTH_SUCCESS_EVENT) {
			String username = authCompletionEvent.getUsername();
			String password = authCompletionEvent.getPassword();
			//Save it
			boolean passwordSaved = RMSPreferenceUtils.savePassword(username, password);
			if (!passwordSaved) {
				RMSCorePlugin.debug("Could not save password for user {0} to the repository", username);
			} else {
				RMSPreferenceUtils.saveRMSPreferences();
			}
		}
	}
}
