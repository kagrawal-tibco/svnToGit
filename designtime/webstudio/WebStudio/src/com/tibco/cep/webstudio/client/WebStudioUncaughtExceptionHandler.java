package com.tibco.cep.webstudio.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.smartgwt.client.util.SC;
import com.tibco.cep.webstudio.client.util.ErrorMessageDialog;

public class WebStudioUncaughtExceptionHandler implements UncaughtExceptionHandler{

	
	ErrorMessageDialog errorDialog;
	
	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.GWT.UncaughtExceptionHandler#onUncaughtException(java.lang.Throwable)
	 */
	@Override
	public void onUncaughtException(Throwable e) {
		SC.clearPrompt();
		
		if (errorDialog != null && errorDialog.isVisible()) {
			errorDialog.destroy();
			errorDialog = null;
		}
		
		if (errorDialog == null) {
			errorDialog = ErrorMessageDialog.showException(e);
			errorDialog.draw();
		}
		
		GWT.log(WebStudioUncaughtExceptionHandler.class.getName(), e);
		e.printStackTrace();
	}
}
