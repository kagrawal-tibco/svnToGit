package com.tibco.be.ws.notification;


/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 17/5/12
 * Time: 2:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class NotificationStatus {

	private boolean isSuccess = false;
	private String errorMessage = null;

	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String message) {
		errorMessage = message;
	}	
}
