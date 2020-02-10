/**
 * 
 */
package com.tibco.cep.studio.rms.ui.listener.impl;

import static com.tibco.cep.studio.rms.ui.utils.ActionConstants.CHECKOUT_ACTION;
import static com.tibco.cep.studio.rms.ui.utils.ActionConstants.COMMIT_ACTION;
import static com.tibco.cep.studio.rms.ui.utils.ActionConstants.GENERATE_DEPLOYABLE_ACTION;
import static com.tibco.cep.studio.rms.ui.utils.ActionConstants.LOGIN_ACTION;
import static com.tibco.cep.studio.rms.ui.utils.ActionConstants.LOGOUT_ACTION;
import static com.tibco.cep.studio.rms.ui.utils.ActionConstants.REVERT_ACTION;
import static com.tibco.cep.studio.rms.ui.utils.ActionConstants.UPDATE_ACTION;
import static com.tibco.cep.studio.rms.ui.utils.ActionConstants.WORKLIST_ACTION;
import static com.tibco.cep.studio.rms.ui.utils.RMSUIUtils.enableDisableAction;

import com.tibco.cep.studio.rms.core.utils.LocationStore;
import com.tibco.cep.studio.rms.ui.listener.AuthCompletionEvent;
import com.tibco.cep.studio.rms.ui.listener.IAuthCompletionListener;

/**
 * @author aathalye
 *
 */
public class PostAuthMenuUpdateListener implements IAuthCompletionListener {

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.ui.listener.IAuthCompletionListener#authCompleted(com.tibco.cep.studio.rms.ui.listener.AuthCompletionEvent)
	 */
	@Override
	public void authCompleted(AuthCompletionEvent authCompletionEvent) {
		// TODO Auto-generated method stub
		if (authCompletionEvent.getAuthEventType() == AuthCompletionEvent.AUTH_SUCCESS_EVENT) {
			//Also store this URL for further operations
			LocationStore.newInstance().setCachedLoginURL((String)authCompletionEvent.getSource());
			enableDisableActions();
		}
	}
	
	/**
	 * 
	 */
	private void enableDisableActions() {
		enableDisableAction(true, LOGOUT_ACTION);
		enableDisableAction(false, LOGIN_ACTION);
		enableDisableAction(true, CHECKOUT_ACTION);
		enableDisableAction(true, COMMIT_ACTION);
		enableDisableAction(true, WORKLIST_ACTION);
		enableDisableAction(true, UPDATE_ACTION);
		enableDisableAction(true, REVERT_ACTION);
		enableDisableAction(true, GENERATE_DEPLOYABLE_ACTION);
	}
}
