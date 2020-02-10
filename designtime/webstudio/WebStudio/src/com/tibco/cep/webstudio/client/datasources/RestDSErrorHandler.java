/**
 * 
 */
package com.tibco.cep.webstudio.client.datasources;

import static com.tibco.cep.webstudio.client.util.ErrorMessageDialog.showError;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.rpc.HandleErrorCallback;
import com.smartgwt.client.util.BooleanCallback;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.i18n.RMSMessages;

/**
 * Handler for Http Communication Errors
 * 
 * @author Vikram Patil
 */
public class RestDSErrorHandler implements HandleErrorCallback {
	private static boolean errorMsgDisplayed = false;
	
	private GlobalMessages globalMsgBundle = (GlobalMessages)I18nRegistry.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);
	private RMSMessages rmsMsgBundle = (RMSMessages)I18nRegistry.getResourceBundle(I18nRegistry.RMS_MESSAGES);
	
	@Override
	public void handleError(DSResponse response, DSRequest request) {
		showErrorMessage(response.getAttributeAsString("data"));
	}
	
	/**
	 * Show error message(if any) during server communication
	 * 
	 * @param errorMessage
	 */
	private void showErrorMessage(final String errorMessage) {
		String message = errorMessage;
		if (errorMessage == null || errorMessage.isEmpty() || (errorMessage != null && errorMessage.indexOf("Transport error") != -1)) {
			message = globalMsgBundle.serverConnection_error();
		}
		
		if (errorMessage.equals("This artifact does not have any approved changes yet.")){
			message = globalMsgBundle.artifactNothaveApprovedChanges_errorMessage();
		}
		
		if (!errorMsgDisplayed) {
			errorMsgDisplayed = true;

			showError(message, new BooleanCallback() {
				public void execute(Boolean value) {
					if (value) {
						errorMsgDisplayed = false;
						
						if (errorMessage.equalsIgnoreCase(rmsMsgBundle.servermessage_not_loggedin())) {
							Cookies.setCookie("AUTH_FAILED_MSG", errorMessage);
							Window.Location.assign(Window.Location.getHref());
						}
					}
				}
			});
		}
	}
}
