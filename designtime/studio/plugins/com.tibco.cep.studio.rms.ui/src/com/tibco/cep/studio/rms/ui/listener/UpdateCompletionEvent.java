package com.tibco.cep.studio.rms.ui.listener;

import java.util.EventObject;

public class UpdateCompletionEvent extends EventObject {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4817281888531229301L;

	/**
	 * Success/Failure
	 */
	private int updateEventType;
	
	
	/**
	 * 
	 * @param repoURL -> The repo URL
	 * @param username
	 * @param updateEventType
	 */
	public UpdateCompletionEvent(String repoURL, int updateEventType) {
		super(repoURL);
		this.updateEventType = updateEventType;
	}
	
	/**
	 * 
	 * @param repoURL -> The repo URL
	 */
	public UpdateCompletionEvent(String repoURL) {
		super(repoURL);
	}
	
	public static final int UPDATE_SUCCESS_EVENT = 1 << 1;
	
	public static final int UPDATE_FAILURE_EVENT = 1 << 2;

	public int getUpdateEventType() {
		return updateEventType;
	}
}