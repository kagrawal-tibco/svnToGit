package com.tibco.cep.studio.rms.ui.listener.impl;

import com.tibco.cep.studio.rms.ui.listener.AuthCompletionEvent;
import com.tibco.cep.studio.rms.ui.listener.IAuthCompletionListener;
import com.tibco.cep.studio.rms.ui.utils.RMSUIUtils;

public class AuthStatusLineInfoUpdateListener implements IAuthCompletionListener {

	@Override
	public void authCompleted(AuthCompletionEvent authCompletionEvent) {
		if (authCompletionEvent.getAuthEventType() == AuthCompletionEvent.AUTH_SUCCESS_EVENT) {
			//Update status line info
			RMSUIUtils.showUserLogged(authCompletionEvent.getUsername());
		}
	}
}
